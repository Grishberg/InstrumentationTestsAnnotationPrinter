package com.github.grishberg.annotationprinter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Method

internal class AnnotationValues {
    private val gson: Gson = GsonBuilder().create()

    fun annotationsAsJson(annotations: MutableList<Annotation>): String {
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
        return AnnotationContainer(annotation.annotationClass.qualifiedName!!, members)
    }

    @Suppress("UNCHECKED_CAST")
    private fun createMember(name: String, type: Class<*>, value: Any): AnnotationMember {
        val member = AnnotationMember(name, type.name)
        if (value is Int) {
            member.intValue = value
        } else if (value is String) {
            member.strValue = value
        } else if (type == IntArray::class.java) {
            addIntArray(value as IntArray, member)
        } else if (type == Array<String>::class.java) {
            addStringArray(value as Array<String>, member)
        }

        return member
    }

    private fun addIntArray(value: IntArray, member: AnnotationMember) {
        val array = ArrayList<Int>()
        value.forEach {
            array.add(it)
        }
        member.intArray = array
    }

    private fun addStringArray(value: Array<String>, member: AnnotationMember) {
        val array = ArrayList<String>()
        value.forEach {
            array.add(it)
        }
        member.strArray = array
    }

    private fun isNonMemberMethod(method: Method): Boolean {
        val name = method.name
        return NON_MEMBERS_METHODS.contains(name)
    }

    companion object {
        private val NON_MEMBERS_METHODS = arrayListOf("equals",
                "hashCode", "annotationType", "toString", "aliases")
    }
}
