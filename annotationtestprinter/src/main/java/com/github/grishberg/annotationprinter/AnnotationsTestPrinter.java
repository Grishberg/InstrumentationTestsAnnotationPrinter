package com.github.grishberg.annotationprinter;

import android.os.Bundle;
import android.util.Log;

import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import androidx.test.internal.runner.listener.InstrumentationResultPrinter;
import androidx.test.internal.runner.listener.InstrumentationRunListener;

/**
 * Main idea https://github.com/medisafe/run-android-tests
 */
public class AnnotationsTestPrinter extends InstrumentationRunListener {
    private static final String TAG = AnnotationsTestPrinter.class.getSimpleName();
    private static final List<String> EXCLUDED_ANNOTATION = Arrays.asList("org.junit.runner.RunWith",
            "org.junit.Test");
    private AnnotationValues annotationValues = new AnnotationValues();

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);

        ArrayList<Annotation> annotations = new ArrayList<>();
        Annotation[] classAnnotations = description.getTestClass().getAnnotations();
        for (Annotation annotation : classAnnotations) {
            String annotationName = annotation.getClass().getName();
            Log.d(TAG, "Class Annotation: " + annotationName);
            if (EXCLUDED_ANNOTATION.contains(annotationName)) {
                continue;
            }
            annotations.add(annotation);
        }

        Log.d(TAG, "testStarted: description: " + description);
        Collection<Annotation> methodAnnotations = description.getAnnotations();
        annotations.addAll(description.getAnnotations());

        for (Annotation annotation : methodAnnotations) {
            Log.d(TAG, "annotation: " + annotation);
            Field[] fields = annotation.annotationType().getFields();
            for (Field field : fields) {
                Class<?> type = field.getType();
                Log.d(TAG, "  field: name = " + field.getName() + ", type = " + type.getName());
            }
        }

        Log.d(TAG, "testStarted: method annotations: " + methodAnnotations);
        if (annotations.isEmpty()) {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("annotations", annotationValues.annotationsAsJson(annotations));
        getInstrumentation()
                .sendStatus(InstrumentationResultPrinter.REPORT_VALUE_RESULT_START, bundle);
    }
}
