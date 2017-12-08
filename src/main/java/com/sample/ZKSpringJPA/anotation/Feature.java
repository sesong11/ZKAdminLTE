package com.sample.ZKSpringJPA.anotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

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
