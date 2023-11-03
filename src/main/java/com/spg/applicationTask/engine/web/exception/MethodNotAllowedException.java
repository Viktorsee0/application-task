package com.spg.applicationTask.engine.web.exception;

public final class MethodNotAllowedException extends ServerException {

    public MethodNotAllowedException(final int code, final String message) {
        super(code, message);
    }
}
