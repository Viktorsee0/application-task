package com.spg.applicationTask.engine.web.exception;

/**
 * Extends the basic exception to be used as an invalid request exception.
 */
public final class InvalidRequestException extends ServerException {

    /**
     * Constructs an exception with a code and a message.
     *
     * @param code    a code.
     * @param message a message.
     */
    public InvalidRequestException(final int code, final String message) {
        super(code, message);
    }
}
