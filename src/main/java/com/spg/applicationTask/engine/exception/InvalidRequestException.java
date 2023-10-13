package com.spg.applicationTask.engine.exception;

public final class InvalidRequestException extends ConfigException {

    public InvalidRequestException(final int code, final String message) {
        super(code, message);
    }
}
