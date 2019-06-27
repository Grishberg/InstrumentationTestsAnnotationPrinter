package com.github.grishberg.annotationprinter;

import android.os.Bundle;
import android.support.test.internal.runner.listener.InstrumentationResultPrinter;
import android.support.test.internal.runner.listener.InstrumentationRunListener;
import android.util.Log;

import com.github.grishberg.annotaions.Feature;
import com.github.grishberg.annotaions.FeatureForClass;
import com.github.grishberg.annotaions.Flags;
import com.github.grishberg.annotaions.FlagsForClass;

import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Main idea https://github.com/medisafe/run-android-tests
 */
public class AnnotationsTestPrinter extends InstrumentationRunListener {
    private static final String TAG = AnnotationsTestPrinter.class.getSimpleName();
    private static final List<String> EXCLUDED_ANNOTATION = Arrays.asList("org.junit.runner.RunWith",
            "org.junit.Test");

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
        StringBuilder annotationsBuilder = new StringBuilder();
        boolean shouldAddComma = false;

        for (Annotation annotation : annotations) {
            AnnotationContainer annotationContainer = AnnotationValues.extractMemberValues(annotation);

            if (shouldAddComma) {
                annotationsBuilder.append(",");
            }
            annotationsBuilder.append(annotation.annotationType().getName());

            // feature with single parameter.
            if (annotation instanceof Feature || annotation instanceof FeatureForClass) {
                String featureParam;
                if (annotation instanceof Feature) {
                    featureParam = ((Feature) annotation).param();
                } else {
                    featureParam = ((FeatureForClass) annotation).param();
                }
                bundle.putString("feature", featureParam);
            }

            // feature with array of parameters.
            if (annotation instanceof Flags || annotation instanceof FlagsForClass) {
                String[] values;
                if (annotation instanceof Flags) {
                    values = ((Flags) annotation).values();
                } else {
                    values = ((FlagsForClass) annotation).values();
                }
                bundle.putString("flags",
                        processParametrizedFlagsAnnotation(values));
            }

            shouldAddComma = true;
        }

        bundle.putString("annotations", annotationsBuilder.toString());
        getInstrumentation()
                .sendStatus(InstrumentationResultPrinter.REPORT_VALUE_RESULT_START, bundle);
    }

    private String processParametrizedFlagsAnnotation(String[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(values[i]);
        }
        return sb.toString();
    }
}
