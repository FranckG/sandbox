/**
 * Copyright (c) THALES, 2011. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.server;

import java.io.File;
import java.text.MessageFormat;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.thalesgroup.orchestra.framework.FrameworkActivator;

/**
 * A job dedicated to folder deletion, that is owned by a specific family.
 * @author t0076261
 */
public class FolderDeletionJob extends Job {
  /**
   * Job family identifier.
   */
  public static final String JOB_FAMILY_FOLDER_DELETION = "JF_FolderDeletion"; //$NON-NLS-1$
  /**
   * Folder to delete.
   */
  private File[] _foldersToDelete;

  /**
   * Constructor.
   * @param name_p
   */
  public FolderDeletionJob(File[] foldersToDelete_p, String message_p) {
    super(message_p);
    Assert.isNotNull(foldersToDelete_p);
    _foldersToDelete = foldersToDelete_p;
  }

  /**
   * @see org.eclipse.core.runtime.jobs.Job#belongsTo(java.lang.Object)
   */
  @Override
  public boolean belongsTo(Object family_p) {
    return JOB_FAMILY_FOLDER_DELETION.equals(family_p);
  }

  /**
   * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus run(IProgressMonitor monitor_p) {
    File deletedFolder = null;
    try {
      for (File folder : _foldersToDelete) {
        deletedFolder = folder;
        FileUtils.deleteDirectory(folder);
      }
    } catch (Exception exception_p) {
      String errorMessage = MessageFormat.format(Messages.ServerManager_StatusMessage_CustomSharedDirDeletionError, deletedFolder.getAbsolutePath());
      return new Status(IStatus.ERROR, FrameworkActivator.getDefault().getPluginId(), errorMessage);
    }
    return Status.OK_STATUS;
  }
}