package com.spg.applicationTask.engine.web.exception;

/**
 * Extends the basic exception to be used as an element doesn't exist exception.
 */
public class ElementDoesNotExistException extends ServerException {

    /**
     * Constructs an exception with a code and a message.
     *
     * @param code    a code.
     * @param message a message.
     */
    public ElementDoesNotExistException(int code, String message) {
        super(code, message);
    }
}
