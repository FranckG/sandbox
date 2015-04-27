/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.providers.nodes;

import org.eclipse.core.runtime.Path;

import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefact;
import com.thalesgroup.orchestra.framework.oe.artefacts.IArtefactProperties;
import com.thalesgroup.orchestra.framework.oe.artefacts.internal.Artefact;
import com.thalesgroup.orchestra.framework.oe.ui.OrchestraExplorerState;

/**
 * @author S0024585
 */
public class ArtefactNode extends AbstractNode<Artefact> {

  protected OrchestraExplorerState _orchestraExplorerState;

  /**
   * Constructor
   * @param value_p
   * @param parent_p
   */
  public ArtefactNode(Artefact value_p, AbstractNode<?> parent_p, OrchestraExplorerState orchestraExplorerState_p) {
    super(value_p, parent_p);
    _orchestraExplorerState = orchestraExplorerState_p;
  }

  /**
   * @see org.eclipse.jface.viewers.TreeNode#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object_p) {
    if (object_p instanceof ArtefactNode) {
      return getValue().equals(((ArtefactNode) object_p).getValue());
    }
    return super.equals(object_p);
  }

  /**
   * Retrieve the environment of a {@link ArtefactNode} based on its parents with a recursive search.
   * @return The environment, may be null
   */
  public String getFatherEnvironment() {
    String result = getValue().getProperties().get(IArtefactProperties.ENVIRONMENT);
    if (null == result) {
      // Recursive call to find the first node which contains the description of the environment
      AbstractNode<?> parent = this;
      // When the parent's parent is null or the parent's parent is no longer an ArtefactNode, the parent is the top node
      while ((null != parent.getParent()) && (parent.getParent() instanceof ArtefactNode)) {
        parent = parent.getParent();
      }
      // Get the environment
      result = ((ArtefactNode) parent).getValue().getPropertyValue(IArtefactProperties.ENVIRONMENT);
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.AbstractNode#getLabel()
   */
  @Override
  public String getLabel() {
    IArtefact artefact = getValue();
    String result = artefact.getLabel();
    if ((result == null) || result.isEmpty()) {
      result = artefact.getName();
    }
    if (_orchestraExplorerState.isFolded()) {
      Path path = new Path(result);
      // Make sure no path is used if there is one segment at upmost, because path structure eats leading 'XXXYYY : ...' scheme as a device ID, and does not
      // include it in the last segment.
      // See prod00094305
      if (1 < path.segmentCount()) {
        result = path.lastSegment();
      }
    }
    return result;
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.AbstractNode#getNodeSegment()
   */
  @Override
  public String getNodeSegment() {
    return getValue().getName();
  }

}
