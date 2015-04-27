/**
 * <p>Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management</p>
 * <p>Société : Thales Services - Engineering & Process Management</p>
 * <p>Thales Part Number 16 262 601</p>
 */
package com.thalesgroup.orchestra.framework.oe.ui.dnd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

import com.thalesgroup.orchestra.framework.lib.utils.uri.OrchestraURI;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.DragableStringNode;

/**
 * <p>
 * Title : ArtifactDragListener
 * </p>
 * <p>
 * Description : Supports dragging artifacts from a structured viewer
 * </p>
 * @author Papeete Tool Suite developer
 * @version 3.7.0
 */
public class ArtefactDragSourceAdapter extends DragSourceAdapter {
  private final StructuredViewer _viewer;

  public ArtefactDragSourceAdapter(StructuredViewer viewer_p) {
    _viewer = viewer_p;
  }

  /**
   * @see org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
   */
  @Override
  public void dragSetData(DragSourceEvent event_p) {
    // Provide the data of the requested type.
    if (ArtefactTransfer.getInstance().isSupportedType(event_p.dataType)) {
      event_p.data = getDragData();
    }
  }

  /**
   * @see org.eclipse.swt.dnd.DragSourceAdapter#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
   */
  @Override
  public void dragStart(DragSourceEvent event_p) {
    // Get current selection.
    IStructuredSelection selection = (IStructuredSelection) _viewer.getSelection();
    Object[] objects = selection.toArray();
    // Precondition.
    if (0 == objects.length) {
      event_p.doit = false;
      return;
    }
    // Compute result.
    boolean result = true;
    for (Object object : objects) {
      // Stop here if needed.
      if (!result) {
        break;
      }
      // DragableStringNode is accepted.
      if (object instanceof DragableStringNode) {
        continue;
      } else if (object instanceof ArtefactNode) {
        Artefact artefact = ((ArtefactNode) object).getValue();
        // ArtefactNode is accepted, if it can be extracted...
        result &= artefact.getGenericArtifact().isToolAtomicallyExtractable();
        // ... and this is not a deprecated one.
        result &= !RootArtefactsBag.getInstance().isOld(artefact);
      } else {
        // Whatever that might be, this is not supported (yet ?).
        result = false;
      }
    }
    event_p.doit = result;
  }

  /**
   * Get dragable data.
   * @return
   */
  protected IArtefact[] getDragData() {
    IStructuredSelection selection = (IStructuredSelection) _viewer.getSelection();
    int artefactsCount = selection.size();
    List<Artefact> artefactsSelected = new ArrayList<Artefact>(artefactsCount);
    for (Object object : selection.toList()) {
      if (object instanceof ArtefactNode) {
        artefactsSelected.add(((ArtefactNode) object).getValue());
      } else if (object instanceof DragableStringNode) {
        DragableStringNode dragableStringNode = (DragableStringNode) object;
        OrchestraURI artefactSetUri = ArtefactSetDialog.chooseArtefactSetParameters(dragableStringNode);
        if (null != artefactSetUri) {
          Artefact artefact = new Artefact(artefactSetUri, false);
          artefactsSelected.add(artefact);
          artefact.setLabel(artefactSetUri.getParameters().get("LogicalFolderPath")); //$NON-NLS-1$
        }
      }
    }
    return artefactsSelected.toArray(new Artefact[artefactsSelected.size()]);
  }
}