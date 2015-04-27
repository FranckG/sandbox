package com.thalesgroup.orchestra.framework.oe.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.DragableStringNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode;

public class FlatContentProvider extends AbstractTreeContentProvider {

  public FlatContentProvider(OrchestraExplorerState orchestraExplorerState_p) {
    super(orchestraExplorerState_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetChildren(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode)
   */
  @Override
  protected Collection<Object> doGetChildren(ArtefactNode artefactNode_p) {
    List<Object> result = new ArrayList<Object>(0);
    for (Artefact artefact : artefactNode_p.getValue().getChildren()) {
      ArtefactNode node = new ArtefactNode(artefact, artefactNode_p, _orchestraExplorerState);
      result.add(node);
      _artefactToArtefactNode.put(artefact, node);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetChildren(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected Collection<Object> doGetChildren(StringNode node_p) {
    List<Object> result = new ArrayList<Object>(0);
    for (Artefact artefact : RootArtefactsBag.getInstance().getAllRootArtefacts()) {
      ArtefactNode node = new ArtefactNode(artefact, node_p, _orchestraExplorerState);
      result.add(node);
      _artefactToArtefactNode.put(artefact, node);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetElements(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode)
   */
  @Override
  protected Collection<Object> doGetElements(ArtefactNode artefactNode_p) {
    return doGetChildren(artefactNode_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetElements(com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag)
   */
  @Override
  protected Collection<Object> doGetElements(RootArtefactsBag rootArtefactsBag_p) {
    Collection<Object> result = new ArrayList<Object>(0);
    if (_orchestraExplorerState.isFolded()) {
      final DragableStringNode node = new DragableStringNode("/", null); //$NON-NLS-1$
      _pathToDragableStringNode.put("/", node); //$NON-NLS-1$
      result.add(node);
    } else {
      Collection<Artefact> artefacts = RootArtefactsBag.getInstance().getAllRootArtefacts();
      for (Artefact artefact : artefacts) {
        ArtefactNode node = new ArtefactNode(artefact, null, _orchestraExplorerState);
        result.add(node);
        _artefactToArtefactNode.put(artefact, node);
      }
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetElements(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected Collection<Object> doGetElements(StringNode node_p) {
    List<Object> result = new ArrayList<Object>(0);
    for (Artefact artefact : RootArtefactsBag.getInstance().getRootArtefactsForFilePath(node_p.getNodePath(true), null)) {
      ArtefactNode node = new ArtefactNode(artefact, node_p, _orchestraExplorerState);
      result.add(node);
      _artefactToArtefactNode.put(artefact, node);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetFoldedChildren(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected Collection<Object> doGetFoldedChildren(StringNode node_p) {
    IPath path = node_p.getNodePath(false);
    Collection<Artefact> artefacts = RootArtefactsBag.getInstance().getRootArtefactsForPath(null, path);
    return getPathChildren(path, node_p, artefacts);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetFoldedElements(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected Collection<Object> doGetFoldedElements(StringNode node_p) {
    List<Object> result = new ArrayList<Object>(0);
    IPath path = node_p.getNodePath(false);
    Collection<Artefact> artefacts = RootArtefactsBag.getInstance().getRootArtefactsForPath(null, path);
    result = getPathElements(path, node_p, artefacts);
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doHasChildren(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected boolean doHasChildren(StringNode node_p) {
    if (!_orchestraExplorerState.isFolded()) {
      // We are not folded, so the Path does contain only the type.
      return (RootArtefactsBag.getInstance().getBagSize() > 0);
    }
    // We are folded.
    IPath path = node_p.getNodePath(false);
    Collection<Artefact> artefacts = RootArtefactsBag.getInstance().getRootArtefactsForPath(null, path);
    return hasPathChildren(path, artefacts);
  }
}
