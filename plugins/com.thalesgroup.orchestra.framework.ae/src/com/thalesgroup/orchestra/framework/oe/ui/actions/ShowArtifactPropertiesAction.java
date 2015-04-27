/** 
 * <p>THALES Services - Engineering & Process Management</p>
 * <p>THALES Part Number 16 262 601</p>
 * <p>Copyright (c) 2002-2008 : THALES Services - Engineering & Process Management</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.actions;

import org.eclipse.swt.widgets.Display;

import com.thalesgroup.orchestra.framework.oe.OrchestraExplorerActivator;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefactProperties;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.constants.IImageConstants;
import com.thalesgroup.orchestra.framework.oe.ui.dialogs.PropertiesDialog;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;

/**
 * This class displays the properties of the selected element
 * @author s0018747
 */
public class ShowArtifactPropertiesAction extends ArtefactAction {
  /**
   * Number of selected elements.
   */
  private int _elementsCount;

  /**
   * Constructor.
   */
  public ShowArtifactPropertiesAction() {
    super(Messages.ShowArtifactPropertiesAction_PropertiesLabel);
    setToolTipText(Messages.ShowArtifactPropertiesAction_PropertiesToolTip);
    setImageDescriptor(OrchestraExplorerActivator.getDefault().getImageDescriptor(IImageConstants.DESC_SHOW_PROPERTIES));
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.actions.ArtefactAction#calculateEnabledState()
   */
  @Override
  protected boolean calculateEnabledState() {
    _elementsCount = 0;
    return applyEnablementConditionToSelection();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.actions.ArtefactAction#isEnablingCondition(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode)
   */
  @Override
  protected boolean isEnablingCondition(ArtefactNode artefactNode_p) {
    _elementsCount++;
    // Only applicable to one element.
    if (_elementsCount > 1) {
      return false;
    }
    // Get underlying model element.
    Artefact artefact = artefactNode_p.getValue();
    if (null != artefact) {
      // Precondition.
      if (RootArtefactsBag.getInstance().isOld(artefact)) {
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    final ArtefactNode element = getCurrentTreeSelection(ArtefactNode.class).get(0);
    if (element != null) {
      Display.getCurrent().syncExec(new Runnable() {
        /**
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
          // Check if the element has its environment, if not retrieve it from its parent.
          Artefact value = element.getValue();
          if (null == value.getPropertyValue(IArtefactProperties.ENVIRONMENT)) {
            String env = element.getFatherEnvironment();
            value.getProperties().put(IArtefactProperties.ENVIRONMENT, env);
          }
          new PropertiesDialog(Display.getCurrent().getActiveShell(), value).open();
        }
      });
    }
  }
}