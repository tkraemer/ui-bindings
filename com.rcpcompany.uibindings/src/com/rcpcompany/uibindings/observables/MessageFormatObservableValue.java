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
package com.rcpcompany.uibindings.observables;

import java.text.MessageFormat;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Assert;

import com.rcpcompany.uibindings.model.utils.BasicUtils;

/**
 * A decorating observable value that formats the decorated value with a {@link MessageFormat}.
 * <p>
 * When the message is <code>null</code> will the decorated valued just be copied through.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class MessageFormatObservableValue extends AbstractObservableValue {

	/**
	 * The current format - possible <code>null</code>.
	 */
	private String myFormat;

	/**
	 * The observable to be decorated.
	 */
	private final IObservableValue myDecorated;

	/**
	 * Constructs and returns a new observable value.
	 * 
	 * @param decorated the decorated observable value
	 * @param format the initial format - possibly <code>null</code>
	 */
	public MessageFormatObservableValue(IObservableValue decorated, String format) {
		super();
		myDecorated = decorated;
		Assert.isTrue(decorated.getValueType() == String.class);
		setMessageFormat(format);
		if (format == null) {
			updateValue();
		}
	}

	@Override
	public synchronized void dispose() {
		if (hasListeners()) {
			lastListenerRemoved();
		}
		super.dispose();
	}

	private final IChangeListener myChangeListener = new IChangeListener() {
		@Override
		public void handleChange(ChangeEvent event) {
			updateValue();
		}
	};

	@Override
	protected void firstListenerAdded() {
		myDecorated.addChangeListener(myChangeListener);
		super.firstListenerAdded();
	}

	@Override
	protected void lastListenerRemoved() {
		myDecorated.removeChangeListener(myChangeListener);
		super.lastListenerRemoved();
	}

	/**
	 * Sets the format to use.
	 * 
	 * @param format the new format
	 */
	public void setMessageFormat(String format) {
		if (format == null ? myFormat == null : format.equals(myFormat)) return;
		myFormat = format;

		updateValue();
	}

	@Override
	public Object getValueType() {
		return String.class;
	}

	@Override
	protected Object doGetValue() {
		return myCurrectValue;
	}

	@Override
	protected void doSetValue(Object value) {
		if (myFormat != null)
			throw new IllegalStateException(getClass().getSimpleName() + " has format and can thus not be set");
		myDecorated.setValue(value);
	}

	/**
	 * The current decorated value.
	 */
	public String myCurrectValue;

	/**
	 * Updates the current value and fires an event if needed.
	 */
	protected void updateValue() {
		String decoratedValue = null;
		final Object v = myDecorated.getValue();
		final String origValue = v != null ? v.toString() : "";
		if (myFormat == null) {
			decoratedValue = origValue;
		} else {
			decoratedValue = MessageFormat.format(myFormat, origValue);
		}

		if (BasicUtils.equals(decoratedValue, myCurrectValue)) return;

		fireValueChange(Diffs.createValueDiff(myCurrectValue, myCurrectValue = decoratedValue));
	}
}
