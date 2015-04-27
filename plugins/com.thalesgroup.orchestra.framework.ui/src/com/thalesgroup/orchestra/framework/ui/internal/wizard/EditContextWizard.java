/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.preference.IPreferenceStore;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.contextsproject.Administrator;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectFactory;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;
import com.thalesgroup.orchestra.framework.root.ui.DisplayHelper;
import com.thalesgroup.orchestra.framework.ui.activator.OrchestraFrameworkUiActivator;
import com.thalesgroup.orchestra.framework.ui.internal.wizard.page.EditContextPage;
import com.thalesgroup.orchestra.ldap.activator.LdapActivator;
import com.thalesgroup.orchestra.ldap.manager.LdapManager;
import com.thalesgroup.orchestra.ldap.model.OrganisationUser;
import com.thalesgroup.orchestra.ldap.preferences.LdapPreferenceConstants;

/**
 * Context edition wizard.
 * @author t0076261
 */
public class EditContextWizard extends AbstractChangeWizard<Context> {
  private List<Couple<String, OrganisationUser>> _administrators;
  /**
   * Context edition page.
   */
  protected EditContextPage _editContextPage;

  /**
   * Constructor.
   * @param context_p
   */
  public EditContextWizard(Context context_p) {
    setObject(context_p);
    setNeedsProgressMonitor(false);
    setWindowTitle(Messages.EditContextWizard_Wizard_Title);
    initAdministratorList();
  }

  private void initAdministratorList() {
    _administrators = new ArrayList<Couple<String, OrganisationUser>>(0);
    // Precondition.
    if (!ProjectActivator.getInstance().isAdministrator()) {
      return;
    }
    IPreferenceStore store = LdapActivator.getDefault().getPreferenceStore();
    String host = store.getString(LdapPreferenceConstants.LDAP_HOST);
    String port = store.getString(LdapPreferenceConstants.LDAP_PORT);
    String baseDn = store.getString(LdapPreferenceConstants.LDAP_BASE_DN);
    boolean anonymous = store.getBoolean(LdapPreferenceConstants.LDAP_ANONYMOUS);
    EList<Administrator> administrators = getContextsProject(getObject()).getAdministrators();
    MultiStatus status = null;
    for (Administrator administrator : administrators) {
      String id = administrator.getId();
      OrganisationUser ldapUser = null;
      try {
        ldapUser = LdapManager.getInstance().findUserInLdap(id, anonymous, host, port, baseDn);
      } catch (Exception exception_p) {
        if (null == status) {
          status =
              new MultiStatus(OrchestraFrameworkUiActivator.getDefault().getPluginId(), IStatus.WARNING, Messages.EditContextWizard_LdapAccessError_Reason,
                  null);
        }
        status.add(new Status(IStatus.WARNING, OrchestraFrameworkUiActivator.getDefault().getPluginId(), MessageFormat.format(
            Messages.EditContextWizard_LdapAccessError_UnknownUser, id)));
        CommonActivator.getInstance().logMessage(
            "Unable to retrieve user info from LDAP. Check your LDAP connectivity in preferences.", IStatus.WARNING, exception_p); //$NON-NLS-1$
      }
      _administrators.add(new Couple<String, OrganisationUser>(id, ldapUser));
    }
    if (null != status) {
      DisplayHelper.displayErrorDialog(Messages.EditContextWizard_LdapAccessError_Title, Messages.EditContextWizard_LdapAccessError_WarningMessage, status);
    }
  }

  /**
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    _editContextPage = new EditContextPage(getObject(), getContextsProject(getObject()), _administrators);
    addPage(_editContextPage);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard#getCommandLabel()
   */
  @Override
  protected String getCommandLabel() {
    return Messages.EditContextWizard_Command_Label;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.common.ui.wizards.AbstractChangeWizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    if (ProjectActivator.getInstance().isAdministrator()) {
      // Set changes in the ContextsProject object.
      ContextsProject contextsProject = getContextsProject(getObject());
      // Set parent path (don't change it if it was null because parent context is DefaultContext).
      if (null != contextsProject.getParentProject()) {
        contextsProject.setParentProject(_editContextPage.getParentProjectRawPath());
      }
      // Manage administrator list for admin context.
      EList<Administrator> administrators = contextsProject.getAdministrators();
      // Clean current administrator list.
      administrators.clear();
      // Populate it using wizard content.
      for (Couple<String, OrganisationUser> newAdministrator : _administrators) {
        Administrator administrator = ContextsProjectFactory.eINSTANCE.createAdministrator();
        administrator.setId(newAdministrator.getKey());
        administrators.add(administrator);
      }
      try {
        // Save context description file.
        ProjectActivator.getInstance().getEditingDomain().saveResource(contextsProject.eResource());
      } catch (IOException ex) {
        // A problem occurred during save, show an error dialog.
        Status status = new Status(IStatus.ERROR, OrchestraFrameworkUiActivator.getDefault().getPluginId(), ex.getMessage());
        DisplayHelper.displayErrorDialog(Messages.EditContextWizard_ContextDescriptionSaveError_Title,
            Messages.EditContextWizard_ContextDescriptionSaveError_Message, status);
        // Wizard can't finish.
        return false;
      }
    }
    return super.performFinish();
  }

  protected ContextsProject getContextsProject(Context context_p) {
    URI uri = context_p.eResource().getURI();
    RootContextsProject projectFromContextUri = ProjectActivator.getInstance().getProjectHandler().getProjectFromContextUri(uri);
    return projectFromContextUri._contextsProject;
  }
}