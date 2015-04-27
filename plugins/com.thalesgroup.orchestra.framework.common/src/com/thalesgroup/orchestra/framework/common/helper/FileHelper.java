/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.common.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
//import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;

import com.thalesgroup.orchestra.framework.common.CommonActivator;
import com.thalesgroup.orchestra.framework.common.activator.ICommonConstants;

/**
 * @author t0076261
 */
public class FileHelper {
  /**
   * Clean a directory.<br />
   * Each folder which names are specified in the exception list will <strong>not</strong> be removed.
   * @param projectContentDestination_p the folder to clean.
   * @throws IOException in any error occurs in deletion.
   */
  public static void cleanDirectoryExceptDirectories(String directoryToClean_p, final Collection<String> exceptions_p) throws IOException {
    FilenameFilter filenameFilter = null;
    if (null != exceptions_p) {
      filenameFilter = new FilenameFilter() {
        public boolean accept(File directory_p, String filename_p) {
          return !exceptions_p.contains(filename_p);
        }
      };
    }
    File directory = new File(directoryToClean_p);
    for (File content : directory.listFiles(filenameFilter)) {
      if (content.isDirectory()) {
        FileUtils.deleteDirectory(content);
      } else {
        content.delete();
      }
    }
  }

  /**
   * Copy given source file content in given target file.
   * @param sourceFileRelativePath_p File path relative to the project or plug-in, included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @param targetFileRelativePath_p File path relative to the project or plug-in, included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   */
  public static void copyFile(String sourceFileRelativePath_p, String targetFileRelativePath_p) {
    byte[] content = readRawFile(sourceFileRelativePath_p);
    writeFile(targetFileRelativePath_p, true, content);
  }

