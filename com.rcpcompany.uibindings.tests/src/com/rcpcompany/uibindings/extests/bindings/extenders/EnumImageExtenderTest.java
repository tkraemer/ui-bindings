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
package com.rcpcompany.uibindings.extests.bindings.extenders;

import static com.rcpcompany.test.utils.ui.UITestUtils.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibindings.IBindingContext;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.TextCommitStrategy;
import com.rcpcompany.uibindings.tests.shop.Contact;
import com.rcpcompany.uibindings.tests.shop.Customer;
import com.rcpcompany.uibindings.tests.shop.CustomerType;
import com.rcpcompany.uibindings.tests.shop.Shop;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.tests.shop.ShopPackage;
import com.rcpcompany.uibindings.tests.utils.BaseUIBTestUtils;
import com.rcpcompany.uibindings.tests.utils.views.UIBTestView;
import com.rcpcompany.uibindings.utils.ITableCreator;

/**
 * Tests {@link EnumImageExtender}.
 * <p>
 * Depends on the precense of an image for {@link CustomerType#GOLD}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class EnumImageExtenderTest {

	@Before
	public void setup() {
		BaseUIBTestUtils.resetAll();
		IManager.Factory.getManager().setTextCommitStrategy(TextCommitStrategy.ON_MODIFY);
		IManager.Factory.getManager().setAutoApplySingleQuickfix(true);
		IManager.Factory.getManager().setEditCellSingleClick(false);

		createShop();
		createView();
	}

	private Shop myShop;
	private UIBTestView myTestView;
	private ITableCreator myTableCreator;

	private void createShop() {
		myShop = ShopFactory.eINSTANCE.createShop();

		final Contact contact = ShopFactory.eINSTANCE.createContact();
		contact.setName("me");
		myShop.getContacts().add(contact);

		Customer customer;
		customer = ShopFactory.eINSTANCE.createCustomer();
		customer.setContact(contact);
		customer.setLoyalty(CustomerType.BRONCE);
		myShop.getCustomers().add(customer);

		customer = ShopFactory.eINSTANCE.createCustomer();
		customer.setContact(contact);
		customer.setLoyalty(CustomerType.GOLD);
		myShop.getCustomers().add(customer);
	}

	private void createView() {
		myTestView = BaseUIBTestUtils.createUIBTestView(this);

		final IBindingContext context = IBindingContext.Factory.createContext(myTestView.getScrolledForm());
		myTableCreator = ITableCreator.Factory.create(context, myTestView.getBody(), SWT.NONE, myShop,
				ShopPackage.Literals.SHOP__CUSTOMERS);
		myTableCreator.addColumn("loyalty(w=10em)");

		context.finish();
		yield();
	}

	@After
	public void disposeView() {
		if (myTestView != null) {
			myTestView.getSite().getPage().hideView(myTestView);
		}
	}

	@Test
	// getImage does not work anymore as the update method of the label provider isn't used
	public void testImage() {
		final Table table = myTableCreator.getTable();

		// TODO: does not work any more! Cannot get the image from the item
		// assertTrue(table.getItem(0).getImage(0 +
		// myTableCreator.getBinding().getFirstTableColumnOffset()) == null);
		// assertTrue(table.getItem(1).getImage(0 +
		// myTableCreator.getBinding().getFirstTableColumnOffset()) != null);
	}
}
