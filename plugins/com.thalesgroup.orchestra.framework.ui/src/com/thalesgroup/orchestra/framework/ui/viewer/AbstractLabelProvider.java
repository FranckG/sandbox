/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedImage.Point;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.environment.EnvironmentActivator;
import com.thalesgroup.orchestra.framework.environment.IEnvironmentHandler;
import com.thalesgroup.orchestra.framework.environment.ui.AbstractVariablesHandler;
import com.thalesgroup.orchestra.framework.model.ModelUtil;
import com.thalesgroup.orchestra.framework.model.contexts.Category;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable;
import com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.FileVariable;
import com.thalesgroup.orchestra.framework.model.contexts.FolderVariable;
import com.thalesgroup.orchestra.framework.model.contexts.ModelElement;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariable;
import com.thalesgroup.orchestra.framework.model.contexts.OverridingVariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.PendingElementsCategory;
import com.thalesgroup.orchestra.framework.model.contexts.Variable;
import com.thalesgroup.orchestra.framework.model.contexts.VariableValue;
import com.thalesgroup.orchestra.framework.model.contexts.provider.ContextsEditPlugin;
import com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.DataUtil;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.internal.validation.UIValidationHelper;
import com.thalesgroup.orchestra.framework.ui.viewer.component.AbstractNode;

/**
 * Abstract common label provider.
 * @author t0076261
 */
public abstract class AbstractLabelProvider extends CellLabelProvider implements ILabelProvider {
  protected static final String IMAGE_PATH_DISABLED_CATEGORY = "full/obj16/DCategory"; //$NON-NLS-1$
  protected static final String IMAGE_PATH_DISABLED_FILE_VARIABLE = "full/obj16/DFileVariable"; //$NON-NLS-1$
  protected static final String IMAGE_PATH_DISABLED_ENVIRONMENT_VARIABLE = "full/obj16/DEnvironmentVariable"; //$NON-NLS-1$
  protected static final String IMAGE_PATH_DISABLED_FOLDER_VARIABLE = "full/obj16/DFolderVariable"; //$NON-NLS-1$
  protected static final String IMAGE_PATH_DISABLED_VARIABLE = "full/obj16/DVariable"; //$NON-NLS-1$
  protected static final String IMAGE_PATH_DISABLED_VARIABLE_VALUE = "full/obj16/DVariableValue"; //$NON-NLS-1$
  protected static final String OVERLAY_IMAGE_PATH_CONTEXT_IN_WORKSPACE = "full/ovr16/in_ws.gif"; //$NON-NLS-1$
  protected static final String OVERLAY_IMAGE_PATH_CONTRIBUTED_ELEMENT = "full/ovr16/link_ovr.gif"; //$NON-NLS-1$
  protected static final String OVERLAY_IMAGE_PATH_FINAL_ELEMENT = "full/ovr16/final_co.gif"; //$NON-NLS-1$
  protected static final String OVERLAY_IMAGE_PATH_INHERITED_ELEMENT = "full/ovr16/implm_co.gif"; //$NON-NLS-1$
  protected static final String OVERLAY_IMAGE_PATH_OVERRIDDEN_ELEMENT = "full/ovr16/over_co.gif"; //$NON-NLS-1$
  protected static final DisabledVariableImageResolverSwitch DISABLED_VARIABLE_IMAGE_RESOLVER_SWITCH = new DisabledVariableImageResolverSwitch();
  /**
   * Real label provider.
   */
  protected AdapterFactoryLabelProvider _provider;
  /**
   * Environment support implementation.
   */
  protected EnvironmentSupport _environmentSupport;

  /**
   * Constructor.
   */
  public AbstractLabelProvider() {
    _provider = new AdapterFactoryLabelProvider(ModelHandlerActivator.getDefault().getEditingDomain().getAdapterFactory());
    _environmentSupport = new EnvironmentSupport();
  }

