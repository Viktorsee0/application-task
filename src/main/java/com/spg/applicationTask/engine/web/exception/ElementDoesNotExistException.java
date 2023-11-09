package com.spg.applicationTask.engine.web.exception;

public class ElementDoesNotExistException extends ServerException {

    public ElementDoesNotExistException(int code, String message) {
        super(code, message);
    }
}
