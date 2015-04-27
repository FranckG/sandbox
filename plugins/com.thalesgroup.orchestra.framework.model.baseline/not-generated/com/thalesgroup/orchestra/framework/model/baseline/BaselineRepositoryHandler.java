/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.model.baseline;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.thalesgroup.orchestra.framework.lib.base.UtilFunction;

/**
 * A handler responsible for CRUD operations on the existing baselines repository.
 * @author t0076261
 */
public class BaselineRepositoryHandler {
  /**
   * Catalog file extension.
   */
  private static final String FILE_EXTENSION_BASELINE_CATALOG = "baseline"; //$NON-NLS-1$
  /**
   * ID used by current framework to acquire/release repository lock.
   */
  private String _currentId;
  /**
   * Lock file.
   */
  private File _lockFile;
  /**
   * Dedicated resource set.
   */
  private ResourceSet _resourceSet;
  /**
   * Baseline catalog is a set of files at specified root location.
   */
  private File _rootFolder;

  /**
   * Constructor.
   * @param rootFolderAbsolutePath_p
   */
  public BaselineRepositoryHandler(String rootFolderAbsolutePath_p) {
    _rootFolder = new File(rootFolderAbsolutePath_p);
    // Make sure folders are created.
    if (!_rootFolder.exists()) {
      _rootFolder.mkdirs();
    }
    // Check this.
    Assert.isTrue(_rootFolder.isDirectory());
    // Prepare resource set.
    _resourceSet = new ResourceSetImpl();
  }

  /**
   * Get baseline catalog fragment filename
   * @return Filename if available, null otherwise
   */
  public String getBaselineCatalogFilename() {
    String[] absolutePath = new String[] { null };
    // Get fragment absolute path.
    getActiveFragmentAbsolutePath(absolutePath);
    return absolutePath[0];
  }

  /**
   * Check if active baseline catalog fragment is writable
   * @return true if baseline catalog is writable and false otherwise
   */
  public boolean isBaselineCatalogWritable() {
    String filename = getBaselineCatalogFilename();
    File file = new File(filename);
    // The catalog is writable if it does not exist or it is writable
    if (!file.exists() || file.canWrite()) {
      return true;
    }
    return false;
  }

  /**
   * Add a new baseline reference.
   * @param baselineName_p
   * @param baselineDescription_p
   * @param contextId_p
   * @param projectAbsolutePath_p
   * @return
   */
  public boolean addBaselineReference(String baselineName_p, String baselineDescription_p, String contextId_p, String projectAbsolutePath_p) {
    // Preconditions.
    if ((null == baselineName_p) || (null == contextId_p) || (null == projectAbsolutePath_p)) {
      return false;
    }
    // Get repository.
    Resource activeFragment = getActiveFragment();
    Repository repository = getRepository(activeFragment);
    // Could not access repository.
    if (null == repository) {
      return false;
    }
    // Add baseline.
    Baseline baseline = BaselineFactory.eINSTANCE.createBaseline();
    // ID.
    baseline.setContextId(contextId_p);
    // Name.
    baseline.setName(baselineName_p);
    // Description.
    if (null != baselineDescription_p) {
      baseline.setDescription(baselineDescription_p);
    }
    // Compute relative path.
    IPath rootPath = new Path(_rootFolder.getAbsolutePath());
    IPath relativePath = new Path(projectAbsolutePath_p).makeRelativeTo(rootPath);
    baseline.setProjectRelativePath(relativePath.toString());
    // And set it.
    repository.getBaselines().add(baseline);
    // Save resource.
    try {
      // Check if catalog already exists
      java.nio.file.Path path = Paths.get(activeFragment.getURI().toFileString());
      boolean existsCatalog = Files.exists(path);

      // Save catalog
      activeFragment.save(null);

      // On inital version of the catalog, give read/write access to everyone
      if (!existsCatalog) {
        setWorldReadWriteAccess(path);
      }
    } catch (IOException exception_p) {
      System.err.println("Could not save fragment");
      // Could not save fragment !
      return false;
    }
    return true;
  }

  /**
   * Clean existing model data from handler.<br>
   * Handler is still usable by clients.
   */
  public void clean() {
    // Precondition.
    if (_resourceSet.getResources().isEmpty()) {
      return;
    }
    List<Resource> existingResources = new ArrayList<Resource>(_resourceSet.getResources());
    for (Resource resource : existingResources) {
      // Remove resource.
      _resourceSet.getResources().remove(resource);
      // Unload resource.
      resource.unload();
    }
  }

