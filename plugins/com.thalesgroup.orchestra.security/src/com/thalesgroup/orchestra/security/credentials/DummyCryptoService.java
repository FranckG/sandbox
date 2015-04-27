/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

class DummyCryptoService implements CryptoService {

    public DummyCryptoService(String masterPassword) {
    }

    @Override
    public byte[] decrypt(byte[] data) throws CryptoServiceException {
        return data;
    }

    @Override
    public byte[] encrypt(byte[] data) throws CryptoServiceException {
        return data;
    }

}
