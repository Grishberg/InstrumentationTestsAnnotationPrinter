package com.github.grishberg.annotationprinter

import com.google.gson.annotations.SerializedName

data class AnnotationMember(
        @SerializedName("name")  val name: String,
        @SerializedName("valueType") val valueType: String,
        var intValue: Int? = null,
        var strValue: String? = null,
        var strArray: ArrayList<String>? = null,
        var intArray: ArrayList<Int>? = null
)

data class AnnotationContainer(
        @SerializedName("name")val name: String,
        @SerializedName("members")val members: List<AnnotationMember>
)