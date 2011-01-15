/*******************************************************************************
 * Copyright (c) 2017, 2011 The RCP Company and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The RCP Company - initial API and implementation
 *******************************************************************************/
package com.rcpcompany.uibindings.extests;

import static com.rcpcompany.uibindings.extests.BaseTestUtils.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.ISourceProvider;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.ui.services.ISourceProviderService;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibindings.internal.Activator;

/**
 * Tests of all used property names in the expression related extension points
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class UsedPropertyNamesTest {
	/**
	 * List with all defined property names (UI Bindings, Eclipse, etc)
	 */
	private final List<String> myNames = new ArrayList<String>();

	@Before
	public void before() {
		resetAll();

		for (final IConfigurationElement ce : Platform.getExtensionRegistry().getConfigurationElementsFor(
				"org.eclipse.core.expressions.propertyTesters")) {
			if (!ce.getName().equals("propertyTester")) {
				continue;
			}

			final String ns = ce.getAttribute("namespace");
			for (final String p : ce.getAttribute("properties").split(",")) {
				final String prop = ns + "." + p;
				myNames.add(prop);
			}
		}

		final IServiceLocator locator = PlatformUI.getWorkbench();

		final ISourceProviderService sourceProviders = (ISourceProviderService) locator
				.getService(ISourceProviderService.class);

		for (final ISourceProvider sp : sourceProviders.getSourceProviders()) {
			if (!sp.getClass().getName().startsWith(Activator.ID)) {
				continue;
			}
			for (final String n : sp.getProvidedSourceNames()) {
				myNames.add(n);
			}
		}
	}

	/**
	 * Tests that the used sources in the uibinding plug-ins are all legal.
	 */
	@Test
	public void testUsedSourcesUIHandlers() {
		testUsedSources("org.eclipse.ui.handlers");
	}

	/**
	 * Tests that the used sources in the uibinding plug-ins are all legal.
	 */
	@Test
	public void testUsedSourcesUIMenus() {
		testUsedSources("org.eclipse.ui.menus");
	}

	/**
	 * Tests that the used sources in the uibinding plug-ins are all legal.
	 */
	@Test
	public void testUsedSourcesExpressionsDefinitions() {
		testUsedSources("org.eclipse.core.expressions.definitions");
	}

	private void testUsedSources(String epName) {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		for (final IConfigurationElement ce : registry.getConfigurationElementsFor(epName)) {
			if (!ce.getContributor().getName().startsWith(Activator.ID)) {
				continue;
			}
			testUsedSourcesElement(ce);
		}
	}

	private void testUsedSourcesElement(IConfigurationElement ce) {
		if (ce.getName().equals("test")) {
			final String attribute = ce.getAttribute("property");
			if (attribute != null && attribute.length() != 0) {
				assertTrue(attribute + " not present", myNames.contains(attribute));
			}
		}
		for (final IConfigurationElement cce : ce.getChildren()) {
			testUsedSourcesElement(cce);
		}
	}

}
