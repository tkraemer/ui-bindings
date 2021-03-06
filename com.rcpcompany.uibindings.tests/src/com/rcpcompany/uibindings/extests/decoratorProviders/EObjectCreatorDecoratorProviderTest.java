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
package com.rcpcompany.uibindings.extests.decoratorProviders;

import static com.rcpcompany.test.utils.BaseTestUtils.*;
import static com.rcpcompany.test.utils.ui.UITestUtils.*;
import static com.rcpcompany.uibindings.tests.utils.BaseUIBTestUtils.*;
import static org.junit.Assert.*;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibindings.IBindingContext;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IValueBinding;
import com.rcpcompany.uibindings.TextCommitStrategy;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.tests.shop.ShopItem;
import com.rcpcompany.uibindings.tests.shop.ShopItemDescription;
import com.rcpcompany.uibindings.tests.shop.ShopItemInformation;
import com.rcpcompany.uibindings.tests.shop.ShopItemURL;
import com.rcpcompany.uibindings.tests.shop.ShopPackage;
import com.rcpcompany.uibindings.tests.utils.BaseUIBTestUtils;
import com.rcpcompany.uibindings.tests.utils.views.UIBTestView;

/**
 * This class tests the use of the eobjectCreator binding provider.
 * 
 * @author Tonny Madsen, The RCP Company
 */

public class EObjectCreatorDecoratorProviderTest {
	protected ShopItem myShopItem1;
	protected ShopItem myShopItem2;
	protected ShopItem myShopItem3;

	protected ShopItemDescription myDescription;

	protected UIBTestView myView;
	protected Composite myBody;
	protected Combo myCombo1;
	protected Combo myCombo2;
	protected Combo myCombo3;

	protected IBindingContext myContext;
	protected IValueBinding myTest1Binding;
	protected IValueBinding myTest2Binding;
	protected IValueBinding myTest3Binding;

	@Before
	public void setup() {
		BaseUIBTestUtils.resetAll();
		IManager.Factory.getManager().setTextCommitStrategy(TextCommitStrategy.ON_MODIFY);
		IManager.Factory.getManager().setEditCellSingleClick(false);

		createModel();
		createView();
		bindUI();
	}

	protected void createModel() {
		myShopItem1 = ShopFactory.eINSTANCE.createShopItem();

		myShopItem2 = ShopFactory.eINSTANCE.createShopItem();
		myDescription = ShopFactory.eINSTANCE.createShopItemDescription();
		myShopItem2.setInformation(myDescription);

		myShopItem3 = ShopFactory.eINSTANCE.createShopItem();
	}

	protected void createView() {
		myView = BaseUIBTestUtils.createUIBTestView(this);
		myBody = myView.getBody();

		myCombo1 = new Combo(myBody, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		myCombo1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		myCombo2 = new Combo(myBody, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		myCombo2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		myCombo3 = new Combo(myBody, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		myCombo3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	}

	@After
	public void disposeView() {
		if (myView != null) {
			myView.getSite().getPage().hideView(myView);
		}
	}

	protected void bindUI() {
		assertOneLog(new Runnable() {
			@Override
			public void run() {
				final IObservableList list = WritableList.withElementType(EClass.class);
				list.add(ShopPackage.Literals.SHOP_ITEM_DESCRIPTION);
				list.add(ShopPackage.Literals.SHOP_ITEM_URL);

				myContext = IBindingContext.Factory.createContext(myView.getScrolledForm());
				myTest1Binding = myContext
						.addBinding(myCombo1, myShopItem1, ShopPackage.Literals.SHOP_ITEM__INFORMATION)
						.type("eobjectCreator").validValues(list);
				myTest2Binding = myContext
						.addBinding(myCombo2, myShopItem2, ShopPackage.Literals.SHOP_ITEM__INFORMATION)
						.type("eobjectCreator").validValues(list);
				myTest3Binding = myContext.addBinding(myCombo3, myShopItem3,
						ShopPackage.Literals.SHOP_ITEM__INFORMATION).type("eobjectCreator");

				myContext.finish();
				yield();
			}
		});
	}

	final protected String[] options = new String[] { "Shop Item Description", "Shop Item URL", "" };

	@Test
	public void test1() {
		assertNoLog(new Runnable() {
			@Override
			public void run() {
				assertEquals(false, myTest1Binding.getStaticDataType().isRequired());
				assertEquals(false, myTest1Binding.getModelFeature().isRequired());
				testUIValidList(myTest1Binding, options[0], options[1], options[2]);
				yield();
				assertEquals("", myCombo1.getText());
				assertArrayEquals(options, myCombo1.getItems());

				myCombo1.setText(options[0]);
				yield();
				final ShopItemInformation description = myShopItem1.getInformation();
				assertNotNull(description);
				assertTrue(description instanceof ShopItemDescription);

				myCombo1.setText(options[1]);
				yield();
				final ShopItemInformation url = myShopItem1.getInformation();
				assertNotNull(url);
				assertTrue(url instanceof ShopItemURL);

				myCombo1.setText(options[0]);
				yield();
				assertEquals(description, myShopItem1.getInformation());

				myCombo1.setText("");
				yield();
				assertEquals(null, myShopItem1.getInformation());
			}
		});
	}

	@Test
	public void test2() {
		assertNoLog(new Runnable() {
			@Override
			public void run() {
				assertEquals(false, myTest2Binding.getStaticDataType().isRequired());
				assertEquals(false, myTest2Binding.getModelFeature().isRequired());
				testUIValidList(myTest2Binding, options[0], options[1], options[2]);
				yield();
				assertEquals(options[0], myCombo2.getText());
				assertArrayEquals(options, myCombo2.getItems());

				myCombo2.setText(options[1]);
				yield();
				final ShopItemInformation url = myShopItem2.getInformation();
				assertNotNull(url);
				assertTrue(url instanceof ShopItemURL);

				myCombo2.setText(options[0]);
				yield();
				assertEquals(myDescription, myShopItem2.getInformation());

				myCombo2.setText(options[1]);
				yield();
				assertEquals(url, myShopItem2.getInformation());

				myCombo2.setText("");
				yield();
				assertEquals(null, myShopItem2.getInformation());
			}
		});
	}

	@Test
	public void test3() {
		assertNoLog(new Runnable() {
			@Override
			public void run() {
				yield();
				// widget should be red as there are no valid list
				assertEquals(myCombo3.getDisplay().getSystemColor(SWT.COLOR_RED), myCombo3.getBackground());
			}
		});
	}
}
