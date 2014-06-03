package com.ixxus.cguerrero;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.nodelocator.CompanyHomeNodeLocator;
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class LibraryAspectTest {

	//private static final String ADMIN_USER_NAME = "admin";

	static Logger log = Logger.getLogger(LibraryAspectTest.class);

	//TODO Add more properties
	private final QName BOOKED = QName.createQName(LibraryModelI.NAMESPACE,
			LibraryModelI.PROP_BOOKED);

	private NodeService nodeService;

	private NodeLocatorService nodeLocatorService;

	@Before
	public void setUp() {

		nodeService = mock(NodeService.class);
		nodeLocatorService = mock(NodeLocatorService.class);
	}

	@Test
	public void testAddLibraryAspect() {
		//AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
		NodeRef companyHome = nodeLocatorService.getNode(
				CompanyHomeNodeLocator.NAME, null, null);

		// assign name
		String name = "Add Library Aspect Test (" + System.currentTimeMillis()+ ")";
		Map<QName, Serializable> contentProps = new HashMap<QName, Serializable>();
		contentProps.put(ContentModel.PROP_NAME, name);

		// create content node
		ChildAssociationRef association = nodeService.createNode(companyHome,
				ContentModel.ASSOC_CONTAINS,
				QName.createQName(NamespaceService.CONTENT_MODEL_PREFIX, name),
				ContentModel.TYPE_CONTENT, contentProps);

		NodeRef content = association.getChildRef();

		// set up some aspect-based properties
		Map<QName, Serializable> aspectProps = new HashMap<QName, Serializable>();
		aspectProps.put(BOOKED, false);

		// add the aspect and set the properties
		nodeService.addAspect(content, QName.createQName(
				LibraryModelI.NAMESPACE, LibraryModelI.ASPECT_BH_LIBRARY),
				aspectProps);
		
		//TODO CHECK MORE PROPERTIES.
		assertFalse((Boolean) nodeService.getProperty(content, BOOKED));
		
		// once we finish, we need to delete the node that we created previously
		nodeService.deleteNode(content);
	}

}