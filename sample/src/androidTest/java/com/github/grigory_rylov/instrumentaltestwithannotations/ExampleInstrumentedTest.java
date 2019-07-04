package com.github.grigory_rylov.instrumentaltestwithannotations;

import android.content.Context;

import com.github.grishberg.annotaions.Feature;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@Feature(param = "+morda?param1=value1")
public class ExampleInstrumentedTest {

    @Test
    @SampleAnnotation(
            stringParam = "someParam",
            intParam = 777,
            intArray = {0, 1, 2},
            strArray = {"one", "two"})
    public void useAppContext1() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.github.grigory_rylov.instrumentaltestwithannotations", appContext.getPackageName());
    }

    @Test
    public void useAppContext2() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.github.grigory_rylov.instrumentaltestwithannotations", appContext.getPackageName());
    }
}
