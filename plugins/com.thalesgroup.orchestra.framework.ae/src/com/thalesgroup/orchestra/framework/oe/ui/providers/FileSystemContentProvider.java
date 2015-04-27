package com.thalesgroup.orchestra.framework.oe.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.AbstractNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode;

public class FileSystemContentProvider extends AbstractTreeContentProvider {

  public FileSystemContentProvider(OrchestraExplorerState orchestraExplorerState_p) {
    super(orchestraExplorerState_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetChildren(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode)
   */
  @Override
  protected Collection<Object> doGetChildren(ArtefactNode artefactNode_p) {
    Collection<Object> result = new ArrayList<Object>(0);
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
    Collection<Object> result = new ArrayList<Object>(0);
    String env = node_p.getValue();
    // Get all the artefacts linked to the environment of this node
    for (Artefact artefact : RootArtefactsBag.getInstance().getRootArtefactsByEnvironment(env)) {
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
   * rootArtefactBag_p parameter of type {@link RootArtefactsBag} not used in this implementation
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetElements(com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag)
   */
  @Override
  protected Collection<Object> doGetElements(RootArtefactsBag rootArtefactsBag_p) {
    List<Object> result = new ArrayList<Object>(0);
    // Retrieve the list of environments in the RootArtefact bag
    List<String> environmentList = RootArtefactsBag.getInstance().getEnvironmentsList();
    for (String env : environmentList) {
      // Create a node for each one
      result.add(new StringNode(env, null));
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
    IPath path = node_p.getNodePath(false);
    // Get all the artefacts linked to the environment of this node
    Collection<Artefact> artefacts = getStringNodeChildren(node_p);
    List<Object> pathChildren = getPathChildren(path, node_p, artefacts);
    return pathChildren;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doGetFoldedElements(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected Collection<Object> doGetFoldedElements(StringNode node_p) {
    // Folded mode
    IPath path = node_p.getNodePath(false);
    Collection<Artefact> artefacts = getStringNodeChildren(node_p);
    List<Object> pathElements = getPathElements(path, node_p, artefacts);
    return pathElements;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.AbstractTreeContentProvider#doHasChildren(com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode)
   */
  @Override
  protected boolean doHasChildren(StringNode node_p) {
    if (!_orchestraExplorerState.isFolded()) {
      return (RootArtefactsBag.getInstance().getRootArtefactsForFilePath(node_p.getNodePath(true), null) != null);
    }
    // We are folded.
    IPath path = node_p.getNodePath(false);
    Collection<Artefact> artefacts = getStringNodeChildren(node_p);
    /*
     * if (artefacts.isEmpty()) { return false; } return true;
     */
    return hasPathChildren(path, artefacts);
  }

  /**
   * Retrieve the list of {@link Artefact} under a {@link StringNode} (recursively).
   * @param node_p The {@link StringNode} where to start the search
   * @return The list of {@link Artefact} linked to this {@link StringNode}
   */
  protected Collection<Artefact> getStringNodeChildren(StringNode node_p) {
    // Get root path of given node (environment description).
    AbstractNode<?> rootNode = node_p.getRootNode();
    if (!(rootNode instanceof StringNode)) {
      return Collections.emptyList();
    }
    String environmentName = ((StringNode) rootNode).getValue();
    // Get relative path of given node (relative to the root node).
    IPath givenNodeRelativePath = node_p.getNodePath(false);
    // Get artefacts belonging to the selected node.
    Collection<Artefact> artefacts =
        RootArtefactsBag.getInstance().getRootArtefactsFromEnvironmentAndRelativePath(environmentName, givenNodeRelativePath.toString(), true);

    return artefacts;
  }
}