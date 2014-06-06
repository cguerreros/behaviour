package com.ixxus.cguerrero;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.io.Serializable;

import org.alfresco.service.cmr.attributes.AttributeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.junit.Before;
import org.junit.Test;


public class AddAspectActionExecuterTest {
	
	private NodeService nodeService;
	private AttributeService attributeService;
	
	private NodeRef testContent;
	private QName aspectQName;
	
	@Before
	public void setUp() throws Exception {
		nodeService = mock(NodeService.class);
		attributeService = mock(AttributeService.class);
		aspectQName = QName.createQName(LibraryModelI.NAMESPACE,
				LibraryModelI.ASPECT_BH_LIBRARY);
	}

	/* testGetAction - doesn't actually test the class, can be removed */

	/*
	testExecuteAction
	- don't mock a method that you are going to execute as part of the test setup!
	(instead, consider creating the class directly). You should only mock calls that happen inside the class under test.

	Example:

	NodeRef testContent = new NodeRef("workspace://SpacesStore/testContent");
	AddAspectActionExecuter actionExecutor = new AddAspectActionExecuter();
	actionExecutor.setParameterValue(AddAspectActionExecuter.PARAM_ASPECT_NAME, LibraryModelI.ASPECT_BH_LIBRARY);
	actionExecutor.executeImpl(null, testContent);

	- Call the execute directly on your action executor class, not the action service (as this is mocked)
	- Set a debug breakpoint in the class under test to see if it is executing the correct parts of your code
	- You'll need to mock the services that are called inside your AddAspectActionExecuter class*/
	
	
	/*
	 * The only thing you could add would be to verify your attributeService mock.
	 */ 
	
	@Test
	public void testExecuteAddAspectAction(){
		nodeService = mock(NodeService.class);
		testContent = new NodeRef("workspace://SpacesStore/testContent");
		when(nodeService.exists(testContent)).thenReturn(true);
		
		AddAspectActionExecuter actionExecutor = new AddAspectActionExecuter();
		actionExecutor.setNodeService(nodeService);
		actionExecutor.setAttributeService(attributeService);
		actionExecutor.executeImpl(null, testContent);
		when(nodeService.hasAspect(any(NodeRef.class), any(QName.class))).thenReturn(true);
	
		
		verify(nodeService).addAspect(testContent,aspectQName,null);
		
		/*when(attributeService.exists(LibraryModelI.NAMESPACE,
				LibraryModelI.ASPECT_BH_LIBRARY,
				LibraryModelI.PROP_BOOKED)).thenReturn(false);*/
		//verify(attributeService).createAttribute(any(Serializable.class), any(Serializable.class));
		
	}
	
	public void testCreateAttribute(){
		
	}
	
	
}
