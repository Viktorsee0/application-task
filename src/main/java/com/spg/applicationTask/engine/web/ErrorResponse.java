package com.spg.applicationTask.engine.web;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.spg.applicationTask.engine.extension.Validator;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static com.spg.applicationTask.engine.web.WebConstants.Messages.STRING_TO_JSON_ERROR;

/**
 * The error response model that contains an error message and a code of error.
 */
public final class ErrorResponse implements Jsonable {

    private int rCode;
    private String message;

    private ErrorResponse(final Builder builder) {
        this.rCode = builder.rCode;
        this.message = builder.message;
    }

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

    @Override
    public void toJson(final Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("code", rCode);
        json.put("message", message);
        json.toJson(writer);
    }

    public final static class Builder {

        private int rCode;
        private String message;

        /**
         * Constructs am error response model with the message parameter.
         *
         * @param message an error response message.
         * @return a builder of the error response model.
         */
        public Builder message(final String message) {
            this.message = Validator.of(message).get();
            return this;
        }

        /**
         * Constructs am error response model with the rCode parameter.
         *
         * @param rCode an error response rCode.
         * @return a builder of the error response model.
         */
        Builder error(final int rCode) {
            this.rCode = Validator.of(rCode).get();
            return this;
        }

        /**
         * Builds an error response model with required parameters.
         *
         * @return a builder of the error response model.
         */
        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
