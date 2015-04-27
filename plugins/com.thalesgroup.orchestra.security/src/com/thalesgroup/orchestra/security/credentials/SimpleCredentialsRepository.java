package com.thalesgroup.orchestra.security.credentials;

import java.io.Serializable;
import java.util.HashMap;

class SimpleCredentialsRepository implements CredentialsRepository,
        Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6409425756112657559L;
    private HashMap<String, Credentials> db = new HashMap<String, Credentials>();

    @Override
    public Credentials getCredentials(String key) throws Exception {
        if (!db.containsKey(key)) {
            throw new CredentialsNotFoundException();
        }
        return db.get(key);
    }

    @Override
    public void addCredentials(String key, Credentials credentials)
            throws Exception {
        if (db.containsKey(key)) {
            throw new CredentialsExistException();
        }
        db.put(key, credentials);
    }

    @Override
    public void purge() throws Exception {
        db.clear();
    }

    @Override
    public void deleteCredentials(String key) throws Exception {
        if (!db.containsKey(key)) {
            throw new CredentialsNotFoundException();
        }
        db.remove(key);
    }

}
