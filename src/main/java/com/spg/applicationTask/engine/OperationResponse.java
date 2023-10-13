package com.spg.applicationTask.engine;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.spg.applicationTask.extension.ExtJsonable;
import com.spg.applicationTask.extension.Validator;

import java.io.IOException;
import java.io.Writer;

import static com.spg.applicationTask.Constants.CREATE_CONSTANT_CLASS_ERROR;

/**
 * The operation response model that contains a result, an error message and a flag of success.
 */
public final class OperationResponse<T> implements ExtJsonable {
    private final boolean success;
    private final String error;
    private final T result;
    /**
     * Fields constants for the operation response.
     */
    public final static class Fields {

        private Fields() {
            throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
        }

        // The success field
        public static final String SUCCESS = "success";
        // The error field
        public static final String ERROR = "error";
        // The success field
        public static final String RESULT = "result";
    }

    private OperationResponse(final Builder<T> builder) {
        this.success = builder.success;
        this.error = builder.error;
        this.result = builder.result;
    }

    /**
     * Returns a success flag.
     *
     * @return an operation response success.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns an error message.
     *
     * @return an operation response error.
     */
    public String getError() {
        return error;
    }

    /**
     * Returns a result of the request.
     *
     * @return an operation response result.
     */
    public T getResult() {
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toJson(final Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put(Fields.SUCCESS, success);
        json.put(Fields.ERROR, error);
        json.put(Fields.RESULT, result instanceof Jsonable ? ((Jsonable) result).toJson() : result);
        json.toJson(writer);
    }

    /**
     * Wraps and builds the instance of the operation response model.
     */
    public final static class Builder<T> {
        private boolean success;
        private String error;
        private T result;

        public Builder<T> result(final T result) {
            this.success = true;
            this.result = Validator.of(result).get();
            return this;
        }

        Builder<T> error(final String error) {
            this.success = false;
            this.error = Validator.of(error).get();
            return this;
        }

        public OperationResponse<T> build() {
            return new OperationResponse<>(this);
        }
    }
}
