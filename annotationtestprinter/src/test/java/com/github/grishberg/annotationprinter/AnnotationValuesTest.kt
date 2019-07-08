package com.github.grishberg.annotationprinter

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Arrays.asList

@RunWith(JUnit4::class)
class AnnotationValuesTest {
    private val annotationValues = AnnotationValues()

    @Test
    fun `contains string param after serialization`() {
        val annotations = asList(SampleClass().javaClass.declaredAnnotations[0])

        val json = annotationValues.annotationsAsJson(annotations)

        assertTrue(json.contains("{\"name\":\"stringParam\",\"valueType\":\"java.lang.String\",\"strValue\":\"some string\"}"))
    }

    @Test
    fun `contains int param after serialization`() {
        val annotations = asList(SampleClass().javaClass.declaredAnnotations[0])

        val json = annotationValues.annotationsAsJson(annotations)

        assertTrue(json.contains("{\"name\":\"intParam\",\"valueType\":\"int\",\"intValue\":123}"))
    }

    @Test
    fun `contains int array param after serialization`() {
        val annotations = asList(SampleClass().javaClass.declaredAnnotations[0])

        val json = annotationValues.annotationsAsJson(annotations)

        assertTrue(json.contains("{\"name\":\"intArray\",\"valueType\":\"[I\",\"intArray\":[1,2,3]}"))
    }

    @Test
    fun `contains string array param after serialization`() {
        val annotations = asList(SampleClass().javaClass.declaredAnnotations[0])

        val json = annotationValues.annotationsAsJson(annotations)

        assertTrue(json.contains("{\"name\":\"strArray\",\"valueType\":\"[Ljava.lang.String;\",\"strArray\":[\"1\",\"2\",\"3\"]}"))
    }

    @Test
    fun `contains bool param after serialization`() {
        val annotations = asList(SampleClass().javaClass.declaredAnnotations[0])

        val json = annotationValues.annotationsAsJson(annotations)

        assertTrue(json.contains("{\"name\":\"boolParam\",\"valueType\":\"boolean\",\"boolValue\":true}"))
    }

    @SampleAnnotation(
            stringParam = "some string",
            intParam = 123,
            strArray = ["1", "2", "3"],
            intArray = [1, 2, 3],
            boolParam = true
    )
    private class SampleClass
}