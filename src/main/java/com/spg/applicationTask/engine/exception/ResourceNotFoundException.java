package com.spg.applicationTask.engine.exception;

public final class ResourceNotFoundException extends ConfigException {

    ResourceNotFoundException(final int code, final String message) {
        super(code, message);
    }
}
