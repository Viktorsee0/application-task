package com.spg.applicationTask.engine.IoC;

import com.spg.applicationTask.engine.IoC.annotation.ComponentFactory;
import com.spg.applicationTask.engine.IoC.annotation.Configuration;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This factory creates objects by annotations
 */
public class JavaObjectFactory {
    private final List<PostConstructObjectConfigurator> configurators = new ArrayList<>();
    private final Set<Class<?>> configClasses;
    private final ApplicationContext context;

    JavaObjectFactory(final ApplicationContext context) {
        this.context = context;
        try {
            Reflections scanner = context.getConfig().getScanner();
            for (Class<? extends PostConstructObjectConfigurator> aClass : scanner.getSubTypesOf(PostConstructObjectConfigurator.class)) {
                configurators.add(aClass.getDeclaredConstructor().newInstance());
            }
            configClasses = scanner.getTypesAnnotatedWith(Configuration.class);
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates components and put it to context
     */
    void create() {
        try {
            Map<Class, Object> result = new HashMap<>();
            for (Class<?> aClass : configClasses) {
                Object newInstance = createInstance(aClass);
                configure(aClass, newInstance);
                result.putAll(invokeFactoryMethods(aClass, newInstance));
            }
            context.addCache(result);
        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns an instance of the passed class
     *
     * @param aClass an instance which will be returned
     * @return an instance of aClass
     * @throws NoSuchMethodException     when a reflection some problem
     * @throws InvocationTargetException when a reflection some problem
     * @throws InstantiationException    when a reflection some problem
     * @throws IllegalAccessException    when a reflection some problem
     */
    private <T> T createInstance(final Class<T> aClass) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        return aClass.getDeclaredConstructor().newInstance();
    }

    private <T> void configure(final Class<?> aClass, final Object instance) throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        configurators.forEach(c -> {
            try {
                c.configure(instance);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Map<Class, Object> invokeFactoryMethods(final Class<?> aClass, final Object instance) throws InvocationTargetException, IllegalAccessException {
        Map<Class, Object> obj = new HashMap<>();
        for (Method method : aClass.getMethods()) {
            if (method.isAnnotationPresent(ComponentFactory.class)) {
                Class<?> returnType = method.getReturnType();
                obj.put(returnType, method.invoke(instance));
            }
        }
        return obj;
    }
}
