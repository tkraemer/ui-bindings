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
package com.rcpcompany.uibindings.extests.manager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IUIBindingDecoratorExtenderDescriptor;

/**
 * Tests the decorator extenders from the manager.
 * 
 * @author Tonny Madsen, The RCP Company
 */
@RunWith(Parameterized.class)
public class ManagerDecoratorExtendersTest {

	private final IUIBindingDecoratorExtenderDescriptor myExtender;

	@Parameters
	public static Collection<Object[]> data() {
		final Collection<Object[]> l = new ArrayList<Object[]>();

		for (final IUIBindingDecoratorExtenderDescriptor p : IManager.Factory.getManager().getDecoratorExtenders()) {
			l.add(new Object[] { p });
		}

		return l;
	}

	public ManagerDecoratorExtendersTest(IUIBindingDecoratorExtenderDescriptor provider) {
		myExtender = provider;
	}

	/**
	 * Tests for {@link IManager#getProviders()}.
	 */
	@Test
	public void testExtender() {
		assertTrue(myExtender.getPriority() >= 0);
		assertNotNull(myExtender.getFactory());
		assertNotNull(myExtender.getFactory().getObject());
	}
}
