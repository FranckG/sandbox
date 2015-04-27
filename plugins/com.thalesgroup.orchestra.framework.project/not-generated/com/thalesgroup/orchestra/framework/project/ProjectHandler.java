/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.project;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.helper.FileHelper;
import com.thalesgroup.orchestra.framework.common.helper.ProjectHelper;
import com.thalesgroup.orchestra.framework.common.util.Couple;
import com.thalesgroup.orchestra.framework.contextsproject.ContextReference;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProject;
import com.thalesgroup.orchestra.framework.contextsproject.ContextsProjectPackage;
import com.thalesgroup.orchestra.framework.project.nature.ProjectNature;

/**
 * Provides with various utilities about {@link ContextsProject}/{@link RootContextsProject}/{@link ContextReference} handling.
 * @author s0011584
 * @author t0076261
 */
public class ProjectHandler {
  /**
   * Delete directory content.
   * @param projectContentDestination_p the destination directory to clear.
   * @throws IOException
   */
  private void cleanExportDirectory(String projectContentDestination_p) throws IOException {
    FileHelper.cleanDirectoryExceptDirectories(projectContentDestination_p, null);
  }

  /**
   * Delete directory content except the user directory.
   * @param projectContentDestination_p the destination directory to clear.
   * @throws IOException
   */
  private void cleanExportDirectoryExceptUserDir(String projectContentDestination_p) throws IOException {
    FileHelper.cleanDirectoryExceptDirectories(projectContentDestination_p, Arrays.asList(new String[] { RootContextsProject.USERS_DIR }));
  }

  /**
   * Copy context hierarchy from specified source using specified destination locator.<br>
   * If copy of one of the element of hierarchy fails, then the whole process stops, and either returns <code>false</code> or throws an {@link Exception}.
   * @param source_p
   * @param destinationLocator_p
   * @return
   * @throws Exception
   */
  @SuppressWarnings("deprecation")
  public boolean copyHierarchy(RootContextsProject source_p, IFileLocator destinationLocator_p) throws Exception {
    // Precondition.
    if ((null == source_p) || (null == destinationLocator_p)) {
      return false;
    }
    // Copy hierarchy.
    boolean result = true;
    List<ContextReference> contextReferences = source_p._contextsProject.getContextReferences();
    for (ContextReference contextReference : contextReferences) {
      URI contextUri = URI.createURI(contextReference.getUri());
      String fileAbsoluteLocation = ProjectHelper.getAbsolutePath(source_p._description, contextUri);
      byte[] fileContent = FileHelper.readFile(new File(fileAbsoluteLocation).toURL().openStream());
      String copyFileAbsoluteLocation = destinationLocator_p.getContextAbsolutePath(contextReference);
      result &= FileHelper.writeFile(new File(copyFileAbsoluteLocation).toURL(), true, fileContent);
      if (!result) {
        break;
      }
    }
    return result;
  }

  /**
   * Export a source workspace project to the specified destination.<br />
   * The destination can not be the source project location.<br />
   * If override parameter is
   * <ul>
   * <li><code>false</code> and some conflict occurs in the destination, a warning will be thrown.</li>
   * <li><code>true</code> any conflict will be solved by removing content.</li>
   * </ul>
   * @param project_p the source project to export.
   * @param destinationLocation_p the destination when the project should be exported.
   * @param override_p whether to solve conflict by removal. <code>null</code> for default value, which is <code><code>false</code>.
   * @return the export status.
   */
  public IStatus exportProjectTo(IProject project_p, String destinationLocation_p, boolean override_p) {
    String generateContextProjectName = ProjectHelper.generateContextProjectName(project_p.getName());
    String projectContentDestination = destinationLocation_p + File.separator + generateContextProjectName;
    File projectContentDestinationFile = new File(projectContentDestination);
    if (projectContentDestinationFile.exists()) {
      List<RootContextsProject> validContextsFromLocation =
          ProjectActivator.getInstance().getProjectHandler().getValidContextsFromLocation(projectContentDestination);
      if (validContextsFromLocation.size() > 0) {
        if (override_p) {
          try {
            cleanExportDirectoryExceptUserDir(projectContentDestination);
          } catch (IOException exception_p) {
            return new Status(IStatus.ERROR, ProjectActivator.getInstance().getPluginId(), Messages.ProjectHandler_Clean_Error, exception_p);
          }
        } else {
          return new Status(IStatus.WARNING, ProjectActivator.getInstance().getPluginId(), MessageFormat.format(
              Messages.ProjectHandler_Export_Warning_ExistingProject, generateContextProjectName));
        }
      } else {
        if (override_p) {
          try {
            cleanExportDirectory(projectContentDestination);
          } catch (IOException exception_p) {
            return new Status(IStatus.ERROR, ProjectActivator.getInstance().getPluginId(), Messages.ProjectHandler_Clean_Error, exception_p);
          }
        } else {
          return new Status(IStatus.WARNING, ProjectActivator.getInstance().getPluginId(), MessageFormat.format(
              Messages.ProjectHandler_Export_Warning_ExistingFolder, generateContextProjectName));
        }
      }
    }
    Collection<IResource> exceptions = new ArrayList<IResource>();
    IFolder userFolder = project_p.getFolder(RootContextsProject.USERS_DIR);
    if (userFolder.exists()) {
      try {
        for (IResource userContext : userFolder.members()) {
          if (!userContext.getName().startsWith(ProjectActivator.getInstance().getCurrentUserId())) {
            exceptions.add(userContext);
          }
        }
      } catch (CoreException exception_p) {
        return new Status(IStatus.ERROR, ProjectActivator.getInstance().getPluginId(), Messages.ProjectHandler_FindUsers_Error, exception_p);
      }
    }
    return ProjectHelper.copyProjectTo(project_p, destinationLocation_p, override_p, exceptions);
  }

