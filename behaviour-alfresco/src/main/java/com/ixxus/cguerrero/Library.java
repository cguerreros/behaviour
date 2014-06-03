package com.ixxus.cguerrero;

import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

public class Library implements NodeServicePolicies.OnCreateNodePolicy, NodeServicePolicies.OnUpdateNodePolicy, NodeServicePolicies.OnAddAspectPolicy {

	//Log
	static Logger log = Logger.getLogger(Library.class);
	
	// Dependencies
	private NodeService nodeService;
	private PolicyComponent policyComponent;

	// Behaviours
	private Behaviour onCreateNode;
	private Behaviour onUpdateNode;
	
	private Behaviour onAddAspect;
	//private Behaviour onDeleteNode;
	//NodeServicePolicies.OnDeleteNodePolicy,
	public void init(){
		//create the behaviours.
		log.debug("");
		//TODO Check what the notification frecuency do!!!
		onCreateNode = new JavaBehaviour(this,"onCreateNode", NotificationFrequency.TRANSACTION_COMMIT);//FIRST_EVENT, EVERY_EVENT.
		onUpdateNode = new JavaBehaviour(this,"onUpdateNode", NotificationFrequency.EVERY_EVENT);
		onAddAspect = new JavaBehaviour(this, "onAddAspect", NotificationFrequency.EVERY_EVENT);
		//onDeleteNode = new JavaBehaviour(this,"onDeleteNode", NotificationFrequency.TRANSACTION_COMMIT);
		
		//bind behaviours to node policies.
		
		//this.policyComponent.bi
		this.policyComponent.bindClassBehaviour(
				QName.createQName(NamespaceService.ALFRESCO_URI,"onCreateNode"),
				"cm:content",
				this.onCreateNode);
		
		this.policyComponent.bindClassBehaviour(
				QName.createQName(NamespaceService.ALFRESCO_URI,"onUpdateNode"),
				"cm:content",
				this.onUpdateNode);
		this.policyComponent.bindClassBehaviour(
				QName.createQName(NamespaceService.ALFRESCO_URI,"onAddAspect"),
				"cm:content",
				this.onAddAspect);
		
		
		
	}
	
	@Override
	public void onCreateNode(ChildAssociationRef childAssocRef) {
		
		log.debug("Setting properties onCreateNode");
		// get the parent node
	    NodeRef parentRef = childAssocRef.getParentRef();
	    setPropertiesAspect(parentRef);
	}

	@Override
	public void onUpdateNode(NodeRef nodeRef) {
		log.debug("Setting properties onUpdateNode");
		// get the parent node
	    setPropertiesAspect(nodeRef);
	}


	@Override
	public void onAddAspect(NodeRef nodeRef, QName aspectTypeQName) {
		log.debug("Setting properties onAddAspect");
		// get the parent node
	    setPropertiesAspect(nodeRef);
		
	}

	
	public void setPropertiesAspect(NodeRef parentRef){
		
		  // check the parent to make sure it has the right aspect
	    if (nodeService.exists(parentRef) && nodeService.hasAspect(parentRef, QName.createQName(LibraryModelI.NAMESPACE,LibraryModelI.ASPECT_BH_LIBRARY))) {
	        // set the properties that we want.
	    	  log.debug("Setting aspect properties in the  Node." + parentRef);
	    	 nodeService.setProperty(
	    		        parentRef,
	    		        QName.createQName(
	    		        		LibraryModelI.NAMESPACE,
	    		                LibraryModelI.PROP_BOOKED),
	    		        true);
	    	 nodeService.setProperty(
	    		        parentRef,
	    		        QName.createQName(
	    		        		LibraryModelI.NAMESPACE,
	    		                LibraryModelI.PROP_EXPEDIENT),
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

}
