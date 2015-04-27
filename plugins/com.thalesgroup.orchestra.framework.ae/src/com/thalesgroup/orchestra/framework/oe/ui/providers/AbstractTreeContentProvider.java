/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.RootArtefactsBag;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.AbstractNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.ArtefactNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.DragableStringNode;
import com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.StringNode;

/**
 * @author S0024585
 */
public abstract class AbstractTreeContentProvider implements ITreeContentProvider {

  protected OrchestraExplorerState _orchestraExplorerState;

  protected Map<IArtefact, ArtefactNode> _artefactToArtefactNode = new HashMap<IArtefact, ArtefactNode>();

  /*
   * Artefact set path to DragableStringNode
   */
  protected Map<String, DragableStringNode> _pathToDragableStringNode = new HashMap<String, DragableStringNode>();

  public AbstractTreeContentProvider(OrchestraExplorerState orchestraExplorerState_p) {
    _orchestraExplorerState = orchestraExplorerState_p;
  }

  /**
   * @see org.eclipse.jface.viewers.IContentProvider#dispose()
   */
  @Override
  public void dispose() {
    // Default implementation does nothing.
  }

  /**
   * Get children of specified {@link ArtefactNode}.<br>
   * {@link ArtefactNode}s are not dependent on folded mode.
   * @param artefactNode_p
   * @return A not <code>null</code> but possibly empty collection of {@link AbstractNode}.
   */
  protected abstract Collection<Object> doGetChildren(ArtefactNode artefactNode_p);

  /**
   * Get children of specified node in non-folded mode.
   * @param artefactNode_p
   * @return A not <code>null</code> but possibly empty collection of {@link AbstractNode}.
   */
  protected abstract Collection<Object> doGetChildren(StringNode node_p);

  /**
   * Get elements for specified {@link ArtefactNode}.<br>
   * {@link ArtefactNode}s are not dependent on folded mode.
   * @param artefactNode_p
   * @return A not <code>null</code> but possibly empty collection of {@link AbstractNode}.
   */
  protected abstract Collection<Object> doGetElements(ArtefactNode artefactNode_p);

  /**
   * Get elements for specified {@link RootArtefactsBag}.<br>
   * {@link ArtefactNode}s are not dependent on folded mode.
   * @param artefactNode_p
   * @return A not <code>null</code> but possibly empty collection of {@link AbstractNode}.
   */
  protected abstract Collection<Object> doGetElements(RootArtefactsBag rootArtefactsBag_p);

  /**
   * Get elements of specified node in non-folded mode.
   * @param artefactNode_p
   * @return A not <code>null</code> but possibly empty collection of {@link AbstractNode}.
   */
  protected abstract Collection<Object> doGetElements(StringNode node_p);

  /**
   * Get children of specified node in folded mode.
   * @param artefactNode_p
   * @return A not <code>null</code> but possibly empty collection of {@link AbstractNode}.
   */
  protected abstract Collection<Object> doGetFoldedChildren(StringNode node_p);

  /**
   * Get elements of specified node in folded mode.
   * @param artefactNode_p
   * @return A not <code>null</code> but possibly empty collection of {@link AbstractNode}.
   */
  protected abstract Collection<Object> doGetFoldedElements(StringNode node_p);

  /**
   * Do test if specified node has children.
   * @param node_p
   * @return
   */
  protected boolean doHasChildren(StringNode node_p) {
    return true;
  }

  /**
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
   */
  @Override
  public Object[] getChildren(Object parentElement_p) {
    List<Object> result = new ArrayList<Object>(0);
    if (parentElement_p instanceof StringNode) {
      StringNode node = (StringNode) parentElement_p;
      if (!_orchestraExplorerState.isFolded()) {
        result.addAll(doGetChildren(node));
      } else {
        result.addAll(doGetFoldedChildren(node));
      }
    } else if (parentElement_p instanceof ArtefactNode) {
      ArtefactNode artefactNode = (ArtefactNode) parentElement_p;
      result.addAll(doGetChildren(artefactNode));
    }
    return result.toArray();
  }

  /**
   * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
   */
  @Override
  public Object[] getElements(Object inputElement_p) {
    List<Object> result = new ArrayList<Object>(0);
    if (inputElement_p instanceof RootArtefactsBag) {
      result.addAll(doGetElements((RootArtefactsBag) inputElement_p));
    } else if (inputElement_p instanceof StringNode) {
      StringNode node = (StringNode) inputElement_p;
      if (!_orchestraExplorerState.isFolded()) {
        result.addAll(doGetElements(node));
      } else {
        result.addAll(doGetFoldedElements(node));
      }
    } else if (inputElement_p instanceof ArtefactNode) {
      ArtefactNode artefactNode = (ArtefactNode) inputElement_p;
      result.addAll(doGetElements(artefactNode));
    }
    return result.toArray();
  }

