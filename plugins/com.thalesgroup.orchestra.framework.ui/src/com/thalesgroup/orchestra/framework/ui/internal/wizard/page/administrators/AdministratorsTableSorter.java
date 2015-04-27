package com.thalesgroup.orchestra.framework.ui.internal.wizard.page.administrators;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.ldap.model.OrganisationUser;

/**
 * The table sorter of the Administrators list.<br />
 * see {@link http://http://www.vogella.de/articles/EclipseJFaceTable/article.html#sortcolumns}.
 * @author s0011584
 */
public class AdministratorsTableSorter extends ViewerSorter {
  /**
   * The code for the descending direction.
   */
  private static final int DESCENDING = 1;
  /**
   * The current sorting direction.
   */
  private int direction = DESCENDING;

  private int propertyIndex;

  public AdministratorsTableSorter() {
    this.propertyIndex = 0;
    direction = DESCENDING;
  }

  @SuppressWarnings("unchecked")
  @Override
  public int compare(Viewer viewer, Object e1, Object e2) {
    Couple<String, OrganisationUser> p1 = (Couple<String, OrganisationUser>) e1;
    Couple<String, OrganisationUser> p2 = (Couple<String, OrganisationUser>) e2;
    int comparison = 0;
    switch (propertyIndex) {
      case 0:
        // TGI are compared ignoring case.
        comparison = p1.getKey().compareToIgnoreCase(p2.getKey());
      break;
      case 1:
        comparison = p1.getValue().getCommonName().compareTo(p2.getValue().getCommonName());
      break;
      default:
        comparison = 0;
    }
    // If descending order, flip the direction
    if (direction == DESCENDING) {
      comparison = -comparison;
    }
    return comparison;
  }

  public void setColumn(int column) {
    if (column == this.propertyIndex) {
      // Same column as last sort; toggle the direction
      direction = 1 - direction;
    } else {
      // New column; do an ascending sort
      this.propertyIndex = column;
      direction = DESCENDING;
    }
  }
}
