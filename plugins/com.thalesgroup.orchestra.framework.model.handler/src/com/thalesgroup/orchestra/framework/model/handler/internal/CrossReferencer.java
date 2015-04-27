/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.internal;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * An {@link ECrossReferenceAdapter} implementation that is able to clean itself.
 * @author t0076261
 */
public class CrossReferencer extends ECrossReferenceAdapter {
  /**
   * Clean references.
   */
  public void clean() {
    if (null != inverseCrossReferencer) {
      inverseCrossReferencer.clear();
    }
  }

  /**
   * @see org.eclipse.emf.ecore.util.ECrossReferenceAdapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
   */
  @Override
  public void notifyChanged(Notification notification_p) {
    // Do not take into account NonDirtyingNotification.
    if (!(notification_p instanceof NonDirtyingNotification)) {
      super.notifyChanged(notification_p);
    }
  }

  /**
   * @see org.eclipse.emf.ecore.util.ECrossReferenceAdapter#createInverseCrossReferencer()
   */
  @Override
  protected InverseCrossReferencer createInverseCrossReferencer() {
    return new SelfCleaningInverseCrossReferencer();
  }

  /**
   * @see org.eclipse.emf.ecore.util.ECrossReferenceAdapter#handleContainment(org.eclipse.emf.common.notify.Notification)
   */
  @Override
  protected void handleContainment(Notification notification_p) {
    super.handleContainment(notification_p);
    int eventType = notification_p.getEventType();
    switch (eventType) {
      case Notification.SET:
        Object newValue = notification_p.getNewValue();
        if (null != newValue) {
          break;
        }
        //$FALL-THROUGH$
      case Notification.REMOVE:
        EObject oldValue = null;
        try {
          oldValue = EObject.class.cast(notification_p.getOldValue());
        } catch (Exception exception_p) {
          // Do not deal with this value.
        }
        if (null != oldValue) {
          // Free references pointing this element.
          // Remove references for all children...
          TreeIterator<EObject> contents = oldValue.eAllContents();
          while (contents.hasNext()) {
            EObject next = contents.next();
            next.eAdapters().remove(this);
          }
          // ... then for root element itself.
          oldValue.eAdapters().remove(this);
        }
      break;
      default:
      break;
    }
  }

  /**
   * An inverse cross referencer that removes pending references.<br>
   * Pending references are elements that are no longer contained in a model resource, and still referenced in the cross referencer.
   * @author t0076261
   */
  protected class SelfCleaningInverseCrossReferencer extends InverseCrossReferencer {
    /**
     * Serialization UID.
     */
    private static final long serialVersionUID = 6491776096535453546L;

    /**
     * @see java.util.HashMap#get(java.lang.Object)
     */
    @Override
    public Collection<Setting> get(Object key_p) {
      // Get contained collection.
      Collection<Setting> collection = super.get(key_p);
      // Precondition.
      if (null == collection) {
        return collection;
      }
      // Try and remove pending elements from resulting collection.
      for (Iterator<Setting> settings = collection.iterator(); settings.hasNext();) {
        Setting setting = settings.next();
        EObject eObject = setting.getEObject();
        EObject rootContainer = EcoreUtil.getRootContainer(eObject);
        if (null == rootContainer.eResource()) {
          // This object has already been removed, do not return its setting !
          settings.remove();
        }
      }
      return collection;
    }
  }
}