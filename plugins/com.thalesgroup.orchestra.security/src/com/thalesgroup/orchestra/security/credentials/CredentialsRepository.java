/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

interface CredentialsRepository {

    public Credentials getCredentials(String key) throws Exception;

    public void addCredentials(String key, Credentials credentials)
            throws Exception;

    public void purge() throws Exception;

    public void deleteCredentials(String key) throws Exception;

}