package com.github.grigory_rylov.instrumentaltestwithannotations;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.grishberg.annotaions.FeatureForClass;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FeatureForClass(param = "+morda?param1=value1")
public class ExampleInstrumentedTest {

    @Test
    @SampleAnnotation(stringParam = "someParam", intParam = 777)
    public void useAppContext1() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.github.grigory_rylov.instrumentaltestwithannotations", appContext.getPackageName());
    }

    @Test
    public void useAppContext2() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.github.grigory_rylov.instrumentaltestwithannotations", appContext.getPackageName());
    }
}
