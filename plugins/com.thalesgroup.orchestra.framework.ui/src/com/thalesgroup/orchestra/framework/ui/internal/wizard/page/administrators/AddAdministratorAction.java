package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.ldap.model.OrganisationUser;
import com.thalesgroup.orchestra.ldap.ui.LdapUserWizardPage;

/**
 * The add administrator action.
 * @author s0011584
 */
public class AddAdministratorAction extends SelectionAdapter {
  private final Composite _parent;
  protected final TableViewer _viewer;
  protected final List<Couple<String, OrganisationUser>> _administrators;

  public AddAdministratorAction(Composite parent_p, TableViewer viewer_p, List<Couple<String, OrganisationUser>> administrators_p) {
    _parent = parent_p;
    _viewer = viewer_p;
    _administrators = administrators_p;
  }

  @Override
  public void widgetSelected(final SelectionEvent e_p) {
    Wizard newWizard = new Wizard() {
      LdapUserWizardPage _ldapPage;
      @Override
      public void addPages() {
        _ldapPage = new LdapUserWizardPage("ldap page") { //$NON-NLS-1$
          @Override
          protected void createCustomControl() {
            addCreateSection();
          }
          @Override
          public boolean isPageComplete() {
            setMessage(ICommonConstants.EMPTY_STRING);
            boolean pageComplete = super.isPageComplete();
            for (Couple<String, OrganisationUser> existingUser : _administrators) {
              boolean alreadyExists = existingUser.getKey().equalsIgnoreCase(_ldapPage.getLogin());
              if (alreadyExists) {
                setMessage(MessageFormat.format(Messages.AddAdministratorAction_AlreadyExists_Message, existingUser.getKey()));
              }
              pageComplete = pageComplete & !alreadyExists;
            }
            return pageComplete;
          }
        };
        addPage(_ldapPage);
      }
      @Override
      public boolean performFinish() {
        final OrganisationUser user = new OrganisationUser();
        user.setGivenname(_ldapPage.getFirstName());
        user.setSurname(_ldapPage.getLastName());
        Couple<String, OrganisationUser> element = new Couple<String, OrganisationUser>(_ldapPage.getLogin(), user);
        _administrators.add(element);
        _viewer.add(element);
        return true;
      }
    };
    newWizard.setWindowTitle(Messages.AddAdministratorAction_Wizard_WindowTitle);
    WizardDialog dialog = new WizardDialog(_parent.getShell(), newWizard);
    dialog.open();
  }
}