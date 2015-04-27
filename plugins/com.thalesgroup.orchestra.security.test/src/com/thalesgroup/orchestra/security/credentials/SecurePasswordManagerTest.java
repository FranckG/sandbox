/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;

//import org.junit.Test;

public class SecurePasswordManagerTest {

  /*  
  private static SecurePasswordManager createPasswordManager(
            CredentialsRepository credentialsRepository)
            throws CannotOpenDatabaseException {
        StorageService storageService = mock(StorageService.class);
        CryptoService cryptoService = mock(CryptoService.class);
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);
        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);
        passwordManager.setupRepositoryForTests(credentialsRepository);
        return passwordManager;
    }

    @Test
    public void shouldUseCredentialRepositoryToAddCredentials()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);
        Credentials credentials = new Credentials(null, null);
        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.addCredentials("key", credentials);
        verify(credentialsRepository).addCredentials("key", credentials);
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void shouldThrowCannotOpenDatabaseExceptionWhenAddThrowsException()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);
        Credentials credentials = new Credentials(null, null);
        doThrow(new Exception()).when(credentialsRepository).addCredentials(
                "key", credentials);

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.addCredentials("key", credentials);
    }

    @Test(expected = CredentialsExistException.class)
    public void addShouldBubbleUpThrowCredentialsExistException()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);
        Credentials credentials = new Credentials(null, null);
        doThrow(new CredentialsExistException()).when(credentialsRepository)
                .addCredentials("key", credentials);

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.addCredentials("key", credentials);
    }

    @Test
    public void shouldUseCredentialRepositoryToGetCredentials()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);
        Credentials dbCredentials = new Credentials(null, null);
        when(credentialsRepository.getCredentials("key")).thenReturn(
                dbCredentials);

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        Credentials credentials = passwordManager.getCredentials("key");

        assertEquals(dbCredentials, credentials);
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void shouldThrowCannotOpenDatabaseExceptionWhenGetThrowsException()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);
        doThrow(new Exception()).when(credentialsRepository).getCredentials(
                "key");

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.getCredentials("key");
    }

    @Test(expected = CredentialsNotFoundException.class)
    public void getShouldBubbleUpCredentialsNotFoundException()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);
        doThrow(new CredentialsNotFoundException()).when(credentialsRepository)
                .getCredentials("key");

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.getCredentials("key");
    }

    @Test
    public void shouldUseCredentialRepositoryToDeleteCredentials()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.deleteCredentials("key");

        verify(credentialsRepository).deleteCredentials("key");
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void shouldThrowCannotOpenDatabaseExceptionWhenDeleteThrowsException()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);
        doThrow(new Exception()).when(credentialsRepository).deleteCredentials(
                "key");

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.deleteCredentials("key");
    }

    @Test(expected = CredentialsNotFoundException.class)
    public void deleteShouldBubbleUpCredentialsNotFoundExceptionWhen()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);
        doThrow(new CredentialsNotFoundException()).when(credentialsRepository)
                .deleteCredentials("key");

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.deleteCredentials("key");
    }

    @Test
    public void shouldUseCredentialRepositoryToPurgeCredentials()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.purgeDatabase();

        verify(credentialsRepository).purge();
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void shouldThrowCannotOpenDatabaseExceptionWhenPurgeThrowsException()
            throws Exception {
        CredentialsRepository credentialsRepository = mock(CredentialsRepository.class);
        doThrow(new Exception()).when(credentialsRepository).purge();

        SecurePasswordManager passwordManager = createPasswordManager(credentialsRepository);
        passwordManager.purgeDatabase();
    }

    @Test
    public void canOpenPasswordManager() throws Exception {
        StorageService storageService = mock(StorageService.class);
        final byte[] encryptedData = new byte[0];
        when(storageService.exists()).thenReturn(true);
        when(storageService.read()).thenReturn(encryptedData);
        CryptoService cryptoService = mock(CryptoService.class);
        byte[] decryptedData = new byte[0];
        when(cryptoService.decrypt(encryptedData)).thenReturn(decryptedData);
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);

        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);
        passwordManager.open();

        verify(serializer).deserialize(decryptedData);
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void ThrowsCannotOpenDatabaseExceptionInOpenForAnyExceptionFromStorageService()
            throws Exception {
        StorageService storageService = mock(StorageService.class);
        when(storageService.exists()).thenReturn(true);
        doThrow(new IllegalArgumentException()).when(storageService).read();
        CryptoService cryptoService = mock(CryptoService.class);
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);

        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);
        passwordManager.open();
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void ThrowsCannotOpenDatabaseExceptionInOpenForAnyExceptionFromCryptoService()
            throws Exception {
        StorageService storageService = mock(StorageService.class);
        when(storageService.exists()).thenReturn(true);
        CryptoService cryptoService = mock(CryptoService.class);
        doThrow(new IllegalArgumentException()).when(cryptoService).decrypt(
                any(byte[].class));
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);

        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);
        passwordManager.open();
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void ThrowsCannotOpenDatabaseExceptionInOpenForAnyExceptionFromCredentialsRepositorySerializer()
            throws Exception {
        StorageService storageService = mock(StorageService.class);
        when(storageService.exists()).thenReturn(true);
        CryptoService cryptoService = mock(CryptoService.class);
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);
        doThrow(new IllegalArgumentException()).when(serializer).deserialize(
                any(byte[].class));

        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);
        passwordManager.open();
    }

    @Test
    public void saveShouldPersistCredentialsRepository() throws Exception {
        StorageService storageService = mock(StorageService.class);
        CryptoService cryptoService = mock(CryptoService.class);
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);
        CredentialsRepository repository = mock(CredentialsRepository.class);
        byte[] serializedData = new byte[0];
        when(serializer.serialize(repository)).thenReturn(serializedData);
        byte[] encryptedSerializedData = new byte[0];
        when(cryptoService.encrypt(serializedData)).thenReturn(
                encryptedSerializedData);

        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);

        passwordManager.open();
        passwordManager.setupRepositoryForTests(repository);
        passwordManager.save();

        verify(storageService).write(encryptedSerializedData);
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void throwsCannotOpenDatabaseExceptionInSaveForAnyExceptionFromStorageService()
            throws Exception {
        StorageService storageService = mock(StorageService.class);
        CryptoService cryptoService = mock(CryptoService.class);
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);
        CredentialsRepository repository = mock(CredentialsRepository.class);
        doThrow(new StorageServiceException("Message", null)).when(
                storageService).write(any(byte[].class));

        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);

        passwordManager.open();
        passwordManager.setupRepositoryForTests(repository);
        passwordManager.save();
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void throwsCannotOpenDatabaseExceptionInSaveForAnyExceptionFromSerializer()
            throws Exception {
        StorageService storageService = mock(StorageService.class);
        CryptoService cryptoService = mock(CryptoService.class);
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);
        CredentialsRepository repository = mock(CredentialsRepository.class);
        doThrow(new IllegalArgumentException()).when(serializer).serialize(
                any(CredentialsRepository.class));

        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);

        passwordManager.open();
        passwordManager.setupRepositoryForTests(repository);
        passwordManager.save();
    }

    @Test(expected = CannotOpenDatabaseException.class)
    public void throwsCannotOpenDatabaseExceptionInSaveForAnyExceptionFromCryptoService()
            throws Exception {
        StorageService storageService = mock(StorageService.class);
        CryptoService cryptoService = mock(CryptoService.class);
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);
        CredentialsRepository repository = mock(CredentialsRepository.class);
        doThrow(new IllegalArgumentException()).when(cryptoService).encrypt(
                any(byte[].class));

        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);

        passwordManager.open();
        passwordManager.setupRepositoryForTests(repository);
        passwordManager.save();
    }

    @Test
    public void createRepositoryIfItDoesNotExist() throws Exception {
        StorageService storageService = mock(StorageService.class);
        when(storageService.exists()).thenReturn(false);
        CryptoService cryptoService = mock(CryptoService.class);
        CredentialsRepositorySerializer serializer = mock(CredentialsRepositorySerializer.class);

        SecurePasswordManager passwordManager = new SecurePasswordManager(
                storageService, cryptoService, serializer);
        passwordManager.open();

        verify(storageService, never()).read();
        verify(cryptoService, never()).decrypt(any(byte[].class));
        verify(serializer, never()).deserialize(any(byte[].class));
    }
    */
}
