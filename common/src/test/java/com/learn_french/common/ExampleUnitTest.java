package com.learn_french.common;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void fileObjectShouldNotBeNull() throws Exception {
        assertTrue(getClass().getResource("test.json") == null);
    }
}