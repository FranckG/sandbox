/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

public class CredentialsExistException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6971214929458454619L;

    public CredentialsExistException() {
        super("Credentials already exist");
    }
}
