package com.spg.applicationTask.engine.web.exception;

public final class ResourceNotFoundException extends ServerException {

    public ResourceNotFoundException(final int code, final String message) {
        super(code, message);
    }
}
