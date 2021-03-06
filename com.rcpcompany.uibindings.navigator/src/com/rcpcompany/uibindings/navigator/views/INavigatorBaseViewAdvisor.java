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
package com.rcpcompany.uibindings.navigator.views;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.ui.IViewSite;

import com.rcpcompany.uibindings.IDisposable;

/**
 * Advisor for {@link NavigatorBaseView}
 * 
 * @author Tonny Madsen, The RCP Company
 */
public interface INavigatorBaseViewAdvisor extends IDisposable {
	/**
	 * Sets the site of the view on this advisor.
	 * <p>
	 * Can be used to monitor the selection or other view based services.
	 * 
	 * @param site the view site
	 */
	void setSite(IViewSite site);

	/**
	 * Returns the root elements of the navigator
	 * 
	 * @return the root elements
	 */
	IObservableList getRootElements();

	/**
	 * Returns the navigator (tree) ID.
	 * <p>
	 * Used to control the items that are available in the navigator. See the
	 * <code>com.rcpcompany.uibindings.uiBindings</code> extension point.
	 * 
	 * @return the navigator ID
	 */
	String getTreeID();

	/**
	 * Returns whether the navigator tree is readonly.
	 * <p>
	 * Defaults to <code>false</code>.
	 * 
	 * @return <code>true</code> if the tree should be readonly
	 */
	boolean isTreeReadonly();

	/**
	 * Returns whether to use label decorators for the navigator.
	 * <p>
	 * Defaults to <code>true</code>.
	 * 
	 * @return <code>true</code> if using label decorator
	 */
	boolean useLabelDecoration();

	/**
	 * Returns whether to use tree filter for the navigator.
	 * <p>
	 * Defaults to <code>true</code>.
	 * 
	 * @return <code>true</code> if using tree filter
	 */
	boolean useTreeFilter();
}
