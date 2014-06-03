package com.ixxus.cguerrero;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SampleActionEvaluatorTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void todo() {
     
        // test setup
        boolean expected = true;
        
        // execute code
        boolean result = new SampleActionEvaluator().evaluate(null);
        
        // assertions 
        assertThat("Expected true from evaluate", result, is(expected));

    }
}