/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

//import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ObjectSerializerTest {

  @Test
  public void canSerializeCredentialsRepository() throws Exception {
    SimpleCredentialsRepository repository = new SimpleCredentialsRepository();
    Credentials credentials = new Credentials("username", "password");
    repository.addCredentials("key", credentials);

    ObjectSerializer serializer = new ObjectSerializer();
    byte[] data = serializer.serialize(repository);
    Credentials repoCredentials = serializer.deserialize(data).getCredentials("key");

    assertEquals(credentials.getUsername(), repoCredentials.getUsername());
    assertEquals(credentials.getPassword(), repoCredentials.getPassword());
  }

  @Test
  public void deserializeCanThrowException() throws Exception {
    ObjectSerializer serializer = new ObjectSerializer();

    try {
      serializer.deserialize("aze".getBytes());
      fail("CredentialsRepositorySerializerException should be thrown");
    } catch (CredentialsRepositorySerializerException e) {
      // assertThat(e.getMessage(), containsString("Database has been corrupted"));
    }
  }

  @Test
  public void serializeCanThrowException() throws Exception {
    ObjectSerializer serializer = new ObjectSerializer();

    try {
      serializer.serialize(new NotSerializableRepository());
      fail("CredentialsRepositorySerializerException should be thrown");
    } catch (CredentialsRepositorySerializerException e) {
      // assertThat(e.getMessage(), containsString("Database has been corrupted"));
    }
  }

  private class NotSerializableRepository extends SimpleCredentialsRepository {
    /**
         * 
         */
    private static final long serialVersionUID = 1L;

    private NotSerializableRepository() {
    }
  }
}
