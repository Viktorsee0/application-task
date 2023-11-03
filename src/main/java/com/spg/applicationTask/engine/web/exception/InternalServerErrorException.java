package com.spg.applicationTask.engine.web.exception;

public final class InternalServerErrorException extends ServerException {

    InternalServerErrorException(final int code, final String message) {
        super(code, message);
    }
}
