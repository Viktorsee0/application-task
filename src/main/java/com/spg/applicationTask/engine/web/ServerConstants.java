package com.spg.applicationTask.engine.web;

import static com.spg.applicationTask.engine.Constants.CREATE_CONSTANT_CLASS_ERROR;

/**
 * Constants of the web client-server.
 */
public final class ServerConstants {

    private ServerConstants() {
        throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
    }

    /**
     * Header constants for the web client-server.
     */
    public final static class Header {

        private Header() {
            throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
        }

        public static final String APPLICATION_JSON = "application/json";
    }
}