  /**
   * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
   */
  @Override
  public Object getParent(Object element_p) {
    return null;
  }

  /**
   * Get children for specified parameters.
   * @param path_p A logical path (possibly mapping an existing physical path) that leads to specified parent.
   * @param parent_p The source node for children collecting.
   * @param artefacts_p The collection of children artefacts (at any level) of specified parent.
   * @return Shallow children of specified parent, whatever their type might be.
   */
  protected List<Object> getPathChildren(IPath path_p, AbstractNode<?> parent_p, Collection<Artefact> artefacts_p) {
    List<Object> children = new ArrayList<Object>(0);
    List<String> alreadyContained = new ArrayList<String>(0);
    for (Artefact artefact : artefacts_p) {
      String rootName = artefact.getUri().getRootName();
      // Build the path string
      IPath rootPath = new Path(rootName);
      rootPath = rootPath.removeFirstSegments(path_p.segmentCount());
      if (rootPath.segmentCount() <= 1) {
        ArtefactNode node = new ArtefactNode(artefact, parent_p, _orchestraExplorerState);
        children.add(node);
        _artefactToArtefactNode.put(artefact, node);
      } else {
        if (!alreadyContained.contains(rootPath.segment(0))) {
          alreadyContained.add(rootPath.segment(0));
          DragableStringNode node = new DragableStringNode(rootPath.segment(0), parent_p);
          children.add(node);

          // Build artefact set path from hierarchy
          StringBuilder nodePath = new StringBuilder();
          nodePath.append(rootPath.segment(0));
          AbstractNode<?> parent = parent_p;
          while (null != parent) {
            // In flat mode, root node label is "/", skip it
            if ("/".equals(parent.getLabel())) {
              break;
            }
            nodePath.insert(0, "/");
            nodePath.insert(0, parent.getLabel());
            parent = parent.getParent();
          }
          _pathToDragableStringNode.put(nodePath.toString(), node);
        }
      }
    }
    return children;
  }

  /**
   * Get elements for specified parameters.
   * @param path_p A logical path (possibly mapping an existing physical path) that leads to specified parent.
   * @param parent_p The source node for children collecting.
   * @param artefacts_p The collection of children artefacts (at any level) of specified parent.
   * @return Shallow children of specified parent, limited to {@link ArtefactNode}s (logical folders are excluded).
   */
  protected List<Object> getPathElements(IPath path, AbstractNode<?> parent, Collection<Artefact> artefacts) {
    List<Object> result = new ArrayList<Object>(0);
    for (Artefact artefact : artefacts) {
      String rootName = artefact.getUri().getRootName();
      Path rootPath = new Path(rootName);
      if (rootPath.segmentCount() == (path.segmentCount() + 1)) {
        ArtefactNode node = new ArtefactNode(artefact, parent, _orchestraExplorerState);
        result.add(node);
        _artefactToArtefactNode.put(artefact, node);
      }
    }
    return result;
  }

  /**
   * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
   */
  @Override
  public boolean hasChildren(Object element_p) {
    if (element_p instanceof RootArtefactsBag) {
      return (RootArtefactsBag.getInstance().getBagSize() > 0);
    } else if (element_p instanceof ArtefactNode) {
      ArtefactNode artefactNode = (ArtefactNode) element_p;
      return (artefactNode.getValue().hasChildren());
    } else if (element_p instanceof StringNode) {
      return doHasChildren((StringNode) element_p);
    }
    return false;
  }

  /**
   * Has specified path children among specified ones ?
   * @param path_p
   * @param artefacts
   * @return
   */
  protected boolean hasPathChildren(IPath path_p, Collection<Artefact> artefacts) {
    // There's children only if there is at least one path is longer
    int nbSegments = path_p.segmentCount() + 1;
    for (IArtefact artefact : artefacts) {
      String rootName = artefact.getUri().getRootName();
      IPath rootPath = new Path(rootName);
      // Remove the known part
      if (rootPath.segmentCount() > nbSegments) {
        // There's another subPath... so reply!
        return true;
      }
    }
    return false;
  }

  /**
   * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
   */
  @Override
  public void inputChanged(Viewer viewer_p, Object oldInput_p, Object newInput_p) {
    // Default implementation does nothing.
  }

  /**
   * Get ArtefactNode from Artefact
   * @param artefact_p
   * @return ArtefactNode
   */
  public ArtefactNode getArtefactNodeFromArtefact(IArtefact artefact_p) {
    return _artefactToArtefactNode.get(artefact_p);
  }

  /**
   * Get DragableStringNode from path
   * @param path_p
   * @return
   */
  public DragableStringNode getDragableStringNodesFromPath(String path_p) {
    return _pathToDragableStringNode.get(path_p);
  }
}