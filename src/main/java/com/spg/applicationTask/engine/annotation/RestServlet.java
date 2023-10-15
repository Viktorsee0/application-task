package com.spg.applicationTask.engine.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestServlet {

    String url() default "/";
}
