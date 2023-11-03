package com.spg.applicationTask.engine.web.exception;

/**
 * Extends a standard <code>IOException</code> to be used as a business internal exception.
 */
public class ServerException extends RuntimeException {
    private final int code;

    /**
     * Constructs an exception with a code and a message.
     *
     * @param code a code.
     * @param message a message.
     */
    ServerException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    /**
     * Returns a code of the exception.
     *
     * @return a value of the code.
     */
    public int getCode() {
        return code;
    }
}
