package com.spg.applicationTask.engine.web.exception;

/**
 * Extends the basic exception to be used as an internal server exception.
 */
public final class InternalServerErrorException extends ServerException {

    /**
     * Constructs an exception with a code and a message.
     *
     * @param code    a code.
     * @param message a message.
     */
    InternalServerErrorException(final int code, final String message) {
        super(code, message);
    }
}
