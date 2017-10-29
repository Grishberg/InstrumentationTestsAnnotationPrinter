package com.github.grishberg.annotationprinter;

import android.support.test.InstrumentationRegistry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Parametrized annotations.
 */
public class Parameterized {
    public Parameterized() {
        //not used.
    }

    public static final String PARAM_INDEX = "paramIndex";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface AnnotationWithIndex {
        int count();
    }

    public static int getIndex() {
        try {
            return Integer.parseInt(InstrumentationRegistry.getArguments().getString(PARAM_INDEX));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