  /**
   * Get active fragment, where to write to.
   * @return
   */
  protected Resource getActiveFragment() {
    // Resulting resource.
    Resource result = null;
    String[] absolutePath = new String[] { null };
    // Get fragment absolute path.
    boolean createResource = getActiveFragmentAbsolutePath(absolutePath);
    if (createResource) {
      result = _resourceSet.createResource(URI.createFileURI(absolutePath[0]));
    } else {
      result = _resourceSet.getResource(URI.createFileURI(absolutePath[0]), true);
    }
    return result;
  }

  /**
   * Get active fragment absolute path.
   * @param resultingAbsolutePath_p A not <code>null</code>, nor empty, array of {@link String} that will host the resulting absolute path.
   * @return <code>false</code> if no new fragment is required (ie latest existing one is still valid), <code>true</code> if returned path stands for a fragment
   *         that should be created (and does not exist yet).
   */
  protected boolean getActiveFragmentAbsolutePath(String[] resultingAbsolutePath_p) {
    // Get existing fragments.
    List<File> fragments = getExistingFragments();
    // Count them.
    int count = fragments.size();
    // Get latest one.
    File latestFragment = null;
    if (!fragments.isEmpty()) {
      latestFragment = fragments.get(count - 1);
    }
    // Get its path, if its still valid to use this one.
    if (null != latestFragment) {
      long fileLength = latestFragment.length();
      // TODO Guillaume
      // Define a constant/preference here.
      if (fileLength < 100000) {
        resultingAbsolutePath_p[0] = latestFragment.getAbsolutePath();
        return false;
      }
    }
    // Prepare a new one.
    if (null == resultingAbsolutePath_p[0]) {
      resultingAbsolutePath_p[0] = getNewFragmentAbsolutePath(count);
    }
    return true;
  }

  /**
   * Get all baseline references.
   * @return
   */
  public List<Baseline> getAllBaselineReferences() {
    List<Baseline> result = new ArrayList<Baseline>(0);
    // Get existing fragments.
    List<File> fragments = getExistingFragments();
    // Cycle through fragments.
    for (File file : fragments) {
      // Get existing resource.
      Resource resource = _resourceSet.getResource(URI.createFileURI(file.getAbsolutePath()), true);
      // Get repository.
      Repository repository = getRepository(resource);
      // And baselines.
      result.addAll(repository.getBaselines());
    }
    return result;
  }

  /**
   * Get currently available baseline root path.
   * @return
   */
  public String getBaselineRootPath() {
    // Get active fragment absolute path.
    String[] fragmentAbsolutePath = new String[] { null };
    getActiveFragmentAbsolutePath(fragmentAbsolutePath);
    // Retain only sub-folder name, and append it to root one.
    return new Path(_rootFolder.getAbsolutePath()).append(new Path(fragmentAbsolutePath[0]).removeFileExtension().lastSegment()).toOSString();
  }

  /**
   * Get current ID.
   * @return
   */
  protected String getCurrentId() {
    // Lazy instantiation.
    if (null == _currentId) {
      // Make sure there is a unique memory address in this ID, so that only the Framework of this session can be identified.
      _currentId = UtilFunction.GetUserName() + "/@/" + UtilFunction.GetHostName() + "/@/" + toString(); //$NON-NLS-1$ //$NON-NLS-2$
    }
    return _currentId;
  }

  /**
   * Get existing catalog fragment files.
   * @return
   */
  protected List<File> getExistingFragments() {
    // Analyze root folder.
    File[] files = _rootFolder.listFiles(new FileFilter() {
      /**
       * @see java.io.FileFilter#accept(java.io.File)
       */
      public boolean accept(File file_p) {
        // Precondition.
        if (file_p.isDirectory()) {
          return false;
        }
        IPath path = new Path(file_p.getAbsolutePath());
        return FILE_EXTENSION_BASELINE_CATALOG.equals(path.getFileExtension());
      }
    });
    // Result.
    List<File> result = Collections.emptyList();
    if ((null != files) && (0 < files.length)) {
      result = Arrays.asList(files);
      // Sort result.
      Collections.sort(result);
    }
    return result;
  }

  /**
   * Get lock file.
   * @return
   */
  protected File getLockFile() {
    // Lazily create lock file.
    if (null == _lockFile) {
      _lockFile = new Path(_rootFolder.getAbsolutePath()).append(".lock").toFile(); //$NON-NLS-1$
    }
    return _lockFile;
  }

