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
package com.rcpcompany.uibindings.shop.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.rcpcompany.uibindings.tests.shop.Shop;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.tests.shop.ShopPackage;
import com.rcpcompany.uibindings.utils.EditingDomainUtils;
import com.rcpcompany.uibindings.utils.IBindingContextSelectionProvider;
import com.rcpcompany.uibindings.utils.IDnDSupport;
import com.rcpcompany.uibindings.utils.IFormCreator;
import com.rcpcompany.uibindings.utils.ITableCreator;

/**
 * The inventory view.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class GroupView extends ViewPart {

	private IFormCreator myForm;

	@Override
	public void createPartControl(Composite parent) {
		final Shop shop = ShopFactory.eINSTANCE.getShop(EditingDomainUtils.getEditingDomain());
		myForm = IFormCreator.Factory.createScrolledForm(shop, parent, "Group");

		final IFormCreator[] columns = myForm.addColumns(true, true);
		final IFormCreator groupColumn = columns[0];
		final IFormCreator itemColumn = columns[1];

		final ITableCreator groupTable = groupColumn.addTableCreator(ShopPackage.Literals.SHOP__SHOP_GROUPS, true,
				ITableCreator.FILTER);
		groupTable.addColumn("name(w=10em)");

		final IFormCreator itemSection = itemColumn.addSection("Items", groupTable.getBinding().getSingleSelection());
		final ITableCreator itemTable = itemSection.addTableCreator(ShopPackage.Literals.SHOP_ITEM_GROUP__ITEMS, true,
				SWT.NONE);
		itemTable.addColumn("name(w=20em)");

		myForm.finish();
		IBindingContextSelectionProvider.Factory.adapt(myForm.getContext(), getSite());
		IDnDSupport.Factory.installOn(myForm.getContext());
	}

	@Override
	public void setFocus() {

	}

	private void initializeToolBar() {
		getViewSite().getActionBars().getToolBarManager();
	}
}
