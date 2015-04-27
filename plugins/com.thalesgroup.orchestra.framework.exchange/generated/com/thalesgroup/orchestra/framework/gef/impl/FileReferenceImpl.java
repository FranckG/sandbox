/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef.impl;

import com.thalesgroup.orchestra.framework.gef.FileReference;
import com.thalesgroup.orchestra.framework.gef.GefPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.FileReferenceImpl#getMimeType <em>Mime Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.FileReferenceImpl#getNature <em>Nature</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.FileReferenceImpl#getUrl <em>Url</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FileReferenceImpl extends EObjectImpl implements FileReference {
  /**
   * The default value of the '{@link #getMimeType() <em>Mime Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMimeType()
   * @generated
   * @ordered
   */
  protected static final String MIME_TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMimeType() <em>Mime Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMimeType()
   * @generated
   * @ordered
   */
  protected String mimeType = MIME_TYPE_EDEFAULT;

  /**
   * The default value of the '{@link #getNature() <em>Nature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNature()
   * @generated
   * @ordered
   */
  protected static final String NATURE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNature() <em>Nature</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNature()
   * @generated
   * @ordered
   */
  protected String nature = NATURE_EDEFAULT;

  /**
   * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUrl()
   * @generated
   * @ordered
   */
  protected static final String URL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUrl()
   * @generated
   * @ordered
   */
  protected String url = URL_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FileReferenceImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GefPackage.Literals.FILE_REFERENCE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getMimeType() {
    return mimeType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMimeType(String newMimeType) {
    String oldMimeType = mimeType;
    mimeType = newMimeType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.FILE_REFERENCE__MIME_TYPE, oldMimeType, mimeType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getNature() {
    return nature;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setNature(String newNature) {
    String oldNature = nature;
    nature = newNature;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.FILE_REFERENCE__NATURE, oldNature, nature));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getUrl() {
    return url;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUrl(String newUrl) {
    String oldUrl = url;
    url = newUrl;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.FILE_REFERENCE__URL, oldUrl, url));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case GefPackage.FILE_REFERENCE__MIME_TYPE:
        return getMimeType();
      case GefPackage.FILE_REFERENCE__NATURE:
        return getNature();
      case GefPackage.FILE_REFERENCE__URL:
        return getUrl();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case GefPackage.FILE_REFERENCE__MIME_TYPE:
        setMimeType((String)newValue);
        return;
      case GefPackage.FILE_REFERENCE__NATURE:
        setNature((String)newValue);
        return;
      case GefPackage.FILE_REFERENCE__URL:
        setUrl((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case GefPackage.FILE_REFERENCE__MIME_TYPE:
        setMimeType(MIME_TYPE_EDEFAULT);
        return;
      case GefPackage.FILE_REFERENCE__NATURE:
        setNature(NATURE_EDEFAULT);
        return;
      case GefPackage.FILE_REFERENCE__URL:
        setUrl(URL_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case GefPackage.FILE_REFERENCE__MIME_TYPE:
        return MIME_TYPE_EDEFAULT == null ? mimeType != null : !MIME_TYPE_EDEFAULT.equals(mimeType);
      case GefPackage.FILE_REFERENCE__NATURE:
        return NATURE_EDEFAULT == null ? nature != null : !NATURE_EDEFAULT.equals(nature);
      case GefPackage.FILE_REFERENCE__URL:
        return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (mimeType: ");
    result.append(mimeType);
    result.append(", nature: ");
    result.append(nature);
    result.append(", url: ");
    result.append(url);
    result.append(')');
    return result.toString();
  }

} //FileReferenceImpl
