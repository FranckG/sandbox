/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.helper;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsEditingDomain;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsResourceSet.NotificationsListener;
import com.thalesgroup.orchestra.framework.model.handler.internal.CrossReferencer;
import com.thalesgroup.orchestra.framework.model.handler.internal.NonDirtyingNotification;

/**
 * Various helpers about model notifications.
 * @author t0076261
 */
public class NotificationHelper {
  /**
   * Create non-dirtying notification for specified parameters.<br>
   * A non-dirtying notification simulates a real model notification at firing level, except that :<br>
   * <ul>
   * <li>It does not modify the model.
   * <li>It does not register changes to the {@link CrossReferencer}.
   * <li>It does not register changes to the {@link ResourceSet} {@link NotificationsListener}.
   * <li>It does not change the modification flag of a {@link Resource}.
   * </ul>
   * Typically, it is used to refresh a state handled by an {@link Adapter} on the model.
   * @param notificationType_p
   * @param notifier_p
   * @param feature_p
   * @param oldValue_p
   * @param newValue_p
   * @return A new {@link Notification} with specified values.
   */
  public static Notification createNonDirtyingNotification(int notificationType_p, Object notifier_p, EStructuralFeature feature_p, Object oldValue_p,
      Object newValue_p) {
    return new NonDirtyingNotification(notificationType_p, notifier_p, feature_p, oldValue_p, newValue_p);
  }

  /**
   * Create and fire a non-dirtying notification at {@link ResourceSet} level (for {@link ContextsEditingDomain}).<br>
   * See {@link #createNonDirtyingNotification(int, Object, EStructuralFeature, Object, Object)} for a complete description of a non-dirtying notification.
   * @param notificationType_p
   * @param notifier_p
   * @param feature_p
   * @param oldValue_p
   * @param newValue_p
   */
  public static void fireNonDirtyingNotification(int notificationType_p, Object notifier_p, EStructuralFeature feature_p, Object oldValue_p, Object newValue_p) {
    ModelHandlerActivator.getDefault().getEditingDomain().getResourceSet().eNotify(
        createNonDirtyingNotification(notificationType_p, notifier_p, feature_p, oldValue_p, newValue_p));
  }
}