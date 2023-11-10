package com.spg.applicationTask.engine.IoC;

/**
 * Provides methods for customizing component
 */
public interface ObjectConfigurator {

    /**
     * Configures component
     *
     * @param t       a component for customizing
     * @param context a application context
     * @throws Exception when a reflection some problem
     */
    void configure(Object t, ApplicationContext context) throws Exception;
}
