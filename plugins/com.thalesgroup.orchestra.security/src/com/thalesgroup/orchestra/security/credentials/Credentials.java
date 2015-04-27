/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import java.io.Serializable;

public class Credentials implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2253360203243492277L;
    private final String username;
    private final String password;

    public Credentials(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public final String getUsername() {
        return username;
    }

    public final String getPassword() {
        return password;
    }
}
