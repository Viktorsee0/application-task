package com.spg.applicationTask.engine.IoC;


public class Application {

    private static ApplicationContext context;

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

    public static ApplicationContext getContext() {
        return context;
    }

    private static Package getPackage(Class<?> primarySource) {
        return primarySource.getPackage();
    }
}
