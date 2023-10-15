package com.spg.applicationTask.engine.annotation;

import com.spg.applicationTask.engine.Server;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

class RestServletAnnotationProcessor {

    private Server server;

  public static void main(String[] args) {
   Reflections reflections = new Reflections(
           new ConfigurationBuilder()
                   .setScanners(TypesAnnotated)
                   .forPackage("com.spg.applicationTask")
//                        .filterInputsBy(new FilterBuilder().includePackage("com.spg"))
   );

   Set<Class<?>> classes = reflections.get(TypesAnnotated.of(RestServlet.class).asClass());

   System.out.println();

   classes.forEach(clazz -> {
    String url = clazz.getAnnotation(RestServlet.class).url();
       try {
           Field declaredField = clazz.getDeclaredField("url");
           System.out.println();
       } catch (NoSuchFieldException e) {
           throw new RuntimeException(e);
       }
   });

  }
}
