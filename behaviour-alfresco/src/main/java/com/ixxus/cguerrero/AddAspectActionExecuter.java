package com.ixxus.cguerrero;

import java.io.Serializable;
import java.util.List;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.attributes.AttributeService;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;

public class AddAspectActionExecuter extends ActionExecuterAbstractBase {

	private static Logger log = Logger.getLogger(AddAspectActionExecuter.class);

	// create the constant name for our action.
	public static final String NAME = "add-aspect";
	public static final String PARAM_ASPECT_NAME = "aspect-name";

	private NodeService nodeService;
	private AttributeService attributeService;

	@Override
	protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {

		if (this.nodeService.exists(actionedUponNodeRef)) {
			// get the name of the aspect.
			QName aspectQName = QName.createQName(LibraryModelI.NAMESPACE,
					LibraryModelI.ASPECT_BH_LIBRARY);
			nodeService.addAspect(actionedUponNodeRef, aspectQName, null);
			if (nodeService.hasAspect(actionedUponNodeRef, aspectQName)) {
				QName propBooked = QName.createQName(LibraryModelI.NAMESPACE,
						LibraryModelI.PROP_BOOKED);
				Serializable nodePropValue = nodeService.getProperty(
						actionedUponNodeRef, propBooked);
				if(!attributeService.exists(LibraryModelI.NAMESPACE,
						LibraryModelI.ASPECT_BH_LIBRARY,
						LibraryModelI.PROP_BOOKED)){
				attributeService.createAttribute(nodePropValue,
						LibraryModelI.NAMESPACE,
						LibraryModelI.ASPECT_BH_LIBRARY,
						LibraryModelI.PROP_BOOKED);
				log.debug("New attribute added, value " + nodePropValue + " keys " + LibraryModelI.NAMESPACE +","+
						LibraryModelI.ASPECT_BH_LIBRARY+","+
						LibraryModelI.PROP_BOOKED);
				}
			}

		} else {
			log.debug("The node does not exists");
		}
	}

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		// add definition for action parameteres. We only have param-aspect-name
		paramList.add(new ParameterDefinitionImpl(PARAM_ASPECT_NAME,
				DataTypeDefinition.QNAME, true,
				getParamDisplayLabel(PARAM_ASPECT_NAME)));

	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

}
