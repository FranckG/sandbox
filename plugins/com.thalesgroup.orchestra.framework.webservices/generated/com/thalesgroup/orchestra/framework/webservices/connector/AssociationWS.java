/**
 * AssociationWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.thalesgroup.orchestra.framework.webservices.connector;

public class AssociationWS implements java.io.Serializable {
  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(AssociationWS.class, true);

  static {
    typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.framework.orchestra.thalesgroup.com/connector", "AssociationWS"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("logical");
    elemField.setXmlName(new javax.xml.namespace.QName("", "logical"));
    elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("physical");
    elemField.setXmlName(new javax.xml.namespace.QName("", "physical"));
    elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("balanced");
    elemField.setXmlName(new javax.xml.namespace.QName("", "balanced"));
    elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("valid");
    elemField.setXmlName(new javax.xml.namespace.QName("", "valid"));
    elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
  }

  private java.lang.Object __equalsCalc = null;

  private boolean __hashCodeCalc = false;

  private boolean balanced;

  private java.lang.String logical;

  private java.lang.String physical;

  private boolean valid;

  public AssociationWS() {
  }

  public AssociationWS(java.lang.String logical, java.lang.String physical, boolean balanced, boolean valid) {
    this.logical = logical;
    this.physical = physical;
    this.balanced = balanced;
    this.valid = valid;
  }

  @Override
  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof AssociationWS))
      return false;
    AssociationWS other = (AssociationWS) obj;
    if (obj == null)
      return false;
    if (this == obj)
      return true;
    if (__equalsCalc != null) {
      return (__equalsCalc == obj);
    }
    __equalsCalc = obj;
    boolean _equals;
    _equals =
        true && ((this.logical == null && other.getLogical() == null) || (this.logical != null && this.logical.equals(other.getLogical())))
            && ((this.physical == null && other.getPhysical() == null) || (this.physical != null && this.physical.equals(other.getPhysical())))
            && this.balanced == other.isBalanced() && this.valid == other.isValid();
    __equalsCalc = null;
    return _equals;
  }

  /**
   * Gets the logical value for this AssociationWS.
   * @return logical
   */
  public java.lang.String getLogical() {
    return logical;
  }

  /**
   * Gets the physical value for this AssociationWS.
   * @return physical
   */
  public java.lang.String getPhysical() {
    return physical;
  }

  @Override
  public synchronized int hashCode() {
    if (__hashCodeCalc) {
      return 0;
    }
    __hashCodeCalc = true;
    int _hashCode = 1;
    if (getLogical() != null) {
      _hashCode += getLogical().hashCode();
    }
    if (getPhysical() != null) {
      _hashCode += getPhysical().hashCode();
    }
    _hashCode += (isBalanced() ? Boolean.TRUE : Boolean.FALSE).hashCode();
    _hashCode += (isValid() ? Boolean.TRUE : Boolean.FALSE).hashCode();
    __hashCodeCalc = false;
    return _hashCode;
  }

  /**
   * Gets the balanced value for this AssociationWS.
   * @return balanced
   */
  public boolean isBalanced() {
    return balanced;
  }

  /**
   * Gets the valid value for this AssociationWS.
   * @return valid
   */
  public boolean isValid() {
    return valid;
  }

  /**
   * Sets the balanced value for this AssociationWS.
   * @param balanced
   */
  public void setBalanced(boolean balanced) {
    this.balanced = balanced;
  }

  /**
   * Sets the logical value for this AssociationWS.
   * @param logical
   */
  public void setLogical(java.lang.String logical) {
    this.logical = logical;
  }

  /**
   * Sets the physical value for this AssociationWS.
   * @param physical
   */
  public void setPhysical(java.lang.String physical) {
    this.physical = physical;
  }

  /**
   * Sets the valid value for this AssociationWS.
   * @param valid
   */
  public void setValid(boolean valid) {
    this.valid = valid;
  }

  /**
   * Get Custom Deserializer
   */
  public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
    return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
  }

  /**
   * Get Custom Serializer
   */
  public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType, java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
    return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
  }

  /**
   * Return type metadata object
   */
  public static org.apache.axis.description.TypeDesc getTypeDesc() {
    return typeDesc;
  }

}
