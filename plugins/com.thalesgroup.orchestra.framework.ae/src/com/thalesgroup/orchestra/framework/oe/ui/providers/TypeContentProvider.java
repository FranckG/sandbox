package com.thalesgroup.orchestra.framework.oe.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IPath;

import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.TypeStringNode;

public class TypeContentProvider extends AbstractTreeContentProvider {

  public TypeContentProvider(OrchestraExplorerState orchestraExplorerState_p) {
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
    // Path contains the type.
    for (Artefact artefact : RootArtefactsBag.getInstance().getRootArtefactsForType(node_p.getValue())) {
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
    List<Object> result = new ArrayList<Object>(0);
    Set<String> rootArtifactsTypes = RootArtefactsBag.getInstance().getRootArtefactsTypes();
    for (String type : rootArtifactsTypes) {
      result.add(new TypeStringNode(type, null));
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetElements(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected Collection<Object> doGetElements(StringNode node_p) {
    return doGetChildren(node_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetFoldedChildren(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected Collection<Object> doGetFoldedChildren(StringNode node_p) {
    StringNode typeNode = (StringNode) node_p.getRootNode();
    IPath path = node_p.getNodePath(false);
    Collection<Artefact> artefacts = RootArtefactsBag.getInstance().getRootArtefactsForPath(typeNode.getValue(), path);
    return getPathChildren(path, node_p, artefacts);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetFoldedElements(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected Collection<Object> doGetFoldedElements(StringNode node_p) {
    StringNode typeNode = (StringNode) node_p.getRootNode();
    IPath path = node_p.getNodePath(false);
    Collection<Artefact> artefacts = RootArtefactsBag.getInstance().getRootArtefactsForPath(typeNode.getValue(), path);
    return getPathElements(path, node_p, artefacts);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doHasChildren(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected boolean doHasChildren(StringNode node_p) {
    if (!_orchestraExplorerState.isFolded()) {
      // We are not folded, so the Path does contain only the type.
      return (RootArtefactsBag.getInstance().getRootArtefactsForType(node_p.getValue()) != null);
    }
    // We are folded.
    StringNode typeNode = (StringNode) node_p.getRootNode();
    IPath path = node_p.getNodePath(false);
    Collection<Artefact> artefacts = RootArtefactsBag.getInstance().getRootArtefactsForPath(typeNode.getValue(), path);
    return hasPathChildren(path, artefacts);
  }
}