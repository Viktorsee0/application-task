package com.spg.applicationTask.annotation;

import org.reflections.Reflections;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;


public class ValueAnnotationScanner {

    private static final String VALUE_ANNOTATION_NAME = "com.spg.applicationTask.annotation.Value";

    public static void main(String[] args) {
        Reflections reflections = new Reflections("com.spg.applicationTask");

        reflections.
        Set<Field> fields = reflections
                .getFieldsAnnotatedWith(Value.class);

        List<String> annotatedFields = fields.stream()
                .map(field -> field.getAnnotation(Value.class)
                        .value())
                .collect(Collectors.toList());


        System.out.println();
        // Находим классы, аннотированные указанной аннотацией

    }
}
