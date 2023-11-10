package com.spg.applicationTask.engine.IoC;

/**
 * Provides methods for starting application context
 */
public class Application {

    private static ApplicationContext context;

    /**
     * Creates application context
     */
    public static ApplicationContext run(Class<?> primarySource) {
        JavaConfig config = new JavaConfig(primarySource);
        ApplicationContext applicationContext = new ApplicationContext(config);
        JavaObjectFactory javaObjectFactory = new JavaObjectFactory(applicationContext);
        AnnotationObjectFactory annotationObjectFactory = new AnnotationObjectFactory(applicationContext);
        applicationContext.setFactory(javaObjectFactory);
        applicationContext.setFactory(annotationObjectFactory);
        applicationContext.createContext();
        context = applicationContext;
        return context;
    }

    /**
     * Returns context
     *
     * @return context an application context
     */
    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * Cleans application context
     */
    public static void clean() {
        context.clean();
    }

}
