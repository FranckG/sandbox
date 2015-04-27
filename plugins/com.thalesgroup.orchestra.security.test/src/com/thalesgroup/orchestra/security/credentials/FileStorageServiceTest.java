/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class FileStorageServiceTest {

  @Test
  public void canReadFromPath() throws Exception {
    final String text = "This is a text";
    Path path = Files.createTempFile(null, null);
    path.toFile().deleteOnExit();
    byte[] buf = new String(text).getBytes();
    Files.write(path, buf);

    byte[] data = new FileStorageService(path.toString()).read();

    assertEquals(text, new String(data));
  }

  @Test
  public void canWriteToPath() throws Exception {
    final String text = "This is a text";
    Path path = Files.createTempFile(null, null);
    path.toFile().deleteOnExit();

    new FileStorageService(path.toString()).write(text.getBytes());

    byte[] fileContent = Files.readAllBytes(path);
    assertEquals(text, new String(fileContent));
  }

  @Test
  public void canCheckThatFileExists() throws IOException {
    Path path = Files.createTempFile(null, null);
    path.toFile().deleteOnExit();

    FileStorageService storageService = new FileStorageService(path.toString());

    assertTrue(storageService.exists());
  }

  @Test
  public void canCheckThatFileDoesNotExist() throws IOException {
    final String path = "/" + java.util.UUID.randomUUID().toString();
    FileStorageService storageService = new FileStorageService(path);

    assertFalse(storageService.exists());
  }

  @Test
  public void exceptionMessageForRead() {
    final String path = "/" + java.util.UUID.randomUUID().toString();
    FileStorageService storageService = new FileStorageService(path);

    try {
      storageService.read();
      fail("StorageServiceException should be raised");
    } catch (StorageServiceException e) {
      // assertThat(e.getMessage(), containsString("Cannot read from: " + path));
    }
  }

  @Test
  public void exceptionMessageForWrite() {
    final String path = "/" + java.util.UUID.randomUUID().toString();
    FileStorageService storageService = new FileStorageService(path);

    try {
      storageService.write("Something".getBytes());
      fail("StorageServiceException should be raised");
    } catch (StorageServiceException e) {
      // assertThat(e.getMessage(), containsString("Cannot write to: " + path));
    }
  }

  static String convertStreamToString(java.io.InputStream is) {
    java.util.Scanner s = null;
    try {
      s = new java.util.Scanner(is).useDelimiter("\\A");
      return s.hasNext() ? s.next() : "";
    } finally {
      if (s != null) {
        s.close();
      }
    }
  }
}
