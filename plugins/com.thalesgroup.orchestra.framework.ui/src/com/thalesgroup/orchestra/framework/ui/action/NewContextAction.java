package com.thalesgroup.orchestra.framework.ui.action;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.edit.EMFEditPlugin;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;

import com.thalesgroup.orchestra.framework.model.contexts.provider.ContextsEditPlugin;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.mode.IAdministratorModeListener;
import com.thalesgroup.orchestra.framework.ui.view.Messages;
import com.thalesgroup.orchestra.framework.ui.wizard.NewContextWizard;
import com.thalesgroup.orchestra.framework.ui.wizard.ParticipateContextWizard;

/**
 * New context action.
 */
public class NewContextAction extends AbstractWizardBasedAction implements IAdministratorModeListener {
  private ImageDescriptor _imageDescriptor;

  /**
   * Default constructor.
   */
  public NewContextAction() {
    super(null);
    ProjectActivator.getInstance().addAdministratorModeChangeListener(this);
    modeChanged(ProjectActivator.getInstance().isAdministrator());
  }

  /**
   * @see org.eclipse.jface.action.Action#getImageDescriptor()
   */
  @Override
  public ImageDescriptor getImageDescriptor() {
    if (null == _imageDescriptor) {
      List<Object> images = new ArrayList<Object>(2);
      images.add(ContextsEditPlugin.INSTANCE.getImage("full/obj16/Context")); //$NON-NLS-1$
      images.add(EMFEditPlugin.INSTANCE.getImage("full/ovr16/CreateChild.gif")); //$NON-NLS-1$
      _imageDescriptor = ExtendedImageRegistry.getInstance().getImageDescriptor(new ComposedImage(images) {
        @Override
        public List<Point> getDrawPoints(Size size) {
          List<Point> result = super.getDrawPoints(size);
          result.get(1).x = size.width - 6;
          return result;
        }
      });
    }
    return _imageDescriptor;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.project.mode.IAdministratorModeListener#modeChanged(boolean)
   */
  public void modeChanged(boolean newAdministratorState_p) {
    setText(newAdministratorState_p ? Messages.NewContextAction_Label_Admin : Messages.NewContextAction_Label_User);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.ui.action.AbstractWizardBasedAction#createWizard()
   */
  @Override
  protected Wizard createWizard() {
    if (ProjectActivator.getInstance().isAdministrator()) {
      return new NewContextWizard();
    }
    return new ParticipateContextWizard();
  }

  /**
   * @see java.lang.Object#finalize()
   */
  @Override
  protected void finalize() throws Throwable {
    ProjectActivator.getInstance().removeAdministratorModeListener(this);
    super.finalize();
  }
}