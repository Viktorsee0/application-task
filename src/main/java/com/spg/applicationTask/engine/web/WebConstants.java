package com.spg.applicationTask.engine.web;

/**
 * Constants of the web client-server.
 */
public final class WebConstants {

    private WebConstants() {
    }

    public final static class Header {
        public static final String APPLICATION_JSON = "application/json";
    }

    public final static class Messages {
        public static final String SERVER_STOPPED = "Server stopped.";
        public static final String SERVER_STARTED = "Server started.";
        public static final String METHOD_NOT_ALLOWED = "Method not allowed.";
        public static final String METHOD_NOT_IMPLEMENTED = "Method not implemented.";
        public static final String JSON_ERROR = "JSON can not be parsed.";
        public static final String STRING_TO_JSON_ERROR = "String can not be parsed to JSON.";

    }
}
