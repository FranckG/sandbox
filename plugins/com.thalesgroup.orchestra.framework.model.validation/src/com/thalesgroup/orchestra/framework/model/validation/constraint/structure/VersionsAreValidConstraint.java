/**
 * Copyright (c) THALES, 2012. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.validation.constraint.structure;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.osgi.framework.Version;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.ContextsFactory;
import com.thalesgroup.orchestra.framework.model.contexts.InstallationCategory;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint;

/**
 * A constraint that ensures that each selected version still exist and is compliant with compatibilities constraints.
 * @author t0076261
 */
public class VersionsAreValidConstraint extends AbstractConstraint<Context> {
  /**
   * Closing bracket regular expression for replacement in strings.
   */
  protected static final String CLOSING_BRACKET_REG_EXP = ICommonConstants.PATH_SEPARATOR + ICommonConstants.BRACKET_CLOSE_STRING;
  /**
   * Opening bracket regular expression for replacement in strings.
   */
  protected static final String OPENING_BRACKET_REG_EXP = ICommonConstants.PATH_SEPARATOR + ICommonConstants.BRACKET_OPEN_STRING;

  /**
   * Add compatibility ranges for specified range String representation to specified compatibility holder.
   * @param range_p
   * @param compatibility_p
   * @param validationContext_p
   * @return The error message, if any.
   */
  protected String addCompatibilityRanges(String range_p, Compatibility compatibility_p, IValidationContext validationContext_p) {
    // Precondition.
    if ((null == range_p) || range_p.trim().isEmpty()) {
      return null;
    }
    // Error message.
    String errorMessage = Messages.VersionsAreValidConstraint_InvalidCompatibilityPattern;
    // Split on the range separator first.
    String[] versionsAndBounds = range_p.split(ICommonConstants.COMMA_STRING);
    // Switch on range versions count.
    if (1 == versionsAndBounds.length) { // Single version.
      // Left part of the range.
      VersionAndBound asLeftMember = extractVersionAndBound(range_p, true);
      // Right part of the range.
      VersionAndBound asRightMember = extractVersionAndBound(asLeftMember._version, false);
      // Note that at this stage, the right member version contains the expected version, whereas the left member contains the version and the right bound.
      // Precondition.
      if (null == asRightMember._version) {
        // Error, no version provided.
        return MessageFormat.format(errorMessage, ModelUtil.getElementPath(compatibility_p._source), range_p,
            Messages.VersionsAreValidConstraint_NoVersionProvided);
      }
      // Version handling.
      if (asLeftMember._isBoundIncluded) {
        // Format check.
        if (!asRightMember._isBoundIncluded) {
          // Error, bad format.
          return MessageFormat.format(errorMessage, ModelUtil.getElementPath(compatibility_p._source), range_p,
              Messages.VersionsAreValidConstraint_Malformed_SingleVersionPattern);
        }
        // Cheat code !
        // Make left member valid so that the call to create version will return expected result.
        asLeftMember = extractVersionAndBound(asLeftMember._version, false);
        asLeftMember._isLeftMember = true;
        Version baseVersion = createVersion(asLeftMember, compatibility_p._source, validationContext_p);
        // Add range.
        if (null != baseVersion) {
          compatibility_p._ranges.add(new VersionRange(baseVersion, true, createVersion(asRightMember, compatibility_p._source, validationContext_p), true));
        }
      } else {
        if (asRightMember._isBoundIncluded) {
          // Error, bad format.
          return MessageFormat.format(errorMessage, ModelUtil.getElementPath(compatibility_p._source), range_p,
              Messages.VersionsAreValidConstraint_Malformed_EverythingButVersionPattern);
        }
        // Result stands for : [,version[ U ]version,]
        // Cheat code !
        // Mutate right member so that the call to create version will return expected result.
        asRightMember._isLeftMember = true;
        Version baseVersion = createVersion(asRightMember, compatibility_p._source, validationContext_p);
        if (null != baseVersion) {
          // Range for [,version[
          compatibility_p._ranges.add(new VersionRange(null, true, baseVersion, false));
          // Then range for ]version,]
          compatibility_p._ranges.add(new VersionRange(baseVersion, false, null, true));
        }
      }
      return null;
    }
    // Two versions range.
    {
      // Extract both versions.
      VersionAndBound leftVersionAndBound = extractVersionAndBound(versionsAndBounds[0], true);
      VersionAndBound rightVersionAndBound = extractVersionAndBound(versionsAndBounds[1], false);
      // Make sure both are not missing their version.
      if ((null == leftVersionAndBound._version) && (null == rightVersionAndBound._version)) {
        // Error, bad format.
        return MessageFormat.format(errorMessage, ModelUtil.getElementPath(compatibility_p._source), range_p,
            Messages.VersionsAreValidConstraint_Malformed_RangePattern);
      }
      // Left version.
      boolean leftIsOk = true;
      Version leftVersion = null;
      if (null != leftVersionAndBound._version) {
        leftVersion = createVersion(leftVersionAndBound, compatibility_p._source, validationContext_p);
        // Make sure version creation is OK.
        leftIsOk = (null != leftVersion);
      }
      // Right version.
      boolean rightIsOk = true;
      Version rightVersion = null;
      if (null != rightVersionAndBound._version) {
        rightVersion = createVersion(rightVersionAndBound, compatibility_p._source, validationContext_p);
        // Make sure version creation is OK.
        rightIsOk = (null != rightVersion);
      }
      // Add corresponding range.
      if (leftIsOk && rightIsOk) {
        compatibility_p._ranges.add(new VersionRange(leftVersion, leftVersionAndBound._isBoundIncluded, rightVersion, rightVersionAndBound._isBoundIncluded));
      }
    }
    return null;
  }

