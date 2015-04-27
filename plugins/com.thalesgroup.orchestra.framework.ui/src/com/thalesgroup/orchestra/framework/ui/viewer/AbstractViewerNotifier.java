/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.ui.viewer;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.model.handler.data.ContextsResourceSet.NotificationsListener;

/**
 * Abstract viewer notifier.<br>
 * Self register/remove itself as model notifications listener.<br>
 * Provides with basic runnables factory for viewer refresh.
 * @author t0076261
 */
public abstract class AbstractViewerNotifier extends AdapterImpl {
  /**
   * Viewer reminder.
   */
  protected AbstractTreeViewer _viewer;

  /**
   * Constructor.
   * @param viewer_p
   */
  public AbstractViewerNotifier(AbstractTreeViewer viewer_p) {
    _viewer = viewer_p;
    // Register listener.
    final NotificationsListener notificationsListener = ModelHandlerActivator.getDefault().getEditingDomain().getNotificationsListener();
    notificationsListener.addAdapter(this);
    // Register dispose listener.
    _viewer.getControl().addDisposeListener(new DisposeListener() {
      public void widgetDisposed(DisposeEvent e_p) {
        // Remove listener.
        notificationsListener.removeAdapter(AbstractViewerNotifier.this);
      }
    });
  }

  /**
   * Create "add element to viewer" runnable.
   * @param newElement_p
   * @return
   */
  protected Runnable createAddRunnable(final Object parent_p, final Object newElement_p) {
    // Precondition.
    if ((null == parent_p) || (null == newElement_p)) {
      return null;
    }
    return new Runnable() {
      public void run() {
        _viewer.add(parent_p, newElement_p);
      }
    };
  }

  /**
   * Create "add element to viewer with a disabled viewer comparator" runnable.
   * @param newElement_p
   * @return
   */
  protected Runnable createAddUnsortedRunnable(final Object parent_p, final Object newElement_p) {
    // Precondition.
    if ((null == parent_p) || (null == newElement_p)) {
      return null;
    }
    return new Runnable() {
      public void run() {
        // Disable comparator
        ViewerComparator comparator = _viewer.getComparator();
        _viewer.setComparator(null);
        // Add element
        _viewer.add(parent_p, newElement_p);
        // Re-enable comparator
        _viewer.setComparator(comparator);
      }
    };
  }

  /**
   * Create composite runnable.
   * @param runnables_p The runnables to run.<br>
   *          Note that the specified list is NOT cloned !
   * @return
   */
  protected Runnable createCompositeRunnable(List<Runnable> runnables_p) {
    return new CompositeRunnable(runnables_p);
  }

  /**
   * Create composite runnable.
   * @param runnables_p The runnables to run.<br>
   *          Note that the order is maintained.
   * @return
   */
  protected Runnable createCompositeRunnable(Runnable... runnables_p) {
    return new CompositeRunnable(Arrays.asList(runnables_p));
  }

  /**
   * Create "refresh element in viewer" runnable.
   * @param element_p
   * @return
   */
  protected Runnable createRefreshRunnable(final Object element_p) {
    // Precondition.
    if (null == element_p) {
      return null;
    }
    return new Runnable() {
      public void run() {
        _viewer.refresh(element_p);
      }
    };
  }

  /**
   * Create "remove element from viewer" runnable.
   * @param oldElement_p
   * @return
   */
  protected Runnable createRemoveRunnable(final Object oldElement_p) {
    // Precondition.
    if (null == oldElement_p) {
      return null;
    }
    return new Runnable() {
      public void run() {
        _viewer.remove(oldElement_p);
      }
    };
  }

  /**
   * Create "update elements in viewer" runnable.
   * @param elements_p
   * @return
   */
  protected Runnable createUpdateRunnable(final Object... elements_p) {
    // Precondition.
    if (null == elements_p) {
      return null;
    }
    return new Runnable() {
      public void run() {
        _viewer.update(elements_p, null);
      }
    };
  }

  /**
   * A composite runnable.
   * @author t0076261
   */
  protected class CompositeRunnable implements Runnable {
    /**
     * Composing runnables.
     */
    protected List<Runnable> _runnables;

    /**
     * Constructor.
     * @param runnables_p
     */
    protected CompositeRunnable(List<Runnable> runnables_p) {
      _runnables = runnables_p;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    public void run() {
      for (Runnable runnable : _runnables) {
        runnable.run();
      }
    }
  }
}