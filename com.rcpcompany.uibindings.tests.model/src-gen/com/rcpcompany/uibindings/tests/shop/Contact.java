/**
 */
package com.rcpcompany.uibindings.tests.shop;

import java.util.Date;

import com.rcpcompany.uibindings.moao.INamedObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Contact</b></em>'. <!--
 * end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.rcpcompany.uibindings.tests.shop.Contact#getShop <em>Shop</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.tests.shop.Contact#getAddress <em>Address</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.tests.shop.Contact#getCity <em>City</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.tests.shop.Contact#getZip <em>Zip</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.tests.shop.Contact#getCountry <em>Country</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.tests.shop.Contact#getCustomer <em>Customer</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.tests.shop.Contact#isNewsletter <em>Newsletter</em>}</li>
 * <li>{@link com.rcpcompany.uibindings.tests.shop.Contact#getBirthday <em>Birthday</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.rcpcompany.uibindings.tests.shop.ShopPackage#getContact()
 * @generated
 */
public interface Contact extends INamedObject {

	/**
	 * Returns the value of the '<em><b>Shop</b></em>' container reference. It is bidirectional and
	 * its opposite is '{@link com.rcpcompany.uibindings.tests.shop.Shop#getContacts
	 * <em>Contacts</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shop</em>' container reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Shop</em>' container reference.
	 * @see #setShop(Shop)
	 * @see com.rcpcompany.uibindings.tests.shop.ShopPackage#getContact_Shop()
	 * @see com.rcpcompany.uibindings.tests.shop.Shop#getContacts
	 * @generated
	 */
	Shop getShop();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.tests.shop.Contact#getShop
	 * <em>Shop</em>}' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Shop</em>' container reference.
	 * @see #getShop()
	 * @generated
	 */
	void setShop(Shop value);

	/**
	 * Returns the value of the '<em><b>Address</b></em>' attribute. The default value is
	 * <code>""</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Address</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Address</em>' attribute.
	 * @see #setAddress(String)
	 * @see com.rcpcompany.uibindings.tests.shop.ShopPackage#getContact_Address()
	 * @generated
	 */
	String getAddress();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.tests.shop.Contact#getAddress
	 * <em>Address</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Address</em>' attribute.
	 * @see #getAddress()
	 * @generated
	 */
	void setAddress(String value);

	/**
	 * Returns the value of the '<em><b>City</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>City</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>City</em>' attribute.
	 * @see #setCity(String)
	 * @see com.rcpcompany.uibindings.tests.shop.ShopPackage#getContact_City()
	 * @generated
	 */
	String getCity();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.tests.shop.Contact#getCity
	 * <em>City</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>City</em>' attribute.
	 * @see #getCity()
	 * @generated
	 */
	void setCity(String value);

	/**
	 * Returns the value of the '<em><b>Zip</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Zip</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Zip</em>' attribute.
	 * @see #setZip(String)
	 * @see com.rcpcompany.uibindings.tests.shop.ShopPackage#getContact_Zip()
	 * @generated
	 */
	String getZip();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.tests.shop.Contact#getZip
	 * <em>Zip</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Zip</em>' attribute.
	 * @see #getZip()
	 * @generated
	 */
	void setZip(String value);

	/**
	 * Returns the value of the '<em><b>Country</b></em>' reference. It is bidirectional and its
	 * opposite is '{@link com.rcpcompany.uibindings.tests.shop.Country#getContacts
	 * <em>Contacts</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Country</em>' reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Country</em>' reference.
	 * @see #setCountry(Country)
	 * @see com.rcpcompany.uibindings.tests.shop.ShopPackage#getContact_Country()
	 * @see com.rcpcompany.uibindings.tests.shop.Country#getContacts
	 * @generated
	 */
	Country getCountry();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.tests.shop.Contact#getCountry
	 * <em>Country</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Country</em>' reference.
	 * @see #getCountry()
	 * @generated
	 */
	void setCountry(Country value);

	/**
	 * Returns the value of the '<em><b>Customer</b></em>' reference. It is bidirectional and its
	 * opposite is '{@link com.rcpcompany.uibindings.tests.shop.Customer#getContact
	 * <em>Contact</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Customer</em>' reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Customer</em>' reference.
	 * @see #setCustomer(Customer)
	 * @see com.rcpcompany.uibindings.tests.shop.ShopPackage#getContact_Customer()
	 * @see com.rcpcompany.uibindings.tests.shop.Customer#getContact
	 * @generated
	 */
	Customer getCustomer();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.tests.shop.Contact#getCustomer
	 * <em>Customer</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Customer</em>' reference.
	 * @see #getCustomer()
	 * @generated
	 */
	void setCustomer(Customer value);

	/**
	 * Returns the value of the '<em><b>Newsletter</b></em>' attribute. The default value is
	 * <code>"true"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Newsletter</em>' attribute isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Newsletter</em>' attribute.
	 * @see #setNewsletter(boolean)
	 * @see com.rcpcompany.uibindings.tests.shop.ShopPackage#getContact_Newsletter()
	 * @generated
	 */
	boolean isNewsletter();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.tests.shop.Contact#isNewsletter
	 * <em>Newsletter</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Newsletter</em>' attribute.
	 * @see #isNewsletter()
	 * @generated
	 */
	void setNewsletter(boolean value);

	/**
	 * Returns the value of the '<em><b>Birthday</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Birthday</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Birthday</em>' attribute.
	 * @see #setBirthday(Date)
	 * @see com.rcpcompany.uibindings.tests.shop.ShopPackage#getContact_Birthday()
	 * @generated
	 */
	Date getBirthday();

	/**
	 * Sets the value of the '{@link com.rcpcompany.uibindings.tests.shop.Contact#getBirthday
	 * <em>Birthday</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Birthday</em>' attribute.
	 * @see #getBirthday()
	 * @generated
	 */
	void setBirthday(Date value);
} // Contact