  /**
   * Add specified product category to specified selected versions (for specified context).<br>
   * This is a mock behavior as the product is not persisted as being current in specified context, but only used within this validation rule.
   * @param category_p
   * @param selectedVersions_p
   * @param context_p
   */
  protected void addProductAsSelected(Category category_p, Collection<InstallationCategory> selectedVersions_p, Context context_p) {
    // Get FullVersion value.
    String variablePath = ModelUtil.getElementPath(category_p) + ICommonConstants.PATH_SEPARATOR + ModelUtil.INSTALLATION_VAR_FULL_VERSION_NAME;
    VariableValue variableValue = DataUtil.getValue(variablePath, context_p);
    // Fall-back to BuildVersion value.
    if (null == variableValue) {
      variablePath = ModelUtil.getElementPath(category_p) + ICommonConstants.PATH_SEPARATOR + ModelUtil.INSTALLATION_VAR_PRODUCT_VERSION_NAME;
      variableValue = DataUtil.getValue(variablePath, context_p);
    }
    // Precondition.
    if (null == variableValue) {
      return;
    }
    // Get version.
    String version = variableValue.getValue();
    // Precondition.
    if (null == version) {
      return;
    }
    // Create expected structure.
    InstallationCategory currentProductVersion = ContextsFactory.eINSTANCE.createInstallationCategory();
    currentProductVersion.setReferencePath(ModelUtil.getElementPath(category_p) + ICommonConstants.PATH_SEPARATOR + version);
    selectedVersions_p.add(currentProductVersion);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.model.validation.constraint.AbstractConstraint#batchValidate(org.eclipse.emf.ecore.EObject,
   *      org.eclipse.emf.validation.IValidationContext)
   */
  @Override
  protected IStatus batchValidate(Context target_p, IValidationContext context_p) {
    // Get all selected versions for specified context.
    Collection<InstallationCategory> selectedVersions = new ArrayList<InstallationCategory>(target_p.getSelectedVersions());
    // Get compatibilities rules, and add products .
    Map<String, Collection<Compatibility>> componentToCompatibilities = new HashMap<String, Collection<Compatibility>>(0);
    {
      // Get products category contents.
      String productsCategoryPath = ModelUtil.INSTALLATION_CATEGORY_NAME + ICommonConstants.PATH_SEPARATOR + ModelUtil.INSTALLATION_CATEGORY_PRODUCTS_NAME;
      Collection<Category> productCategories = DataUtil.getCategories(DataUtil.getCategory(productsCategoryPath, target_p), target_p);
      if (null != productCategories) {
        // Cycle through product categories.
        for (Category productCategory : productCategories) {
          // Fake the product as currently selected.
          addProductAsSelected(productCategory, selectedVersions, target_p);
          // Get compatibilities rules from this product.
          InstallationCategory compatibilitiesCategory = ModelUtil.getCompatibilitiesFor(productCategory);
          // Ignore this product for compatibilities checking.
          if (null == compatibilitiesCategory) {
            continue;
          }
          // Cycle through targeted components (materialized as sub-categories).
          for (Category targetedComponentCategory : compatibilitiesCategory.getCategories()) {
            // Get compatibilities variable.
            Variable compatibilitiesVariable = ModelUtil.getVariable(targetedComponentCategory, ModelUtil.INSTALLATION_COMPATIBILITIES_NAME);
            if ((null == compatibilitiesVariable) || compatibilitiesVariable.getValues().isEmpty()) {
              // Skip this component.
              continue;
            }
            // Component name.
            String componentName = targetedComponentCategory.getName();
            // Compatibilities.
            Collection<Compatibility> compatibilities = componentToCompatibilities.get(componentName);
            if (null == compatibilities) {
              compatibilities = new ArrayList<Compatibility>(1);
              componentToCompatibilities.put(componentName, compatibilities);
            }
            Compatibility compatibility = new Compatibility();
            compatibility._source = productCategory;
            compatibility._ranges = new ArrayList<VersionRange>(compatibilitiesVariable.getValues().size());
            // Create compatibility ranges.
            for (VariableValue compatibilityValue : compatibilitiesVariable.getValues()) {
              String errorMessage = addCompatibilityRanges(compatibilityValue.getValue(), compatibility, context_p);
              if (null != errorMessage) {
                addStatus(createFailureStatusWithDescription(productCategory, context_p, errorMessage));
              }
            }
            // Add compatibility.
            compatibilities.add(compatibility);
          }
        }
      }
    }
    // Cycle through current versions.
    for (InstallationCategory selectedVersion : selectedVersions) {
      // Get reference path.
      IPath referencePath = new Path(selectedVersion.getReferencePath());
      // Path should at least be made of \ComponentType\ComponentName\Version.
      if (3 > referencePath.segmentCount()) {
        // Retain error.
        addStatus(createFailureStatusWithDescription(target_p, context_p,
            MessageFormat.format(Messages.VersionsAreValidConstraint_Malformed_SelectedVersion, target_p.getName(), selectedVersion.getReferencePath())));
        // Then ignore version.
        continue;
      }
      // Extract version.
      String versionString = referencePath.lastSegment();
      referencePath = referencePath.removeLastSegments(1);
      // Extract component.
      String component = referencePath.lastSegment();
      ModelElement componentElement = DataUtil.getCategory(referencePath.toOSString(), target_p);
      referencePath = referencePath.removeLastSegments(1);
      // Extract component type.
      String componentType = referencePath.lastSegment();
      // Get targeted element.
      ModelElement resolvedReference = DataUtil.getCategory(selectedVersion.getReferencePath(), target_p);

      // if the resolvedReference is null it means that we are on a Orchestra product, the version is a variable and not a category.
      // so we set the resolved reference to the containing category
      if (resolvedReference == null) {
        resolvedReference = componentElement;
      }
      // First check : make sure version still exists.
      {
        // Exclude fake current versions from the test.
        if (!ModelUtil.INSTALLATION_CATEGORY_PRODUCTS_NAME.equals(componentType)) {
          // No real version found.
          if (null == resolvedReference) {
            addStatus(createFailureStatusWithDescription(
                (null != componentElement) ? componentElement : target_p,
                context_p,
                MessageFormat.format(Messages.VersionsAreValidConstraint_MissingVersion, component,
                    getAskingContext().getName() + selectedVersion.getReferencePath())));
            // Then ignore version.
            continue;
          }
        }
      }
      // Second check : make sure version is compatible with provided compatibilities ranges.
      {
        Collection<Compatibility> compatibilities = componentToCompatibilities.get(component);
        // Nothing to check against.
        if ((null == compatibilities) || compatibilities.isEmpty()) {
          continue;
        }
        // Component version.
        VersionAndBound versionAndBound = new VersionAndBound();
        versionAndBound._version = versionString;
        // Make sure creation will return a SimpleVersion.
        versionAndBound._isLeftMember = true;
        Version version = createVersion(versionAndBound, resolvedReference, context_p);
        // Error on version format, already thrown by createVersion.
        if (null == version) {
          continue;
        }
        // Cycle through compatibilities.
        for (Compatibility compatibility : compatibilities) {
          // Check version against compatibility.
          if (!compatibility.isIncluded(version)) {
            // Point to resolved reference.
            EObject target = resolvedReference;
            // Fall back to resolved component.
            if (null == target) {
              target = componentElement;
            }
            // Fall back to compatibility source.
            if (null == target) {
              target = compatibility._source;
            }
            addStatus(createFailureStatusWithDescription(
                target,
                context_p,
                MessageFormat.format(Messages.VersionsAreValidConstraint_NotCompatibleVersion,
                    getAskingContext().getName() + selectedVersion.getReferencePath(), versionString, ModelUtil.getElementPath(compatibility._source))));
          }
          // Do not stop here.
          // Show as many compatibility issues as possible.
        }
      }
    }
    return null;
  }

