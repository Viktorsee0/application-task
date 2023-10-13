package com.spg.applicationTask.engine;

import static com.spg.applicationTask.Constants.CREATE_CONSTANT_CLASS_ERROR;

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

    /**
     * Method constants for the web client-server.
     */
    public final static class Method {

        private Method() {
            throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
        }

        public static final String POST = "POST";
        public static final String DELETE = "DELETE";
        public static final String GET = "GET";
        public static final String PUT = "PUT";
    }
}
