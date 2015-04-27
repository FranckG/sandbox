/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.thalesgroup.orchestra.framework.models.connector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Service Name Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.thalesgroup.orchestra.framework.models.connector.ConnectorPackage#getServiceNameType()
 * @model extendedMetaData="name='serviceName_._type'"
 * @generated
 */
public enum ServiceNameType implements Enumerator {
  /**
   * The '<em><b>Create</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CREATE_VALUE
   * @generated
   * @ordered
   */
  CREATE(0, "Create", "Create"),

  /**
   * The '<em><b>Expand</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #EXPAND_VALUE
   * @generated
   * @ordered
   */
  EXPAND(1, "Expand", "Expand"),

  /**
   * The '<em><b>Export Doc</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #EXPORT_DOC_VALUE
   * @generated
   * @ordered
   */
  EXPORT_DOC(2, "ExportDoc", "ExportDoc"),

  /**
   * The '<em><b>Export LM</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #EXPORT_LM_VALUE
   * @generated
   * @ordered
   */
  EXPORT_LM(3, "ExportLM", "ExportLM"),

  /**
   * The '<em><b>Navigate</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #NAVIGATE_VALUE
   * @generated
   * @ordered
   */
  NAVIGATE(4, "Navigate", "Navigate"), /**
   * The '<em><b>Search</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SEARCH_VALUE
   * @generated
   * @ordered
   */
  SEARCH(5, "Search", "Search");

  /**
   * The '<em><b>Create</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Create</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CREATE
   * @model name="Create"
   * @generated
   * @ordered
   */
  public static final int CREATE_VALUE = 0;

  /**
   * The '<em><b>Expand</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Expand</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #EXPAND
   * @model name="Expand"
   * @generated
   * @ordered
   */
  public static final int EXPAND_VALUE = 1;

  /**
   * The '<em><b>Export Doc</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Export Doc</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #EXPORT_DOC
   * @model name="ExportDoc"
   * @generated
   * @ordered
   */
  public static final int EXPORT_DOC_VALUE = 2;

  /**
   * The '<em><b>Export LM</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Export LM</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #EXPORT_LM
   * @model name="ExportLM"
   * @generated
   * @ordered
   */
  public static final int EXPORT_LM_VALUE = 3;

  /**
   * The '<em><b>Navigate</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Navigate</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #NAVIGATE
   * @model name="Navigate"
   * @generated
   * @ordered
   */
  public static final int NAVIGATE_VALUE = 4;

  /**
   * The '<em><b>Search</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Search</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SEARCH
   * @model name="Search"
   * @generated
   * @ordered
   */
  public static final int SEARCH_VALUE = 5;

  /**
   * An array of all the '<em><b>Service Name Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final ServiceNameType[] VALUES_ARRAY =
    new ServiceNameType[] {
      CREATE,
      EXPAND,
      EXPORT_DOC,
      EXPORT_LM,
      NAVIGATE,
      SEARCH,
    };

  /**
   * A public read-only list of all the '<em><b>Service Name Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<ServiceNameType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Service Name Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ServiceNameType get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      ServiceNameType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Service Name Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ServiceNameType getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      ServiceNameType result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Service Name Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ServiceNameType get(int value) {
    switch (value) {
      case CREATE_VALUE: return CREATE;
      case EXPAND_VALUE: return EXPAND;
      case EXPORT_DOC_VALUE: return EXPORT_DOC;
      case EXPORT_LM_VALUE: return EXPORT_LM;
      case NAVIGATE_VALUE: return NAVIGATE;
      case SEARCH_VALUE: return SEARCH;
    }
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final int value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String name;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String literal;

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private ServiceNameType(int value, String name, String literal) {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue() {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLiteral() {
    return literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    return literal;
  }
  
} //ServiceNameType
