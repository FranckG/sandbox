/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.exchange;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.ValueListIterator;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import com.thalesgroup.orchestra.framework.gef.Element;
import com.thalesgroup.orchestra.framework.gef.GEF;
import com.thalesgroup.orchestra.framework.gef.GefFactory;
import com.thalesgroup.orchestra.framework.gef.GefPackage;
import com.thalesgroup.orchestra.framework.gef.Mproperty;
import com.thalesgroup.orchestra.framework.gef.MpropertyValue;
import com.thalesgroup.orchestra.framework.gef.Properties;
import com.thalesgroup.orchestra.framework.gef.Property;
import com.thalesgroup.orchestra.framework.gef.TextualDescription;
import com.thalesgroup.orchestra.framework.gef.util.GefResourceFactoryImpl;

/**
 * GEF model format handler.<br>
 * Allows for reading/writing of files contents.
 * @author t0076261
 */
public class GefHandler extends AbstractModelHandler<GEF> {

  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#createRootElement()
   */
  @Override
  protected GEF createRootElement() {
    return GefFactory.eINSTANCE.createGEF();
  }

  /**
   * Get {@link Element} from specified {@link GEF} model and specified URI.
   * @param gef_p
   * @param uri_p
   * @return <code>null</code> if parameters are invalid, or no such element could be found.
   */
  public Element getElementForUri(GEF gef_p, String uri_p) {
    // Precondition.
    if ((null == gef_p) || (null == uri_p)) {
      return null;
    }
    TreeIterator<EObject> allContents = gef_p.eAllContents();
    while (allContents.hasNext()) {
      EObject currentObject = allContents.next();
      // Skip this one.
      if (!GefPackage.Literals.ELEMENT.isInstance(currentObject)) {
        continue;
      }
      Element element = (Element) currentObject;
      if (uri_p.equals(element.getUri())) {
        return element;
      }
    }
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#getFileExtension()
   */
  @Override
  public String getFileExtension() {
    return "gef"; //$NON-NLS-1$
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#isRootElement(java.lang.Object)
   */
  @Override
  protected GEF getRootElement(Object element_p) {
    if (element_p instanceof GEF) {
      return (GEF) element_p;
    }
    return null;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.model.AbstractModelHandler#initialize(org.eclipse.emf.ecore.resource.ResourceSet)
   */
  @Override
  protected void initialize(ResourceSet resourceSet_p) {
    if (!Platform.isRunning()) {
      resourceSet_p.getPackageRegistry().put(GefPackage.eNS_URI, GefPackage.eINSTANCE);
      resourceSet_p.getResourceFactoryRegistry().getExtensionToFactoryMap().put("gef", new GefResourceFactoryImpl()); //$NON-NLS-1$
    }
  }

  /**
   * Add specified value, in specified format, to specified feature map.
   * @param featureMap_p
   * @param type_p
   * @param value_p
   */
  protected static void addValue(FeatureMap featureMap_p, ValueType type_p, String value_p) {
    // Preconditions.
    if ((null == featureMap_p) || (null == type_p) || (null == value_p)) {
      return;
    }
    switch (type_p) {
      case CDATA:
        FeatureMapUtil.addCDATA(featureMap_p, value_p);
      break;
      case TEXT:
        FeatureMapUtil.addText(featureMap_p, value_p);
      break;
      default:
      // Do nothing.
      break;
    }
  }

  /**
   * Add value to {@link Property} with specified type.
   * @param property_p The property which value is to be modified.
   * @param type_p The type of the value to be added.
   * @param value_p The value to be added.
   */
  public static void addValue(Property property_p, ValueType type_p, String value_p) {
    if (null != property_p) {
      addValue(property_p.getMixed(), type_p, value_p);
    }
  }

  /**
   * Add values to {@link MProperty}.
   * @param property_p The property which value is to be modified.
   * @param values_p The value to be added.
   */
  public static void addValue(Mproperty property_p, ValueType type_p, String value_p) {
    if (null != property_p) {
      MpropertyValue propertyValue = GefFactory.eINSTANCE.createMpropertyValue();
      addValue(propertyValue.getMixed(), type_p, value_p);
      property_p.getVALUES().add(propertyValue);
    }
  }

  /**
   * Add value to {@link TextualDescription} with specified type.
   * @param property_p The property which value is to be modified.
   * @param type_p The type of the value to be added.
   * @param value_p The value to be added.
   */
  public static void addValue(TextualDescription textualDescription_p, ValueType type_p, String value_p) {
    if (null != textualDescription_p) {
      addValue(textualDescription_p.getMixed(), type_p, value_p);
    }
  }

  /**
   * Get available values for specified {@link FeatureMap}.
   * @param featureMap_p
   * @return A possibly <code>null</code> or empty list of values.
   */
  protected static List<String> getValue(FeatureMap featureMap_p) {
    if (null == featureMap_p) {
      return null;
    }
    ValueListIterator<Object> valueListIterator = featureMap_p.valueListIterator();
    if (null == valueListIterator) {
      return null;
    }
    List<String> result = new ArrayList<String>(0);
    while (valueListIterator.hasNext()) {
      Object value = valueListIterator.next();
      if (value instanceof String) {
        result.add((String) value);
      }
    }
    return result;
  }

  /**
   * Get available values for specified {@link Property}.
   * @param property_p
   * @return A possibly <code>null</code> or empty list of values.
   */
  public static List<String> getValue(Property property_p) {
    if (null == property_p) {
      return null;
    }
    return getValue(property_p.getMixed());
  }

  /**
   * Get available property values for specified {@link MProperty}.
   * @param property_p
   * @return A possibly <code>null</code> or empty list of values.
   */
  public static List<String> getValues(Mproperty property_p) {
    if (null == property_p) {
      return null;
    }
    List<String> result = new ArrayList<String>();
    for (MpropertyValue propValue : property_p.getVALUES()) {
      StringBuilder builder = new StringBuilder();
      for (String value : getValue(propValue.getMixed())) {
        builder.append(value);
      }
      result.add(builder.toString());
    }
    return result;
  }

  /**
   * Get available values for specified {@link TextualDescription}.
   * @param description_p
   * @return A possibly <code>null</code> or empty list of values.
   */
  public static List<String> getValue(TextualDescription description_p) {
    if (null == description_p) {
      return null;
    }
    return getValue(description_p.getMixed());
  }

  /**
   * Get list of properties ({@link Property} and {@link MProperty}) of a {@link Properties} element, in their declaration order
   * @param properties_p Properties node
   * @return
   */
  public static List<Object> getProperties(Properties properties_p) {
    ValueListIterator<Object> valueListIterator = properties_p.getGroup().valueListIterator();
    if (null == valueListIterator) {
      return null;
    }
    List<Object> result = new ArrayList<Object>();
    while (valueListIterator.hasNext()) {
      result.add(valueListIterator.next());
    }
    return result;
  }

  /**
   * Specific elements value type, as of an XML persistence.
   * @author t0076261
   */
  public enum ValueType {
    CDATA, TEXT
  }
}