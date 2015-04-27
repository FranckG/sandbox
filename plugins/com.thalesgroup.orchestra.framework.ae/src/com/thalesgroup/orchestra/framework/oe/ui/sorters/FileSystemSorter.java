/**
 * <p>Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management</p>
 * <p>Société : Thales Services - Engineering & Process Management</p>
 * <p>Thales Part Number 16 262 601</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.sorters;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefactProperties;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;

/**
 * <p>
 * Title : FileSystemSorter
 * </p>
 * <p>
 * Description : File System Sorter
 * </p>
 * @author Orchestra Framework Tool Suite developer
 * @version 3.7.0
 */
public class FileSystemSorter extends ViewerSorter {
  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.viewers.ViewerSorter#compare (org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public int compare(Viewer viewer, Object e1, Object e2) {
    String empty = "";//$NON-NLS-1$
    String name1 = empty;
    String name2 = empty;
    if (viewer != null) {
      if (viewer instanceof TreeViewer) {
        if (e1 instanceof ArtefactNode) {
          ArtefactNode artefact1 = (ArtefactNode) e1;
          if (artefact1.getValue().isRootArtefact()) {
            ArtefactNode artefact2 = (ArtefactNode) e2;
            name1 = artefact1.getValue().getPropertyValue(IArtefactProperties.ENVIRONMENT);
            if (null == name1) {
              name1 = empty;
            }
            name2 = artefact2.getValue().getPropertyValue(IArtefactProperties.ENVIRONMENT);
            if (null == name2) {
              name2 = empty;
            }
            return name1.compareTo(name2);
          }
        }
        // By default, sort the label text
        // Do not sort alphabetically the sub trees, i.e. do not sort elements below the RootArtefact (PCR prod00082278)
        // If both are artefactNode
        if ((e1 instanceof ArtefactNode) && (e2 instanceof ArtefactNode)) {
          Artefact artefact1 = ((ArtefactNode) e1).getValue();
          Artefact artefact2 = ((ArtefactNode) e2).getValue();
          // If both are NOT RootArtefact
          if (!artefact1.isRootArtefact() && !artefact2.isRootArtefact()) {
            // No sorting
            return 0;
          }
        }
        IBaseLabelProvider prov = ((ContentViewer) viewer).getLabelProvider();
        if (prov instanceof ILabelProvider) {
          ILabelProvider lprov = (ILabelProvider) prov;
          name1 = lprov.getText(e1);
          name2 = lprov.getText(e2);
        } else {
          name1 = e1.toString();
          name2 = e2.toString();
        }
      }

      if (name1 == null)
        name1 = empty;
      if (name2 == null)
        name2 = empty;
    }
    return getComparator().compare(name1, name2);
  }
}