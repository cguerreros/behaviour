package com.ixxus.cguerrero;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.alfresco.util.BaseAlfrescoSpringTest;

public class SampleIntegrationTestIT extends BaseAlfrescoSpringTest {

    public void testEmpty() {
        // test setup
        boolean expected = true;

        // execute code
        boolean result = true;

        // assertions 
        assertThat("Please write some integration tests for your component. Note: use junit 3 style testFoo naming with BaseAlfrescoSpringTest!", result, is(expected));
    }
}