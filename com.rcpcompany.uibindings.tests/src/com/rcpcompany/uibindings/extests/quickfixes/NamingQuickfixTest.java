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
package com.rcpcompany.uibindings.extests.quickfixes;

import static com.rcpcompany.test.utils.ui.UITestUtils.*;
import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibindings.IBindingContext;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IValueBinding;
import com.rcpcompany.uibindings.TextCommitStrategy;
import com.rcpcompany.uibindings.internal.bindingMessages.ValueBindingMessageImageDecorator;
import com.rcpcompany.uibindings.tests.shop.Contact;
import com.rcpcompany.uibindings.tests.shop.Country;
import com.rcpcompany.uibindings.tests.shop.Shop;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.tests.shop.ShopPackage;
import com.rcpcompany.uibindings.tests.utils.BaseUIBTestUtils;
import com.rcpcompany.uibindings.tests.utils.views.UIBTestView;
import com.rcpcompany.uibindings.utils.EditingDomainUtils;
import com.rcpcompany.uibindings.validators.EValidatorAdapter;
import com.rcpcompany.uibindings.validators.IValidatorAdapterManager;

/**
 * Tests that the correct nameing quickfixes are generated
 * <p>
 * Depends on:
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class NamingQuickfixTest {

	private static final int VD = 200;
	private Shop myShop;
	private UIBTestView myView;
	private Composite myBody;
	private Contact myContact;
	private Country myOldCountry;
	private IBindingContext myContext;
	private IValueBinding myBinding;
	private ValueBindingMessageImageDecorator myMessageDecorator;
	private IValidatorAdapterManager myValidatorManager;
	private Text myText;

	@Before
	public void setup() {
		BaseUIBTestUtils.resetAll();
		IManager.Factory.getManager().setTextCommitStrategy(TextCommitStrategy.ON_MODIFY);
		IManager.Factory.getManager().setValidationDelay(VD);
		IManager.Factory.getManager().setEditCellSingleClick(false);

		myShop = ShopFactory.eINSTANCE.getShop(EditingDomainUtils.getEditingDomain());
		myContact = myShop.getContacts().get(0);
		myOldCountry = myContact.getCountry();

		myView = BaseUIBTestUtils.createUIBTestView(this);
		myBody = myView.getBody();

		myText = new Text(myBody, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		myText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		myValidatorManager = IValidatorAdapterManager.Factory.getManager();

		myContext = IBindingContext.Factory.createContext(myView.getScrolledForm());
		myBinding = myContext.addBinding(myText, myContact, ShopPackage.Literals.CONTACT__COUNTRY).validValues(myShop,
				ShopPackage.Literals.SHOP__COUNTRIES);

		myContext.finish();
		yield();

		myMessageDecorator = myBinding.getService(ValueBindingMessageImageDecorator.class);

		myValidatorManager.addRoot(myShop, new EValidatorAdapter());
	}

	@After
	public void disposeView() {
		if (myView != null) {
			myView.getSite().getPage().hideView(myView);
		}
	}

	@After
	public void after() {
		myContact.setCountry(myOldCountry);
		myValidatorManager.removeRoot(myShop);
	}

	@Test
	public void testValue() {
		assertNotNull(myMessageDecorator.getQuickfixes());

		assertEquals(0, myMessageDecorator.getQuickfixes().size());

		// No quickfixes
		myText.setText("n");
		sleep(VD + 200);
		assertEquals(0, myMessageDecorator.getQuickfixes().size());

		// Case
		myText.setText("no");
		sleep(VD + 200);
		assertEquals(1, myMessageDecorator.getQuickfixes().size());
		assertEquals("Change case", myMessageDecorator.getQuickfixes().get(0).getLabel());

		// extra letter
		myText.setText("NOt");
		sleep(VD + 200);
		assertEquals(1, myMessageDecorator.getQuickfixes().size());
		assertEquals("Remove extra letter", myMessageDecorator.getQuickfixes().get(0).getLabel());

		// extra letter
		myText.setText("NxO");
		sleep(VD + 200);
		assertEquals(1, myMessageDecorator.getQuickfixes().size());
		assertEquals("Remove extra letter", myMessageDecorator.getQuickfixes().get(0).getLabel());

		// missing letter
		myText.setText("O");
		sleep(VD + 200);
		assertEquals(1, myMessageDecorator.getQuickfixes().size());
		assertEquals("Add missing letter", myMessageDecorator.getQuickfixes().get(0).getLabel());

		// transposed
		myText.setText("ON");
		sleep(VD + 200);
		assertEquals(1, myMessageDecorator.getQuickfixes().size());
		assertEquals("Transposed letters", myMessageDecorator.getQuickfixes().get(0).getLabel());

		// no quick fixes
		myText.setText("NO");
		sleep(VD + 200);
		assertEquals(0, myMessageDecorator.getQuickfixes().size());
	}
}
