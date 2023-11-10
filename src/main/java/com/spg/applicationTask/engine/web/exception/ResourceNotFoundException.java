package com.spg.applicationTask.engine.web.exception;

/**
 * Extends the basic exception to be used as a resource not found exception.
 */
public final class ResourceNotFoundException extends ServerException {
    /**
     * Constructs an exception with a code and a message.
     *
     * @param code    a code.
     * @param message a message.
     */
    public ResourceNotFoundException(final int code, final String message) {
        super(code, message);
    }
}
