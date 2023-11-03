package com.spg.applicationTask.engine.IoC;

public interface ObjectConfigurator {

    void configure(Object t, ApplicationContext context) throws Exception;
}