  /**
   * Add error decoration at image computation time, if needed.
   * @param element_p
   * @param editionContext_p
   * @param keySuffixes_p
   * @param images_p
   * @param positions_p
   * @return <code>true</code> if an error decoration is added, <code>false</code> otherwise.
   */
  @SuppressWarnings("boxing")
  protected boolean addErrorDecoration(ModelElement element_p, Context editionContext_p, List<String> keySuffixes_p, List<Object> images_p,
      List<Point> positions_p) {
    boolean result = false;
    // Add error decoration, if any.
    Integer severity = null;
    // Do only decorate with errors if context is validated one.
    severity = UIValidationHelper.getInstance().getMaximumSeverity(editionContext_p, element_p);
    if (null != severity) {
      String errorKey = null;
      switch (severity) {
        case IMarker.SEVERITY_ERROR:
          errorKey = FieldDecorationRegistry.DEC_ERROR;
        break;
        case IMarker.SEVERITY_WARNING:
          errorKey = FieldDecorationRegistry.DEC_WARNING;
        break;
        case IMarker.SEVERITY_INFO:
          errorKey = FieldDecorationRegistry.DEC_INFORMATION;
        break;
        default:
        break;
      }
      if (null != errorKey) {
        keySuffixes_p.add(errorKey);
        images_p.add(FieldDecorationRegistry.getDefault().getFieldDecoration(errorKey).getImage());
        positions_p.add(OverlayPosition.NO._point);
        result = true;
      }
    }
    return result;
  }

  /**
   * @see org.eclipse.jface.viewers.BaseLabelProvider#dispose()
   */
  @Override
  public void dispose() {
    try {
      super.dispose();
    } finally {
      if (null != _provider) {
        _provider.dispose();
        _provider = null;
      }
    }
  }

  /**
   * Get image for specified parameters.<br>
   * It does also check if element is on error or not, and adds decorator accordingly.<br>
   * Note that this implementation should not be replaced (by overriding means) by implementors.<br>
   * It is provided to allow for other cases, that are not taken into account by default implementation.<br>
   * But default implementation should always be referred to.<br>
   * @param element_p
   * @param editionContext_p
   * @param keySuffixes_p
   * @param images_p
   * @param positions_p
   * @return
   */
  protected Image getComposedImage(ModelElement element_p, Context editionContext_p, List<String> keySuffixes_p, List<Object> images_p, List<Point> positions_p) {
    // Add error decoration, if any.
    addErrorDecoration(element_p, editionContext_p, keySuffixes_p, images_p, positions_p);
    // Compute composed image.
    return OrchestraFrameworkUiActivator.getDefault().getComposedImage(getImageKey(element_p, keySuffixes_p), images_p, positions_p);
  }

  /**
   * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
   */
  public Color getForeground(EObject object_p, final Context editionContext_p) {
    // Precondition.
    if (null == object_p) {
      return null;
    }
    // No edition context, no foreground color returned.
    if (null == editionContext_p) {
      return null;
    }
    return new ContextsSwitch<Color>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
       */
      @Override
      public Color caseVariableValue(VariableValue variableValue_p) {
        // For variable values, use same color as variable.
        return defaultCase(variableValue_p.eContainer());
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#defaultCase(org.eclipse.emf.ecore.EObject)
       */
      @Override
      public Color defaultCase(EObject element_p) {
        if (ModelHandlerActivator.getDefault().getEditingDomain().isElementCut(editionContext_p, element_p)) {
          return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
        }
        return null;
      }
    }.doSwitch(object_p);

  }

