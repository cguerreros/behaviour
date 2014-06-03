package com.ixxus.cguerrero.bh.model;

import org.alfresco.service.namespace.QName;

/**
 * 
 * TODO: Change the name to something other than SampleContentModel
 *
 */
public class SampleContentModel {
    public final static String NAMESPACE_URI = "http://www.ixxus.co.uk/model/behaviour/1.0";
    public final static String NAMESPACE_PREFIX = "bh";

    public static final class SampleAspect {
        public static final QName ASPECT = bh("sampleAspect");

        private SampleAspect() {
        }

        public static final class Prop {
            private Prop() {
            }
            
            public static final QName SAMPLE_ASPECT_PROPERTY = bh("sampleAspectProperty");
        }
    }

    public static final class SampleType {
    	public static final QName TYPE = bh("sampleType");

    	private SampleType() {
    	}

    	public static final class Prop {
            private Prop() {
            }
            
            public static final QName SAMPLE_TYPE_PROPERTY = bh("sampleTypeProperty");
        }
    }
    
    public static QName bh(final String qname) {
        return QName.createQName(NAMESPACE_URI, qname);
    }
}