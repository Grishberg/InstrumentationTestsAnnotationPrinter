package com.github.grigory_rylov.instrumentaltestwithannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface SampleAnnotation {
    String stringParam();

    int intParam() default 0;

    int[] intArray() default {};

    String[] strArray() default {};
}
