package com.rcpcompany.uibindings;

import org.eclipse.swt.widgets.Control;

/**
 * This factory interface is used to create preferred {@link Control Controls} for
 * {@link IValueBinding}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public interface IControlFactory {
	/**
	 * Creates and returns a new {@link Control}.
	 * 
	 * @param context the context for the new control
	 * 
	 * @return the created control
	 */
	Control create(IControlFactoryContext context);
}
