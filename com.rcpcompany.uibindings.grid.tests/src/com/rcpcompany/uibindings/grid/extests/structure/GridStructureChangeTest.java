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
package com.rcpcompany.uibindings.grid.extests.structure;

import static com.rcpcompany.test.utils.BaseTestUtils.*;
import static com.rcpcompany.test.utils.ui.UITestUtils.*;
import static com.rcpcompany.uibindings.grid.extests.GridBaseTestUtils.*;
import static org.junit.Assert.*;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibinding.tests.model.TestGrid;
import com.rcpcompany.uibinding.tests.model.TestGridCell;
import com.rcpcompany.uibinding.tests.model.TestGridColumn;
import com.rcpcompany.uibinding.tests.model.TestGridRow;
import com.rcpcompany.uibinding.tests.model.TestModelFactory;
import com.rcpcompany.uibinding.tests.model.TestModelPackage;
import com.rcpcompany.uibindings.IBindingContext;
import com.rcpcompany.uibindings.grid.IGridBinding;
import com.rcpcompany.uibindings.grid.IGridBindingCellInformation;
import com.rcpcompany.uibindings.grid.IGridBindingColumnInformation;
import com.rcpcompany.uibindings.grid.IGridBindingRowInformation;
import com.rcpcompany.uibindings.grid.extests.models.TestGridGridModel;
import com.rcpcompany.uibindings.tests.utils.BaseUIBTestUtils;
import com.rcpcompany.uibindings.tests.utils.views.UIBTestView;
import com.rcpcompany.uibindings.utils.EditingDomainUtils;

/**
 * Test of proper handling of changes in the structure of a grid:
 * <ul>
 * <li>add and removal of columns</li>
 * <li>add and removal of rows</li>
 * </ul>
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class GridStructureChangeTest {

	private UIBTestView myView;
	private Grid myGrid;
	private IGridBinding myGridBinding;

	private TestGrid myTestGrid;
	private TestGridGridModel myModel;

	@Before
	public void setup() {
		BaseUIBTestUtils.resetAll();

		createModel();
		createView();
	}

	private void createModel() {
		myTestGrid = createTestGrid();
		myModel = new TestGridGridModel(EditingDomainUtils.getEditingDomain(), myTestGrid,
				TestModelPackage.Literals.TEST_GRID_CELL__DETAILS, null);
	}

	private void createView() {
		myView = BaseUIBTestUtils.createUIBTestView(this);

		myGrid = new Grid(myView.getBody(), SWT.NONE);
		final IBindingContext context = IBindingContext.Factory.createContext(myView.getBody());
		myGridBinding = IGridBinding.Factory.createGrid(context, myGrid, myModel);
		context.finish();
		yield();

		myView.getBody().layout();

		yield();
	}

	@After
	public void disposeView() {
		if (myView != null) {
			myView.getSite().getPage().hideView(myView);
		}
	}

	@Test
	public void testRemoveAddColumn() {
		testCurrentSize();

		assertNoLog(new Runnable() {
			@Override
			public void run() {
				myTestGrid.getColumns().remove(1);
				yield();
			}
		});

		testCurrentSize();

		assertNoLog(new Runnable() {
			@Override
			public void run() {
				final TestGridColumn column = TestModelFactory.eINSTANCE.createTestGridColumn();
				column.setName("new");
				for (final TestGridRow row : myTestGrid.getRows()) {
					final TestGridCell cell = TestModelFactory.eINSTANCE.createTestGridCell();
					cell.setDetails(column.getName() + "," + row.getNumber());
					cell.setPrice(row.getNumber());
					cell.setColumn(column);
				}
				myTestGrid.getColumns().add(0, column);
				yield();
			}
		});

		testCurrentSize();
		final GridItem item = myGrid.getItem(0);
		assertEquals("new,1", item.getText(0));
	}

	@Test
	public void testRemoveAddRow() {
		testCurrentSize();

		assertNoLog(new Runnable() {
			@Override
			public void run() {
				myTestGrid.getRows().remove(1);
				for (final TestGridColumn c : myTestGrid.getColumns()) {
					c.getCells().remove(1);
				}
				yield();
			}
		});

		testCurrentSize();

		assertNoLog(new Runnable() {
			@Override
			public void run() {
				final TestGridRow row = TestModelFactory.eINSTANCE.createTestGridRow();
				row.setNumber(10);
				for (final TestGridColumn column : myTestGrid.getColumns()) {
					final TestGridCell cell = TestModelFactory.eINSTANCE.createTestGridCell();
					cell.setDetails(column.getName() + "," + row.getNumber());
					cell.setPrice(row.getNumber());
					column.getCells().add(0, cell);
				}
				myTestGrid.getRows().add(0, row);
				yield();
			}
		});

		testCurrentSize();
		final GridItem item = myGrid.getItem(0);
		assertEquals("First,10", item.getText(0));
	}

	public void testCurrentSize() {
		final int columns = myTestGrid.getColumns().size();
		final int rows = myTestGrid.getRows().size();

		assertEquals(columns, myGrid.getColumnCount());
		assertEquals(columns + 1, myGridBinding.getColumns().size());
		assertEquals(rows, myGrid.getItemCount());
		assertEquals(rows + 1, myGridBinding.getRows().size());
		for (final IGridBindingColumnInformation c : myGridBinding.getColumns().values()) {
			assertEquals(rows + 1, c.getRowCells().size());
		}
		for (final IGridBindingRowInformation r : myGridBinding.getRows().values()) {
			assertEquals(columns + 1, r.getColumnCells().size());
		}

		for (int column = 0; column < columns; column++) {
			for (int row = 0; row < rows; row++) {
				final TestGridCell cell = myTestGrid.getColumns().get(column).getCells().get(row);

				final GridItem item = myGrid.getItem(row);
				assertEquals(cell.getDetails(), item.getText(column));
			}
		}
	}

	public void testOneGetCell(int x, int y, String content) {
		final IGridBindingCellInformation cell = myGridBinding.getCell(x, y);
		if (content == null) {
			assertEquals(null, cell);
		} else {
			assertNotNull(cell);
			final String text = cell.getDisplayText();
			assertEquals(content, text);
		}
	}
}
