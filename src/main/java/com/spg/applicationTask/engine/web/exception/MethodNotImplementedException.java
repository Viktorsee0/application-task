package com.spg.applicationTask.engine.web.exception;

/**
 * Extends the basic exception to be used as a method not implemented exception.
 */
public class MethodNotImplementedException extends ServerException {

    /**
     * Constructs an exception with a code and a message.
     *
     * @param code    a code.
     * @param message a message.
     */
    public MethodNotImplementedException(final int code, final String message) {
        super(code, message);
    }
}