  /**
   * Get the context projects contained in workspace.
   * @return the list of projects in workspace.
   */
  public Collection<RootContextsProject> getAllProjectsInWorkspace() {
    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IProject[] projects = root.getProjects();
    Collection<RootContextsProject> contextsProjects = new ArrayList<RootContextsProject>();
    for (IProject project : projects) {
      if (!project.isAccessible()) {
        try {
          project.open(new NullProgressMonitor());
        } catch (Exception e_p) {
          // Skip this project, for it is not accessible.
          continue;
        }
      }
      RootContextsProject contextsProject = getRootContextsProject(project);
      if (null != contextsProject) {
        contextsProjects.add(contextsProject);
      }
    }
    return contextsProjects;
  }

  /**
   * Find conflicting RootContextsProjects in the given list. <br>
   * Conflicting RootContextsProjects are projects with the same path in the workspace (eclipse's project name) or the same admin context name.
   * @param rootContextProjects_p
   * @return
   */
  public Set<RootContextsProject> getConflictingContexts(List<RootContextsProject> rootContextProjects_p) {
    // Use a Set to preserve uniqueness.
    Set<RootContextsProject> conflictingContexts = new HashSet<RootContextsProject>();
    for (int i = 0; i < rootContextProjects_p.size(); ++i) {
      String context1Path = rootContextProjects_p.get(i)._project.getFullPath().toString();
      String context1Name = rootContextProjects_p.get(i).getAdministratedContext().getName();
      for (int j = i + 1; j < rootContextProjects_p.size(); ++j) {
        String context2path = rootContextProjects_p.get(j)._project.getFullPath().toString();
        String context2Name = rootContextProjects_p.get(j).getAdministratedContext().getName();
        // Same path or same name -> contexts are conflicting.
        if (context1Path.equalsIgnoreCase(context2path) || context1Name.equalsIgnoreCase(context2Name)) {
          conflictingContexts.add(rootContextProjects_p.get(i));
          conflictingContexts.add(rootContextProjects_p.get(j));
        }
      }
    }
    return conflictingContexts;
  }

  /**
   * Get {@link RootContextsProject} located at specified location.
   * @param location_p An absolute path to a location possibly containing a context project.
   * @param resourceSet_p The {@link ResourceSet} to use for model loading. <code>null</code> if a new one should be created.
   * @return <code>null</code> if there is no context project at specified location (or location is invalid).
   */
  @SuppressWarnings("deprecation")
  public RootContextsProject getContextFromLocation(String location_p, ResourceSet resourceSet_p) {
    // Precondition
    if (null == location_p) {
      return null;
    }
    // Scan for valid projects.
    IPath path = new Path(location_p);
    File directory = path.toFile();
    // Check location nature and existence.
    if (!(directory.exists() && directory.isDirectory())) {
      return null;
    }
    // Resource set, the model bag.
    ResourceSet resourceSet = resourceSet_p;
    if (null == resourceSet) {
      resourceSet = new CaseUnsensitiveResourceSetImpl();
    }
    RootContextsProject value = null;
    // Get project.
    Couple<IProject, IProjectDescription> project = null;
    try {
      project = ProjectHelper.getProject(directory.toURL(), false);
      if (null != project) {
        String absolutePath = directory.getAbsolutePath() + File.separator + ProjectFactory.DEFAULT_CONTEXTS_PROJECT_DESCRIPTION;
        if (project.getValue().hasNature(ProjectNature.CONTEXT_PROJECT_NATURE_ID)) {
          ContextsProject contextsProject = getContextsProject(URI.createFileURI(absolutePath), resourceSet);
          if (null != contextsProject) {
            value = new RootContextsProject();
            value._project = project.getKey();
            value._description = project.getValue();
            value._contextsProject = contextsProject;
          }
        }
      }
    } catch (MalformedURLException exception_p) {
      CommonActivator.getInstance().logMessage(MessageFormat.format("Unable to get project url from ''{0}''.", directory.toString()), IStatus.ERROR, //$NON-NLS-1$
          exception_p);
    }
    return value;
  }

