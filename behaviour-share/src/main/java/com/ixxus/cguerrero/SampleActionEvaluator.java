package com.ixxus.cguerrero;

import org.alfresco.web.evaluator.BaseEvaluator;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

public class SampleActionEvaluator extends BaseEvaluator {

    private static final Logger LOG = Logger.getLogger(SampleActionEvaluator.class);
    
    @Override
    public boolean evaluate(JSONObject jsonObject) {
        LOG.info("Evaluator called, just returning true ...");
        return true;
    }
}
