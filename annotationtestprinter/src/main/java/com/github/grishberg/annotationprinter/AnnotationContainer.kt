package com.github.grishberg.annotationprinter

data class AnnotationMember(
        val name: String,
        val valueType: Class<*>,
        val value: Any
)

data class AnnotationContainer(
        val name: String,
        val members: List<AnnotationMember>
)