/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef.impl;

import com.thalesgroup.orchestra.framework.gef.GefPackage;
import com.thalesgroup.orchestra.framework.gef.LinkDirection;
import com.thalesgroup.orchestra.framework.gef.Reference;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ReferenceImpl#getLinkDirection <em>Link Direction</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ReferenceImpl#getLinkType <em>Link Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ReferenceImpl#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReferenceImpl extends EObjectImpl implements Reference {
  /**
   * The default value of the '{@link #getLinkDirection() <em>Link Direction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLinkDirection()
   * @generated
   * @ordered
   */
  protected static final LinkDirection LINK_DIRECTION_EDEFAULT = LinkDirection.IN;

  /**
   * The cached value of the '{@link #getLinkDirection() <em>Link Direction</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLinkDirection()
   * @generated
   * @ordered
   */
  protected LinkDirection linkDirection = LINK_DIRECTION_EDEFAULT;

  /**
   * This is true if the Link Direction attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean linkDirectionESet;

  /**
   * The default value of the '{@link #getLinkType() <em>Link Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLinkType()
   * @generated
   * @ordered
   */
  protected static final String LINK_TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLinkType() <em>Link Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLinkType()
   * @generated
   * @ordered
   */
  protected String linkType = LINK_TYPE_EDEFAULT;

  /**
   * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUri()
   * @generated
   * @ordered
   */
  protected static final String URI_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getUri()
   * @generated
   * @ordered
   */
  protected String uri = URI_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ReferenceImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GefPackage.Literals.REFERENCE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public LinkDirection getLinkDirection() {
    return linkDirection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLinkDirection(LinkDirection newLinkDirection) {
    LinkDirection oldLinkDirection = linkDirection;
    linkDirection = newLinkDirection == null ? LINK_DIRECTION_EDEFAULT : newLinkDirection;
    boolean oldLinkDirectionESet = linkDirectionESet;
    linkDirectionESet = true;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.REFERENCE__LINK_DIRECTION, oldLinkDirection, linkDirection, !oldLinkDirectionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetLinkDirection() {
    LinkDirection oldLinkDirection = linkDirection;
    boolean oldLinkDirectionESet = linkDirectionESet;
    linkDirection = LINK_DIRECTION_EDEFAULT;
    linkDirectionESet = false;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.UNSET, GefPackage.REFERENCE__LINK_DIRECTION, oldLinkDirection, LINK_DIRECTION_EDEFAULT, oldLinkDirectionESet));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetLinkDirection() {
    return linkDirectionESet;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLinkType() {
    return linkType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLinkType(String newLinkType) {
    String oldLinkType = linkType;
    linkType = newLinkType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.REFERENCE__LINK_TYPE, oldLinkType, linkType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getUri() {
    return uri;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setUri(String newUri) {
    String oldUri = uri;
    uri = newUri;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.REFERENCE__URI, oldUri, uri));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case GefPackage.REFERENCE__LINK_DIRECTION:
        return getLinkDirection();
      case GefPackage.REFERENCE__LINK_TYPE:
        return getLinkType();
      case GefPackage.REFERENCE__URI:
        return getUri();
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
      case GefPackage.REFERENCE__LINK_DIRECTION:
        setLinkDirection((LinkDirection)newValue);
        return;
      case GefPackage.REFERENCE__LINK_TYPE:
        setLinkType((String)newValue);
        return;
      case GefPackage.REFERENCE__URI:
        setUri((String)newValue);
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
      case GefPackage.REFERENCE__LINK_DIRECTION:
        unsetLinkDirection();
        return;
      case GefPackage.REFERENCE__LINK_TYPE:
        setLinkType(LINK_TYPE_EDEFAULT);
        return;
      case GefPackage.REFERENCE__URI:
        setUri(URI_EDEFAULT);
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
      case GefPackage.REFERENCE__LINK_DIRECTION:
        return isSetLinkDirection();
      case GefPackage.REFERENCE__LINK_TYPE:
        return LINK_TYPE_EDEFAULT == null ? linkType != null : !LINK_TYPE_EDEFAULT.equals(linkType);
      case GefPackage.REFERENCE__URI:
        return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
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
    result.append(" (linkDirection: ");
    if (linkDirectionESet) result.append(linkDirection); else result.append("<unset>");
    result.append(", linkType: ");
    result.append(linkType);
    result.append(", uri: ");
    result.append(uri);
    result.append(')');
    return result.toString();
  }

} //ReferenceImpl
