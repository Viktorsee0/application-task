package com.spg.applicationTask.propertyAPI;

import com.spg.applicationTask.extension.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

class PropertyInjector {

    private static final Properties PROPERTIES = new Properties();
    private static final String PATH_APPLICATION_PROPERTIES = "application.properties";

    static {
        load();
    }

    private PropertyInjector() {
    }

    public static void injectProperties(Object obj) {

        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Value.class)) {
                Value fieldAnnotation = field.getAnnotation(Value.class);
                String propertyName = fieldAnnotation.value();
                String propertyValue = PROPERTIES.getProperty(propertyName);

                if (propertyValue != null) {
                    field.setAccessible(true);

                    try {
                        if (field.getType() == String.class) {
                            field.set(obj, propertyValue);
                        } else if (field.getType() == int.class || field.getType() == Integer.class) {
                            field.set(obj, Integer.parseInt(propertyValue));
                        }

                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }

    private static void load() {
        try (InputStream resourceAsStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PATH_APPLICATION_PROPERTIES)) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
