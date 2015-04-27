/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.gef.impl;

import com.thalesgroup.orchestra.framework.gef.Description;
import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.FileReference;
import com.thalesgroup.orchestra.framework.gef.GefPackage;
import com.thalesgroup.orchestra.framework.gef.LinksToArtifacts;
import com.thalesgroup.orchestra.framework.gef.LinksToElements;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Version;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getDESCRIPTION <em>DESCRIPTION</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getVERSION <em>VERSION</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getPROPERTIES <em>PROPERTIES</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getFILEREFERENCE <em>FILEREFERENCE</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getLINKSTOARTIFACTS <em>LINKSTOARTIFACTS</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getLINKSTOELEMENTS <em>LINKSTOELEMENTS</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getELEMENT <em>ELEMENT</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getFullName <em>Full Name</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getHash <em>Hash</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.thalesgroup.orchestra.framework.gef.impl.ElementImpl#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ElementImpl extends EObjectImpl implements Element {
  /**
   * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGroup()
   * @generated
   * @ordered
   */
  protected FeatureMap group;

  /**
   * The default value of the '{@link #getFullName() <em>Full Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFullName()
   * @generated
   * @ordered
   */
  protected static final String FULL_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getFullName() <em>Full Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFullName()
   * @generated
   * @ordered
   */
  protected String fullName = FULL_NAME_EDEFAULT;

  /**
   * The default value of the '{@link #getHash() <em>Hash</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHash()
   * @generated
   * @ordered
   */
  protected static final String HASH_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getHash() <em>Hash</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getHash()
   * @generated
   * @ordered
   */
  protected String hash = HASH_EDEFAULT;

  /**
   * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabel()
   * @generated
   * @ordered
   */
  protected static final String LABEL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLabel()
   * @generated
   * @ordered
   */
  protected String label = LABEL_EDEFAULT;

  /**
   * The default value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected static final String TYPE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getType()
   * @generated
   * @ordered
   */
  protected String type = TYPE_EDEFAULT;

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
  protected ElementImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return GefPackage.Literals.ELEMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public FeatureMap getGroup() {
    if (group == null) {
      group = new BasicFeatureMap(this, GefPackage.ELEMENT__GROUP);
    }
    return group;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Description> getDESCRIPTION() {
    return getGroup().list(GefPackage.Literals.ELEMENT__DESCRIPTION);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Version> getVERSION() {
    return getGroup().list(GefPackage.Literals.ELEMENT__VERSION);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Properties> getPROPERTIES() {
    return getGroup().list(GefPackage.Literals.ELEMENT__PROPERTIES);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FileReference> getFILEREFERENCE() {
    return getGroup().list(GefPackage.Literals.ELEMENT__FILEREFERENCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LinksToArtifacts> getLINKSTOARTIFACTS() {
    return getGroup().list(GefPackage.Literals.ELEMENT__LINKSTOARTIFACTS);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<LinksToElements> getLINKSTOELEMENTS() {
    return getGroup().list(GefPackage.Literals.ELEMENT__LINKSTOELEMENTS);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Element> getELEMENT() {
    return getGroup().list(GefPackage.Literals.ELEMENT__ELEMENT);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFullName(String newFullName) {
    String oldFullName = fullName;
    fullName = newFullName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.ELEMENT__FULL_NAME, oldFullName, fullName));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getHash() {
    return hash;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setHash(String newHash) {
    String oldHash = hash;
    hash = newHash;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.ELEMENT__HASH, oldHash, hash));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLabel() {
    return label;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLabel(String newLabel) {
    String oldLabel = label;
    label = newLabel;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.ELEMENT__LABEL, oldLabel, label));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getType() {
    return type;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setType(String newType) {
    String oldType = type;
    type = newType;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.ELEMENT__TYPE, oldType, type));
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
      eNotify(new ENotificationImpl(this, Notification.SET, GefPackage.ELEMENT__URI, oldUri, uri));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case GefPackage.ELEMENT__GROUP:
        return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
      case GefPackage.ELEMENT__DESCRIPTION:
        return ((InternalEList<?>)getDESCRIPTION()).basicRemove(otherEnd, msgs);
      case GefPackage.ELEMENT__VERSION:
        return ((InternalEList<?>)getVERSION()).basicRemove(otherEnd, msgs);
      case GefPackage.ELEMENT__PROPERTIES:
        return ((InternalEList<?>)getPROPERTIES()).basicRemove(otherEnd, msgs);
      case GefPackage.ELEMENT__FILEREFERENCE:
        return ((InternalEList<?>)getFILEREFERENCE()).basicRemove(otherEnd, msgs);
      case GefPackage.ELEMENT__LINKSTOARTIFACTS:
        return ((InternalEList<?>)getLINKSTOARTIFACTS()).basicRemove(otherEnd, msgs);
      case GefPackage.ELEMENT__LINKSTOELEMENTS:
        return ((InternalEList<?>)getLINKSTOELEMENTS()).basicRemove(otherEnd, msgs);
      case GefPackage.ELEMENT__ELEMENT:
        return ((InternalEList<?>)getELEMENT()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case GefPackage.ELEMENT__GROUP:
        if (coreType) return getGroup();
        return ((FeatureMap.Internal)getGroup()).getWrapper();
      case GefPackage.ELEMENT__DESCRIPTION:
        return getDESCRIPTION();
      case GefPackage.ELEMENT__VERSION:
        return getVERSION();
      case GefPackage.ELEMENT__PROPERTIES:
        return getPROPERTIES();
      case GefPackage.ELEMENT__FILEREFERENCE:
        return getFILEREFERENCE();
      case GefPackage.ELEMENT__LINKSTOARTIFACTS:
        return getLINKSTOARTIFACTS();
      case GefPackage.ELEMENT__LINKSTOELEMENTS:
        return getLINKSTOELEMENTS();
      case GefPackage.ELEMENT__ELEMENT:
        return getELEMENT();
      case GefPackage.ELEMENT__FULL_NAME:
        return getFullName();
      case GefPackage.ELEMENT__HASH:
        return getHash();
      case GefPackage.ELEMENT__LABEL:
        return getLabel();
      case GefPackage.ELEMENT__TYPE:
        return getType();
      case GefPackage.ELEMENT__URI:
        return getUri();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case GefPackage.ELEMENT__GROUP:
        ((FeatureMap.Internal)getGroup()).set(newValue);
        return;
      case GefPackage.ELEMENT__DESCRIPTION:
        getDESCRIPTION().clear();
        getDESCRIPTION().addAll((Collection<? extends Description>)newValue);
        return;
      case GefPackage.ELEMENT__VERSION:
        getVERSION().clear();
        getVERSION().addAll((Collection<? extends Version>)newValue);
        return;
      case GefPackage.ELEMENT__PROPERTIES:
        getPROPERTIES().clear();
        getPROPERTIES().addAll((Collection<? extends Properties>)newValue);
        return;
      case GefPackage.ELEMENT__FILEREFERENCE:
        getFILEREFERENCE().clear();
        getFILEREFERENCE().addAll((Collection<? extends FileReference>)newValue);
        return;
      case GefPackage.ELEMENT__LINKSTOARTIFACTS:
        getLINKSTOARTIFACTS().clear();
        getLINKSTOARTIFACTS().addAll((Collection<? extends LinksToArtifacts>)newValue);
        return;
      case GefPackage.ELEMENT__LINKSTOELEMENTS:
        getLINKSTOELEMENTS().clear();
        getLINKSTOELEMENTS().addAll((Collection<? extends LinksToElements>)newValue);
        return;
      case GefPackage.ELEMENT__ELEMENT:
        getELEMENT().clear();
        getELEMENT().addAll((Collection<? extends Element>)newValue);
        return;
      case GefPackage.ELEMENT__FULL_NAME:
        setFullName((String)newValue);
        return;
      case GefPackage.ELEMENT__HASH:
        setHash((String)newValue);
        return;
      case GefPackage.ELEMENT__LABEL:
        setLabel((String)newValue);
        return;
      case GefPackage.ELEMENT__TYPE:
        setType((String)newValue);
        return;
      case GefPackage.ELEMENT__URI:
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
      case GefPackage.ELEMENT__GROUP:
        getGroup().clear();
        return;
      case GefPackage.ELEMENT__DESCRIPTION:
        getDESCRIPTION().clear();
        return;
      case GefPackage.ELEMENT__VERSION:
        getVERSION().clear();
        return;
      case GefPackage.ELEMENT__PROPERTIES:
        getPROPERTIES().clear();
        return;
      case GefPackage.ELEMENT__FILEREFERENCE:
        getFILEREFERENCE().clear();
        return;
      case GefPackage.ELEMENT__LINKSTOARTIFACTS:
        getLINKSTOARTIFACTS().clear();
        return;
      case GefPackage.ELEMENT__LINKSTOELEMENTS:
        getLINKSTOELEMENTS().clear();
        return;
      case GefPackage.ELEMENT__ELEMENT:
        getELEMENT().clear();
        return;
      case GefPackage.ELEMENT__FULL_NAME:
        setFullName(FULL_NAME_EDEFAULT);
        return;
      case GefPackage.ELEMENT__HASH:
        setHash(HASH_EDEFAULT);
        return;
      case GefPackage.ELEMENT__LABEL:
        setLabel(LABEL_EDEFAULT);
        return;
      case GefPackage.ELEMENT__TYPE:
        setType(TYPE_EDEFAULT);
        return;
      case GefPackage.ELEMENT__URI:
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
      case GefPackage.ELEMENT__GROUP:
        return group != null && !group.isEmpty();
      case GefPackage.ELEMENT__DESCRIPTION:
        return !getDESCRIPTION().isEmpty();
      case GefPackage.ELEMENT__VERSION:
        return !getVERSION().isEmpty();
      case GefPackage.ELEMENT__PROPERTIES:
        return !getPROPERTIES().isEmpty();
      case GefPackage.ELEMENT__FILEREFERENCE:
        return !getFILEREFERENCE().isEmpty();
      case GefPackage.ELEMENT__LINKSTOARTIFACTS:
        return !getLINKSTOARTIFACTS().isEmpty();
      case GefPackage.ELEMENT__LINKSTOELEMENTS:
        return !getLINKSTOELEMENTS().isEmpty();
      case GefPackage.ELEMENT__ELEMENT:
        return !getELEMENT().isEmpty();
      case GefPackage.ELEMENT__FULL_NAME:
        return FULL_NAME_EDEFAULT == null ? fullName != null : !FULL_NAME_EDEFAULT.equals(fullName);
      case GefPackage.ELEMENT__HASH:
        return HASH_EDEFAULT == null ? hash != null : !HASH_EDEFAULT.equals(hash);
      case GefPackage.ELEMENT__LABEL:
        return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
      case GefPackage.ELEMENT__TYPE:
        return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
      case GefPackage.ELEMENT__URI:
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
    result.append(" (group: ");
    result.append(group);
    result.append(", fullName: ");
    result.append(fullName);
    result.append(", hash: ");
    result.append(hash);
    result.append(", label: ");
    result.append(label);
    result.append(", type: ");
    result.append(type);
    result.append(", uri: ");
    result.append(uri);
    result.append(')');
    return result.toString();
  }

} //ElementImpl
