package com.ixxus.cguerrero;

import org.junit.Test;

import com.ixxus.test.AlfrescoTestUtils;

public class ContentModelTest {

    public class LibraryTest {

	}

	@Test
    public void testBootstrapModel() throws Exception {
        AlfrescoTestUtils.testBootstrapModel("alfresco/module/behaviour/model/library-model.xml");
    }
}