  /**
   * Get contexts project from the hosting project, whether in the workspace or outside the workspace.
   * @param createdProject_p
   * @return <code>null</code> if parameters are inconsistent or an error occurred.
   */
  public ContextsProject getContextsProject(IProject createdProject_p) {
    return getContextsProject(getContextsProjectDescriptionUri(createdProject_p), ProjectActivator.getInstance().getEditingDomain().getResourceSet());
  }

  /**
   * Get contexts project from the contexts description model URI, within specified {@link ResourceSet}.
   * @param descriptionUri_p
   * @param resourceSet_p
   * @return <code>null</code> if parameters are inconsistent or an error occurred.
   */
  public ContextsProject getContextsProject(URI descriptionUri_p, ResourceSet resourceSet_p) {
    // Precondition
    if (null == descriptionUri_p) {
      return null;
    }
    if (null == resourceSet_p) {
      return null;
    }
    try {
      Resource resource = resourceSet_p.getResource(descriptionUri_p, true);
      for (EObject content : resource.getContents()) {
        if (ContextsProjectPackage.Literals.CONTEXTS_PROJECT.isInstance(content)) {
          return (ContextsProject) content;
        }
      }
    } catch (WrappedException exception_p) {
      CommonActivator.getInstance().logMessage(
          MessageFormat.format("Unable to load resource ''{0}''.", descriptionUri_p.toString()), IStatus.ERROR, exception_p); //$NON-NLS-1$
    }
    return null;
  }

  /**
   * Get contexts project description URI for specified project.
   * @param project_p
   * @return
   */
  public URI getContextsProjectDescriptionUri(IProject project_p) {
    // Precondition.
    if (null == project_p) {
      return null;
    }
    IPath locationPath = project_p.getFullPath();
    locationPath = locationPath.append(String.valueOf(IPath.SEPARATOR));
    locationPath = locationPath.append(ProjectFactory.DEFAULT_CONTEXTS_PROJECT_DESCRIPTION);
    return FileHelper.getFileFullUri(locationPath.toString());
  }

