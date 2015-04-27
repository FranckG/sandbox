/**
 * ConfDirDelta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package framework.orchestra.thalesgroup.com.VariableManager;

public class ConfDirDelta  implements java.io.Serializable {
    private java.lang.String subPath;  // attribute

    private framework.orchestra.thalesgroup.com.VariableManager.ConfDirDeltaDeltaType deltaType;  // attribute

    public ConfDirDelta() {
    }

    public ConfDirDelta(
           java.lang.String subPath,
           framework.orchestra.thalesgroup.com.VariableManager.ConfDirDeltaDeltaType deltaType) {
           this.subPath = subPath;
           this.deltaType = deltaType;
    }


    /**
     * Gets the subPath value for this ConfDirDelta.
     * 
     * @return subPath
     */
    public java.lang.String getSubPath() {
        return subPath;
    }


    /**
     * Sets the subPath value for this ConfDirDelta.
     * 
     * @param subPath
     */
    public void setSubPath(java.lang.String subPath) {
        this.subPath = subPath;
    }


    /**
     * Gets the deltaType value for this ConfDirDelta.
     * 
     * @return deltaType
     */
    public framework.orchestra.thalesgroup.com.VariableManager.ConfDirDeltaDeltaType getDeltaType() {
        return deltaType;
    }


    /**
     * Sets the deltaType value for this ConfDirDelta.
     * 
     * @param deltaType
     */
    public void setDeltaType(framework.orchestra.thalesgroup.com.VariableManager.ConfDirDeltaDeltaType deltaType) {
        this.deltaType = deltaType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConfDirDelta)) return false;
        ConfDirDelta other = (ConfDirDelta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.subPath==null && other.getSubPath()==null) || 
             (this.subPath!=null &&
              this.subPath.equals(other.getSubPath()))) &&
            ((this.deltaType==null && other.getDeltaType()==null) || 
             (this.deltaType!=null &&
              this.deltaType.equals(other.getDeltaType())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getSubPath() != null) {
            _hashCode += getSubPath().hashCode();
        }
        if (getDeltaType() != null) {
            _hashCode += getDeltaType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConfDirDelta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ConfDirDelta"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("subPath");
        attrField.setXmlName(new javax.xml.namespace.QName("", "subPath"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("deltaType");
        attrField.setXmlName(new javax.xml.namespace.QName("", "deltaType"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", ">ConfDirDelta>deltaType"));
        typeDesc.addFieldDesc(attrField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
