/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package com.rcpcompany.uibindings.moao;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Named Object</b></em>'. <!--
 * end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.rcpcompany.uibindings.moao.INamedObject#getName <em>Name</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.moao.INamedObject#getDescription <em>Description</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.moao.INamedObject#getUuid <em>Uuid</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.rcpcompany.uibindings.moao.IMOAOPackage#getNamedObject()
 * @generated
 */
public interface INamedObject extends IMOAO {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.rcpcompany.uibindings.moao.IMOAOPackage#getNamedObject_Name()
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.moao.INamedObject#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.rcpcompany.uibindings.moao.IMOAOPackage#getNamedObject_Description()
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.moao.INamedObject#getDescription
	 * <em>Description</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Uuid</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uuid</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Uuid</em>' attribute.
	 * @see com.rcpcompany.uibindings.moao.IMOAOPackage#getNamedObject_Uuid()
	 * @generated
	 */
	String getUuid();

} // INamedObject
