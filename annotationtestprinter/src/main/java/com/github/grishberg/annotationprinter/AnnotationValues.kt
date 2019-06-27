package com.github.grishberg.annotationprinter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.annotation.Annotation
import java.lang.reflect.Method

internal class AnnotationValues {
    private val gson: Gson = GsonBuilder().create()

    fun annotationsAsJson(annotations: Collection<Annotation>): String {
        val annotationContainers = ArrayList<AnnotationContainer>()
        for (annotation in annotations) {
            annotationContainers.add(extractMemberValues(annotation))
        }
        return gson.toJson(annotationContainers)
    }

    private fun extractMemberValues(annotation: Annotation): AnnotationContainer {
        val methods = annotation.javaClass.declaredMethods
        val members = ArrayList<AnnotationMember>()
        for (method in methods) {
            if (isNonMemberMethod(method)) {
                continue
            }
            val type = method.returnType
            val value = method.invoke(annotation)
            val name = method.name

            val annotationMember: AnnotationMember = createMember(name, type, value)
            members.add(annotationMember)
        }
        return AnnotationContainer(annotation.annotationType().name, members)
    }

    private fun createMember(name: String, type: Class<*>, value: Any): AnnotationMember {
        val member = AnnotationMember(name, type.name)
        if (type == Int.javaClass) {
            member.intValue = value as Int
        } else if (type == String.javaClass) {
            member.strValue = value as String
        } else if (value is Array<*>) {
            addArrayItems(value, member)
        }

        return member
    }

    private fun addArrayItems(value: Array<*>, member: AnnotationMember) {
        if (value.isArrayOf<String>()) {
            addStringArray(value, member)
        } else if (value.isArrayOf<Int>()) {
            addIntArray(value, member)
        }
    }

    private fun addIntArray(value: Array<*>, member: AnnotationMember) {
        val array = ArrayList<Int>()
        value.forEach {
            array.add(it as Int)
        }
        member.intArray = array
    }

    private fun addStringArray(value: Array<*>, member: AnnotationMember) {
        val array = ArrayList<String>()
        value.forEach {
            array.add(it as String)
        }
        member.strArray = array
    }

    private fun isNonMemberMethod(method: Method): Boolean {
        val name = method.name
        return NON_MEMBERS_METHODS.contains(name)
    }

    companion object {
        private val TAG = AnnotationValues::class.java.simpleName
        private val NON_MEMBERS_METHODS = arrayListOf("equals",
                "hashCode", "annotationType", "toString", "aliases")
    }
}
