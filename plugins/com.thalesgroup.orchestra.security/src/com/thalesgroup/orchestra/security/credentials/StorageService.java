/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

interface StorageService {

    byte[] read() throws StorageServiceException;

    void write(byte[] data) throws StorageServiceException;

    boolean exists();

}

class StorageServiceException extends Exception {

    public StorageServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 7241165198385722987L;

}