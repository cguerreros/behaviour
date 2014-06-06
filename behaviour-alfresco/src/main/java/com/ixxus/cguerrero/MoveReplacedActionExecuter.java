package com.ixxus.cguerrero;

import java.util.List;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileNotFoundException;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.namespace.QNamePattern;
import org.apache.log4j.Logger;

public class MoveReplacedActionExecuter extends ActionExecuterAbstractBase {
	
	public static final String NAME = "move-replaced";
	public static final String PARAM_DESTINATION_FOLDER = "destination-folder";
    private static Logger log = Logger.getLogger(MoveReplacedActionExecuter.class); 
	/**
	 * FileFolder service
	 */
	private FileFolderService fileFolderService;

	
	/**
	 * NodeService service
	 */
	private NodeService nodeService;
	
	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		paramList.add(new ParameterDefinitionImpl(PARAM_DESTINATION_FOLDER,
				DataTypeDefinition.NODE_REF, true,
				getParamDisplayLabel(PARAM_DESTINATION_FOLDER)));
	}

	/**
	 * @see org.alfresco.repo.action.executer.ActionExecuter#execute(org.alfresco.repo.ref.NodeRef,
	 *      org.alfresco.repo.ref.NodeRef)
	 */
	public void executeImpl(Action ruleAction, NodeRef actionedUponNodeRef) {
		//get the REPLACES associations for this node
		log.info("executeImpl Start");
		
		//create the association qname pattern to match against
		QNamePattern qnamePattern = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI, "replaces");
		List<AssociationRef> assocRefs =nodeService.getTargetAssocs(actionedUponNodeRef, qnamePattern);
		
		if (assocRefs.isEmpty()){
			return;
		}else{
			//create the destination node.
			NodeRef destinationParent = (NodeRef) ruleAction
					.getParameterValue(PARAM_DESTINATION_FOLDER);
			for(AssociationRef assocNode : assocRefs){
				//create a new node to do the replace
				
				NodeRef targetNodeRef = assocNode.getTargetRef();
				//if the node exists we need to replace it
				
				if (nodeService.exists(targetNodeRef)){
					try {
						fileFolderService.move(actionedUponNodeRef, destinationParent, null);
					}catch (FileNotFoundException e) {
						log.error("Caught a FileNotFoundException "+e, e);
					}
				}
				
				
			}
		}
		log.info("executeImpl End");
	}
	
	
	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	
	
}
