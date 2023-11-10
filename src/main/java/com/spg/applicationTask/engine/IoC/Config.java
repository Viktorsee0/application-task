package com.spg.applicationTask.engine.IoC;

import org.reflections.Reflections;

interface Config {
    /**
     * Returns the implementation of the passed class if it is of abstract type
     *
     * @param ifc type to be checked if it is an interface type or not
     * @return implementation if ifc
     */
    <T> Class<? extends T> getImplClass(Class<T> ifc);

    /**
     * Returns scanner
     *
     * @return scanner
     * @see Reflections
     */
    Reflections getScanner();
}
