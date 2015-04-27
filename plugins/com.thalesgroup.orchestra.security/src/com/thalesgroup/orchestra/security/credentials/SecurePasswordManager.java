/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

public class SecurePasswordManager implements PasswordManager {

  private CredentialsRepository credentialsRepository;
  private StorageService storageService;
  private CryptoService cryptoService;
  private CredentialsRepositorySerializer serializer;

  void setupRepositoryForTests(final CredentialsRepository credentialsRepository) {
    this.credentialsRepository = credentialsRepository;
  }

  public SecurePasswordManager(final String databasePath, final String masterPassword) throws CannotOpenDatabaseException {
    this(initStorageService(databasePath), initCryptoService(masterPassword), initSerializer());
    this.open();
  }

  private static FileStorageService initStorageService(final String databasePath) {
    return new FileStorageService(databasePath);
  }

  private static CryptoService initCryptoService(final String masterPassword) throws CannotOpenDatabaseException {
    return new AESGCMCryptoService(masterPassword);
  }

  private static ObjectSerializer initSerializer() {
    return new ObjectSerializer();
  }

  SecurePasswordManager(final StorageService storageService, final CryptoService cryptoService, final CredentialsRepositorySerializer serializer)
      throws CannotOpenDatabaseException {
    try {
      this.storageService = storageService;
      this.cryptoService = cryptoService;
      this.serializer = serializer;
    } catch (Exception e) {
      throw new CannotOpenDatabaseException(e.getMessage());
    }
  }

  @Override
  public Credentials getCredentials(final String key) throws CredentialsNotFoundException, CannotOpenDatabaseException {
    try {
      return this.credentialsRepository.getCredentials(key);
    } catch (CredentialsNotFoundException e) {
      throw e;
    } catch (Exception e) {
      throw new CannotOpenDatabaseException(e.getMessage());
    }
  }

  @Override
  public void addCredentials(final String key, final Credentials credentials) throws CredentialsExistException, CannotOpenDatabaseException {
    try {
      this.credentialsRepository.addCredentials(key, credentials);
      this.save();
    } catch (CredentialsExistException e) {
      throw e;
    } catch (Exception e) {
      throw new CannotOpenDatabaseException(e.getMessage());
    }
  }

  @Override
  public void deleteCredentials(final String key) throws CredentialsNotFoundException, CannotOpenDatabaseException {
    try {
      this.credentialsRepository.deleteCredentials(key);
      this.save();
    } catch (CredentialsNotFoundException e) {
      throw e;
    } catch (Exception e) {
      throw new CannotOpenDatabaseException(e.getMessage());
    }
  }

  @Override
  public void purgeDatabase() throws CannotOpenDatabaseException {
    try {
      this.credentialsRepository.purge();
      this.save();
    } catch (Exception e) {
      throw new CannotOpenDatabaseException(e.getMessage());
    }
  }

  void open() throws CannotOpenDatabaseException {
    try {
      if (!this.storageService.exists()) {
        this.credentialsRepository = new SimpleCredentialsRepository();
        return;
      }
      byte[] encryptedData = this.storageService.read();
      byte[] decryptedData = this.cryptoService.decrypt(encryptedData);
      this.credentialsRepository = this.serializer.deserialize(decryptedData);
    } catch (Exception e) {
      throw new CannotOpenDatabaseException(e.getMessage());
    }
  }

  void save() throws CannotOpenDatabaseException, Exception {
    try {
      byte[] serializedData = this.serializer.serialize(this.credentialsRepository);
      byte[] encryptedData = this.cryptoService.encrypt(serializedData);
      this.storageService.write(encryptedData);
    } catch (Exception e) {
      throw new CannotOpenDatabaseException(e.getMessage());
    }
  }
}
