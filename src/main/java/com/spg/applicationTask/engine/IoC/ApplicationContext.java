package com.spg.applicationTask.engine.IoC;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class stores all components that used in app
 */
public class ApplicationContext {

    /**
     * This field store "components"
     */
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();
    private final Config config;
    private AnnotationObjectFactory annotationObjectFactory;
    private JavaObjectFactory javaObjectFactory;

    public ApplicationContext(final Config config) {
        this.config = config;
    }

    /**
     * Returns an instance of the passed class
     *
     * @param type a class that instance will be created
     * @return T an instance of type
     */
    public <T> T getObject(final Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        Map<Class, Object> instance = annotationObjectFactory.create(type);
        cache.putAll(instance);
        return (T) cache.get(type);
    }

    /**
     * Adds all necessary components to the cache
     */
    void createContext() {
        javaObjectFactory.create();
        annotationObjectFactory.create();
    }

    /**
     * Adds the passed components to the cache
     *
     * @param map a map of components
     */
    void addCache(Map<Class, Object> map) {
        for (Class aClass : map.keySet()) {
            cache.computeIfAbsent(aClass, value -> map.get(aClass));
        }
    }

    /**
     * @return config
     * @see Config
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Sets annotation factory to application context
     *
     * @param factory an annotation object factory
     */
    void setFactory(final AnnotationObjectFactory factory) {
        this.annotationObjectFactory = factory;
    }

    /**
     * Sets java factory to application context
     *
     * @param factory an java object factory
     */
    void setFactory(final JavaObjectFactory factory) {
        this.javaObjectFactory = factory;
    }

    /**
     * Cleans the context
     */
    public void clean() {
        cache.clear();
    }
}