  /**
   * Copy specified resource to specified absolute destination path.<br>
   * @param resource_p
   * @param destinationPath_p
   * @param force_p <code>true</code> to force copy, even if destination file already exists, <code>false</code> to fail if destination already exists.
   * @param exceptions_p the resources to not copy.
   * @return <code>{@link Status#OK_STATUS}</code> if copy was successful, a <code>{@link MultiStatus}</code> containing errors otherwise.
   */
  @SuppressWarnings("deprecation")
  public static IStatus copyResourceTo(IResource resource_p, IPath destinationPath_p, boolean force_p, Collection<IResource> exceptions_p) {
    if ((null == resource_p) || (null == destinationPath_p)) {
      return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), ICommonConstants.EMPTY_STRING);
    }
    // Precondition.
    if ((null != exceptions_p) && exceptions_p.contains(resource_p)) {
      return Status.OK_STATUS;
    }
    IPath targetPath = destinationPath_p.append(resource_p.getName());
    File targetFile = new File(targetPath.toOSString());
    if (!force_p && targetFile.exists()) {
      StringBuilder loggerMessage = new StringBuilder("ProjectHelper.copyResourceTo(..) _ "); //$NON-NLS-1$
      loggerMessage.append("Unable to write at ").append(targetPath).append(", a file already exists !"); //$NON-NLS-1$ //$NON-NLS-2$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, null);
      return new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), loggerMessage.toString());
    }
    boolean result = false;
    if (IResource.FILE != resource_p.getType()) {
      // Ensure path availability.
      ensurePathAvailability(targetPath.append("dummy.txt").toOSString()); //$NON-NLS-1$
      // Copy file only.
      IResource[] members = null;
      try {
        members = ((IContainer) resource_p).members();
      } catch (CoreException exception_p) {
        StringBuilder loggerMessage = new StringBuilder("ProjectHelper.copyResourceTo(..) _ "); //$NON-NLS-1$
        loggerMessage.append("Unable to read members of ").append(resource_p); //$NON-NLS-1$
        CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
      }
      // Cycle through members.
      if (null != members) {
        result = true;
        for (IResource member : members) {
          result &= copyResourceTo(member, targetPath, force_p, exceptions_p).isOK();
        }
      }
    } else {
      // Copy file.
      URL destinationURL = null;
      try {
        destinationURL = targetFile.toURL();
      } catch (MalformedURLException exception_p) {
        StringBuilder loggerMessage = new StringBuilder("ProjectHelper.copyResourceTo(..) _ "); //$NON-NLS-1$
        loggerMessage.append("Unable to compute destination path for file ").append(targetFile); //$NON-NLS-1$
        CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.ERROR, exception_p);
      }
      if (null != destinationURL) {
        result = writeFile(destinationURL, true, readRawFile(resource_p.getFullPath().toString()));
      }
    }
    return result ? Status.OK_STATUS : new Status(IStatus.ERROR, CommonActivator.getInstance().getPluginId(), MessageFormat.format(
        Messages.FileHelper_copyResourceTo_failed, resource_p.getFullPath(), destinationPath_p));
  }

  /**
   * Delete given relative file in the workspace.
   * @param workspaceRelativeFile_p
   */
  public static boolean deleteFile(String workspaceRelativeFile_p) {
    boolean result = false;
    // Get the file
    IFile fileToDelete = getPlatformFile(workspaceRelativeFile_p);
    if ((null != fileToDelete) && fileToDelete.exists()) {
      try {
        fileToDelete.delete(true, new NullProgressMonitor());
        result = true;
      } catch (CoreException exception_p) {
        StringBuilder loggerMessage = new StringBuilder("FileHelper.deleteFile(..) _ "); //$NON-NLS-1$
        loggerMessage.append("Failed to delete file:").append(workspaceRelativeFile_p); //$NON-NLS-1$
        CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
      }
    }
    return result;
  }

  /**
   * Delete given relative folder in the workspace.
   * @param workspaceRelativePath_p
   * @return true if successfully deleted, false otherwise.
   */
  public static boolean deleteFolder(String workspaceRelativePath_p) {
    boolean result = false;
    IFolder folderToDelete = getPlatformFolder(workspaceRelativePath_p);
    if ((null != folderToDelete) && folderToDelete.exists()) {
      try {
        folderToDelete.delete(true, new NullProgressMonitor());
        result = true;
      } catch (CoreException exception_p) {
        StringBuilder loggerMessage = new StringBuilder("FileHelper.deleteFolder(..) _ "); //$NON-NLS-1$
        loggerMessage.append("Failed to delete folder:").append(workspaceRelativePath_p); //$NON-NLS-1$
        CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
      }
    }
    return result;
  }

  /**
   * Make sure that given path is safe to use, ie ensure that all parent folders exist.
   * @param fileFullPath_p
   */
  public static void ensurePathAvailability(String fileFullPath_p) {
    // Get rid of file extension and file name, for this has no meaning in the parent folders chain.
    IPath parentFolderPath = new Path(fileFullPath_p).removeFileExtension().removeLastSegments(1);
    // If it still makes sense to create a folder, go for it.
    if (!parentFolderPath.isEmpty()) {
      File parentFolder = parentFolderPath.toFile();
      // Create the chain of parent folders.
      parentFolder.mkdirs();
    }
  }

  /**
   * Correct URI case.
   * <ol>
   * <li>Resolve given URI to find corresponding file in the file-system,</li>
   * <li>Get the canonical path of this file (to have its path with correct case),</li>
   * <li>Generate a new platform URI ("platform:/resource/{ProjectName}/{FileName}) using the canonical path</li>
   * </ol>
   * @param uri_p the URI to correct.
   * @return the given URI with case corrected or the given URI if case can't be corrected (e.g. file doesn't exist).
   */
  public static URI correctURICase(URI uri_p) {
    try {
      // Resolve given URI.
      URL unresolvedURL = new URL(URI.decode(uri_p.toString()));
      URL resolvedURL = FileLocator.resolve(unresolvedURL);
      // Get File from given resolved URL.
      File fsFile = new File(resolvedURL.getFile());
      if (!fsFile.exists()) {
        return uri_p;
      }
      // Get file canonical path.
      String canonicalFilePath = fsFile.getCanonicalPath();
      // Generate corresponding workspace URI.
      String projectName = Path.fromOSString(uri_p.toPlatformString(true)).segment(0);
      String canonicalProjectPath = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getLocation().toFile().getCanonicalPath();
      IPath fileRelativePathToProjectPath = Path.fromOSString(canonicalFilePath).makeRelativeTo(Path.fromOSString(canonicalProjectPath));
      return getFileFullUri(IPath.SEPARATOR + projectName + IPath.SEPARATOR + fileRelativePathToProjectPath);
    } catch (Exception ex) {
      return uri_p;
    }
  }

  /**
   * Is given file relative path pointing to an existing file ?
   * @param fileRelativePath_p File path relative to the plug-in, plug-in id included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @return
   */
  public static boolean exists(String fileRelativePath_p) {
    IFile file = getPlatformFile(fileRelativePath_p);
    return (null != file) ? file.exists() : false;
  }

  /**
   * Generate context file name for specified context name.
   * @param contextName_p
   * @return
   */
  public static String generateContextFileName(String contextName_p) {
    // Precondition.
    if (null == contextName_p) {
      return null;
    }
    return contextName_p.replaceAll("\\W", ICommonConstants.EMPTY_STRING) + '.' + ICommonConstants.FILE_EXTENSION_CONTEXTS; //$NON-NLS-1$
  }

  /**
   * Get absolute path from local URI.<br>
   * That is, a {@link URI} relative to a project, likely <code>platform:/resource/pathToLocation</code>.<br>
   * Then replace local part with specified absolute base location.<br>
   * This allows the conversion of a local reference to an absolute one, without having to link resources in the project.
   * @param absoluteBaseLocation_p
   * @param localUri_p
   * @return <code>null</code> if one of the parameter is not valid.
   */
  public static String getAbsolutePath(String absoluteBaseLocation_p, URI localUri_p) {
    // Precondition.
    if ((null == absoluteBaseLocation_p) || (null == localUri_p)) {
      return null;
    }
    // Initialize path with absolute base location.
    IPath result = new Path(absoluteBaseLocation_p);
    // Add URI contribution part, that is the path of the hierarchical URI.
    if (localUri_p.isPlatform()) {
      result = result.append(localUri_p.toPlatformString(true));
    } else {
      result = result.append(localUri_p.path());
    }
    return result.toString();
  }

  /**
   * Get file as a stream.
   * @param filePath_p File path relative to the project name or plug-in, included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @return If an error occurred <code>null</code> is returned.
   */
  public static InputStream getFileAsStream(String filePath_p) {
    return getURLAsStream(getFileFullUrl(filePath_p));
  }

  /**
   * Returns the file extension portion for given file path, <br>
   * or <code>null</code> if there is none.<br>
   * <p>
   * The file extension portion is defined as the string<br>
   * following the last period (".") character in the last segment.<br>
   * If there is no period in the last segment, the path has no<br>
   * file extension portion. If the last segment ends in a period,<br>
   * the file extension portion is the empty string.<br>
   * </p>
   * @param filePath_p
   * @return the file extension or <code>null</code>
   */
  public static String getFileExtension(String filePath_p) {
    return new Path(filePath_p).getFileExtension();
  }

  /**
   * Get a file uri from relative one which is not resolved against the eclipse platform.<br>
   * The returned uri starts with either 'platform:/plug-in/' or 'platform:/resource/'.
   * @param fileRelativePath_p File path relative to workspace.<br>
   *          It <b>must</b> start with <code>project name</code> or <code>plug-in id</code>.<br>
   *          <b>Example</b> :
   *          <ul>
   *          <li><code>InstallContext/context/installcontext.contexts</code> is a path relative to the workspace that refers to the <code>InstallContext</code>
   *          project, having a <code>context/installcontext.contexts</code> file. The resulting URI looks like
   *          <code>platform:/resource/InstallContext/context/installcontext.contexts</code>.
   *          <li><code>com.thalesgroup.orchestra.framework.model.handler/context/default.contexts</code> is a path relative to the deployed plug-in
   *          <code>com.thalesgroup.orchestra.framework.model.handler</code>. The resulting URI looks like
   *          <code>platform:/plugin/com.thalesgroup.orchestra.framework.model.handler/context/default.contexts</code>.
   *          </ul>
   * @return an {@link URI} not resolved against the eclipse platform.<br>
   */
  public static URI getFileFullUri(String fileRelativePath_p) {
    URI fileUri = null;
    // Precondition.
    if (null == fileRelativePath_p) {
      return fileUri;
    }
    // Find plug-in model base from relative first segment.
    IPath path = new Path(fileRelativePath_p);
    IProject project = ProjectHelper.getProject(path.segment(0));
    if ((null != project) && project.exists()) { // Project found, the file is in the workspace.
      fileUri = URI.createPlatformResourceURI(fileRelativePath_p, true);
    } else { // Resource not found, the file is deployed elsewhere.
      fileUri = URI.createPlatformPluginURI(fileRelativePath_p, true);
    }
    return fileUri;
  }

  /**
   * Get file full url from relative one.
   * @param fileRelativePath_p File path relative to workspace.<br>
   *          It <b>must</b> start with <i>pluginId</i> or <i>project name</i>. It is also recommended that both plug-in id and plug-in project names are the
   *          same.<br>
   *          As a convenience, the full path will refer to the plug-in id.<br>
   *          <b>Example</b> : <i>com.thalesgroup.mde.mdsofa/model/example.ecore</i> is a path relative to the workspace that refers to the
   *          <i>com.thalesgroup.mde.mdsofa plug-in</i>, having a <i>model/example.ecore</i> file in its project.<br>
   *          In Eclipse resource system, such a path is considered as an absolute one against the workspace root.<br>
   *          It's still referred to as a relative path, since the returned URL is absolute in the file system.
   * @return
   */
  public static URL getFileFullUrl(String fileRelativePath_p) {
    // Get the URI for given relative path.
    return getFileFullUrl(getFileFullUri(fileRelativePath_p));
  }

  /**
   * Get file full url from its full uri.<br>
   * See {@link #getFileFullUri(String)} method.
   * @param fileFullUri_p
   * @return
   */
  public static URL getFileFullUrl(URI fileFullUri_p) {
    URL result = null;
    // Resolve url from returned uri.
    try {
      String decodedFileFullUri = URI.decode(fileFullUri_p.toString());
      result = FileLocator.resolve(new URL(decodedFileFullUri));
    } catch (Exception exception_p) {
      StringBuilder loggerMessage = new StringBuilder("FileHelper.getFileFullPath(..) _ "); //$NON-NLS-1$
      loggerMessage.append("Unable to resolve the url for ").append(fileFullUri_p.toString()); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
    }
    return result;
  }

  /**
   * Get platform file as an {@link IResource} from its relative path.
   * @param fileRelativePath_p File path relative to the plug-in, plug-in id included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @return
   */
  public static IFile getPlatformFile(String fileRelativePath_p) {
    IFile result = null;
    // Precondition.
    if (null == fileRelativePath_p) {
      return result;
    }
    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    result = root.getFile(new Path(fileRelativePath_p));
    return result;
  }

  /**
   * Get platform folder as an {@link IResource} from its relative path.
   * @param folderRelativePath_p Folder path relative to the plug-in, plug-in id included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @return
   */
  public static IFolder getPlatformFolder(String folderRelativePath_p) {
    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    return root.getFolder(new Path(folderRelativePath_p));
  }

  /**
   * Get URL as a stream.
   * @param url_p URL to the file to open as a stream.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @return If an error occurred <code>null</code> is returned.
   */
  public static InputStream getURLAsStream(URL url_p) {
    try {
      return url_p.openStream();
    } catch (Exception exception_p) {
      StringBuilder loggerMessage = new StringBuilder("FileHelper.getURLAsStream(..) _ "); //$NON-NLS-1$
      loggerMessage.append("Failed to load ").append(url_p); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
      return null;
    }
  }

  /**
   * Check if the given directory is readable.
   * @return <code>true</code> if the given file is a readable directory, <code>false</code> else.
   */
  public static boolean isDirectoryReadable(File directory_p) {
    return directory_p.exists() && directory_p.isDirectory() && null != directory_p.list();
  }

  /**
   * Check if the given directory is writable by trying to create a file in it.
   * @param directory_p
   * @return <code>true</code> if the given file is a writable directory, <code>false</code> else.
   */
  public static boolean isDirectoryWritable(File directory_p) {
    if (!directory_p.exists() || !directory_p.isDirectory()) {
      return false;
    }
    try {
      // Try to create a file with a random name.
      Random randomGenerator = new Random();
      boolean testFileCreated = false;
      File testFile = null;
      // Try to create a file until the file is truly created or an exception is thrown.
      while (!testFileCreated) {
        testFile = new File(directory_p.getAbsolutePath() + File.separator + Integer.toString(Math.abs(randomGenerator.nextInt())));
        testFileCreated = testFile.createNewFile();
      }
      testFile.delete();
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  /**
   * Is specified absolute path pointing to an existing object on file system ?
   * @param path_p The path to check
   * @return A {@link File} to the path if it exists, <code>null</code> otherwise.
   */
  public static File isValidAbsolutePath(String path_p) {
    File result = null;
    Path path = new Path(path_p);
    // If the path is not absolute, return null
    if (!path.isAbsolute()) {
      return result;
    }
    result = path.toFile();
    // If the file does not exist, return null
    if (!result.exists()) {
      return null;
    }
    return result;
  }

  /**
   * Make file writable.<br>
   * That is, make sure file is modifiable after this call.<br>
   * The user may be asked to take a decision if the file is held by the configuration management system.<br>
   * Nevertheless, if no UI is reachable, then the system is urged into making the file writable.
   * @param filePath_p File path relative to the plug-in, plug-in id included. See {@link #getFileFullUrl(String)} documentation.
   * @return false if file could not be made writable or user denied rights to (in case of a configuration management). true if it does not exist (then it is
   *         writable) or permission was granted (either by the system or by the user).
   */
  public static boolean makeFileWritable(String filePath_p) {
    // TODO Guillaume
    // Introduce the extension point, along with its implementation in common.ui.
    // // Get user helper.
    // IUserEnforcedHelper helper = SolFaCommonActivator.getDefault().getUserEnforcedHelper();
    // // Delegate to it.
    // IStatus status = helper.makeFileWritable(getPlatformFile(filePath_p));
    // return status.isOK();
    return true;
  }

  /**
   * Move resource to given destination path.
   * @param resource_p
   * @param destinationPath_p
   * @return true if move occurred with no exception, false otherwise.
   */
  public static boolean moveResource(IResource resource_p, IPath destinationPath_p) {
    boolean result = false;
    try {
      resource_p.move(destinationPath_p, true, new NullProgressMonitor());
      result = true;
    } catch (Exception e_p) {
      StringBuilder loggerMessage = new StringBuilder("FileHelper.moveResource(..) _ "); //$NON-NLS-1$
      loggerMessage.append("Could not move ").append(resource_p.getFullPath()); //$NON-NLS-1$
      loggerMessage.append(" to ").append(destinationPath_p); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, e_p);
    }
    return result;
  }

  /**
   * Read given input stream as an array of bytes.
   * @param inputStream_p
   * @return a not null array.
   */
  public static byte[] readFile(InputStream inputStream_p) {
    byte[] buffer = null;
    try {
      // Available bytes to read.
      int sizeToRead = inputStream_p.available();
      // Allocate a new buffer with the right size.
      buffer = new byte[sizeToRead];
      // Read bytes from the input stream.
      int readBytes = inputStream_p.read(buffer);
      // Total read bytes count.
      int totalReadBytes = readBytes;
      // Read again, if available bytes remain.
      while (readBytes != -1 && (totalReadBytes < sizeToRead)) {
        readBytes = inputStream_p.read(buffer, totalReadBytes, sizeToRead - totalReadBytes);
        // Add readBytes to the total read bytes count.
        if (readBytes != -1) {
          totalReadBytes += readBytes;
        }
      }
    } catch (Exception exception_p) {
      StringBuilder loggerMessage = new StringBuilder("FileHelper.readFile(..) _ "); //$NON-NLS-1$
      loggerMessage.append("Failed to read the input stream ! "); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
    } finally {
      if (null != inputStream_p) {
        try {
          inputStream_p.close();
        } catch (IOException exception_p) {
          StringBuilder loggerMessage = new StringBuilder("FileHelper.readFile(..) _ "); //$NON-NLS-1$
          loggerMessage.append("Failed to close input stream ! "); //$NON-NLS-1$
          CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
        }
      }
    }
    // Ensure to return a not null array.
    return (null == buffer) ? new byte[0] : buffer;
  }

  /**
   * Read file as a string.
   * @param filePath_p File path relative to the project name or plug-in, included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @return If an error occurred {@link ICommonConstants#EMPTY_STRING} is returned.
   */
  public static String readFile(String filePath_p) {
    byte[] rawContent = readRawFile(filePath_p);
    String result = (0 == rawContent.length) ? ICommonConstants.EMPTY_STRING : new String(rawContent);
    return result;
  }

  /**
   * Read file as a string.
   * @param fileUri_p File URI relative to the project name or plug-in, included.<br>
   *          See {@link #getFileFullUri(String)} documentation.
   * @return If an error occurred {@link ICommonConstants#EMPTY_STRING} is returned.
   */
  public static String readFile(URI fileUri_p) {
    byte[] rawContent = readRawFile(fileUri_p);
    String result = (0 == rawContent.length) ? ICommonConstants.EMPTY_STRING : new String(rawContent);
    return result;
  }

  /**
   * Read file as an array of bytes.
   * @param filePath_p File path relative to the project name or plug-in, included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @return a not <code>null</code> but possibly empty array.
   */
  public static byte[] readRawFile(String filePath_p) {
    byte[] result = null;
    // Get stream from file.
    InputStream inputStream = getFileAsStream(filePath_p);
    // Ensure the input stream got from the file path is not null.
    if (null != inputStream) {
      result = readFile(inputStream);
    }
    return (null == result) ? new byte[0] : result;
  }

  /**
   * Read file as an array of bytes.
   * @param fileUri_p File URI relative to the project name or plug-in, included.<br>
   *          See {@link #getFileFullUri(String)} documentation.
   * @return a not <code>null</code> but possibly empty array.
   */
  public static byte[] readRawFile(URI fileUri_p) {
    byte[] result = null;
    // Get stream from file.
    InputStream inputStream = getURLAsStream(getFileFullUrl(fileUri_p));
    // Ensure the input stream got from the file path is not null.
    if (null != inputStream) {
      result = readFile(inputStream);
    }
    return (null == result) ? new byte[0] : result;
  }

  /**
   * Rename file from source file relative path to destination relative path.
   * @param sourceFileRelativePath_p File path relative to the plug-in, plug-in id included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @param destinationFileRelativePath_p File path relative to the plug-in, plug-in id included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @return
   */
  public static boolean renameFile(String sourceFileRelativePath_p, String destinationFileRelativePath_p) {
    // Preconditions.
    if ((null == sourceFileRelativePath_p) || (null == destinationFileRelativePath_p)) {
      return false;
    }
    IFile sourceFile = getPlatformFile(sourceFileRelativePath_p);
    IPath destinationPath = getPlatformFile(destinationFileRelativePath_p).getFullPath();
    return moveResource(sourceFile, destinationPath);
  }

  /**
   * Rename folder from source folder relative path to destination relative path.
   * @param sourceFolderRelativePath_p Folder path relative to the plug-in, plug-in id included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @param destinationFolderRelativePath_p Folder path relative to the plug-in, plug-in id included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @return
   */
  public static boolean renameFolder(String sourceFolderRelativePath_p, String destinationFolderRelativePath_p) {
    // Preconditions.
    if ((null == sourceFolderRelativePath_p) || (null == destinationFolderRelativePath_p)) {
      return false;
    }
    IFolder sourceFolder = getPlatformFolder(sourceFolderRelativePath_p);
    IPath destinationPath = getPlatformFolder(destinationFolderRelativePath_p).getFullPath();
    return moveResource(sourceFolder, destinationPath);
  }

  /**
   * Write given contents of bytes at specified path.
   * @param filePath_p File path relative to the plug-in, plug-in id included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @param ensureFolders_p Make sure all parent folders exist by creating all necessary ones.
   * @param contents_p Contents that should be written to pointed file.
   * @return
   */
  public static boolean writeFile(String filePath_p, boolean ensureFolders_p, byte[] contents_p) {
    // Make sure file is writable.
    boolean fileWritable = makeFileWritable(filePath_p);
    if (fileWritable) {
      return writeFile(FileHelper.getFileFullUrl(URI.createPlatformResourceURI(filePath_p, true)), ensureFolders_p, contents_p);
    }
    return false;
  }

  /**
   * Write given string contents at specified path.
   * @param filePath_p File path relative to the plug-in, plug-in id included.<br>
   *          See {@link #getFileFullUrl(String)} documentation.
   * @param ensureFolders_p Make sure all parent folders exist by creating all necessary ones.
   * @param contents_p Contents that should be written to pointed file.
   * @return
   */
  public static boolean writeFile(String filePath_p, boolean ensureFolders_p, String contents_p) {
    return writeFile(filePath_p, ensureFolders_p, contents_p.getBytes());
  }

  /**
   * Write given contents of bytes at specified path.
   * @param filePath_p File path.
   * @param ensureFolders_p Make sure all parent folders exist by creating all necessary ones.
   * @param contents_p Contents that should be written to pointed file.
   * @return
   */
  public static boolean writeFile(URL filePath_p, boolean ensureFolders_p, byte[] contents_p) {
    boolean result = false;
    FileChannel channel = null;
    try {
      // Get file full path from its relative one.
      String fileFullPath = filePath_p.getFile();
      // Should path be enforced ?
      if (ensureFolders_p) {
        ensurePathAvailability(fileFullPath);
      }
      // Try and open the resulting file.
      channel = new FileOutputStream(fileFullPath).getChannel();
      // Write contents.
      channel.write(ByteBuffer.wrap(contents_p));
      result = true;
    } catch (Exception exception_p) {
      result = false;
      StringBuilder loggerMessage = new StringBuilder("FileHelper.writeFile(..) _ "); //$NON-NLS-1$
      loggerMessage.append("Failed to open channel in write mode for "); //$NON-NLS-1$
      loggerMessage.append(filePath_p).append(" !"); //$NON-NLS-1$
      CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
    } finally {
      if ((null != channel) && channel.isOpen()) {
        try {
          // Close the channel.
          channel.close();
        } catch (IOException exception_p) {
          result = false;
          StringBuilder loggerMessage = new StringBuilder("FileHelper.writeFile(..) _ "); //$NON-NLS-1$
          loggerMessage.append("Failed to close opened channel in write mode ! "); //$NON-NLS-1$
          loggerMessage.append(filePath_p).append(" may no longer be usable."); //$NON-NLS-1$
          CommonActivator.getInstance().logMessage(loggerMessage.toString(), IStatus.WARNING, exception_p);
        }
      }
    }
    return result;
  }
}