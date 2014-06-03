package com.ixxus.cguerrero.bh.model;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

public class SampleWebScript extends AbstractWebScript {
    private static final Logger LOG = Logger.getLogger(SampleWebScript.class);

    public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
 
        LOG.info("Sample action web script called ...");
        
    }
    
}