/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

interface CredentialsRepositorySerializer {

    CredentialsRepository deserialize(byte[] data)
            throws CredentialsRepositorySerializerException;

    byte[] serialize(CredentialsRepository credentialsRepository)
            throws CredentialsRepositorySerializerException;
}

class CredentialsRepositorySerializerException extends Exception {
    public CredentialsRepositorySerializerException(String message) {
        super(message);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1475630649125407901L;

}