  /**
   * Get image for specified element in specified edition context.
   * @param object_p
   * @param editionContext_p
   * @return
   */
  protected Image getImage(EObject object_p, final Context editionContext_p) {
    // Precondition.
    if (null == object_p) {
      return null;
    }
    // No edition context, return image as is.
    if (null == editionContext_p) {
      return _provider.getImage(object_p);
    }
    return new ContextsSwitch<Image>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseCategory(com.thalesgroup.orchestra.framework.model.contexts.Category)
       */
      @Override
      public Image caseCategory(Category category_p) {
        Couple<List<Object>, List<Point>> imageStructures = getImageStructures(category_p);
        List<Object> images = imageStructures.getKey();
        List<Point> positions = imageStructures.getValue();
        List<String> suffixes = new ArrayList<String>(0);
        // Cut category -> Put the disabled image.
        if (ModelHandlerActivator.getDefault().getEditingDomain().isElementCut(editionContext_p, category_p)) {
          suffixes.add("cut"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(IMAGE_PATH_DISABLED_CATEGORY));
          positions.add(OverlayPosition.NO._point);
        }
        // Transient category.
        if (ModelUtil.isTransientElement(category_p)) {
          suffixes.add("transient"); //$NON-NLS-1$
        }
        // Contributed category (this category has a super category and is defined in this context).
        if (null != category_p.getSuperCategory() && !DataUtil.isExternalElement(category_p, editionContext_p)) {
          suffixes.add("contributed"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_CONTRIBUTED_ELEMENT));
          positions.add(OverlayPosition.SE._point);
        }
        // Inherited with contributed values.
        else if (!DataUtil.getOverridingVariables(category_p, editionContext_p).isEmpty()) {
          suffixes.add("overriden"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_OVERRIDDEN_ELEMENT));
          positions.add(OverlayPosition.SE._point);
        }
        // External (inherited) category.
        else if (DataUtil.isExternalElement(category_p, editionContext_p)) {
          suffixes.add("inherited"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_INHERITED_ELEMENT));
          positions.add(OverlayPosition.SE._point);
        }
        return getComposedImage(category_p, editionContext_p, suffixes, images, positions);
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseContext(com.thalesgroup.orchestra.framework.model.contexts.Context)
       */
      @Override
      public Image caseContext(Context context_p) {
        Couple<List<Object>, List<Point>> imageStructures = getImageStructures(context_p);
        List<Object> images = imageStructures.getKey();
        List<Point> positions = imageStructures.getValue();
        List<String> suffixes = new ArrayList<String>(0);
        RootContextsProject project = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(context_p.eResource().getURI());
        if (project.isInWorkspace()) {
          suffixes.add("inWorkspace"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_CONTEXT_IN_WORKSPACE));
          positions.add(OverlayPosition.SO._point);
        }
        return getComposedImage(context_p, editionContext_p, suffixes, images, positions);
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariable(com.thalesgroup.orchestra.framework.model.contexts.Variable)
       */
      @Override
      public Image caseVariable(Variable variable_p) {
        Context variableContext = ModelUtil.getContext(variable_p);
        Couple<List<Object>, List<Point>> imageStructures = getImageStructures(variable_p);
        List<Object> images = imageStructures.getKey();
        List<Point> positions = imageStructures.getValue();
        List<String> suffixes = new ArrayList<String>(0);
        // Cut Variable -> Put the disabled image.
        if (ModelHandlerActivator.getDefault().getEditingDomain().isElementCut(editionContext_p, variable_p)) {
          suffixes.add("cut"); //$NON-NLS-1$
          images.add(DISABLED_VARIABLE_IMAGE_RESOLVER_SWITCH.doSwitch(variable_p));
          positions.add(OverlayPosition.NO._point);
        }
        if (ModelUtil.isTransientElement(variable_p)) {
          suffixes.add("transient"); //$NON-NLS-1$
        }
        // Final variable
        if (variable_p.isFinal()) {
          suffixes.add("final"); //$NON-NLS-1$
          if (editionContext_p == variableContext) {
            // Fall back.
            // The final variable happens to be a local one.
            // The image returned by the item provider won't fit this case.
            // Thus it is overridden by default variable image.
            suffixes.add("local"); //$NON-NLS-1$
            images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_FINAL_ELEMENT));
            positions.add(OverlayPosition.NE._point);
          } else {
            // When displaying a final variable in an inherited context, show it as disabled since it is not modifiable.
            suffixes.add("inherited"); //$NON-NLS-1$
            images.add(DISABLED_VARIABLE_IMAGE_RESOLVER_SWITCH.doSwitch(variable_p));
            positions.add(OverlayPosition.NO._point);
          }
        }
        OverridingVariable overridingVariable = DataUtil.getOverridingVariable(variable_p, editionContext_p);
        // Overridden variable.
        if ((null != overridingVariable) && (editionContext_p == ModelUtil.getContext(overridingVariable))) {
          suffixes.add("overridden"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_OVERRIDDEN_ELEMENT));
          positions.add(OverlayPosition.SE._point);
        }
        // Inherited variable.
        else if (editionContext_p != variableContext) {
          suffixes.add("inherited"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_INHERITED_ELEMENT));
          positions.add(OverlayPosition.SE._point);
        }
        // Contributed variable
        else if (null != variable_p.getSuperCategory()) {
          suffixes.add("contributed"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_CONTRIBUTED_ELEMENT));
          positions.add(OverlayPosition.SE._point);
        }
        return getComposedImage(variable_p, editionContext_p, suffixes, images, positions);
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
       */
      @Override
      public Image caseVariableValue(VariableValue variableValue_p) {
        Context variableValueContext = ModelUtil.getContext(variableValue_p);
        Couple<List<Object>, List<Point>> imageStructures = getImageStructures(variableValue_p);
        List<Object> images = imageStructures.getKey();
        List<Point> positions = imageStructures.getValue();
        List<String> suffixes = new ArrayList<String>(0);
        // VariableValue contained in a cut variable -> Put the disabled image.
        if (ModelHandlerActivator.getDefault().getEditingDomain().isElementCut(editionContext_p, variableValue_p.eContainer())) {
          suffixes.add("cut"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(IMAGE_PATH_DISABLED_VARIABLE_VALUE));
          positions.add(OverlayPosition.NO._point);
        }
        if (ModelUtil.isTransientElement(variableValue_p)) {
          suffixes.add("transient"); //$NON-NLS-1$
        }
        // Variable value belonging to a final variable.
        ModelElement containingVariable = ModelUtil.getLogicalContainer(variableValue_p, editionContext_p);
        if (containingVariable instanceof Variable && ((Variable) containingVariable).isFinal()) {
          suffixes.add("final"); //$NON-NLS-1$
          if (editionContext_p != variableValueContext) {
            // When displaying a final variable in an inherited context, show it as disabled since it is not modifiable.
            suffixes.add("inherited"); //$NON-NLS-1$
            images.add(ContextsEditPlugin.INSTANCE.getImage(IMAGE_PATH_DISABLED_VARIABLE_VALUE));
            positions.add(OverlayPosition.NO._point);
          }
        }
        // Inherited variable value.
        if ((null != variableValueContext) && (editionContext_p != variableValueContext)) {
          suffixes.add("inherited"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_INHERITED_ELEMENT));
          positions.add(OverlayPosition.SE._point);
        }
        // Overridden variable value.
        else if (variableValue_p instanceof OverridingVariableValue) {
          suffixes.add("overridden"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_OVERRIDDEN_ELEMENT));
          positions.add(OverlayPosition.SE._point);
        }
        // Contributed value.
        else if (variableValue_p.eContainer() instanceof OverridingVariable) {
          suffixes.add("contributed"); //$NON-NLS-1$
          images.add(ContextsEditPlugin.INSTANCE.getImage(OVERLAY_IMAGE_PATH_CONTRIBUTED_ELEMENT));
          positions.add(OverlayPosition.SE._point);
        }
        return getComposedImage(variableValue_p, editionContext_p, suffixes, images, positions);
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#defaultCase(org.eclipse.emf.ecore.EObject)
       */
      @Override
      public Image defaultCase(EObject element_p) {
        return _provider.getImage(element_p);
      }
    }.doSwitch(object_p);
  }

  /**
   * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
   */
  public Image getImage(Object element_p) {
    if (element_p instanceof EObject) {
      return getImage((EObject) element_p, null);
    }
    return null;
  }

  /**
   * Compute image key from element and specified suffixes.
   * @param object_p
   * @param suffixes_p
   * @return
   */
  protected String getImageKey(EObject object_p, List<String> suffixes_p) {
    StringBuilder builder = new StringBuilder(object_p.eClass().getName());
    for (String string : suffixes_p) {
      if (null != string) {
        builder.append('_').append(string);
      }
    }
    return builder.toString();
  }

  /**
   * Get minimum image structures for specified element.
   * @param object_p
   * @return
   */
  protected Couple<List<Object>, List<Point>> getImageStructures(EObject object_p) {
    List<Object> images = new ArrayList<Object>(1);
    images.add(_provider.getImage(object_p));
    List<Point> positions = new ArrayList<Point>(1);
    positions.add(OverlayPosition.NO._point);
    return new Couple<List<Object>, List<Point>>(images, positions);
  }

  /**
   * Get text for specified element in specified edition context.
   * @param object_p
   * @param editionContext_p
   * @return
   */
  protected String getText(EObject object_p, final Context editionContext_p) {
    // Precondition.
    if (null == object_p) {
      return null;
    }
    // No edition context, return text as is.
    if (null == editionContext_p) {
      return _provider.getText(object_p);
    }
    String temp = new ContextsSwitch<String>() {
      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseContext(com.thalesgroup.orchestra.framework.model.contexts.Context)
       */
      @Override
      public String caseContext(Context context_p) {
        String contextDisplayName = defaultCase(context_p);
        // If not in administrator mode
        boolean isAdmistrator = ProjectActivator.getInstance().isAdministrator();
        if (!isAdmistrator) {
          contextDisplayName =
              MessageFormat.format(Messages.AbstractLabelProvider_Context_Label_User, context_p.getName(), context_p.getSuperContext().getName());
        }
        // If this is current context, add specific text.
        boolean isCurrentContext = ModelHandlerActivator.getDefault().getDataHandler().isCurrentContext(context_p);
        if (isCurrentContext) {
          return MessageFormat.format(Messages.AbstractLabelProvider_Context_Label_CurrentContext, contextDisplayName);
        }
        return contextDisplayName;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseEnvironmentVariableValue(com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariableValue)
       */
      @Override
      public String caseEnvironmentVariableValue(EnvironmentVariableValue environmentVariableValue_p) {
        String result = caseVariableValue(environmentVariableValue_p);
        // Super behavior has not returned any value.
        // That is no name was user defined.
        if ((null == result) || ICommonConstants.EMPTY_STRING.equals(result.trim())) {
          // Compute name based on environment capabilities.
          String environmentId = environmentVariableValue_p.getEnvironmentId();
          Couple<IStatus, IEnvironmentHandler> environmentHandlerCouple =
              EnvironmentActivator.getInstance().getEnvironmentRegistry().getEnvironmentHandler(environmentId);
          if (environmentHandlerCouple.getKey().isOK()) {
            // Setup required environment.
            _environmentSupport._editionContext = editionContext_p;
            boolean handlerSet = EnvironmentActivator.getInstance().setVariablesHandlerIfNone(_environmentSupport);
            try {
              result = environmentHandlerCouple.getValue().toString(ModelUtil.convertEnvironmentVariableValues(environmentVariableValue_p));
            } finally {
              // Restore environment.
              _environmentSupport._editionContext = null;
              if (handlerSet) {
                EnvironmentActivator.getInstance().setVariablesHandler(null);
              }
            }
          }
        }
        return result;
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#casePendingElementsCategory(com.thalesgroup.orchestra.framework.model.contexts.PendingElementsCategory)
       */
      @Override
      public String casePendingElementsCategory(PendingElementsCategory pendingElementsCategory_p) {
        EObject eContainer = pendingElementsCategory_p.eContainer();
        if (eContainer instanceof Context) {
          Context sourceContext = (Context) eContainer;
          if (!ModelHandlerActivator.getDefault().getDataHandler().isCurrentContext(sourceContext)) {
            return pendingElementsCategory_p.getName() + " (" + sourceContext.getName() + ")"; //$NON-NLS-1$//$NON-NLS-2$
          }
        }
        return pendingElementsCategory_p.getName();
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariableValue(com.thalesgroup.orchestra.framework.model.contexts.VariableValue)
       */
      @Override
      public String caseVariableValue(VariableValue variableValue_p) {
        return defaultCase(DataUtil.getSubstitutedValue(variableValue_p, editionContext_p));
      }

      /**
       * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#defaultCase(org.eclipse.emf.ecore.EObject)
       */
      @Override
      public String defaultCase(EObject element_p) {
        return _provider.getText(element_p);
      }
    }.doSwitch(object_p);
    return temp;
  }

  /**
   * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
   */
  public String getText(Object element_p) {
    // Adapt to EObject, if needed.
    Object element = element_p;
    if (element_p instanceof IAdaptable) {
      element = ((IAdaptable) element).getAdapter(EObject.class);
    }
    // Get edition context from tree node.
    Context editionContext = null;
    if (element_p instanceof AbstractNode<?>) {
      editionContext = ((AbstractNode<?>) element_p).getEditionContext();
    }
    // Go for it.
    if (element instanceof EObject) {
      return getText((EObject) element, editionContext);
    }
    return null;
  }

  /**
   * Switch used to get the correct disabled image for all types of variable.
   * @author T0052089
   */
  protected static class DisabledVariableImageResolverSwitch extends ContextsSwitch<Object> {
    /**
     * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseEnvironmentVariable(com.thalesgroup.orchestra.framework.model.contexts.EnvironmentVariable)
     */
    @Override
    public Object caseEnvironmentVariable(EnvironmentVariable var_p) {
      return ContextsEditPlugin.INSTANCE.getImage(IMAGE_PATH_DISABLED_ENVIRONMENT_VARIABLE);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseFileVariable(com.thalesgroup.orchestra.framework.model.contexts.FileVariable)
     */
    @Override
    public Object caseFileVariable(FileVariable var_p) {
      return ContextsEditPlugin.INSTANCE.getImage(IMAGE_PATH_DISABLED_FILE_VARIABLE);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseFolderVariable(com.thalesgroup.orchestra.framework.model.contexts.FolderVariable)
     */
    @Override
    public Object caseFolderVariable(FolderVariable var_p) {
      return ContextsEditPlugin.INSTANCE.getImage(IMAGE_PATH_DISABLED_FOLDER_VARIABLE);
    }

    /**
     * @see com.thalesgroup.orchestra.framework.model.contexts.util.ContextsSwitch#caseVariable(com.thalesgroup.orchestra.framework.model.contexts.Variable)
     */
    @Override
    public Object caseVariable(Variable var_p) {
      return ContextsEditPlugin.INSTANCE.getImage(IMAGE_PATH_DISABLED_VARIABLE);
    }
  }

  /**
   * Image overlay positions.
   * @author t0076261
   */
  protected enum OverlayPosition {
    NE(9, 0), NO(0, 0), SE(9, 9), SO(0, 9);
    protected final Point _point;

    OverlayPosition(int x_p, int y_p) {
      _point = new Point();
      _point.x = x_p;
      _point.y = y_p;
    }
  }

  /**
   * @author t0076261
   */
  protected class EnvironmentSupport extends AbstractVariablesHandler {
    /**
     * Context to use within computations.
     */
    protected Context _editionContext;

    /**
     * @see com.thalesgroup.orchestra.framework.environment.ui.IVariablesHandler#getSubstitutedValue(java.lang.String)
     */
    @Override
    public String getSubstitutedValue(String rawValue_p) {
      // Precondition.
      if (null == rawValue_p) {
        return null;
      }
      return DataUtil.getSubstitutedValue(rawValue_p, _editionContext);
    }
  }
}