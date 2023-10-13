package com.spg.applicationTask.engine.exception;

public final class InternalServerErrorException extends ConfigException {

    InternalServerErrorException(final int code, final String message) {
        super(code, message);
    }
}
