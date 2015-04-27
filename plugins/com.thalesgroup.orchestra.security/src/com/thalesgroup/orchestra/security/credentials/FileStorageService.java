/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import java.io.IOException;
import java.nio.file.*;

class FileStorageService implements StorageService {

    private final String path;

    public FileStorageService(String path) {
        this.path = path;
    }

    @Override
    public byte[] read() throws StorageServiceException {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new StorageServiceException("Cannot read from: " + path, e);
        }
    }

    @Override
    public void write(byte[] data) throws StorageServiceException {
        try {
            Files.write(Paths.get(path), data);
        } catch (IOException e) {
            throw new StorageServiceException("Cannot write to: " + path, e);
        }
    }

    @Override
    public boolean exists() {
        return Files.exists(Paths.get(this.path));
    }
}
