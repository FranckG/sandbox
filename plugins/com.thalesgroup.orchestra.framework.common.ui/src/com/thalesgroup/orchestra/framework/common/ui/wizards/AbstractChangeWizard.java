package com.thalesgroup.orchestra.framework.common.ui.wizards;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.thalesgroup.orchestra.framework.common.command.ChangeDescriptionCommand;

public abstract class AbstractChangeWizard<T extends EObject> extends com.thalesgroup.orchestra.framework.root.ui.wizards.AbstractFormsWizard {
  /**
   * Change recorder used to track changes.
   */
  protected ChangeRecorder _recorder;
  /**
   * The root scope object for current edition.
   */
  private T _rootObject;

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractFormsWizard#dispose()
   */
  @Override
  public void dispose() {
    try {
      super.dispose();
    } finally {
      // This might be a good idea to ensure that no recording remains alive.
      stopRecording();
    }
  }

  /**
   * Get underlying command label.
   * @return
   */
  protected abstract String getCommandLabel();

  /**
   * Get root scope object.
   * @return
   */
  protected T getObject() {
    return _rootObject;
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performCancel()
   */
  @Override
  public boolean performCancel() {
    ChangeDescription changeDescription = stopRecording();
    changeDescription.applyAndReverse();
    return super.performCancel();
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    ChangeDescription changeDescription = stopRecording();
    EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(_rootObject);
    // Do not add a command if nothing has changed.
    if ((null != editingDomain) && !changeDescription.getObjectChanges().isEmpty()) {
      editingDomain.getCommandStack().execute(new ChangeDescriptionCommand(changeDescription, getCommandLabel()));
    }
    return true;
  }

  /**
   * Set root scope object.
   * @param object
   */
  public void setObject(T object) {
    _rootObject = object;
    startRecording();
  }

  /**
   * Start recording.
   */
  protected void startRecording() {
    // Listen to the entire resource set.
    _recorder = new ChangeRecorder(_rootObject.eResource().getResourceSet());
  }

  /**
   * Stop recording.
   * @return
   */
  protected ChangeDescription stopRecording() {
    if (null != _recorder) {
      ChangeDescription endRecording = _recorder.endRecording();
      _recorder.dispose();
      _recorder = null;
      return endRecording;
    }
    return null;
  }
}