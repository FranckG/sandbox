/**
 * DeltaState.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package framework.orchestra.thalesgroup.com.VariableManager;

public class DeltaState  implements java.io.Serializable {
    private framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta delta;

    private boolean success;  // attribute

    private java.lang.String description;  // attribute

    public DeltaState() {
    }

    public DeltaState(
           framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta delta,
           boolean success,
           java.lang.String description) {
           this.delta = delta;
           this.success = success;
           this.description = description;
    }


    /**
     * Gets the delta value for this DeltaState.
     * 
     * @return delta
     */
    public framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta getDelta() {
        return delta;
    }


    /**
     * Sets the delta value for this DeltaState.
     * 
     * @param delta
     */
    public void setDelta(framework.orchestra.thalesgroup.com.VariableManager.ConfDirDelta delta) {
        this.delta = delta;
    }


    /**
     * Gets the success value for this DeltaState.
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }


    /**
     * Sets the success value for this DeltaState.
     * 
     * @param success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * Gets the description value for this DeltaState.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this DeltaState.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DeltaState)) return false;
        DeltaState other = (DeltaState) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.delta==null && other.getDelta()==null) || 
             (this.delta!=null &&
              this.delta.equals(other.getDelta()))) &&
            this.success == other.isSuccess() &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription())));
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
        if (getDelta() != null) {
            _hashCode += getDelta().hashCode();
        }
        _hashCode += (isSuccess() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DeltaState.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "DeltaState"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("success");
        attrField.setXmlName(new javax.xml.namespace.QName("", "success"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("description");
        attrField.setXmlName(new javax.xml.namespace.QName("", "description"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "delta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "ConfDirDelta"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
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
