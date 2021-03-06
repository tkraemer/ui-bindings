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
package com.rcpcompany.uibindings.navigator.extests.manager;

import static com.rcpcompany.test.utils.BaseTestUtils.*;
import static org.junit.Assert.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibindings.navigator.IEditorInformation;
import com.rcpcompany.uibindings.navigator.IEditorPartDescriptor;
import com.rcpcompany.uibindings.navigator.INavigatorManager;
import com.rcpcompany.uibindings.navigator.extests.NavigatorTestUtils;
import com.rcpcompany.uibindings.navigator.internal.Activator;
import com.rcpcompany.utils.extensionpoints.CEObjectHolder;

/**
 * Tests the preferences for the registrered mode types.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class PreferenceTest {
	@Before
	public void before() {
		NavigatorTestUtils.resetAll();
	}

	/**
	 * Tests that the defaults and the current values are all correct.
	 */
	@Test
	public void testDefaults() {
		final IPreferenceStore ps = Activator.getDefault().getPreferenceStore();
		final INavigatorManager manager = INavigatorManager.Factory.getManager();

		for (final CEObjectHolder<EObject> pmt : manager.getPreferenceModelTypes()) {
			final IEditorInformation mt = manager.getEditorInformation(pmt.getObjectClass());

			final String c = mt.getModelType();
			final String def = ps.getDefaultString(c);
			if (def == null || def.length() == 0) {
				continue;
			}

			assertEquals(mt.getEditors().get(0).getId(), def);
			assertEquals(ps.getString(c), def);
			assertEquals(mt.getEditors().get(0), mt.getPreferredEditor());
		}
	}

	/**
	 * Tests that setting the preferred will change the preferences
	 */
	@Test
	public void testPreferredToPreference() {
		final IPreferenceStore ps = Activator.getDefault().getPreferenceStore();

		/*
		 * Find the model type with multiple editors.
		 */
		final IEditorInformation mt = NavigatorTestUtils.getMultipleEditorModelType();

		final IEditorPartDescriptor first = mt.getEditors().get(0);
		final IEditorPartDescriptor second = mt.getEditors().get(1);

		assertEquals(first, mt.getPreferredEditor());
		assertEquals(first.getId(), ps.getString(mt.getModelType()));

		assertNoLog(new Runnable() {
			@Override
			public void run() {
				mt.setPreferredEditor(second);
			}
		});

		assertEquals(second, mt.getPreferredEditor());
		assertEquals(second.getId(), ps.getString(mt.getModelType()));

		assertNoLog(new Runnable() {
			@Override
			public void run() {
				mt.setPreferredEditor(null);
			}
		});

		assertEquals(first, mt.getPreferredEditor());
		assertEquals(first.getId(), ps.getString(mt.getModelType()));
	}

	/**
	 * Tests that setting the preference will change the preferred
	 */
	@Test
	public void testPreferenceToPreferred() {
		final IPreferenceStore ps = Activator.getDefault().getPreferenceStore();

		/*
		 * Find the model type with multiple editors.
		 */
		final IEditorInformation mt = NavigatorTestUtils.getMultipleEditorModelType();

		final IEditorPartDescriptor first = mt.getEditors().get(0);
		final IEditorPartDescriptor second = mt.getEditors().get(1);

		assertEquals(first, mt.getPreferredEditor());
		assertEquals(first.getId(), ps.getString(mt.getModelType()));

		assertNoLog(new Runnable() {
			@Override
			public void run() {
				ps.setValue(mt.getModelType(), second.getId());
			}
		});

		assertEquals(second, mt.getPreferredEditor());
		assertEquals(second.getId(), ps.getString(mt.getModelType()));

		// Wrong value defaults to first..
		assertOneLog(new Runnable() {
			@Override
			public void run() {
				ps.setValue(mt.getModelType(), "aaa");
			}
		});

		assertEquals(first, mt.getPreferredEditor());
		assertEquals(first.getId(), ps.getString(mt.getModelType()));
	}
}
