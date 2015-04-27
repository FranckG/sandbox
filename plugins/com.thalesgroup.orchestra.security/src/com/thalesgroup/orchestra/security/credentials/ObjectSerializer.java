/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import java.io.*;

class ObjectSerializer implements CredentialsRepositorySerializer {

    private final static String exceptionMessage = "Database has been corrupted";

    @Override
    public CredentialsRepository deserialize(byte[] data)
            throws CredentialsRepositorySerializerException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new ByteArrayInputStream(data));

            return (CredentialsRepository) objectInputStream.readObject();
        } catch (Exception e) {
            throw new CredentialsRepositorySerializerException(exceptionMessage);
        }
    }

    @Override
    public byte[] serialize(CredentialsRepository credentialsRepository)
            throws CredentialsRepositorySerializerException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    outputStream);

            objectOutputStream.writeObject(credentialsRepository);

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new CredentialsRepositorySerializerException(exceptionMessage);
        }
    }
}
