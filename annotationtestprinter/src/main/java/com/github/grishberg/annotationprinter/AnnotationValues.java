package com.github.grishberg.annotationprinter;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

class AnnotationValues {
    private static final String TAG = AnnotationValues.class.getSimpleName();

    static String annotationAsJson(Annotation annotation) {
        AnnotationContainer annotationContainer = extractMemberValues(annotation);
        return "";
    }

    private static AnnotationContainer extractMemberValues(Annotation annotation) {
        ArrayList<AnnotationMember> members = new ArrayList<>();
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
        try {
            Field field = invocationHandler.getClass().getDeclaredField("elements");
            field.setAccessible(true);
            Object[] elements = (Object[]) field.get(invocationHandler);
            for (Object element : elements) {
                Field elementNameField = element.getClass().getDeclaredField("name");
                elementNameField.setAccessible(true);
                String elementName = (String) elementNameField.get(element);
                Log.d(TAG, "elementName = " + elementName);

                Field elementTypeField = element.getClass().getDeclaredField("elementType");
                elementTypeField.setAccessible(true);
                Class<?> valueType = (Class<?>) elementTypeField.get(element);
                Log.d(TAG, "type: " + valueType);

                Field valueField = element.getClass().getDeclaredField("value");
                valueField.setAccessible(true);
                Object value = valueField.get(element);
                Log.d(TAG, "value = " + value);

                members.add(new AnnotationMember(elementName, valueType, value));
            }
        } catch (Exception e) {
            Log.e(TAG, "member values reading failed", e);
        }
        return new AnnotationContainer(annotation.getClass().getName(), members);
    }
}
