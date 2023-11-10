package com.spg.applicationTask.engine.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class is a "REST Controller".
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestController {

    /**
     * The value may indicate an apiPath for a controller
     *
     * @return apiPath value
     */
    String apiPath() default "/";

    ;
}
