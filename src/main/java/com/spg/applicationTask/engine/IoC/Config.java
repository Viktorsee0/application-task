package com.spg.applicationTask.engine.IoC;

import org.reflections.Reflections;

interface Config {
    <T> Class<? extends T> getImplClass(Class<T> ifc);

    Reflections getScanner();
}
