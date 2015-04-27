/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.handler.data;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.model.contexts.Context;
import com.thalesgroup.orchestra.framework.model.handler.activator.ModelHandlerActivator;
import com.thalesgroup.orchestra.framework.project.ProjectActivator;
import com.thalesgroup.orchestra.framework.project.RootContextsProject;

/**
 * A logical container for contexts projects.<br>
 * Can either represent projects in the workspace, of from another location.
 * @author t0076261
 */
public class RootElement {
  /**
   * All contexts projects handled by this element.
   */
  protected List<RootContextsProject> _projects = new ArrayList<RootContextsProject>(0);

  protected Map<RootContextsProject, Context> _projectToContextMap = new HashMap<RootContextsProject, Context>();

  /**
   * All contexts matching projects
   */
  private List<Context> _contexts = null;

  /**
   * Add a new project to handled projects.
   * @param project_p A not <code>null</code> {@link RootContextsProject}.
   */
  public void addProject(RootContextsProject project_p) {
    if (null != project_p) {
      _projects.add(project_p);
      if (null != _contexts) {
        Context context = getContext(project_p);
        _contexts.add(context);
        _projectToContextMap.put(project_p, context);
      }
    }
  }

  /**
   * Remove elements that specified administrator can not modified.<br>
   * Contained projects collection is modified accordingly.
   * @param administratorId_p
   */
  public void filterForAdministrator(String administratorId_p) {
    // Precondition.
    if (null == administratorId_p) {
      return;
    }
    // Cycle through contexts project.
    for (Iterator<RootContextsProject> iterator = _projects.iterator(); iterator.hasNext();) {
      RootContextsProject project = iterator.next();
      boolean isAdministrated = project.isAdministrator(administratorId_p);
      // Specified administrator is not registered, remove this project.
      if (!isAdministrated) {
        if (null != _contexts) {
          Context context = _projectToContextMap.get(project);
          _contexts.remove(context);
          _projectToContextMap.remove(project);
        }
        iterator.remove();
      }
    }
  }

  /**
   * Get all referenced contexts.
   * @return
   */
  public List<Context> getContexts() {
    if (null == _contexts) {
      _contexts = new ArrayList<Context>(_projects.size());
      boolean administratorMode = ProjectActivator.getInstance().isAdministrator();
      String userId = ProjectActivator.getInstance().getCurrentUserId();
      // Go through a copy of the _projects list to avoid ConcurrentModificationException.
      List<RootContextsProject> projectsCopy = new ArrayList<RootContextsProject>(_projects);
      for (RootContextsProject project : projectsCopy) {
        // Get context no matter user is admin or not of this context
        ContextReference contextReference = project.getContext(false);
        if (null == contextReference) {
          continue;
        }
        Context context = null;
        try {
          context = ModelHandlerActivator.getDefault().getDataHandler().getContext(contextReference);
        } catch (Exception exception_p) {
          // Nothing to do, wait for null test to trigger.
        }
        if (null != context) {
          boolean isDefaultContext = context.eResource().isDefault();
          // Keep context in the following cases:
          // - Framework is in user mode, or
          // - Context is the current context no matter user is admin or not of the context
          // (this is usefull for the setCurrentContext command line parameter), or
          // - Context is the default context, or
          // - Framework is in admin mode and user is an admin of the context
          if (!administratorMode || ModelHandlerActivator.getDefault().getDataHandler().isCurrentContext(context) || isDefaultContext
              || project.isAdministrator(userId)) {
            _contexts.add(context);
            // Skip default context since there is no reason
            // to reference it for any usage
            if (!isDefaultContext) {
              _projectToContextMap.put(project, context);
            }
          }
        } else {
          CommonActivator.getInstance().logMessage(MessageFormat.format("Unable to load context model for ''{0}''.", project.getContext().getUri()), //$NON-NLS-1$
              IStatus.ERROR, null);
        }
      }
    }
    return _contexts;
  }

  /**
   * Get {@link RootContextsProject} handling specified context, as pointed by its {@link URI}.
   * @param contextUri_p
   * @return <code>null</code> if parameter is invalid, or no such context could be found.
   */
  protected RootContextsProject getContextsProjectFor(URI contextUri_p) {
    // Precondition.
    if (null == contextUri_p) {
      return null;
    }
    // Search for the exact same URI in referenced contexts.
    for (RootContextsProject contextsProject : _projects) {
      if (contextsProject.isHandling(contextUri_p)) {
        return contextsProject;
      }
    }
    return null;
  }

  /**
   * Get projects.<br>
   * This is a clone of internal collection. It can then be modified by the caller, but with no effect on root element.
   * @return A not <code>null</code>, but possibly empty collection of {@link RootContextsProject}.
   */
  public Collection<RootContextsProject> getProjects() {
    ArrayList<RootContextsProject> result = new ArrayList<RootContextsProject>(_projects);
    return result;
  }

  /**
   * Is element handling specified context, as pointed by its {@link URI}.
   * @param contextUri_p
   * @return <code>false</code> if parameter is invalid, or no such context could be found.
   */
  public boolean isHandling(URI contextUri_p) {
    return (null != getContextsProjectFor(contextUri_p));
  }

  /**
   * Remove all specified {@link RootContextsProject} from children.
   * @param projectsToRemove_p
   */
  public void removeAll(Collection<RootContextsProject> projectsToRemove_p) {
    // Precondition.
    if (null == projectsToRemove_p) {
      return;
    }
    for (RootContextsProject project : projectsToRemove_p) {
      removeProject(project);
    }
  }

  /**
   * Remove {@link RootContextsProject} from children for specified context, as pointed by its {@link URI}.<br>
   * Does nothing if no {@link RootContextsProject} is in charge of specified context, or parameter is invalid.
   * @param contextUri_p
   */
  public void removeProjectFor(URI contextUri_p) {
    RootContextsProject contextsProject = getContextsProjectFor(contextUri_p);
    if (null != contextsProject) {
      removeProject(contextsProject);
    }
  }

  /**
   * Set projects.<br>
   * Replaces known children with specified ones.<br>
   * Does nothing if specified collection is <code>null</code>.<br>
   * Call with empty collection to reset existing one.
   * @param children_p
   */
  public void setProjects(Collection<RootContextsProject> children_p) {
    // Precondition.
    if (null == children_p) {
      return;
    }
    // Clear list.
    _projects.clear();
    // Add new children.
    _projects.addAll(children_p);

    // Reset context list
    if (null != _contexts) {
      _contexts.clear();
      _contexts = null;
    }
  }

  /**
   * Get context from project
   * @param project_p
   * @return
   */
  public Context getContext(RootContextsProject project_p) {
    ContextReference contextReference = project_p.getContext();
    Context context = null;
    try {
      context = ModelHandlerActivator.getDefault().getDataHandler().getContext(contextReference);
    } catch (Exception exception_p) {
      // Nothing to do, wait for null test to trigger.
    }
    return context;
  }

  /**
   * Remove project and its matching context
   * @param project_p
   */
  public void removeProject(RootContextsProject project_p) {
    if (null != _contexts) {
      Context context = _projectToContextMap.get(project_p);
      _contexts.remove(context);
      _projectToContextMap.remove(project_p);
    }
    _projects.remove(project_p);
  }
}