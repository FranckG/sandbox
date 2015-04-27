/**
 * <p>
 * Copyright (c) 2002-2008 : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Société : Thales Services - Engineering & Process Management
 * </p>
 * <p>
 * Thales Part Number 16 262 601
 * </p>
 */
package com.thalesgroup.orchestra.framework.util;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemLoopException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Title : FileUtils
 * </p>
 * <p>
 * Description : Class for File Utility functions using Java 7
 * </p>
 */
public class FileUtils {

  /**
   * Delete a directory
   * @param directory_p Directory
   * @throws IOException
   */
  public static void DeleteDirectory(Path directory_p) throws IOException {
    Files.walkFileTree(directory_p, new SimpleFileVisitor<Path>() {

      /**
       * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
       */
      @Override
      public FileVisitResult visitFile(Path file_p, BasicFileAttributes attrs_p) throws IOException {
        Files.setAttribute(file_p, "dos:readonly", false);
        Files.delete(file_p);
        return FileVisitResult.CONTINUE;
      }

      /**
       * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
       */
      @Override
      public FileVisitResult postVisitDirectory(Path dir_p, IOException exc_p) throws IOException {
        Files.delete(dir_p);
        return FileVisitResult.CONTINUE;
      }

    });
  }

  /**
   * Clean the content of a directory without removing it
   * @param directory_p
   * @throws IOException
   */
  public static void CleanDirectory(Path directory_p) throws IOException {
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory_p)) {
      for (Path path : stream) {
        if (Files.isDirectory(path)) {
          DeleteDirectory(path);
        } else {
          Files.delete(path);
        }
      }
    } catch (IOException exception_p) {
      // The stream is automatically closed on catch. Then rethrow the exception
      throw exception_p;
    }
  }

  /**
   * Copy a directory tree to a directory
   * @param source_p Source directory
   * @param destination_p Destination directory
   * @throws IOException
   */
  public static void CopyDirectory(Path source_p, Path destination_p) throws IOException {
    if (Files.notExists(destination_p)) {
      Files.createDirectory(destination_p);
    }
    Files.walkFileTree(source_p, new TreeCopier(source_p, destination_p, false));
  }

  //
  // Copyright (c) 2008, 2010, Oracle and/or its affiliates. All rights reserved.
  //
  // Redistribution and use in source and binary forms, with or without
  // modification, are permitted provided that the following conditions
  // are met:
  //
  // - Redistributions of source code must retain the above copyright
  // notice, this list of conditions and the following disclaimer.
  //
  // - Redistributions in binary form must reproduce the above copyright
  // notice, this list of conditions and the following disclaimer in the
  // documentation and/or other materials provided with the distribution.
  //
  // - Neither the name of Oracle nor the names of its
  // contributors may be used to endorse or promote products derived
  // from this software without specific prior written permission.
  //
  // THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
  // IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
  // THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
  // PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
  // CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
  // EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
  // PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
  // PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
  // LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
  // NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
  // SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
  //

  /**
   * Copy file to destination
   * @param source Source file
   * @param target Destination path
   * @param preserve do not replace if true
   */
  public static void CopyFile(Path source, Path target, boolean preserve) {
    CopyOption[] options =
        (preserve) ? new CopyOption[] { StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING }
                  : new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
    try {
      if (Files.exists(target)) {
        // Make file writable in order to be able to overwrite it
        if (!Files.isWritable(target)) {
          Files.setAttribute(target, "dos:readonly", false);
        }
      }
      Files.copy(source, target, options);
      Files.setAttribute(target, "dos:readonly", false);
    } catch (IOException x) {
      System.err.format("Unable to copy: %s: %s%n", source, x);
    }
  }

  /**
   * A {@code FileVisitor} that copies a file-tree ("cp -r")
   */
  static class TreeCopier implements FileVisitor<Path> {
    private final Path source;
    private final Path target;
    private final boolean preserve;

    TreeCopier(Path source_p, Path target_p, boolean preserve_p) {
      this.source = source_p;
      this.target = target_p;
      this.preserve = preserve_p;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
      // before visiting entries in a directory we copy the directory
      // (okay if directory already exists).
      CopyOption[] options = (preserve) ? new CopyOption[] { StandardCopyOption.COPY_ATTRIBUTES } : new CopyOption[0];

      Path newdir = target.resolve(source.relativize(dir));
      try {
        Files.copy(dir, newdir, options);
      } catch (FileAlreadyExistsException x) {
        // ignore
      } catch (IOException x) {
        System.err.format("Unable to create: %s: %s%n", newdir, x);
        return FileVisitResult.SKIP_SUBTREE;
      }
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
      CopyFile(file, target.resolve(source.relativize(file)), preserve);
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
      // fix up modification time of directory when done
      if (exc == null && preserve) {
        Path newdir = target.resolve(source.relativize(dir));
        try {
          FileTime time = Files.getLastModifiedTime(dir);
          Files.setLastModifiedTime(newdir, time);
        } catch (IOException x) {
          System.err.format("Unable to copy all attributes to: %s: %s%n", newdir, x);
        }
      }
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
      if (exc instanceof FileSystemLoopException) {
        System.err.println("cycle detected: " + file);
      } else {
        System.err.format("Unable to copy: %s: %s%n", file, exc);
      }
      return FileVisitResult.CONTINUE;
    }
  }

  /**
   * Set directory writable by the means of ACL
   * @param dir_p Directory
   * @param writable_p true if writable, false otherwise
   * @throws IOException
   */
  public static void setDirectoryWritable(Path dir_p, boolean writable_p) throws IOException {
    String username = System.getProperty("user.name");
    UserPrincipal user = dir_p.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(username);

    AclFileAttributeView view = Files.getFileAttributeView(dir_p, AclFileAttributeView.class);

    AclEntryType newType = writable_p ? AclEntryType.ALLOW : AclEntryType.DENY;

    List<AclEntry> acl = view.getAcl();

    List<AclEntry> newAcl = new ArrayList<AclEntry>();

    AclEntry allowEntry = null;
    AclEntry denyEntry = null;

    // Update existing entry accordingly and create a new list
    for (AclEntry e : acl) {
      if (e.principal().equals(user)) {
        AclEntryType currentType = e.type();
        Set<AclEntryPermission> perm = e.permissions();
        switch (currentType) {
          case ALLOW:
            allowEntry = e;
            switch (newType) {
              case ALLOW:
                perm.add(AclEntryPermission.WRITE_DATA);
              break;
              case DENY:
                perm.remove(AclEntryPermission.WRITE_DATA);
              break;
              default:
            }
          break;
          case DENY:
            denyEntry = e;
            switch (newType) {
              case ALLOW:
                perm.remove(AclEntryPermission.WRITE_DATA);
              break;
              case DENY:
                perm.add(AclEntryPermission.WRITE_DATA);
              break;
              default:
            }
          break;
          default:
        }
        if (!perm.isEmpty()) {
          AclEntry entry = AclEntry.newBuilder().setType(currentType).setPrincipal(user).setPermissions(perm).build();
          newAcl.add(entry);
        }
      } else {
        newAcl.add(e);
      }
    }

    // Create new entry when it does not already exist
    if ((newType == AclEntryType.ALLOW && null == allowEntry) || (newType == AclEntryType.DENY && null == denyEntry)) {
      AclEntry entry = AclEntry.newBuilder().setType(newType).setPrincipal(user).setPermissions(AclEntryPermission.WRITE_DATA).build();
      newAcl.add(0, entry);
    }

    // Replace previous ACL with new one
    view.setAcl(newAcl);
  }
}
