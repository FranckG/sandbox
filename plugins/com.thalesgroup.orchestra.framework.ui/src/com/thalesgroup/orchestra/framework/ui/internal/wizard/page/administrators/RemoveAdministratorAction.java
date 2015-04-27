package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators;

import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.ldap.model.OrganisationUser;

/**
 * @author s0011584
 */
public class RemoveAdministratorAction extends SelectionAdapter {
  private final TableViewer _viewer;
  private final List<Couple<String, OrganisationUser>> _administrators;

  public RemoveAdministratorAction(TableViewer viewer_p, List<Couple<String,OrganisationUser>> administrators_p) {
    _viewer = viewer_p;
    _administrators = administrators_p;
  }

  @Override
  public void widgetSelected(SelectionEvent e_p) {
    StructuredSelection selectionIndex = (StructuredSelection) _viewer.getSelection();
    Object firstElement = selectionIndex.getFirstElement();
    _viewer.remove(firstElement);
    _administrators.remove(firstElement);
  }
}