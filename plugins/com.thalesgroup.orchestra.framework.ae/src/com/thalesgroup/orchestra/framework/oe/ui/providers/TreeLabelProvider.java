/**
 * <p>Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management</p>
 * <p>Société : Thales Services - Engineering & Process Management</p>
 * <p>Thales Part Number 16 262 601</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.providers;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefactProperties;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.ArtefactsDescriptionLoader;
import com.thalesgroup.orchestra.framework.oe.artefacts.description.IArtefactTypeDescription;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState.GroupType;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.AbstractNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;

/**
 * <p>
 * Title : ArtifactsTreeLabelProvider
 * </p>
 * <p>
 * Description : <code>ArtifactExplorerLabelProvider</code> label provider.
 * </p>
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class TreeLabelProvider extends CellLabelProvider implements ILabelProvider, IFontProvider, ILabelDecorator {

  protected static final String NOT_ALLOWED = "decorators/not_allowed.gif"; //$NON-NLS-1$

  protected OrchestraExplorerState _orchestraExplorerState;

  protected OrchestraExplorerActivator _activator = OrchestraExplorerActivator.getDefault();

  public TreeLabelProvider(OrchestraExplorerState orchestraExplorerState_p) {
    _orchestraExplorerState = orchestraExplorerState_p;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
   */
  FontRegistry _registry = new FontRegistry();

  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.viewers.IBaseLabelProvider# addListener(org.eclipse.jface.viewers.ILabelProviderListener)
   */
  @Override
  public void addListener(ILabelProviderListener listener) {
    // Nothing
  }

  /**
   * @see org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse.swt.graphics.Image, java.lang.Object)
   */
  @Override
  public Image decorateImage(Image image_p, Object element_p) {
    Image result = image_p;
    if (element_p instanceof ArtefactNode) {
      ArtefactNode artefactNode = (ArtefactNode) element_p;
      Artefact artefact = artefactNode.getValue();
      if (!artefact.getGenericArtifact().isToolAtomicallyExtractable()) {
        // Synchronization is required because the image registry is shared
        // among multiple orchestra explorers
        synchronized (_activator) {
          // Build the image descriptor key
          String imageDescriptorKey = artefact.getUri().getRootType() + artefact.getUri().getObjectType() + NOT_ALLOWED;
          ImageDescriptor imageDescriptor = OrchestraExplorerActivator.getDefault().getImageDescriptor(imageDescriptorKey);
          if (null == imageDescriptor) {
            // Decorate
            Image decoratorImage = OrchestraExplorerActivator.getDefault().getImage(NOT_ALLOWED);
            imageDescriptor = new OverlayedImageDescriptor(image_p, decoratorImage, false, true);
            OrchestraExplorerActivator.getDefault().getImageRegistry().put(imageDescriptorKey, imageDescriptor);
          }
          result = OrchestraExplorerActivator.getDefault().getImage(imageDescriptorKey);
        }
      }
    }
    return result;
  }

  /**
   * @see org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.String, java.lang.Object)
   */
  @Override
  public String decorateText(String text_p, Object element_p) {
    // No text decoration
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
   */
  public Font getFont(Object element) {
    if (element instanceof ArtefactNode) {
      ArtefactNode artefactNode = (ArtefactNode) element;
      if (RootArtefactsBag.getInstance().isNew(artefactNode.getValue())) {
        return _registry.getBold(Display.getCurrent().getSystemFont().getFontData()[0].getName());
      }
    }
    return null;
  }

  /**
   * Returns the foreground color for the label
   * @param element
   * @return color
   */
  public Color getForeground(Object element) {
    Color itColor = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
    if (element instanceof ArtefactNode) {
      ArtefactNode artefactNode = (ArtefactNode) element;
      if (RootArtefactsBag.getInstance().isOld(artefactNode.getValue())) {
        itColor = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
      }
    }
    return itColor;
  }

  /**
   * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
   */
  @Override
  public Image getImage(Object element_p) {
    return getImage(element_p, false);
  }

  /**
   * @param obj
   * @param reportErrors_p <code>true</code> to report errors to the end user, <code>false</code> to remain silent.
   * @return the image for the object if it exists, otherwise it returns the default image.
   */
  public Image getImage(Object obj, boolean reportErrors_p) {
    Image result = null;
    if (obj instanceof ArtefactNode) {
      ArtefactNode artefactNode = (ArtefactNode) obj;
      String rootType = artefactNode.getValue().getUri().getRootType();
      result = OrchestraExplorerActivator.getDefault().getImage(rootType, artefactNode.getValue().getGenericArtifact().getIcon());
    } else if (obj instanceof StringNode) {
      StringNode node = (StringNode) obj;
      if ((_orchestraExplorerState.getGroupType() == GroupType.TYPE) && (node.getRootNode() == node)) {
        String value = node.getValue();
        ArtefactsDescriptionLoader instance = ArtefactsDescriptionLoader.getInstance();
        IArtefactTypeDescription artefactTypeDescription = instance.getArtefactTypeDescription(value + '.');
        // Null check, happens when the ConfigurationDirectories are missing or wrong
        // Return a default value to avoid a NPE returned to the user
        if (null != artefactTypeDescription) {
          String icon = artefactTypeDescription.getIcon();
          result = OrchestraExplorerActivator.getDefault().getImage(value, icon);
        } else {
          result = OrchestraExplorerActivator.getDefault().getImage(IImageConstants.DESC_FOLD);
          // Display warning as artifact parameters not found in ConfigurationDirectories
          if (reportErrors_p) {
            IStatus status =
                new Status(IStatus.WARNING, OrchestraExplorerActivator.getDefault().getPluginId(), Messages.TreeLabelProvider_Error_Dialog_Message);
            DisplayHelper.displayErrorDialog(Messages.TreeLabelProvider_Error_Dialog_Title, Messages.TreeLabelProvider_Error_Dialog_Cause, status);
          }
        }
      } else if ((_orchestraExplorerState.getGroupType() == GroupType.ENVIRONMENT) && (node.getRootNode() == node)) {
        // Same icon for all environments
        result = OrchestraExplorerActivator.getDefault().getImage(IImageConstants.DESC_ENVIRONMENT);
      } else {
        result = OrchestraExplorerActivator.getDefault().getImage(IImageConstants.DESC_FOLD);
      }
    } else {
      result = OrchestraExplorerActivator.getDefault().getImage(IImageConstants.DESC_FOLD);
    }
    return result;
  }

  /**
   * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
   */
  public String getText(Object element) {
    if (element instanceof AbstractNode<?>) {
      AbstractNode<?> node = (AbstractNode<?>) element;
      return node.getLabel();
    }
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipDisplayDelayTime(java.lang.Object)
   */
  @Override
  public int getToolTipDisplayDelayTime(Object object_p) {
    return 200;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipFont(java.lang.Object)
   */
  @Override
  public Font getToolTipFont(Object object_p) {
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipImage(java.lang.Object)
   */
  @Override
  public Image getToolTipImage(Object object_p) {
    return getImage(object_p, false);
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipStyle(java.lang.Object)
   */
  @Override
  public int getToolTipStyle(Object object_p) {
    return SWT.SHADOW_OUT;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
   */
  @Override
  public String getToolTipText(Object element_p) {
    if (element_p instanceof ArtefactNode) {
      ArtefactNode artefactNode = (ArtefactNode) element_p;
      // Set the type
      StringBuilder result = new StringBuilder(Messages.TreeLabelProvider_Type);
      result.append(artefactNode.getValue().getType());
      // Set the environment
      String env = artefactNode.getFatherEnvironment();
      if (null == env || env.isEmpty()) {
        env = Messages.TreeLabelProvider_Env_Error;
      }
      result.append(Messages.TreeLabelProvider_Env);
      result.append(env);
      String path = artefactNode.getValue().getPropertyValue(IArtefactProperties.ABSOLUTE_PATH);
      // If path not null, show the artefact path
      if ((path != null) && !path.isEmpty()) {
        result.append(Messages.TreeLabelProvider_Path);
        result.append(path);
      }
      return result.toString();
    } else if (element_p instanceof StringNode) {
      StringNode node = (StringNode) element_p;
      return node.getNodePath(true).toString();
    }
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipTimeDisplayed(java.lang.Object)
   */
  @Override
  public int getToolTipTimeDisplayed(Object object_p) {
    return 30000;
  }

  /**
   * @see org.eclipse.jface.viewers.BaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
   */
  @Override
  public boolean isLabelProperty(Object element, String property) {
    return false;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
   */
  @Override
  public void update(ViewerCell cell_p) {
    Object element = cell_p.getElement();
    cell_p.setForeground(getForeground(element));
    cell_p.setText(getText(element));
    cell_p.setImage(decorateImage(getImage(element), element));
    cell_p.setFont(getFont(element));
  }
}