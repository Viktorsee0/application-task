package com.spg.applicationTask.engine.extension;

import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;

import static com.spg.applicationTask.engine.web.WebConstants.Messages.STRING_TO_JSON_ERROR;

/**
 * Extends the basic interface of <code>Jsonable</code>.
 *
 * @see Jsonable for more information.
 */
public interface ExtJsonable extends Jsonable {

    /**
     * The default implementation of the toJson method.
     *
     * @see Jsonable for more information.
     */
    @Override
    default String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            toJson(writable);
        } catch (final IOException e) {
            throw new RuntimeException(STRING_TO_JSON_ERROR, e);
        }

        return writable.toString();
    }
}
