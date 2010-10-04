/*******************************************************************************
 * Copyright (c) 2007, 2010 The RCP Company and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The RCP Company - initial API and implementation
 *******************************************************************************/
package com.rcpcompany.uibindings.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.rcpcompany.uibindings.Constants;
import com.rcpcompany.uibindings.IArgumentContext;
import com.rcpcompany.uibindings.IArgumentInformation;
import com.rcpcompany.uibindings.IArgumentProvider;
import com.rcpcompany.uibindings.IArgumentValue;
import com.rcpcompany.uibindings.IBinding;
import com.rcpcompany.uibindings.IBindingDataType;
import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.IUIBindingsPackage;
import com.rcpcompany.uibindings.internal.BindingImpl.ArgumentValue;
import com.rcpcompany.uibindings.internal.bindingDataTypes.BindingDataTypeFactory;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Binding Data Type</b></em>
 * '. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.rcpcompany.uibindings.internal.BindingDataTypeImpl#getName <em>Name</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.BindingDataTypeImpl#getValueType <em>Value Type
 * </em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.BindingDataTypeImpl#getEType <em>EType</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.BindingDataTypeImpl#getDataType <em>Data Type</em>}
 * </li>
 * <li>{@link com.rcpcompany.uibindings.internal.BindingDataTypeImpl#getEAnnotation <em>EAnnotation
 * </em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.BindingDataTypeImpl#getParentDataType <em>Parent
 * Data Type</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.BindingDataTypeImpl#isRequired <em>Required</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.BindingDataTypeImpl#isChangeable <em>Changeable
 * </em>}</li>
 * <li>{@link com.rcpcompany.uibindings.internal.BindingDataTypeImpl#isUnsettable <em>Unsettable
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class BindingDataTypeImpl extends EObjectImpl implements IBindingDataType {
	@Override
	public abstract IArgumentProvider getArgumentProvider(String type);

	@Override
	public <ArgumentType> void addArguments(final IArgumentContext<ArgumentType> context) {
		getEAnnotationArguments(context);
		if (context.isResultFound()) return;

		/*
		 * Avoid recursion when resolving the "type" argument!!!
		 */
		if (!Constants.ARG_TYPE.equals(context.getName())) {
			final String type = context.getType();
			if (type != null && type.length() > 0) {
				IManager.Factory.getManager().addArgumentProviderArguments(this.getArgumentProvider(type), context);
				if (context.isResultFound()) return;
			}
		}
		IManager.Factory.getManager().addArgumentProviderArguments(this.getArgumentProvider(null), context);
	}

	@Override
	public <ArgumentType> void addParentDataTypeArguments(final IArgumentContext<ArgumentType> context,
			Collection<IBindingDataType> visitedDataTypes) {
		final IBindingDataType pDataType = getParentDataType();
		if (pDataType != null && !visitedDataTypes.contains(pDataType)) {
			pDataType.addArguments(context);
			visitedDataTypes.add(pDataType);
		}
	}

	@Override
	public <ArgumentType> void addSuperDataTypeArguments(final IArgumentContext<ArgumentType> context,
			Collection<IBindingDataType> visitedDataTypes) {

		for (final IBindingDataType dt : BindingDataTypeFactory.getSuperTypes(this)) {
			if (visitedDataTypes.contains(dt)) {
				continue;
			}
			dt.addArguments(context);
			visitedDataTypes.add(dt);
			if (context.isResultFound()) return;
		}
	}

	private <ArgumentType> void getEAnnotationArguments(IArgumentContext<ArgumentType> context) {
		String value = null;

		final EAnnotation annotation = getEAnnotation();
		if (annotation == null) return;
		value = annotation.getDetails().get(context.getName());
		if (value == null) return;

		IManager.Factory.getManager().addArgumentValue(context, this, null, null, value);
	}

	@Override
	public <ArgumentType> ArgumentType getArgument(final String name, final String type,
			final Class<? extends ArgumentType> argumentType, ArgumentType defaultValue) {
		final List<IArgumentValue<ArgumentType>> results = getArguments(name, type, argumentType, true);

		if (results.isEmpty()) return defaultValue;
		return results.get(0).getValue();
	}

	@Override
	public <ArgumentType> List<IArgumentValue<ArgumentType>> getArguments(final String name, final String type,
			final Class<? extends ArgumentType> argumentType, final boolean firstOnly) {
		final List<IArgumentValue<ArgumentType>> results = new ArrayList<IArgumentValue<ArgumentType>>();

		final IArgumentInformation ai = IManager.Factory.getManager().getArgumentInformation(name);
		final IArgumentContext<ArgumentType> context = new IArgumentContext<ArgumentType>() {
			@Override
			public IBinding getBinding() {
				return null;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public IArgumentInformation getArgumentInformation() {
				return ai;
			}

			@Override
			public String getType() {
				return type;
			}

			@Override
			public Class<? extends ArgumentType> getArgumentType() {
				return argumentType;
			}

			@Override
			public boolean firstOnly() {
				return firstOnly;
			}

			@Override
			public void addResult(Object source, ArgumentType value) {
				results.add(new ArgumentValue<ArgumentType>(this, value));
			}

			@Override
			public boolean isResultFound() {
				return firstOnly() && !results.isEmpty();
			}
		};

		final Collection<IBindingDataType> visitedDataTypes = new ArrayList<IBindingDataType>();
		visitedDataTypes.add(this);
		// LogUtils.debug(this, this + ": " + getStaticDataType() + "/" + getDataType());

		addArguments(context);
		if (context.isResultFound()) return results;

		addSuperDataTypeArguments(context, visitedDataTypes);
		if (context.isResultFound()) return results;

		addParentDataTypeArguments(context, visitedDataTypes);
		if (context.isResultFound()) return results;

		return results;
	}

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getValueType() <em>Value Type</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getValueType()
	 * @generated
	 * @ordered
	 */
	protected static final Object VALUE_TYPE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isRequired() <em>Required</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isRequired()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REQUIRED_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isChangeable() <em>Changeable</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isChangeable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CHANGEABLE_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isUnsettable() <em>Unsettable</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isUnsettable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UNSETTABLE_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BindingDataTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IUIBindingsPackage.Literals.BINDING_DATA_TYPE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public abstract String getName();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public abstract Object getValueType();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public abstract EClassifier getEType();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public abstract Class<?> getDataType();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public abstract EAnnotation getEAnnotation();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public IBindingDataType getParentDataType() {
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public abstract boolean isRequired();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public abstract boolean isChangeable();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public abstract boolean isUnsettable();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IUIBindingsPackage.BINDING_DATA_TYPE__NAME:
			return getName();
		case IUIBindingsPackage.BINDING_DATA_TYPE__VALUE_TYPE:
			return getValueType();
		case IUIBindingsPackage.BINDING_DATA_TYPE__ETYPE:
			return getEType();
		case IUIBindingsPackage.BINDING_DATA_TYPE__DATA_TYPE:
			return getDataType();
		case IUIBindingsPackage.BINDING_DATA_TYPE__EANNOTATION:
			return getEAnnotation();
		case IUIBindingsPackage.BINDING_DATA_TYPE__PARENT_DATA_TYPE:
			return getParentDataType();
		case IUIBindingsPackage.BINDING_DATA_TYPE__REQUIRED:
			return isRequired();
		case IUIBindingsPackage.BINDING_DATA_TYPE__CHANGEABLE:
			return isChangeable();
		case IUIBindingsPackage.BINDING_DATA_TYPE__UNSETTABLE:
			return isUnsettable();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IUIBindingsPackage.BINDING_DATA_TYPE__NAME:
			return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
		case IUIBindingsPackage.BINDING_DATA_TYPE__VALUE_TYPE:
			return VALUE_TYPE_EDEFAULT == null ? getValueType() != null : !VALUE_TYPE_EDEFAULT.equals(getValueType());
		case IUIBindingsPackage.BINDING_DATA_TYPE__ETYPE:
			return getEType() != null;
		case IUIBindingsPackage.BINDING_DATA_TYPE__DATA_TYPE:
			return getDataType() != null;
		case IUIBindingsPackage.BINDING_DATA_TYPE__EANNOTATION:
			return getEAnnotation() != null;
		case IUIBindingsPackage.BINDING_DATA_TYPE__PARENT_DATA_TYPE:
			return getParentDataType() != null;
		case IUIBindingsPackage.BINDING_DATA_TYPE__REQUIRED:
			return isRequired() != REQUIRED_EDEFAULT;
		case IUIBindingsPackage.BINDING_DATA_TYPE__CHANGEABLE:
			return isChangeable() != CHANGEABLE_EDEFAULT;
		case IUIBindingsPackage.BINDING_DATA_TYPE__UNSETTABLE:
			return isUnsettable() != UNSETTABLE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} // BindingDataTypeImpl
