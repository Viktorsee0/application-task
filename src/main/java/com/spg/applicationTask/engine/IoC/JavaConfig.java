package com.spg.applicationTask.engine.IoC;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of config
 *
 * @see Config
 */
final class JavaConfig implements Config {

    private final Reflections scanner;

    /**
     * Cache of classes that have already been checked for implementation
     */
    private final Map<Class, Class> ifc2ImplClass;

    /**
     * Constructs java config by primary source
     *
     * @param primarySource a main class
     */
    public JavaConfig(Class<?> primarySource) {
        this.scanner = new Reflections(new ConfigurationBuilder().forPackages(primarySource.getPackageName()));
        this.ifc2ImplClass = new HashMap<>();
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new RuntimeException(ifc + " has 0 or more than one impl please update your config");
            }
            return classes.iterator().next();
        });
    }

    @Override
    public Reflections getScanner() {
        return scanner;
    }
}
