/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.oe.ui.providers.nodes;

/**
 * @author S0024585
 */
public class StringNode extends AbstractNode<String> {

  /**
   * Constructor
   * @param value_p
   * @param parent_p
   */
  public StringNode(String value_p, AbstractNode<?> parent_p) {
    super(value_p, parent_p);
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.AbstractNode#getLabel()
   */
  @Override
  public String getLabel() {
    return getValue();
  }

  /**
   * @see com.thalesgroup.orchestra.framework.oe.ui.providers.nodes.AbstractNode#getNodeSegment()
   */
  @Override
  public String getNodeSegment() {
    return getValue();
  }

}
