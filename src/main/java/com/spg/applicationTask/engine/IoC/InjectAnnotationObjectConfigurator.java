package com.spg.applicationTask.engine.IoC;

import com.spg.applicationTask.engine.IoC.annotation.Inject;

import java.lang.reflect.Field;

public class InjectAnnotationObjectConfigurator implements ObjectConfigurator{

    @Override
    public void configure(Object t, ApplicationContext context) throws IllegalAccessException {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)){
                field.setAccessible(true);
                Object object = context.getObject(field.getType());
                field.set(t, object);
            }
        }
    }
}
