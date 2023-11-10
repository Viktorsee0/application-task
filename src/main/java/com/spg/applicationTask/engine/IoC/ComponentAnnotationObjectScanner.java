package com.spg.applicationTask.engine.IoC;

import com.spg.applicationTask.engine.IoC.annotation.Component;
import org.reflections.Reflections;

import java.util.Set;

/**
 * An object scanner that discovers candidates
 * specified as a component to create
 *
 * @see AnnotationObjectScanner
 */
public class ComponentAnnotationObjectScanner implements AnnotationObjectScanner {

    @Override
    public Set<Class<?>> scan(Reflections reflections) {
        return reflections.getTypesAnnotatedWith(Component.class);
    }
}
