/*******************************************************************************
 * Copyright (c) 2006-2013 The RCP Company and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The RCP Company - initial API and implementation
 *******************************************************************************/
package com.rcpcompany.uibindings.extests.bindings;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EventObject;
import java.util.List;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.rcpcompany.uibindings.Constants;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.TextCommitStrategy;
import com.rcpcompany.uibindings.tests.shop.Contact;
import com.rcpcompany.uibindings.tests.shop.Country;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.tests.utils.BaseUIBTestUtils;
import com.rcpcompany.uibindings.tests.utils.views.UIBTestView;
import com.rcpcompany.uibindings.utils.EditingDomainUtils;
import com.rcpcompany.uibindings.utils.IFormCreator;

/**
 * Tests that the creation of a new context does not commit any changes to the model.
 * <p>
 * Parameterized by the control class and the commit strategy
 * <p>
 * Depends on:
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author Tonny Madsen, The RCP Company
 */
@RunWith(Parameterized.class)
public class ContextNoCommitTest<C extends Control> implements CommandStackListener {
	@Parameters
	public static Collection<Object[]> data() {
		final List<Object[]> l = new ArrayList<Object[]>();
		final Class<?>[] controls = new Class<?>[] { Text.class, StyledText.class, Combo.class, CCombo.class };
		final TextCommitStrategy[] strategies = TextCommitStrategy.values();

		for (final Class<?> c : controls) {
			for (final TextCommitStrategy s : strategies) {
				l.add(new Object[] { c, s });
			}
		}

		return l;
	}

	private Country myCountry;
	private Contact myContact1;

	private UIBTestView myView;

	protected final Class<C> myControlClass;
	private final TextCommitStrategy myStrategy;

	private CommandStack myCommandStack;

	public ContextNoCommitTest(Class<C> cls, TextCommitStrategy strategy) {
		myControlClass = cls;
		myStrategy = strategy;
	}

	@Before
	public void before() {
		BaseUIBTestUtils.resetAll();
		IManager.Factory.getManager().setTextCommitStrategy(myStrategy);
		IManager.Factory.getManager().setEditCellSingleClick(false);

		createShop();

		myCommandStack = EditingDomainUtils.getCommandStack();
		myCommandStack.addCommandStackListener(this);

		myView = BaseUIBTestUtils.createUIBTestView(this);

		myView.getSite().getPage().activate(myView);
	}

	@After
	public void after() {
		myCommandStack.removeCommandStackListener(this);
	}

	/**
	 * Creates the shop itself
	 */
	public void createShop() {
		myCountry = ShopFactory.eINSTANCE.createCountry();
		myCountry.setName("1");

		myContact1 = ShopFactory.eINSTANCE.createContact();
		myContact1.setName("NN 1");
		myContact1.setCountry(myCountry);
	}

	/**
	 * Creates the view
	 */
	@Test
	public void createView() {
		final IFormCreator creator = myView.createFormCreator(myContact1);
		creator.addField("country").arg(Constants.ARG_PREFERRED_CONTROL, myControlClass.getName());
		creator.finish();
	}

	@After
	public void disposeView() {
		if (myView != null) {
			myView.getSite().getPage().hideView(myView);
		}
	}

	@Override
	public void commandStackChanged(EventObject event) {
		fail("Control class '" + myControlClass + "' resulted in command stack changed");
	}
}
