package com.spg.applicationTask.engine.IoC;


import org.reflections.Reflections;

import java.util.Set;

public interface AnnotationObjectScanner {

    Set<Class<?>> scan(Reflections reflections);
}
