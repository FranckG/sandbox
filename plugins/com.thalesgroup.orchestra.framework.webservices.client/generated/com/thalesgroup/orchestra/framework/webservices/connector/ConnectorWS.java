/**
 * ConnectorWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.thalesgroup.orchestra.framework.webservices.connector;

public class ConnectorWS implements java.io.Serializable {
  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(ConnectorWS.class, true);

  static {
    typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservices.framework.orchestra.thalesgroup.com/connector", "ConnectorWS"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("type");
    elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
    elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("association");
    elemField.setXmlName(new javax.xml.namespace.QName("", "association"));
    elemField.setXmlType(new javax.xml.namespace.QName("http://webservices.framework.orchestra.thalesgroup.com/connector", "AssociationWS"));
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("iconpath");
    elemField.setXmlName(new javax.xml.namespace.QName("", "iconpath"));
    elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("createpossible");
    elemField.setXmlName(new javax.xml.namespace.QName("", "createpossible"));
    elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
  }

  private java.lang.Object __equalsCalc = null;

  private boolean __hashCodeCalc = false;

  private com.thalesgroup.orchestra.framework.webservices.connector.AssociationWS association;

  private boolean createpossible;

  private java.lang.String iconpath;

  private java.lang.String type;

  public ConnectorWS() {
  }

  public ConnectorWS(java.lang.String type, com.thalesgroup.orchestra.framework.webservices.connector.AssociationWS association, java.lang.String iconpath,
      boolean createpossible) {
    this.type = type;
    this.association = association;
    this.iconpath = iconpath;
    this.createpossible = createpossible;
  }

  @Override
  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ConnectorWS))
      return false;
    ConnectorWS other = (ConnectorWS) obj;
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
        true && ((this.type == null && other.getType() == null) || (this.type != null && this.type.equals(other.getType())))
            && ((this.association == null && other.getAssociation() == null) || (this.association != null && this.association.equals(other.getAssociation())))
            && ((this.iconpath == null && other.getIconpath() == null) || (this.iconpath != null && this.iconpath.equals(other.getIconpath())))
            && this.createpossible == other.isCreatepossible();
    __equalsCalc = null;
    return _equals;
  }

  /**
   * Gets the association value for this ConnectorWS.
   * @return association
   */
  public com.thalesgroup.orchestra.framework.webservices.connector.AssociationWS getAssociation() {
    return association;
  }

  /**
   * Gets the iconpath value for this ConnectorWS.
   * @return iconpath
   */
  public java.lang.String getIconpath() {
    return iconpath;
  }

  /**
   * Gets the type value for this ConnectorWS.
   * @return type
   */
  public java.lang.String getType() {
    return type;
  }

  @Override
  public synchronized int hashCode() {
    if (__hashCodeCalc) {
      return 0;
    }
    __hashCodeCalc = true;
    int _hashCode = 1;
    if (getType() != null) {
      _hashCode += getType().hashCode();
    }
    if (getAssociation() != null) {
      _hashCode += getAssociation().hashCode();
    }
    if (getIconpath() != null) {
      _hashCode += getIconpath().hashCode();
    }
    _hashCode += (isCreatepossible() ? Boolean.TRUE : Boolean.FALSE).hashCode();
    __hashCodeCalc = false;
    return _hashCode;
  }

  /**
   * Gets the createpossible value for this ConnectorWS.
   * @return createpossible
   */
  public boolean isCreatepossible() {
    return createpossible;
  }

  /**
   * Sets the association value for this ConnectorWS.
   * @param association
   */
  public void setAssociation(com.thalesgroup.orchestra.framework.webservices.connector.AssociationWS association) {
    this.association = association;
  }

  /**
   * Sets the createpossible value for this ConnectorWS.
   * @param createpossible
   */
  public void setCreatepossible(boolean createpossible) {
    this.createpossible = createpossible;
  }

  /**
   * Sets the iconpath value for this ConnectorWS.
   * @param iconpath
   */
  public void setIconpath(java.lang.String iconpath) {
    this.iconpath = iconpath;
  }

  /**
   * Sets the type value for this ConnectorWS.
   * @param type
   */
  public void setType(java.lang.String type) {
    this.type = type;
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
