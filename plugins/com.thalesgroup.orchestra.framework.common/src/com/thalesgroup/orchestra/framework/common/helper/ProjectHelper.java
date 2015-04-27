/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.helper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.prefs.BackingStoreException;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;
import com.thalesgroup.orchestra.framework.common.util.Couple;

/**
 * @author t0076261
 */
public class ProjectHelper {
	
	public static class ValidationException extends RuntimeException {
		
		private final String _message;
		
		private static final long serialVersionUID = -7174319247409476827L;
		
		/**
		 * Constructor
		 * @param message_p
		 */
		public ValidationException(final String message_p) {
			this._message = message_p;
		}
		
		@Override
		public String getMessage() {
			return this._message;
		}
	}
	
	/**
	 * Add new nature to specified project.
	 * @param project_p
	 * @param natureId_p
	 * @return <code>false</code> if parameters are invalid, or nature could not be added. <code>true</code> otherwise.
	 */
	public static boolean addNatureToProject(final IProject project_p, final String natureId_p) {
		// Preconditions.
		if ((null == project_p) || (null == natureId_p)) {
			return false;
		}
		try {
			// Get project description.
			final IProjectDescription description = project_p.getDescription();
			// Get existing natured IDs.
			final String[] natureIds = description.getNatureIds();
			// Unlikely to be empty or null as the project as to be a context project before being able to be a baseline one.
			final String[] newNatureIds = Arrays.copyOf(natureIds, natureIds.length + 1);
			newNatureIds[natureIds.length] = natureId_p;
			// Set new nature IDs.
			description.setNatureIds(newNatureIds);
			// Save it.
			project_p.setDescription(description, null);
			return true;
		}
		catch (final Exception exception_p) {
			return false;
		}
	}
	