  /**
   * Version factory method.
   * @param version_p
   * @param modelElement_p The model element which points to the version.
   * @param validationContext_p The validation context in use.
   * @return
   */
  protected Version createVersion(VersionAndBound version_p, ModelElement modelElement_p, IValidationContext validationContext_p) {
    try {
      if (version_p._isLeftMember || !version_p._isBoundIncluded) {
        // For a left member, or a right member that should be excluded, always return a simple version.
        return new SimpleVersion(version_p._version);
      }
      // For a right member, that should be included, always return an extended version.
      return new ExtendedVersion(version_p._version);
    } catch (Exception exception_p) {
      addStatus(createFailureStatusWithDescription(modelElement_p, validationContext_p,
          MessageFormat.format(Messages.VersionsAreValidConstraint_Malformed_VersionPattern, getFullPath(modelElement_p), version_p._version)));
      return null;
    }
  }

  /**
   * Extract version string and bound characteristics from specified range part.
   * @param versionAndBound_p The string representation of both the version and the bound.
   * @param isLeftMember_p
   * @return
   */
  protected VersionAndBound extractVersionAndBound(String versionAndBound_p, boolean isLeftMember_p) {
    // Result.
    VersionAndBound result = new VersionAndBound();
    // Precondition.
    if (null == versionAndBound_p) {
      return result;
    }
    // Remind position.
    result._isLeftMember = isLeftMember_p;
    // Then handle it.
    if (isLeftMember_p) {
      result._version = versionAndBound_p;
      if (result._version.startsWith(ICommonConstants.BRACKET_OPEN_STRING)) {
        result._isBoundIncluded = true;
        result._version = result._version.replaceFirst(OPENING_BRACKET_REG_EXP, ICommonConstants.EMPTY_STRING).trim();
      } else if (result._version.startsWith(ICommonConstants.BRACKET_CLOSE_STRING)) {
        result._isBoundIncluded = false;
        result._version = result._version.replaceFirst(CLOSING_BRACKET_REG_EXP, ICommonConstants.EMPTY_STRING).trim();
      }
    } else {
      result._version = versionAndBound_p;
      if (result._version.endsWith(ICommonConstants.BRACKET_CLOSE_STRING)) {
        result._isBoundIncluded = true;
        result._version = result._version.replaceAll(CLOSING_BRACKET_REG_EXP, ICommonConstants.EMPTY_STRING).trim();
      } else if (result._version.endsWith(ICommonConstants.BRACKET_OPEN_STRING)) {
        result._isBoundIncluded = false;
        result._version = result._version.replaceAll(OPENING_BRACKET_REG_EXP, ICommonConstants.EMPTY_STRING).trim();
      }
    }
    // No version provided.
    if ((null != result._version) && result._version.isEmpty()) {
      result._version = null;
    }
    return result;
  }

