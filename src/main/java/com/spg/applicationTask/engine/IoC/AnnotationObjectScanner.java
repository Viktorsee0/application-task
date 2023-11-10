package com.spg.applicationTask.engine.IoC;


import org.reflections.Reflections;

import java.util.Set;

/**
 * Provides a method that scans packets and returns the set of types found
 */
public interface AnnotationObjectScanner {

    /**
     * Returns candidates given to create
     *
     * @return set of types which will be created
     */
    Set<Class<?>> scan(Reflections reflections);
}
