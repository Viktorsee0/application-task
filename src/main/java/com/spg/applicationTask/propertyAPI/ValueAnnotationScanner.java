package com.spg.applicationTask.propertyAPI;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import static org.reflections.scanners.Scanners.*;


public class ValueAnnotationScanner {

    public static void main(String[] args) {

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setScanners(SubTypes)
                        .forPackage("com.spg.applicationTask")
//                        .filterInputsBy(new FilterBuilder().includePackage("com.spg"))
        );

        Set<Class<?>> classesWithAnnotatedFields = new HashSet<>();

        List<? extends Class<?>> classes = reflections.getAll(SubTypes)
                .stream()
                .map(name -> {
                    try {
                        return Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        classes.forEach(clazz -> {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Value.class)) {
                    classesWithAnnotatedFields.add(clazz);
                }
            }
        });

        for (Class<?> clazz : classesWithAnnotatedFields) {
            PropertyInjector.injectProperties(clazz);
        }
    }
}
