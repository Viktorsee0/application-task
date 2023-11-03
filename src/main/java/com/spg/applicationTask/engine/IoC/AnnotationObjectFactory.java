package com.spg.applicationTask.engine.IoC;


import com.spg.applicationTask.engine.IoC.annotation.Component;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class AnnotationObjectFactory {

    private List<AnnotationObjectScanner> objectScanners = new ArrayList<>();
    private List<PostConstructObjectConfigurator> configurators = new ArrayList<>();
    private List<ObjectConfigurator> injectors = new ArrayList<>();
    private Set<Class<?>> componentClasses;
    private final ApplicationContext context;

    AnnotationObjectFactory(final ApplicationContext context) {
        this.context = context;
        Reflections scanner = context.getConfig().getScanner();
        try {
            for (Class<? extends AnnotationObjectScanner> aClass : scanner.getSubTypesOf(AnnotationObjectScanner.class)) {
                objectScanners.add(aClass.getDeclaredConstructor().newInstance());
            }
            for (Class<? extends PostConstructObjectConfigurator> aClass : scanner.getSubTypesOf(PostConstructObjectConfigurator.class)) {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            }
            for (Class<? extends ObjectConfigurator> aClass : scanner.getSubTypesOf(ObjectConfigurator.class)) {
                injectors.add(aClass.getDeclaredConstructor().newInstance());
            }
            componentClasses = scanner.getTypesAnnotatedWith(Component.class);
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    void create() {
        try {
            Map<Class, Object> result = new HashMap<>();
            for (Class<?> aClass : scan()) {
                Object newInstance = createInstance(aClass);
                if (aClass.isInterface()) {
                    result.put(aClass, newInstance);
                } else if (aClass.getInterfaces().length > 0) {
                    for (Class<?> classInterface : aClass.getInterfaces()) {
                        result.put(classInterface, newInstance);
                    }
                } else {
                    result.put(aClass, newInstance);
                }
            }
            context.addCache(result);
            for (Class aClass : result.keySet()) {
                configure(aClass, result.get(aClass));
            }
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    <T> Map<Class, Object> create(final Class<T> aClass) {
        try {
            Map<Class, Object> result = new HashMap<>();
            T newInstance = createInstance(aClass);
            configure(aClass, newInstance);
            result.put(aClass, newInstance);
            return result;
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private Set<Class<?>> scan() {
        Set<Class<?>> scannedClasses = new HashSet<>();
        objectScanners.forEach(s -> {
            Set<Class<?>> scan = s.scan(context.getConfig().getScanner());
            scannedClasses.addAll(scan);
        });
        return scannedClasses;
    }

    private <T> T createInstance(final Class<T> aClass) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        return aClass.getDeclaredConstructor().newInstance();
    }

    <T> void configure(final Class<?> aClass, final Object instance) {
        try {
            injectProperties(instance);
            injectDependencies(instance);
            invokeInit(aClass, instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void injectProperties(final T t) {
        configurators.forEach(c -> {
            try {
                c.configure(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> void injectDependencies(final T t) {
        injectors.forEach(i -> {
            try {
                i.configure(t, context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> void invokeInit(Class<?> implClass, T t)
            throws IllegalAccessException, InvocationTargetException {
        for (Method method : implClass.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }
}
