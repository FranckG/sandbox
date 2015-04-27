/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

interface CryptoService {

    byte[] decrypt(byte[] data) throws CryptoServiceException;

    byte[] encrypt(byte[] data) throws CryptoServiceException;
}

class CryptoServiceException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 7239886967267850594L;

    public CryptoServiceException(String message) {
        super(message);
    }
}