  /**
   * Get a new baseline catalog fragment file absolute path.
   * @param existingFragmentsCount_p
   * @return
   */
  protected String getNewFragmentAbsolutePath(int existingFragmentsCount_p) {
    IPath path = new Path(_rootFolder.getAbsolutePath()).append("Catalog" + existingFragmentsCount_p).addFileExtension(FILE_EXTENSION_BASELINE_CATALOG); //$NON-NLS-1$
    if (path.toFile().exists()) {
      return getNewFragmentAbsolutePath(existingFragmentsCount_p + 1);
    }
    return path.toOSString();
  }

  /**
   * Get repository from specified resource.
   * @param resource_p
   * @return
   */
  protected Repository getRepository(Resource resource_p) {
    // Precondition.
    if (null == resource_p) {
      return null;
    }
    // Get resource contents.
    List<EObject> contents = resource_p.getContents();
    // None, this is a new resource.
    if (contents.isEmpty()) {
      // Add a repository then.
      Repository result = BaselineFactory.eINSTANCE.createRepository();
      contents.add(result);
      return result;
    }
    // Get existing one.
    return (Repository) contents.get(0);
  }

  /**
   * Get root folder.
   * @return
   */
  public File getRootFolder() {
    return _rootFolder;
  }

  /**
   * Is repository currently locked ?
   * @return {@link LockState}.
   */
  public LockState isLocked() {
    // Lock file.
    File lockFile = getLockFile();
    return isLockedLocally(lockFile);
  }

  /**
   * Is specified lock file owned by current session ?
   * @param lockFile_p
   * @return A {@link LockState}.
   */
  protected LockState isLockedLocally(File lockFile_p) {
    // Precondition.
    if (!lockFile_p.exists()) {
      return LockState.AVAILABLE;
    }
    try {
      FileInputStream stream = new FileInputStream(lockFile_p);
      int available = stream.available();
      ByteBuffer buffer = ByteBuffer.allocate(available);
      stream.getChannel().read(buffer);
      stream.close();
      String result = new String(((ByteBuffer) buffer.flip()).array());
      if (!getCurrentId().equals(result)) {
        return LockState.LOCKED_BY_OTHER;
      }
      return LockState.OWNED;
    } catch (IOException exception_p) {
      return LockState.ERROR;
    }
  }

  /**
   * Lock baseline catalog for current process.
   * @return A {@link LockState}.
   */
  public LockState lock() {
    // Lock file.
    File lockFile = getLockFile();
    try {
      // Touch file.
      // Atomically.
      boolean result = lockFile.createNewFile();
      // Precondition.
      if (!result) {
        return isLockedLocally(lockFile);
      }
      // For debug purposes, write computer and session data.
      FileOutputStream stream = new FileOutputStream(lockFile);
      // Id string.
      String idString = getCurrentId();
      stream.write(idString.getBytes());
      stream.close();
      return LockState.OWNED;
    } catch (IOException exception_p) {
      return LockState.ERROR;
    }
  }

  /**
   * Unlock baseline catalog for current process.
   * @return A {@link LockState}.
   */
  public LockState unlock() {
    // Lock file.
    File lockFile = getLockFile();
    LockState state = isLockedLocally(lockFile);
    if (LockState.OWNED.equals(state)) {
      boolean deleted = lockFile.delete();
      if (!deleted) {
        return LockState.ERROR;
      }
      return LockState.AVAILABLE;
    }
    return state;
  }

  /**
   * Give read/write access to everyone
   * @param path_p Path
   * @throws IOException
   */
  static void setWorldReadWriteAccess(java.nio.file.Path path_p) throws IOException {
    UserPrincipal user = path_p.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("Everyone");
    AclFileAttributeView view = Files.getFileAttributeView(path_p, AclFileAttributeView.class);
    List<AclEntry> acl = view.getAcl();
    AclEntry entry =
        AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(user)
            .setPermissions(AclEntryPermission.READ_DATA, AclEntryPermission.WRITE_DATA, AclEntryPermission.APPEND_DATA).build();
    acl.add(0, entry);
    // Replace previous ACL with new one
    view.setAcl(acl);
  }

  /**
   * Test method.
   * @param args
   */
  public static void main(String[] args) throws Exception {
    BaselineRepositoryHandler baselineCatalogHandler = new BaselineRepositoryHandler("c:/tmp/models"); //$NON-NLS-1$
    baselineCatalogHandler.lock();
    try {
      Thread.sleep(10000);
    } finally {
      baselineCatalogHandler.unlock();
    }
  }

  /**
   * Possible lock states.
   * @author t0076261
   */
  public enum LockState {
    AVAILABLE, ERROR, LOCKED_BY_OTHER, OWNED;
  }
}