package com.spg.applicationTask.engine.web;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.spg.applicationTask.api.utils.Validator;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static com.spg.applicationTask.engine.Constants.Messages.STRING_TO_JSON_ERROR;

/**
 * The operation response model that contains a result, an error message and a flag of success.
 */
public final class ErrorResponse implements Jsonable {

    private int rCode;
    private String message;

    private ErrorResponse(final Builder builder) {
        this.rCode = builder.rCode;
        this.message = builder.message;
    }

    /**
     * Returns an error message.
     *
     * @return an operation response error.
     */
    public String getError() {
        return message;
    }

    /**
     * Returns a result of the request.
     *
     * @return an operation response result.
     */
    public int getCode() {
        return rCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            toJson(writable);
        } catch (final IOException e) {
            throw new RuntimeException(STRING_TO_JSON_ERROR, e);
        }

        return writable.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toJson(final Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("code", rCode);
        json.put("message", message);
        json.toJson(writer);
    }

    /**
     * Wraps and builds the instance of the operation response model.
     */
    public final static class Builder {

        private int rCode;
        private String message;

        public Builder message(final String message) {
            this.message = Validator.of(message).get();
            return this;
        }

        Builder error(final int rCode) {
            this.rCode = Validator.of(rCode).get();
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
