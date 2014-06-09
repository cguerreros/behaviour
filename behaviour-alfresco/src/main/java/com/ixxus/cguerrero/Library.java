package com.ixxus.cguerrero;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ActionService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

public class Library implements NodeServicePolicies.OnCreateNodePolicy,
		NodeServicePolicies.OnUpdateNodePolicy,
		NodeServicePolicies.OnAddAspectPolicy {

	// Log
	private static Logger log = Logger.getLogger(Library.class);

	// Dependencies
	private NodeService nodeService;
	private PolicyComponent policyComponent;
	private ActionService actionService;

	public void init() {
		// Behaviours
		Behaviour onCreateNode;
		Behaviour onUpdateNode;
		Behaviour onAddAspect;

		// create the behaviours.
		log.debug("Method init start.");

		onCreateNode = new JavaBehaviour(this, "onCreateNode",
				NotificationFrequency.TRANSACTION_COMMIT);
		onUpdateNode = new JavaBehaviour(this, "onUpdateNode",
				NotificationFrequency.EVERY_EVENT);
		onAddAspect = new JavaBehaviour(this, "onAddAspect",
				NotificationFrequency.EVERY_EVENT);

		// bind behaviours to node policies.
		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "onCreateNode"), "cm:content",
				onCreateNode);

		this.policyComponent.bindClassBehaviour(QName.createQName(
				NamespaceService.ALFRESCO_URI, "onUpdateNode"), "cm:content",
				onUpdateNode);
		this.policyComponent
				.bindClassBehaviour(QName.createQName(
						NamespaceService.ALFRESCO_URI, "onAddAspect"),
						"cm:content", onAddAspect);
		log.debug("Method init end.");
	}

	@Override
	public void onCreateNode(ChildAssociationRef childAssocRef) {

		log.debug("onCreateNode start");
		Action addAspectAction = actionService
				.createAction(AddAspectActionExecuter.NAME);
		addAspectAction.setParameterValue(
				AddAspectActionExecuter.PARAM_ASPECT_NAME,
				LibraryModelI.ASPECT_BH_LIBRARY);
		log.debug("Before execute action " + AddAspectActionExecuter.NAME);

		NodeRef nodeRef = childAssocRef.getChildRef();
		actionService.executeAction(addAspectAction, nodeRef, false, true);
		log.debug("After execute action " + AddAspectActionExecuter.NAME);
		log.debug("onCreateNode end");
	}

	@Override
	public void onUpdateNode(NodeRef nodeRef) {
		log.debug("Setting properties onUpdateNode");
		setPropertiesAspect(nodeRef);
	}

	@Override
	public void onAddAspect(NodeRef nodeRef, QName aspectTypeQName) {
		log.debug("Setting properties onAddAspect");
		setPropertiesAspect(nodeRef);

	}

	private void setPropertiesAspect(NodeRef parentRef) {

		// check the parent to make sure it has the right aspect
		if (nodeService.exists(parentRef)
				&& nodeService.hasAspect(parentRef, QName.createQName(
						LibraryModelI.NAMESPACE,
						LibraryModelI.ASPECT_BH_LIBRARY))) {
			// set the properties that we want.
			log.debug("Setting aspect properties in the  Node." + parentRef);
			nodeService.setProperty(parentRef, QName.createQName(
					LibraryModelI.NAMESPACE, LibraryModelI.PROP_BOOKED), true);
			nodeService.setProperty(parentRef, QName.createQName(
					LibraryModelI.NAMESPACE, LibraryModelI.PROP_EXPEDIENT),
					"success");
		} else {
			log.debug("Does not exists the aspect on the node.");
			return;

		}

		return;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setPolicyComponent(PolicyComponent policyComponent) {
		this.policyComponent = policyComponent;
	}

	public void setActionService(ActionService actionService) {
		this.actionService = actionService;
	}

}
