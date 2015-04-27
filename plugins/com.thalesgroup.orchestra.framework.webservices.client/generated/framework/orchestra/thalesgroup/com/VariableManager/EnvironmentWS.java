/**
 * EnvironmentWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package framework.orchestra.thalesgroup.com.VariableManager;

public class EnvironmentWS  implements java.io.Serializable {
    private java.lang.String environmentId;

    private java.lang.String environmentCategory;

    private framework.orchestra.thalesgroup.com.VariableManager.EnvironmentValueWS[] values;

    public EnvironmentWS() {
    }

    public EnvironmentWS(
           java.lang.String environmentId,
           java.lang.String environmentCategory,
           framework.orchestra.thalesgroup.com.VariableManager.EnvironmentValueWS[] values) {
           this.environmentId = environmentId;
           this.environmentCategory = environmentCategory;
           this.values = values;
    }


    /**
     * Gets the environmentId value for this EnvironmentWS.
     * 
     * @return environmentId
     */
    public java.lang.String getEnvironmentId() {
        return environmentId;
    }


    /**
     * Sets the environmentId value for this EnvironmentWS.
     * 
     * @param environmentId
     */
    public void setEnvironmentId(java.lang.String environmentId) {
        this.environmentId = environmentId;
    }


    /**
     * Gets the environmentCategory value for this EnvironmentWS.
     * 
     * @return environmentCategory
     */
    public java.lang.String getEnvironmentCategory() {
        return environmentCategory;
    }


    /**
     * Sets the environmentCategory value for this EnvironmentWS.
     * 
     * @param environmentCategory
     */
    public void setEnvironmentCategory(java.lang.String environmentCategory) {
        this.environmentCategory = environmentCategory;
    }


    /**
     * Gets the values value for this EnvironmentWS.
     * 
     * @return values
     */
    public framework.orchestra.thalesgroup.com.VariableManager.EnvironmentValueWS[] getValues() {
        return values;
    }


    /**
     * Sets the values value for this EnvironmentWS.
     * 
     * @param values
     */
    public void setValues(framework.orchestra.thalesgroup.com.VariableManager.EnvironmentValueWS[] values) {
        this.values = values;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EnvironmentWS)) return false;
        EnvironmentWS other = (EnvironmentWS) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.environmentId==null && other.getEnvironmentId()==null) || 
             (this.environmentId!=null &&
              this.environmentId.equals(other.getEnvironmentId()))) &&
            ((this.environmentCategory==null && other.getEnvironmentCategory()==null) || 
             (this.environmentCategory!=null &&
              this.environmentCategory.equals(other.getEnvironmentCategory()))) &&
            ((this.values==null && other.getValues()==null) || 
             (this.values!=null &&
              java.util.Arrays.equals(this.values, other.getValues())));
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
        if (getEnvironmentId() != null) {
            _hashCode += getEnvironmentId().hashCode();
        }
        if (getEnvironmentCategory() != null) {
            _hashCode += getEnvironmentCategory().hashCode();
        }
        if (getValues() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getValues());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getValues(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EnvironmentWS.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "EnvironmentWS"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("environmentId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "environmentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("environmentCategory");
        elemField.setXmlName(new javax.xml.namespace.QName("", "environmentCategory"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("values");
        elemField.setXmlName(new javax.xml.namespace.QName("", "values"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://com.thalesgroup.orchestra.framework/VariableManager", "EnvironmentValueWS"));
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
