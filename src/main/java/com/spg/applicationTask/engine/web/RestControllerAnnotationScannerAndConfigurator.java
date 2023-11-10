package com.spg.applicationTask.engine.web;

import com.spg.applicationTask.engine.IoC.AnnotationObjectScanner;
import com.spg.applicationTask.engine.IoC.PostConstructObjectConfigurator;
import org.reflections.Reflections;

import java.util.Set;

/**
 * An object scanner that discovers candidates specified as a REST controller to create and customize
 *
 * @see PostConstructObjectConfigurator
 * @see AnnotationObjectScanner
 */
public final class RestControllerAnnotationScannerAndConfigurator implements PostConstructObjectConfigurator, AnnotationObjectScanner {

    @Override
    public void configure(Object t) {
        Class<?> aClass = t.getClass();
        if (aClass.isAnnotationPresent(RestController.class)) {
            RestController annotation = aClass.getAnnotation(RestController.class);
            ((AbstractController) t).setApiPath(annotation.apiPath());
        }
    }

    @Override
    public Set<Class<?>> scan(Reflections reflections) {
        return reflections.getTypesAnnotatedWith(RestController.class);
    }
}
