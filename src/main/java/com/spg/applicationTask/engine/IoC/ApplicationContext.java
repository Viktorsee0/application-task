package com.spg.applicationTask.engine.IoC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();
    private final Config config;
    private AnnotationObjectFactory annotationObjectFactory;
    private JavaObjectFactory javaObjectFactory;

    public ApplicationContext(final Config config) {
        this.config = config;
    }

    public <T> T getObject(final Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        Map<Class, Object> instance = annotationObjectFactory.create(type);
        cache.putAll(instance);
        return (T) cache.get(type);
    }

    void createContext() {
        javaObjectFactory.create();
        annotationObjectFactory.create();
    }

    void addCache(Map<Class, Object> map) {
        for (Class aClass : map.keySet()) {
            cache.computeIfAbsent(aClass, value -> map.get(aClass));
        }
    }

    public Config getConfig() {
        return config;
    }

    void setFactory(final AnnotationObjectFactory factory) {
        this.annotationObjectFactory = factory;
    }

    void setFactory(final JavaObjectFactory factory) {
        this.javaObjectFactory = factory;
    }
}
