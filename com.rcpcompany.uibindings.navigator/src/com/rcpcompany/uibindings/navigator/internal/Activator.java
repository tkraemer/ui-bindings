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
package com.rcpcompany.uibindings.navigator.internal;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	/**
	 * The plug-in ID.
	 */
	public static final String ID = "com.rcpcompany.uibindings.navigator";

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/**
	 * <code>true</code> if tracing the life-cycle of editor parts.
	 */
	public boolean TRACE_EDITOR_PARTS_LIFECYCLE = false;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		if (isDebugging()) {
			TRACE_EDITOR_PARTS_LIFECYCLE = Boolean.parseBoolean(Platform.getDebugOption(ID
					+ "/trace/EditorParts/LifeCycle")); //$NON-NLS-1$
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	private ResourceManager myResources;

	/**
	 * Returns a local resource manager for use in this plug-in.
	 * 
	 * @return the manager
	 */
	public ResourceManager getResourceManager() {
		if (myResources == null) {
			myResources = new LocalResourceManager(JFaceResources.getResources());
		}
		return myResources;
	}
}
