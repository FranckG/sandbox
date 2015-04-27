/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.ldap.model.OrganisationUser;

/**
 * Label providing for the Administrator table.
 * 
 * @author s0011584
 */
public class AdministratorsLabelProvider extends CellLabelProvider implements ITableLabelProvider {

  /**
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
   */
  public Image getColumnImage(Object element_p, int columnIndex_p) {
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
   */
  @SuppressWarnings("unchecked")
  public String getColumnText(Object element_p, int columnIndex_p) {
    Couple<String, OrganisationUser> entry = (Couple<String, OrganisationUser>) element_p;
    if (columnIndex_p == 0) {
      return entry.getKey();
    }
    if (columnIndex_p == 1) {
      OrganisationUser value = entry.getValue();
      if (null != value) {
        return value.getCommonName();
      }
      return ICommonConstants.EMPTY_STRING;
    }
    return null;
  }

  /**
   * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
   */
  @Override
  public void update(ViewerCell cell_p) {
    Object element = cell_p.getElement();
    int columnIndex = cell_p.getColumnIndex();
    cell_p.setText(getColumnText(element, columnIndex));
  }
}