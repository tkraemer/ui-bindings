package com.rcpcompany.uibindings.participants;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Interface used to initialize an object or a feature of an object when objects are created.
 * <p>
 * When an object is initialized, it is very important that all changes to the object as well as to
 * other object in the system, is made as {@link Command commands}. All needed commands must be
 * added with {IInitializerContext#addCommand(Command)}.
 * <p>
 * Please note that the object is <em>not</em> yet included in the containment tree when this method
 * is called.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public interface IInitializationParticipant {
	/**
	 * Initializes the object according to the specified context and facet.
	 * <p>
	 * The facet is either an {@link EClass} for a class-wide initialization or an
	 * {@link EStructuralFeature} for a feature-specific initialization.
	 * 
	 * @param context a context with the
	 * @param facet the facet - either {@link EClass} or {@link EStructuralFeature}
	 */
	void initialize(IInitializationParticipantContext context, Object facet);
}