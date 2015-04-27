/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

import com.thalesgroup.orchestra.framework.model.ModelActivator;
import com.thalesgroup.orchestra.framework.model.handler.internal.CrossReferencer;
import com.thalesgroup.orchestra.framework.model.handler.internal.NonDirtyingNotification;
import com.thalesgroup.orchestra.framework.model.handler.internal.migration.EnvironmentMigrationHandler;
import com.thalesgroup.orchestra.framework.model.migration.IMigrationHandler;
import com.thalesgroup.orchestra.framework.project.CaseUnsensitiveResourceSetImpl;

/**
 * Specific {@link ResourceSet} implementation that monitors resources modification.
 * @author t0076261
 */
public class ContextsResourceSet extends CaseUnsensitiveResourceSetImpl implements IEditingDomainProvider {
  /**
   * Dedicated cross referencer.
   */
  private CrossReferencer _crossReferencer;
  /**
   * Editing domain.
   */
  private EditingDomain _editingDomain;
  /**
   * Notifications listener.
   */
  private NotificationsListener _listener;

  /**
   * Constructor.
   */
  public ContextsResourceSet() {
    // Activate migration support.
    activateMigrationSupport();
    // Activate notifications listener.
    activateNotificationsListener();
    // Activate cross referencer.
    activateCrossReferencer();
  }

  /**
   * Activate notifications listener for this resource set.<br>
   * Thus all added contents will be monitored by a specific listener, that propagates notifications and adapts itself to model changes.
   */
  public void activateCrossReferencer() {
    _crossReferencer = new CrossReferencer();
    eAdapters().add(_crossReferencer);
  }

  /**
   * Activate migration support.
   */
  protected void activateMigrationSupport() {
    IMigrationHandler migrationHandler = ModelActivator.getInstance().getMigrationHandler();
    // Make sure a migration handler will be contributed.
    if (null == migrationHandler) {
      // Instantiate it.
      migrationHandler = new EnvironmentMigrationHandler();
      // And set it as current handler.
      ModelActivator.getInstance().setMigrationHandler(migrationHandler);
    }
    // Activate migration support for current resource set.
    migrationHandler.activateMigrationSupport(this);
  }

  /**
   * Activate notifications listener for this resource set.<br>
   * Thus all added contents will be monitored by a specific listener, that propagates notifications and adapts itself to model changes.
   */
  public void activateNotificationsListener() {
    _listener = new NotificationsListener();
    eAdapters().add(_listener);
  }

  /**
   * Clean resource set.<br>
   * This removes all resources.<br>
   * Also resets the cross referencer to emptiness.<br>
   * <b>WARNING</b> This should only be used by the tests framework. This is not intended to be used within the application !
   */
  public void clean() {
    getResources().clear();
    _crossReferencer.clean();
  }

  /**
   * Deactivate notifications listener for this resource set.<br>
   * The specific listener is removed from the resource set and its resources.<br>
   * For resources trees, it is removed as the trees are traversed.
   */
  public void deactivateNotificationsListener() {
    _listener.dispose();
    eAdapters().remove(_listener);
    _listener = null;
  }

  /**
   * Get cross referencer.
   * @return
   */
  public CrossReferencer getCrossReferencer() {
    return _crossReferencer;
  }

  /**
   * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
   */
  @Override
  public EditingDomain getEditingDomain() {
    return _editingDomain;
  }

  /**
   * Get centralized notifications listener.
   * @return
   */
  protected NotificationsListener getListener() {
    return _listener;
  }

  /**
   * Set parent editing domain.
   * @param editingDomain_p
   */
  protected void setEditingDomain(EditingDomain editingDomain_p) {
    _editingDomain = editingDomain_p;
  }

  /**
   * Notifications listener.<br>
   * Centralized notification adapter.<br>
   * Adapts itself to additions/removals in the model.<br>
   * Delegates all notifications to registered model listeners.
   * @author t0076261
   */
  public class NotificationsListener extends AdapterImpl {
    /**
     * Registered listeners.
     */
    private final Set<Adapter> _adapters;
    /**
     * Activation flag.
     */
    private volatile boolean _isActive;
    /**
     * Disposed flag.
     */
    private volatile boolean _isDisposed;

