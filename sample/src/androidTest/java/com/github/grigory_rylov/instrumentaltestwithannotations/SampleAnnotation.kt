package com.github.grigory_rylov.instrumentaltestwithannotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.CLASS,
        AnnotationTarget.FILE,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER)
annotation class SampleAnnotation(
        val stringParam: String,
        val intParam: Int,
        val intArray: IntArray,
        val strArray: Array<String>
)
