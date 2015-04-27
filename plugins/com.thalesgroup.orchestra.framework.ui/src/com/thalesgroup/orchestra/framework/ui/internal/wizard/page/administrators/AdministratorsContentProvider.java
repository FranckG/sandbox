package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.ldap.model.OrganisationUser;

/**
 * Content providing for the Administrator table.
 * @author s0011584
 */
public class AdministratorsContentProvider implements IStructuredContentProvider {
  public void dispose() {
    // Nothing to do.
  }

  @SuppressWarnings("unchecked")
  public Object[] getElements(Object inputElement_p) {
    List<Couple<String, OrganisationUser>> admins = (List<Couple<String, OrganisationUser>>) inputElement_p;
    return admins.toArray();
  }

  public void inputChanged(Viewer viewer_p, Object oldInput_p, Object newInput_p) {
    // Nothing to do.
  }
}