    /**
     * Constructor.
     */
    protected NotificationsListener() {
      _adapters = new HashSet<Adapter>(0);
      _isDisposed = false;
      _isActive = true;
    }

    /**
     * Add a new model adapter.
     * @param adapter_p
     */
    public void addAdapter(Adapter adapter_p) {
      _adapters.add(adapter_p);
    }

    /**
     * Add listener to specified element, if applicable.
     * @param element_p
     */
    protected void addToElement(Object element_p) {
      if (element_p instanceof Resource) {
        // Add itself to resource.
        ((Resource) element_p).eAdapters().add(this);
      } else if (element_p instanceof EObject) {
        // Add itself to object.
        EObject element = (EObject) element_p;
        element.eAdapters().add(this);
        // Add to sub-tree.
        for (EObject containedElement : element.eContents()) {
          addToElement(containedElement);
        }
      }
    }

    /**
     * Dispose.<br>
     * As an effect, this listener will remove itself from the model (as the model is traversed).<br>
     * It will also no longer send events to registered adapters.
     */
    public void dispose() {
      if (_isDisposed) {
        return;
      }
      _isDisposed = true;
      // Clear adapters.
      _adapters.clear();
      // Remove itself from resources.
      for (Resource resource : getResources()) {
        resource.eAdapters().remove(this);
      }
    }

    /**
     * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
     */
    @SuppressWarnings("fallthrough")
    @Override
    public void notifyChanged(Notification notification_p) {
      boolean isNonDirtying = (notification_p instanceof NonDirtyingNotification);
      if (_isDisposed) {
        // Do not take into account NonDirtyingNotification.
        if (!isNonDirtying) {
          removeFromNotifiers(notification_p);
        }
        return;
      }
      // Do not take into account NonDirtyingNotification.
      if (!isNonDirtying) {
        Collection<?> addedElements = null;
        Collection<?> removedElements = null;
        switch (notification_p.getEventType()) {
        // In case of addition, add itself to new element.
          case Notification.ADD:
            addedElements = Collections.singletonList(notification_p.getNewValue());
          case Notification.ADD_MANY:
            if (null == addedElements) {
              addedElements = (Collection<?>) notification_p.getNewValue();
            }
            for (Object object : addedElements) {
              addToElement(object);
            }
          break;
          // In case of removal, remove itself from old element.
          case Notification.REMOVE:
            removedElements = Collections.singletonList(notification_p.getOldValue());
          case Notification.REMOVE_MANY:
            if (null == removedElements) {
              removedElements = (Collection<?>) notification_p.getOldValue();
            }
            for (Object object : removedElements) {
              removeFromElement(object);
            }
          break;
          default:
          break;
        }
      }
      // Make sure listener is active.
      if (_isActive) {
        // Propagate notification to registered adapters.
        // Since registered adapters can change _adapters (e.g.: call removeAdapter), go through a copy to avoid ConcurrentModificationException.
        Adapter[] adaptersToNotify = _adapters.toArray(new Adapter[0]);
        for (Adapter adapterToNotify : adaptersToNotify) {
          adapterToNotify.notifyChanged(notification_p);
        }
      }
    }

    /**
     * Remove an existing model adapter.
     * @param adapter_p
     */
    public void removeAdapter(Adapter adapter_p) {
      _adapters.remove(adapter_p);
    }

    /**
     * Remove listener from specified element, if applicable.
     * @param element_p
     */
    protected void removeFromElement(Object element_p) {
      if (element_p instanceof Resource) {
        // Remove itself from resource.
        ((Resource) element_p).eAdapters().remove(this);
      } else if (element_p instanceof EObject) {
        // Remove itself from object.
        ((EObject) element_p).eAdapters().remove(this);
      }
    }

    /**
     * Remove listener from notifiers contained in specified notification.
     * @param notification_p
     */
    protected void removeFromNotifiers(Notification notification_p) {
      removeFromElement(notification_p.getNotifier());
      removeFromElement(notification_p.getOldValue());
      removeFromElement(notification_p.getNewValue());
    }

    /**
     * Set listener activation.<br>
     * If not active, the listener still registers itself on model elements, but does not launch any notification to registered adapters, and does not dirty
     * impacted resources.
     * @param isActive_p
     */
    public void setActive(boolean isActive_p) {
      _isActive = isActive_p;
    }
  }
}