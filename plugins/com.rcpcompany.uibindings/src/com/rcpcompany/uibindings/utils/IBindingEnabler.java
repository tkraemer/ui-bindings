package com.rcpcompany.uibindings.utils;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.rcpcompany.uibindings.IDisposable;
import com.rcpcompany.uibindings.IValueBinding;
import com.rcpcompany.uibindings.UIBindingsEMFObservables;
import com.rcpcompany.uibindings.internal.utils.BindingEnabler;

/**
 * This interface provides a number of simpole interfaces that can be used to control the
 * "enablement" of the controls of exiting bindings.
 * <p>
 * The corresponding binding context need not be finished.
 * <p>
 * An example: assume we have a form for some object with a <code>unit</code> attribute with type
 * <code>WeightUnit<code>.
 * And we have an already created binding, we to make enabled only when the <code>unit</code>
 * attribute has the values <code>KG</code> or <code>TONNE</code>, then the following code can be
 * used.
 * 
 * <pre>
 * IFormCreator myForm;
 * IValueBinding myBinding;
 * 
 * IBindingEnabler enabler = IBindingEnabler.Factory.add(myBinding, myForm.getObservableValue(&quot;unit&quot;), WeightUnit.KG,
 * 		WeightUnit.TONNE);
 * </pre>
 * 
 * @author Tonny Madsen, The RCP Company
 */
public interface IBindingEnabler extends IDisposable {
	/**
	 * Factory methods.
	 */
	public static final class Factory {
		private Factory() {
		}

		/**
		 * Constructs and returns a new binding enabler for the specified feature of the observable
		 * value.
		 * 
		 * @param b the binding that "owns" the control that are handled with this enabler
		 * @param ov the observable value
		 * @param feature the feature of the observable value
		 * @param values the values that will mean the control is enabled
		 * @return the enabler
		 */
		public static IBindingEnabler add(IValueBinding b, IObservableValue ov, EStructuralFeature feature,
				Object... values) {
			return new BindingEnabler(b, UIBindingsEMFObservables.observeDetailValue(ov, feature), values);
		}

		/**
		 * Constructs and returns a new binding enabler for the specified observable value.
		 * 
		 * @param b the binding that "owns" the control that are handled with this enabler
		 * @param ov the observable value
		 * @param values the values that will mean the control is enabled
		 * @return the enabler
		 */
		public static IBindingEnabler add(IValueBinding b, IObservableValue ov, Object... values) {
			return new BindingEnabler(b, ov, values);
		}

	}

}
