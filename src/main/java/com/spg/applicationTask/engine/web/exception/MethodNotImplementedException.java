package com.spg.applicationTask.engine.web.exception;

public class MethodNotImplementedException extends ServerException {
    public MethodNotImplementedException(final int code, final String message) {
        super(code, message);
    }
}
