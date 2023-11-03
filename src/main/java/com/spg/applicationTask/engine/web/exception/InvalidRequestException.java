package com.spg.applicationTask.engine.web.exception;

public final class InvalidRequestException extends ServerException {

    public InvalidRequestException(final int code, final String message) {
        super(code, message);
    }
}
