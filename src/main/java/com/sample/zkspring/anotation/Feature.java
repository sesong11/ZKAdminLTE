package com.sample.zkspring.anotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Feature {
    String view() default "";
    String uuid() default "";
    String menuName() default "";
    String menuOrder() default "";
    String menuSclass() default "";
    String menuIcon() default "";
    String displayName() default "";
}
