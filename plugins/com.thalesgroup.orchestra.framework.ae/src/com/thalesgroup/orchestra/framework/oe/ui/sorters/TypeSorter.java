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

import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;

/**
 * <p>
 * Title : ToolSorter
 * </p>
 * <p>
 * Description : Tool Sorter
 * </p>
 * @author Orchestra Tool Suite developer
 * @version 3.7.0
 */
public class TypeSorter extends ViewerSorter {
  /*
   * (non-Javadoc)
   * @see org.eclipse.jface.viewers.ViewerSorter#compare (org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public int compare(Viewer viewer, Object e1, Object e2) {
    String name1 = "";//$NON-NLS-1$
    String name2 = "";//$NON-NLS-1$
    if (viewer != null) {
      if (viewer instanceof TreeViewer) {
        if (e1 instanceof ArtefactNode) {
          ArtefactNode artefact1 = (ArtefactNode) e1;
          if (artefact1.getValue().isRootArtefact()) {
            ArtefactNode artefact2 = (ArtefactNode) e2;
            String type1 = artefact1.getValue().getType();
            String type2 = artefact2.getValue().getType();
            int typeCompareResult = type1.compareTo(type2);
            if (typeCompareResult != 0) {
              return typeCompareResult;
            }
          }
        }
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
    }
    if (name1 == null)
      name1 = ""; //$NON-NLS-1$
    if (name2 == null)
      name2 = ""; //$NON-NLS-1$
    return getComparator().compare(name1, name2);
  }
}