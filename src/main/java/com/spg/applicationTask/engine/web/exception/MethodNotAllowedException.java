package com.spg.applicationTask.engine.web.exception;

/**
 * Extends the basic exception to be used as a method not allowed exception.
 */
public final class MethodNotAllowedException extends ServerException {

    /**
     * Constructs an exception with a code and a message.
     *
     * @param code    a code.
     * @param message a message.
     */
    public MethodNotAllowedException(final int code, final String message) {
        super(code, message);
    }
}