  /**
   * A compatibility is
   * @author t0076261
   */
  protected class Compatibility {
    protected List<VersionRange> _ranges;
    protected Category _source;

    /**
     * Is specified version included in compatibility ranges ?
     * @param version_p
     * @param ranges_p
     * @return
     */
    protected boolean isIncluded(Version version_p) {
      // No range means it is compatible.
      if ((null == _ranges) || _ranges.isEmpty()) {
        return true;
      }
      // Cycle through range.
      for (VersionRange versionRange : _ranges) {
        if (versionRange.isIncluded(version_p)) {
          return true;
        }
      }
      return false;
    }

  }

  /**
   * The extended version of specified one.<br>
   * The extended version includes all children versions of specified one.
   * @author t0076261
   */
  protected class ExtendedVersion extends SimpleVersion {
    /**
     * The extended version of specified one.
     * @param version_p
     */
    public ExtendedVersion(String version_p) {
      super(version_p);
    }

    /**
     * @see org.osgi.framework.Version#getMicro()
     */
    @Override
    public int getMicro() {
      if (0 == super.getMicro()) {
        return Integer.MAX_VALUE;
      }
      return super.getMicro();
    }

    /**
     * @see org.osgi.framework.Version#getMinor()
     */
    @Override
    public int getMinor() {
      if (0 == super.getMinor()) {
        return Integer.MAX_VALUE;
      }
      return super.getMinor();
    }

