package com.spg.applicationTask.engine.IoC;

/**
 * Provides a method to be executed after initializing the component and before using it
 */
public interface PostConstructObjectConfigurator {

    /**
     * Configures the component after using the constructor
     *
     * @param t a component
     * @throws Exception when a reflection some problem
     */
    void configure(Object t) throws Exception;
}
