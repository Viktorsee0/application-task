package com.spg.applicationTask.engine.extension;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.math.BigDecimal;
import java.util.List;

public final class JsonUtils {

    /**
     * Returns a value of the parameter name.
     *
     * @param jsonObject a raw json object.
     * @param name       a parameter name.
     * @return a value.
     */
    public static int getInt(final JsonObject jsonObject, final String name) {
        final Object value = jsonObject.get(name);
        return value != null ? ((BigDecimal) value).intValue() : 0;
    }

    /**
     * Returns a value of the parameter name.
     *
     * @param t a list of objects that extend Jsonable.
     * @return a string value as json list.
     */
    public static <T extends Jsonable> String toJson(List<T> t) {
        StringBuilder jsonResponse = new StringBuilder("[");
        t.forEach(r -> {
            jsonResponse.append(r.toJson());
        });
        jsonResponse.append("]");

        return jsonResponse.toString();
    }
}
