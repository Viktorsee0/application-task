package com.spg.applicationTask.engine.exception;

public final class MethodNotAllowedException extends ConfigException {

    public MethodNotAllowedException(final int code, final String message) {
        super(code, message);
    }
}