    /**
     * @see org.osgi.framework.Version#getQualifier()
     */
    @Override
    public String getQualifier() {
      if (ICommonConstants.EMPTY_STRING.equals(super.getQualifier())) {
        return UNLIMITED_QUALIFIER;
      }
      return super.getQualifier();
    }
  }

  /**
   * A version that compares object differently from default implementation.
   * @author t0076261
   */
  protected class SimpleVersion extends Version {
    /**
     * The qualifier constant for unlimited value.
     */
    protected static final String UNLIMITED_QUALIFIER = "__UQV--"; //$NON-NLS-1$

    /**
     * Constructor.
     * @param version_p
     */
    public SimpleVersion(String version_p) {
      super(version_p);
    }

    /**
     * @see org.osgi.framework.Version#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Version other_p) {
      if (other_p == this) { // quicktest
        return 0;
      }

      int result = getMajor() - other_p.getMajor();
      if (result != 0) {
        return result;
      }

      result = getMinor() - other_p.getMinor();
      if (result != 0) {
        return result;
      }

      result = getMicro() - other_p.getMicro();
      if (result != 0) {
        return result;
      }

      if (UNLIMITED_QUALIFIER.equals(getQualifier())) {
        if (UNLIMITED_QUALIFIER.equals(other_p.getQualifier())) {
          return 0;
        }
        return +1;
      } else if (UNLIMITED_QUALIFIER.equals(other_p.getQualifier())) {
        return -1;
      }
      return getQualifier().compareTo(other_p.getQualifier());
    }
  }

  /**
   * A version and its associated bound characteristics.
   * @author t0076261
   */
  protected class VersionAndBound {
    protected boolean _isBoundIncluded;
    protected String _version;
    protected boolean _isLeftMember;
  }
}