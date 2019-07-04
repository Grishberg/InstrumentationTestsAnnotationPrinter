package com.github.grishberg.annotationprinter

data class AnnotationMember(
        val name: String,
        val valueType: String,
        var intValue: Int? = null,
        var strValue: String? = null,
        var strArray: ArrayList<String>? = null,
        var intArray: ArrayList<Int>? = null
)

data class AnnotationContainer(
        val name: String,
        val members: List<AnnotationMember>
)