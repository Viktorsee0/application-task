package com.spg.applicationTask.engine.IoC;

import com.spg.applicationTask.engine.IoC.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

final class ValueAnnotationObjectConfigurator implements PostConstructObjectConfigurator {

    private Map<String, String> properties;

    public ValueAnnotationObjectConfigurator() {
        try {
            try (InputStream inputStream = getClass().getResourceAsStream("/application.properties");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                properties = reader.lines()
                        .filter(line -> !line.isEmpty()).map(line -> line.split("="))
                        .collect(toMap(arr -> arr[0], arr -> arr[1]));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void configure(Object t) throws IllegalAccessException {
        Class<?> implClass = t.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            Value annotation = field.getAnnotation(Value.class);
            if (annotation != null) {
                String value = annotation.value().isEmpty() ?
                        properties.get(field.getName()) :
                        properties.get(annotation.value());
                field.setAccessible(true);
                field.set(t, value);
            }
        }
    }
}
