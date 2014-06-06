package com.ixxus.cguerrero;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.nodelocator.CompanyHomeNodeLocator;
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.junit.Before;
import org.junit.Test;


public class AddAspectActionExecuterTest {
	
	private NodeService nodeService;
	private ActionService actionService;
	private NodeLocatorService nodeLocatorService;
	
	@Before
	public void setUp() throws Exception {
		nodeService = mock(NodeService.class);
		actionService = mock(ActionService.class);
		nodeLocatorService = mock(NodeLocatorService.class);
	}

	@Test
	public void testGetAction() {
		Action mockAction = mock(Action.class);
		when(actionService.createAction("add-aspect")).thenReturn(mockAction);
		Action action =  actionService.createAction("add-aspect");
		assertNotNull(action);
	}

	public void testExecuteAction(){
		when(nodeLocatorService.getNode(CompanyHomeNodeLocator.NAME, null,null)).thenReturn(mock(NodeRef.class));
		
		NodeRef companyHome = nodeLocatorService.getNode(CompanyHomeNodeLocator.NAME, null,null);
		
		Map<QName,Serializable> contentProps = new HashMap<QName, Serializable>();
		String name ="Add Aspect Action Test("+ System.currentTimeMillis()+")";
		contentProps.put(ContentModel.PROP_NAME, name);
		when(nodeService.createNode(companyHome,
                ContentModel.ASSOC_CONTAINS,
                QName.createQName(NamespaceService.CONTENT_MODEL_PREFIX, name),
                ContentModel.TYPE_CONTENT,
                contentProps)).thenReturn(mock(ChildAssociationRef.class));
		
		// create content node
        ChildAssociationRef association = nodeService.createNode(
        				companyHome,
                        ContentModel.ASSOC_CONTAINS,
                        QName.createQName(NamespaceService.CONTENT_MODEL_PREFIX, name),
                        ContentModel.TYPE_CONTENT,
                        contentProps
                        );
        
        NodeRef content = association.getChildRef();
    	Action mockAction = mock(Action.class);
		when(actionService.createAction("add-aspect")).thenReturn(mockAction);
        Action action = actionService.createAction("add-aspect");
        action.setParameterValue(AddAspectActionExecuter.PARAM_ASPECT_NAME, LibraryModelI.ASPECT_BH_LIBRARY);
        actionService.executeAction(action, content);
        when(QName.createQName(LibraryModelI.NAMESPACE,
				LibraryModelI.ASPECT_BH_LIBRARY)).thenReturn(mock(QName.class));
        QName aspectQName = QName.createQName(LibraryModelI.NAMESPACE,
				LibraryModelI.ASPECT_BH_LIBRARY);
        assertTrue(nodeService.hasAspect(content,aspectQName));
		
	}
	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	
	
}
