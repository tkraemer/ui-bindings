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

/**
 * Participant to take part in delete operations.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public interface IDeleteParticipant {
	/**
	 * Returns whether an element (in the specified context) can be be deleted.
	 * 
	 * @param context the context for the operation
	 * @return <code>true</code> if the element can be deleted
	 */
	boolean canDelete(IDeleteParticipantContext context);

	/**
	 * Called just before an element (in the specified context) is in fact deleted.
	 * 
	 * @param context the context for the operation
	 */
	void preDelete(IDeleteParticipantContext context);

	/**
	 * Called just after an element (in the specified context) has been deleted.
	 * 
	 * @param context the context for the operation
	 */
	void postDelete(IDeleteParticipantContext context);
}
