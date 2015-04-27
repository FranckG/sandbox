/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
//import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.fail;

import org.junit.Test;

public class AESGCMCryptoServiceTest {

  @Test
  public void cryptoServiceShouldTakeMoreThan500msToStart() throws Exception {
    long minimumTimeInNs = 500 * 1000 * 1000;
    long startTime = System.nanoTime();
    AESGCMCryptoService cryptoService = new AESGCMCryptoService("masterPassword");
    cryptoService.encrypt("Some random string".getBytes("UTF-8"));
    long timeSpent = System.nanoTime() - startTime;

    System.out.println("Key derivation time: " + timeSpent / 1e6);
    assertTrue(timeSpent > minimumTimeInNs);
  }

  @Test
  public void cannotEncryptThrowsException() throws Exception {
    AESGCMCryptoService cryptoService = new AESGCMCryptoService("masterPassword");

    try {
      cryptoService.encrypt(null);
      fail("CryptoServiceException should be thrown");
    } catch (CryptoServiceException e) {
      // assertThat(e.getMessage(), containsString("Encryption failed"));
    }
  }

  @Test
  public void cannotDecryptWithWrongPassword() throws Exception {
    byte[] initialData = "My secret information".getBytes("UTF-8");

    AESGCMCryptoService cryptoService = new AESGCMCryptoService("masterPassword");
    byte[] encryptedData = cryptoService.encrypt(initialData);

    AESGCMCryptoService wrongCryptoService = new AESGCMCryptoService("garbage");
    try {
      wrongCryptoService.decrypt(encryptedData);
      fail("CryptoServiceException should be thrown");
    } catch (CryptoServiceException e) {
      // assertThat(e.getMessage(), containsString("Decryption failed"));
    }
  }

  @Test
  public void canEncryptDecrypt() throws Exception {
    byte[] initialData = "My secret information".getBytes("UTF-8");

    AESGCMCryptoService cryptoService = new AESGCMCryptoService("masterPassword");
    byte[] encryptedData = cryptoService.encrypt(initialData);

    AESGCMCryptoService anotherCryptoService = new AESGCMCryptoService("masterPassword");
    byte[] decryptedData = anotherCryptoService.decrypt(encryptedData);
    assertArrayEquals(initialData, decryptedData);
  }
}
