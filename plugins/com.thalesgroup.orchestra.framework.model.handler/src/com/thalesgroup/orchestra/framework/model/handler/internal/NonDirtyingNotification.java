/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.internal;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * @author t0076261
 */
public class NonDirtyingNotification extends ENotificationImpl {
  /**
   * Notifier.
   */
  private Object _notifier;

  /**
   * Constructor.
   * @param eventType_p
   * @param notifier_p
   * @param feature_p
   * @param oldValue_p
   * @param newValue_p
   */
  public NonDirtyingNotification(int eventType_p, Object notifier_p, EStructuralFeature feature_p, Object oldValue_p, Object newValue_p) {
    super(null, eventType_p, feature_p, oldValue_p, newValue_p);
    _notifier = notifier_p;
  }

  /**
   * @see org.eclipse.emf.ecore.impl.ENotificationImpl#getNotifier()
   */
  @Override
  public Object getNotifier() {
    return _notifier;
  }
}