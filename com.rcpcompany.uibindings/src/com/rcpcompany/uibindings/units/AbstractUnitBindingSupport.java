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
package com.rcpcompany.uibindings.units;

import java.util.ArrayList;
import java.util.List;

import com.rcpcompany.utils.logging.LogUtils;

/**
 * Abstract base class for {@link IUnitBindingSupport}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public abstract class AbstractUnitBindingSupport implements IUnitBindingSupport {
	@Override
	public abstract double getFactor(IUnitBindingSupportContext context);

	@Override
	public abstract String getUnitDescription(IUnitBindingSupportContext context);

	/**
	 * Fires a {@link IUnitBindingSupportListener#unitsChanged()} to all added listeners.
	 */
	public void fireUnitsChanged() {
		if (myListeners == null) return;
		for (final IUnitBindingSupportListener l : myListeners) {

			try {
				l.unitsChanged();
			} catch (final Exception ex) {
				LogUtils.error(l, ex);
			}
		}
	}

	/**
	 * Listeners for this support object.
	 */
	private List<IUnitBindingSupportListener> myListeners = null;

	@Override
	public void addListener(IUnitBindingSupportListener listener) {
		if (myListeners == null) {
			myListeners = new ArrayList<IUnitBindingSupportListener>();
		}
		if (myListeners.contains(listener)) return;
		myListeners.add(listener);
	}

	@Override
	public void removeListener(IUnitBindingSupportListener listener) {
		if (myListeners == null) return;
		myListeners.remove(listener);
		if (myListeners.size() == 0) {
			myListeners = null;
		}
	}
}
