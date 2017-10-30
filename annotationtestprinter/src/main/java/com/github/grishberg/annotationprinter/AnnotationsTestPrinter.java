package com.github.grishberg.annotationprinter;

import android.os.Bundle;
import android.support.test.internal.runner.listener.InstrumentationResultPrinter;
import android.support.test.internal.runner.listener.InstrumentationRunListener;

import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * source https://github.com/medisafe/run-android-tests
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
        boolean isNeedComm = false;

        for (Annotation annotation : annotations) {
            if (isNeedComm) {
                annotationsBuilder.append(",");
            }
            annotationsBuilder.append(annotation.annotationType().getName());

            // special case of AnnotationWithIndex
            if (annotation instanceof Parameterized.AnnotationWithIndex) {
                Parameterized.AnnotationWithIndex repeat = (Parameterized.AnnotationWithIndex) annotation;
                annotationsBuilder.append(":");
                annotationsBuilder.append(repeat.count());
            }

            isNeedComm = true;
        }

        bundle.putString("annotations", annotationsBuilder.toString());
        getInstrumentation()
                .sendStatus(InstrumentationResultPrinter.REPORT_VALUE_RESULT_START, bundle);
    }
}
