package com.thalesgroup.orchestra.security.credentials;

import org.junit.Test;

public class SimpleCredentialsRepositoryTest {

    private SimpleCredentialsRepository createSimpleCredentialsRepository()
            throws CannotOpenDatabaseException {
        return new SimpleCredentialsRepository();
    }

    @Test
    public void canAddAngGetCredentials() throws Exception {
        final String key = "myKey";
        final String username = "myUsername";
        final String password = "myPassword";
        SimpleCredentialsRepository repository = createSimpleCredentialsRepository();
        repository.addCredentials(key, new Credentials(username, password));
        Credentials credentials = repository.getCredentials(key);

        CredentialsAssert.assertEquals(username, password, credentials);
    }

    @Test(expected = CredentialsNotFoundException.class)
    public void getKeyWithoutCredentialsShouldThrowException() throws Exception {
        SimpleCredentialsRepository repository = createSimpleCredentialsRepository();
        repository.getCredentials("someKey");
    }

    @Test(expected = CredentialsNotFoundException.class)
    public void deleteInexistantKeyShouldThrowException() throws Exception {
        SimpleCredentialsRepository repository = createSimpleCredentialsRepository();
        repository.deleteCredentials("someKey");
    }

    @Test(expected = CredentialsExistException.class)
    public void addExistingKeyShouldThrowException() throws Exception {
        SimpleCredentialsRepository repository = createSimpleCredentialsRepository();
        repository.addCredentials("key", new Credentials("u1", "p1"));
        repository.addCredentials("key", new Credentials("u2", "p2"));
    }

    @Test
    public void getWithSeveralCredentialsShouldReturnCorrectCredentials()
            throws Exception {
        SimpleCredentialsRepository repository = createSimpleCredentialsRepository();
        repository.addCredentials("key1", new Credentials("u1", "p1"));
        repository.addCredentials("key2", new Credentials("u2", "p2"));
        repository.addCredentials("key3", new Credentials("u3", "p3"));

        Credentials credentials = repository.getCredentials("key1");
        CredentialsAssert.assertEquals("u1", "p1", credentials);

        credentials = repository.getCredentials("key2");
        CredentialsAssert.assertEquals("u2", "p2", credentials);

        credentials = repository.getCredentials("key3");
        CredentialsAssert.assertEquals("u3", "p3", credentials);
    }

    @Test
    public void purgeWithNoCredentialsShouldDoNothing() throws Exception {
        SimpleCredentialsRepository repository = createSimpleCredentialsRepository();
        repository.purge();
    }

    @Test
    public void purgeWithCredentialsShouldDeletesAllCredentials()
            throws Exception {
        SimpleCredentialsRepository repository = createSimpleCredentialsRepository();
        repository.addCredentials("key1", new Credentials("11", "p11"));
        repository.addCredentials("key2", new Credentials("22", "p22"));
        repository.addCredentials("key3", new Credentials("33", "p33"));

        repository.purge();

        repository.addCredentials("key1", new Credentials("u1", "p1"));
        repository.addCredentials("key2", new Credentials("u2", "p2"));
        repository.addCredentials("key3", new Credentials("u3", "p3"));

        Credentials credentials = repository.getCredentials("key1");
        CredentialsAssert.assertEquals("u1", "p1", credentials);

        credentials = repository.getCredentials("key2");
        CredentialsAssert.assertEquals("u2", "p2", credentials);

        credentials = repository.getCredentials("key3");
        CredentialsAssert.assertEquals("u3", "p3", credentials);
    }
}
