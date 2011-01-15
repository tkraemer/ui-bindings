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
package com.rcpcompany.uibindings.participants;

import org.eclipse.emf.ecore.EObject;

/**
 * Context for various operations in {@link IDeleteParticipant}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public interface IDeleteParticipantContext {
	/**
	 * The element to delete.
	 * 
	 * @return the element
	 */
	EObject getElement();
}