  /**
   * Get the contexts project - referenced in the workspace - for specified context URI.
   * @param contextURI_p
   * @return
   */
  public RootContextsProject getProjectFromContextUri(URI contextURI_p) {
    IPath path = new Path(contextURI_p.toPlatformString(true));
    String projectName = path.segment(0);
    IProject project = ProjectHelper.getProject(projectName);
    // There is nothing that can be done.
    if (null == project) {
      return null;
    }
    // Create result.
    RootContextsProject result = new RootContextsProject();
    result._project = project;
    try {
      result._description = project.getDescription();
    } catch (CoreException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("ProjectHandler.getProjectFromContextUri(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
      return null;
    }
    result._contextsProject = getContextsProject(result._project);
    return result;
  }

  /**
   * Get {@link RootContextsProject} descriptor for specified project.
   * @param project_p
   * @return <code>null</code> if specified project is <code>null</code>.
   */
  public RootContextsProject getRootContextsProject(IProject project_p) {
    if (null == project_p) {
      return null;
    }
    RootContextsProject contextsProject = new RootContextsProject();
    contextsProject._project = project_p;
    try {
      contextsProject._description = project_p.getDescription();
    } catch (CoreException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("ProjectHandler.getAllProjectsInWorkspace(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
    }
    contextsProject._contextsProject = getContextsProject(project_p);
    // This was not a real project !
    if (null == contextsProject._contextsProject) {
      return null;
    }
    return contextsProject;
  }

  /**
   * Visit valid contexts with directory filtering
   * @author s0040806
   */
  class ValidContextsVisitor extends SimpleFileVisitor<java.nio.file.Path> {

    private java.nio.file.Path _rootPath;
    private List<RootContextsProject> _contextsProjects;
    private List<PathMatcher> _excludedDirectoriesMatchers;

    public ValidContextsVisitor(String location_p, List<RootContextsProject> contextsProjects_p, List<String> excludedDirectories_p) {
      _rootPath = Paths.get(location_p);
      _contextsProjects = contextsProjects_p;
      // Build path matchers for all excluded directories
      if (null != excludedDirectories_p) {
        _excludedDirectoriesMatchers = new ArrayList<PathMatcher>();
        for (String directory : excludedDirectories_p) {
          // Convert "\" to "/" for matching purpose
          _excludedDirectoriesMatchers.add(FileSystems.getDefault().getPathMatcher("glob:" + directory.replace('\\', '/'))); //$NON-NLS-1$
        }
      }
    }

    private boolean isExcluded(java.nio.file.Path directory_p) {
      if (null != _excludedDirectoriesMatchers) {
        // Match on relative directory
        java.nio.file.Path relative = _rootPath.relativize(directory_p);
        for (PathMatcher matcher : _excludedDirectoriesMatchers) {
          if (matcher.matches(relative)) {
            return true;
          }
        }
      }
      return false;
    }

    private void addRootContext(java.nio.file.Path directory_p) {
      // Create a new model bag.
      ResourceSet resourceSet = new CaseUnsensitiveResourceSetImpl();
      RootContextsProject currentProject = getContextFromLocation(directory_p.toAbsolutePath().toString(), resourceSet);
      if (null != currentProject) {
        _contextsProjects.add(currentProject);
      }
    }

    /**
     * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
     */
    @Override
    public FileVisitResult visitFile(java.nio.file.Path file_p, BasicFileAttributes attributes_p) throws IOException {
      // When using max depth on walkFileTree, directories are processed like files in the deepest level
      // so we also have to process them here
      if (Files.isDirectory(file_p)) {
        addRootContext(file_p);
      }
      return FileVisitResult.CONTINUE;
    }

    /**
     * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
     */
    @Override
    public FileVisitResult preVisitDirectory(java.nio.file.Path directory_p, BasicFileAttributes attributes_p) throws IOException {
      addRootContext(directory_p);

      // Filter directory
      if (isExcluded(directory_p)) {
        return FileVisitResult.SKIP_SUBTREE;
      }
      return FileVisitResult.CONTINUE;
    }
  }

  /**
   * Scan for valid contexts in the location.
   * @param location_p An absolute path to a location possibly containing context projects.
   * @return A not <code>null</code> but possibly empty list of {@link RootContextsProject}.
   */
  public List<RootContextsProject> getValidContextsFromLocation(String location_p) {
    return getValidContextsFromLocation(location_p, null, Integer.MAX_VALUE);
  }

  /**
   * Scan for valid contexts in the location.
   * @param location_p An absolute path to a location possibly containing context projects.
   * @param excludedDirectories_p List of directory exclusion pattern
   * @param max depth of context discovery
   * @return A not <code>null</code> but possibly empty list of {@link RootContextsProject}.
   */
  public List<RootContextsProject> getValidContextsFromLocation(String location_p, List<String> excludedDirectories_p, int maxDepth_p) {
    // Precondition
    if (null == location_p) {
      return Collections.emptyList();
    }

    java.nio.file.Path directory = Paths.get(location_p);
    if (!Files.isDirectory(directory)) {
      return Collections.emptyList();
    }

    // Create a new model bag.
    ResourceSet resourceSet = new CaseUnsensitiveResourceSetImpl();
    // Go on with discovery.
    List<RootContextsProject> contextsProjects = new ArrayList<RootContextsProject>(0);
    try {
      Files.walkFileTree(directory, new HashSet<FileVisitOption>(), maxDepth_p, new ValidContextsVisitor(location_p, contextsProjects, excludedDirectories_p));
    } catch (IOException exception_p) {
      StringBuilder loggerMessage = new StringBuilder("ProjectHandler.getValidContextsFromLocation(..) _ "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
    }

    return contextsProjects;
  }

  /**
   * Provide with context absolute path (on file system) for copy operations.
   * @author t0076261
   */
  public interface IFileLocator {
    /**
     * Get context absolute path for specified context reference.
     * @param contextReference_p
     * @return
     */
    public String getContextAbsolutePath(ContextReference contextReference_p);
  }
}