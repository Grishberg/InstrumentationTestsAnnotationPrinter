package com.github.grishberg.annotationprinter;

import android.os.Bundle;
import android.support.test.internal.runner.listener.InstrumentationResultPrinter;
import android.support.test.internal.runner.listener.InstrumentationRunListener;

import com.github.grishberg.annotaions.Feature;
import com.github.grishberg.annotaions.ParametrizedFeature;

import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Main idea https://github.com/medisafe/run-android-tests
 */
public class AnnotationsTestPrinter extends InstrumentationRunListener {

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);

        Collection<Annotation> annotations = description.getAnnotations();
        if (annotations == null) {
            return;
        }

        Bundle bundle = new Bundle();
        StringBuilder annotationsBuilder = new StringBuilder();
        boolean shouldAddComma = false;

        for (Annotation annotation : annotations) {
            if (shouldAddComma) {
                annotationsBuilder.append(",");
            }
            annotationsBuilder.append(annotation.annotationType().getName());

            // feature with single parameter.
            if (annotation instanceof Feature) {
                bundle.putString("feature", processFeatureAnnotation((Feature) annotation));
            }

            // feature with array of parameters.
            if (annotation instanceof ParametrizedFeature) {
                bundle.putString("parametrizedFeature",
                        processParametrizedFeatureAnnotation((ParametrizedFeature) annotation));
            }

            shouldAddComma = true;
        }

        bundle.putString("annotations", annotationsBuilder.toString());
        getInstrumentation()
                .sendStatus(InstrumentationResultPrinter.REPORT_VALUE_RESULT_START, bundle);
    }

    private String processParametrizedFeatureAnnotation(ParametrizedFeature annotation) {
        StringBuilder sb = new StringBuilder();
        String[] params = annotation.params();
        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(params[i]);
        }
        return sb.toString();
    }

    private String processFeatureAnnotation(Feature feature) {
        return feature.param();
    }
}