	/**
	 * Copy specified project to workspace and import it.<br>
	 * As a side effect, specified description is modified to point to the workspace.
	 * @param project_p
	 * @param description_p
	 * @return <code>true</code> if project content was copied to workspace, and project was successfully opened, <code>false</code> if it could not be copied or imported.
	 */
	public static IStatus copyProjectAndImportToWorkspace(final IProject project_p, final IProjectDescription description_p) {
		// Precondition.
		if ((null == project_p) || (null == description_p)) {
			return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), ICommonConstants.EMPTY_STRING);
		}
		// Copy project.
		try {
			File destinationDir = new File(ResourcesPlugin.getWorkspace().getRoot().getLocationURI());
			final IPath destinationPath = new Path(destinationDir.getAbsolutePath()).append(project_p.getName());
			destinationDir = destinationPath.toFile();
			final File sourceDir = new File(description_p.getLocationURI());
			FileUtils.copyDirectory(sourceDir, destinationDir, false);
		}
		catch (final IOException exception_p) {
			final StringBuilder loggerMessage = new StringBuilder("ProjectHelper.copyProjectAndAddToWorkspace(..) _ "); //$NON-NLS-1$
			CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
			return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), Messages.ProjectHelper_Project_Copy_Failed, exception_p);
		}
		// Update description.
		description_p.setLocationURI(null);
		// Import into workspace.
		return importExistingProject(project_p, description_p);
	}
	
	/**
	 * Copy entire project contents to specified absolute root destination location.
	 * @param project_p
	 * @param rootDestinationLocation_p
	 * @param exceptions_p the resources to not copy. Can be <code>null</code> if there are no resources to not copy.
	 * @param <code>true</code> to force copy, even if destination already exists, <code>false</code> to fail if destination already exists.
	 * @return <code>true</code> if copy was successful, <code>false</code> otherwise.
	 */
	public static IStatus copyProjectTo(final IProject project_p, final String rootDestinationLocation_p, final boolean force_p, final Collection<IResource> exceptions_p) {
		// Precondition.
		if ((null == rootDestinationLocation_p) || (null == project_p)) {
			return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), ICommonConstants.EMPTY_STRING);
		}
		// Precondition.
		if (!project_p.exists() || !project_p.isOpen()) {
			return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), Messages.ProjectHelper_Precondition_ExistOpen_Failed);
		}
		// Precondition.
		try {
			final String destinationCanonicalPath = new File(rootDestinationLocation_p).getCanonicalPath();
			if (project_p.getLocation().removeLastSegments(1).toFile().getCanonicalPath().equals(destinationCanonicalPath)) {
				return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), MessageFormat.format(Messages.ProjectHelper_Precondition_NotInCurrentLocation_Failed, project_p.getName(), destinationCanonicalPath));
			}
		}
		catch (final IOException exception_p) {
			return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), Messages.ProjectHelper_Precondition_Files_Failed, exception_p);
		}
		return FileHelper.copyResourceTo(project_p, new Path(rootDestinationLocation_p), force_p, exceptions_p);
	}
	
	/**
	 * Creates an {@link IProject} for the name in the default workspace location or in the newProjectLocation.
	 * @param projectName_p the project name to create.
	 * @param newProjectLocation_p a location to create the project or <code>null</code> to create project in the default location.
	 * @param projectNature_p the project nature if any
	 * @return the create project.
	 */
	public static IProject createAnEmptyContextsProject(final String projectName_p, final String newProjectLocation_p, final String projectNature_p) {
		final IProject result = getProject(projectName_p);
		final IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(projectName_p);
		if (null != projectNature_p) {
			description.setNatureIds(new String[] { projectNature_p });
		}
		if (null != newProjectLocation_p) {
			description.setLocation(new Path(newProjectLocation_p).append("/" + projectName_p)); //$NON-NLS-1$
		}
		importExistingProject(result, description);
		return result;
	}
	
	/**
	 * Delete specified project.
	 * @param project_p
	 * @param deleteContents_p <code>true</code> to suppress contents on disk, <code>false</code> to simply remove from workspace.
	 * @return <code>true</code> if the project existed and was successfully deleted, <code>false</code> otherwise.
	 */
	public static boolean deleteProject(final IProject project_p, final boolean deleteContents_p) {
		boolean result = false;
		if ((null != project_p) && project_p.exists()) {
			try {
				project_p.delete(deleteContents_p, true, null);
				result = true;
			}
			catch (final CoreException exception_p) {
				final StringBuilder loggerMessage = new StringBuilder("ProjectHelper.deleteProject(..) _ "); //$NON-NLS-1$
				CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
			}
		}
		return result;
	}
	
	/**
	 * Delete project from its name.
	 * @param projectName_p
	 * @param deleteContents_p <code>true</code> to suppress contents on disk, <code>false</code> to simply remove from workspace.
	 * @return <code>true</code> if the project existed and was successfully deleted, <code>false</code> otherwise.
	 */
	public static boolean deleteProject(final String projectName_p, final boolean deleteContents_p) {
		return deleteProject(getProject(projectName_p), deleteContents_p);
	}
	
	/**
	 * Generate context hosting project name for specified context name.
	 * @param contextName_p
	 * @return
	 */
	public static String generateContextProjectName(final String contextName_p) {
		// Precondition.
		if (null == contextName_p) {
			return null;
		}
		return contextName_p.replaceAll("\\W", ICommonConstants.EMPTY_STRING); //$NON-NLS-1$
	}
	
	/**
	 * Get absolute path from local URI.<br>
	 * See {@link FileHelper#getAbsolutePath(String, URI)} with first parameter being the specified project description location.
	 * @param projectDescription_p
	 * @param localUri_p
	 * @return <code>null</code> if one of the parameter is not valid.
	 */
	public static String getAbsolutePath(final IProjectDescription projectDescription_p, final URI localUri_p) {
		// Precondition.
		if (null == projectDescription_p) {
			return null;
		}
		// Find project's container path.
		String projectContainerLocation = null;
		if (null == projectDescription_p.getLocationURI()) {
			// No location in project description -> project is localized in the workspace.
			projectContainerLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		}
		else {
			// Location is specified, remove project directory.
			projectContainerLocation = new Path(projectDescription_p.getLocationURI().getPath()).removeLastSegments(1).toString();
		}
		// Project name is removed from the absolute location, for this is already embedded in the specified local URI.
		return FileHelper.getAbsolutePath(projectContainerLocation, localUri_p);
	}
	
	/**
	 * Get project from its name.<br>
	 * It is assumed that this project name lies in the workspace.
	 * @param projectName_p A project name that points to a project in the workspace.
	 * @return
	 */
	public static IProject getProject(final String projectName_p) {
		// Precondition.
		if (null == projectName_p) {
			return null;
		}
		return ResourcesPlugin.getWorkspace().getRoot().getProject(projectName_p);
	}
	
	/**
	 * Get project from its absolute URL.<br>
	 * Note that such a project does not necessarily exists in the workspace.<br>
	 * It might have to be created and opened to exist in the workspace.<br>
	 * The best thing to test at first is project existence.
	 * @param projectRootURL_p
	 * @return <code>null</code> if specified URL does not point to a project, or another error happened.
	 */
	public static Couple<IProject, IProjectDescription> getProject(final URL projectRootURL_p, final boolean trace_p) {
		// Precondition.
		if (null == projectRootURL_p) {
			return null;
		}
		final IPath rootPath = new Path(projectRootURL_p.getPath());
		final IPath path = rootPath.append("/.project"); //$NON-NLS-1$
		try {
			final IProjectDescription projectDescription = ResourcesPlugin.getWorkspace().loadProjectDescription(path);
			projectDescription.setLocationURI(rootPath.toFile().toURI());
			final IProject project = getProject(projectDescription.getName());
			return new Couple<IProject, IProjectDescription>(project, projectDescription);
		}
		catch (final Exception exception_p) {
			if (trace_p) {
				final StringBuilder loggerMessage = new StringBuilder("ProjectHelper.getProject(..) _ "); //$NON-NLS-1$
				CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
			}
		}
		return null;
	}
	
	/**
	 * Get all unreachable projects
	 * @return
	 */
	public static Map<URL, String> getUnreachablebleProjects() {
		return ProjectHelper._unreachableProjectMap;
	}
	
	/**
	 * Handle unaccessible projects from workspace.<br>
	 * Such projects are loaded in workspace but either their directory has been deleted or moved, or their definition is corrupted (within current workspace).<br>
	 * Note that the handling consists in the following actions :
	 * <ul>
	 * <li>Detect corrupted projects.
	 * <li>Try and remove them from workspace.
	 * <li>Flush (ie save) workspace.
	 * <li>Re-import projects that had a corrupted definition, but exist on file system.
	 * </ul>
	 * @return <code>{@link Status#OK_STATUS}</code> if no project was corrupted, a <code>{@link MultiStatus}</code> with handling results if some projects are corrupted.
	 */
	public static IStatus handleUnaccessibleProjectsFromWorkspace() {
		loadUnreachableProjectsPreferences();
		final ArrayList<IStatus> childrenStatuses = new ArrayList<IStatus>();
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		final List<URL> deletedProjectURLs = new ArrayList<URL>(0);
		// Go through list of projects loaded in workspace to find unaccessible ones.
		for (final IProject project : projects) {
			if (!project.isAccessible()) {
				URL url = null;
				try {
					url = project.getLocationURI().toURL();
				}
				catch (final MalformedURLException exception_p) {
					// Getting information failed.
					childrenStatuses.add(new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), MessageFormat.format(Messages.ProjectHelper_HandleWorkspace_Error_GettingCorruptedProjectData, project.getName())));
				}
				// Save deleted project information
				System.out.println(project.getName() + " " + project.getLocation() + " " + url + " " + url.getFile()); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				ProjectHelper._unreachableProjectMap.put(url, project.getName());
				final boolean deleted = deleteProject(project, false);
				if (deleted) {
					// Retain project URL.
					if (null != url) {
						deletedProjectURLs.add(url);
					}
					// Deletion was successful.
					childrenStatuses.add(new Status(IStatus.WARNING, CommonActivator.getInstance().getPluginId(), MessageFormat.format(Messages.ProjectHelper_UnaccessibleProject_Delete_Success, project.getName())));
				}
				else {
					// Deletion failed.
					childrenStatuses.add(new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), MessageFormat.format(Messages.ProjectHelper_UnaccessibleProject_Delete_Failure, project.getName())));
				}
			}
		}
		// Try and re-import removed corrupted projects.
		final boolean importRemovedProjects = !deletedProjectURLs.isEmpty() || !ProjectHelper._unreachableProjectMap.isEmpty();
		if (importRemovedProjects) {
			// Flush workspace first.
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			if (null != workspace) {
				try {
					workspace.save(true, null);
				}
				catch (final CoreException exception_p) {
					// Save workspace failed.
					childrenStatuses.add(new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), Messages.ProjectHelper_HandleWorkspace_Error_CantSaveWorkspace));
				}
			}
			deletedProjectURLs.addAll(ProjectHelper._unreachableProjectMap.keySet());
			// Then try and re-import those that have been deleted.
			for (final URL deletedProjectUrl : deletedProjectURLs) {
				try {
					final File file = new File(deletedProjectUrl.toURI());
					if (file.exists()) {
						IStatus importStatus = importExistingProject(deletedProjectUrl);
						if (importStatus.isOK()) {
							// Project successfully re-imported.
							importStatus = new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), MessageFormat.format(Messages.ProjectHelper_HandleWorkspace_Warning_ProjectReImportedAfterCorruption, deletedProjectUrl));
							ProjectHelper._unreachableProjectMap.remove(deletedProjectUrl);
						}
						childrenStatuses.add(importStatus);
					}
				}
				catch (final Exception exception_p) {
					// Import failed.
					childrenStatuses.add(new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), MessageFormat.format(Messages.ProjectHelper_HandleWorkspace_Error_ProjectCantBeReImportedAfterCorruption, deletedProjectUrl)));
				}
			}
		}
		saveUnreachableProjectsPreferences();
		// Some errors occurred.
		if (!childrenStatuses.isEmpty()) {
			return new MultiStatus(CommonActivator.getInstance().getPluginId(), 0, childrenStatuses.toArray(new IStatus[0]), Messages.ProjectHelper_UnaccessibleProject_Delete_Result, null);
		}
		return Status.OK_STATUS;
	}
	
	/**
	 * Import specified remote project into workspace.<br>
	 * Does not copy the content of the project into the workspace.
	 * @param remoteProject_p
	 * @param projectDescription_p
	 * @return <code>true</code> if the project was successfully imported, <code>false</code> otherwise.
	 */
	public static IStatus importExistingProject(final IProject remoteProject_p, final IProjectDescription projectDescription_p) {
		// Precondition.
		if (null == remoteProject_p) {
			return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), ICommonConstants.EMPTY_STRING);
		}
		// Create and open project.
		IStatus result = null;
		try {
			if (null != projectDescription_p) {
				if (isPhysicallyInWorkspace(projectDescription_p.getLocationURI())) {
					projectDescription_p.setLocation(null);
				}
				remoteProject_p.create(projectDescription_p, null);
			}
			else {
				remoteProject_p.create(null);
			}
			remoteProject_p.open(null);
			result = Status.OK_STATUS;
		}
		catch (final Exception exception_p) {
			final StringBuilder loggerMessage = new StringBuilder("ProjectHelper.importExistingProject(..) _ "); //$NON-NLS-1$
			CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
			result = new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), Messages.ProjectHelper_Project_Import_Failed, exception_p);
		}
		return result;
	}
	
	/**
	 * Import existing project into workspace.<br>
	 * Does not copy the content of the project into the workspace.
	 * @param projectRootURL_p
	 * @return <code>true</code> if the project was successfully imported, <code>false</code> otherwise.
	 */
	public static IStatus importExistingProject(final URL projectRootURL_p) {
		// Precondition.
		if (null == projectRootURL_p) {
			return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), ICommonConstants.EMPTY_STRING);
		}
		final Couple<IProject, IProjectDescription> project = getProject(projectRootURL_p, true);
		if (null != project) {
			return importExistingProject(project.getKey(), project.getValue());
		}
		return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), Messages.ProjectHelper_Project_Not_Found);
	}
	
	/**
	 * Whether the project is physically stored in workspace.
	 * @param uri_p usually, the URI of the project description.
	 * @return <code>true</code> if the project is physically stored in workspace, <code>false</code> otherwise.
	 */
	public static boolean isPhysicallyInWorkspace(final java.net.URI uri_p) {
		if (null == uri_p) {
			return true;
		}
		// We are only able to deal with file system project.
		final Path location = new Path(new File(uri_p).toString());
		return Platform.getLocation().isPrefixOf(location);
	}
	
	/**
	 * Whether the project with the given name exists.
	 * @param projectName_p the project name.
	 * @return <code>true</code> if the project exists, <code>false</code> if it is not or if the given name is null or empty.
	 */
	public static boolean isProjectExisting(final String projectName_p) {
		if (null == projectName_p || projectName_p.isEmpty()) {
			return false;
		}
		final IProject handle = ProjectHelper.getProject(projectName_p);
		return handle.exists();
	}
	
	/**
	 * Save unreachable projects lists to preferences
	 */
	private static void loadUnreachableProjectsPreferences() {
		final String value = Platform.getPreferencesService().getString(CommonActivator.getInstance().getPluginId(), ProjectHelper.UNREACHABLE_PROJECTS_PREFERENCE_KEY, ICommonConstants.EMPTY_STRING, null);
		final String[] values = value.split(ProjectHelper.PREFERENCE_VALUE_SEPARATOR);
		if (values.length > 1) {
			for (int i = 0; i < values.length; i += 2) {
				try {
					ProjectHelper._unreachableProjectMap.put(new URL(values[i]), values[i + 1]);
				}
				catch (final MalformedURLException exception_p) {
					// TODO Auto-generated catch block
					final StringBuilder loggerMessage = new StringBuilder("ProjectHelper.loadUnreachableProjects(..) _ "); //$NON-NLS-1$
					CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
				}
			}
		}
	}
	
	/**
	 * Refresh a project in the workspace.
	 * @param projectToRefresh_p
	 * @param depth_p
	 * @param monitor_p
	 * @see {@link IResource#refreshLocal(int, IProgressMonitor)}
	 */
	public static void refreshProject(final IProject projectToRefresh_p, final int depth_p, final IProgressMonitor monitor_p) {
		try {
			projectToRefresh_p.refreshLocal(depth_p, monitor_p);
		}
		catch (final CoreException exception_p) {
			final StringBuilder loggerMessage = new StringBuilder("ProjectHelper.refreshProject(..) _ "); //$NON-NLS-1$
			CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
		}
	}
	
	/**
	 * Refresh given project in the workspace.
	 * @param project_p
	 * @param monitor_p
	 */
	public static void refreshProject(final IProject project_p, final IProgressMonitor monitor_p) {
		refreshProject(project_p, IResource.DEPTH_INFINITE, monitor_p);
	}
	
	/**
	 * Remove a project from the unreachable projects
	 * @param projectUrl_p
	 */
	public static void removeProjectFromUnreachableProjects(final URL projectUrl_p) {
		ProjectHelper._unreachableProjectMap.remove(projectUrl_p);
		saveUnreachableProjectsPreferences();
	}
	
	/**
	 * Remove a list of projects from the unreachable projects
	 * @param projectUrls_p List of URLs
	 */
	public static void removeProjectsFromUnreachableProjects(final List<URL> projectUrls_p) {
		for (final URL url : projectUrls_p) {
			ProjectHelper._unreachableProjectMap.remove(url);
		}
		saveUnreachableProjectsPreferences();
	}
	
	/**
	 * Load unreachable projects lists from preferences
	 */
	private static void saveUnreachableProjectsPreferences() {
		final StringBuilder stringBuilder = new StringBuilder();
		for (final Entry<URL, String> entry : ProjectHelper._unreachableProjectMap.entrySet()) {
			stringBuilder.append(entry.getKey());
			stringBuilder.append(ProjectHelper.PREFERENCE_VALUE_SEPARATOR);
			stringBuilder.append(entry.getValue());
			stringBuilder.append(ProjectHelper.PREFERENCE_VALUE_SEPARATOR);
		}
		
		// Save preferences.
		final IEclipsePreferences node = InstanceScope.INSTANCE.getNode(CommonActivator.getInstance().getPluginId());
		node.put(ProjectHelper.UNREACHABLE_PROJECTS_PREFERENCE_KEY, stringBuilder.toString());
		// Flush right now for further preference key readings.
		try {
			node.flush();
		}
		catch (final BackingStoreException exception_p) {
			final StringBuilder loggerMessage = new StringBuilder("ProjectHelpler.saveUnreachableProjects(..) _ "); //$NON-NLS-1$
			CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
		}
	}
	
	/**
	 * Validates that a project's name is valid regarding RCP native validation and filesystem prerequisites
	 * @param projectName_p the project name
	 * @throws ValidationException
	 */
	public final static void validateProjectName(final String projectName_p) throws ValidationException {
		if (!projectName_p.matches(".*\\w+.*")) { //$NON-NLS-1$
			throw new ValidationException(Messages.ProjectHelper_NewContextWizard_NoAlphaNum_Error_Message);
		}
		
		// See {@link WizardNewProjectCreationPage#validatePage()}
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IStatus nameStatus = workspace.validateName(projectName_p, IResource.PROJECT);
		if (!nameStatus.isOK()) {
			throw new ValidationException(nameStatus.getMessage());
		}
	}
	
	/**
	 * Current context preference key.
	 */
	private static final String UNREACHABLE_PROJECTS_PREFERENCE_KEY = "unreachableProjects"; //$NON-NLS-1$
	
	private static final String PREFERENCE_VALUE_SEPARATOR = ","; //$NON-NLS-1$
	
	private static Map<URL, String> _unreachableProjectMap = new HashMap<URL, String>();
}
