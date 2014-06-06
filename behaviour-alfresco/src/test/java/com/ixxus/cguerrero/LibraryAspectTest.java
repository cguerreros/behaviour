package com.ixxus.cguerrero;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.Serializable;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.junit.Before;
import org.junit.Test;

public class LibraryAspectTest {

	private Library libraryBehaviour;

	private NodeRef testNode;
	private NodeRef childRef;

	private ActionService actionService;
	private PolicyComponent policyComponent;
	private NodeService nodeService;
	private QName aspectName;

	
	@Before
	public void setUp() {
		nodeService = mock(NodeService.class);
		actionService = mock(ActionService.class);
		libraryBehaviour = new Library();
		policyComponent = mock(PolicyComponent.class);
		aspectName = QName.createQName(LibraryModelI.NAMESPACE,
				LibraryModelI.ASPECT_BH_LIBRARY);
		communInitialization();
	}

	@Test
	public void testInit() throws Exception {
		libraryBehaviour = new Library();
		libraryBehaviour.setPolicyComponent(policyComponent);
		libraryBehaviour.init();

		verify(policyComponent, times(3)).bindClassBehaviour(any(QName.class),
				anyObject(), any(JavaBehaviour.class));
	}

	@Test
	public void testOnCreateNode() {
		testNode = new NodeRef("workspace://spaceStore/testNode");
		libraryBehaviour.setActionService(actionService);
		childRef = new NodeRef("workspace://spacesStore/childNode");

		ChildAssociationRef childAssocRef = new ChildAssociationRef(
				ContentModel.ASSOC_CHILDREN, testNode, null, childRef);
		Action action = mock(Action.class);
		when(actionService.createAction(AddAspectActionExecuter.NAME))
				.thenReturn(action);
		libraryBehaviour.onCreateNode(childAssocRef);

		verify(actionService).executeAction(action,
				childAssocRef.getChildRef(), false, true);

	}

	public void communInitialization() {
		libraryBehaviour = new Library();
		libraryBehaviour.setNodeService(nodeService);
		testNode = new NodeRef("workspace://spaceStore/testNode");

		when(nodeService.exists(testNode)).thenReturn(true);
		when(nodeService.hasAspect(testNode, aspectName)).thenReturn(true);

	}

	@Test
	public void testOnUpdateNode() {
		libraryBehaviour.onUpdateNode(testNode);
		verify(nodeService, times(2)).setProperty(any(NodeRef.class),
				any(QName.class), any(Serializable.class));
	}

	@Test
	public void testOnAddAspect() {
		libraryBehaviour.onAddAspect(testNode, aspectName);
		verify(nodeService, times(2)).setProperty(any(NodeRef.class),
				any(QName.class), any(Serializable.class));
	}
}