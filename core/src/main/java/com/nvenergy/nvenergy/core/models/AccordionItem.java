package com.nvenergy.nvenergy.core.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccordionItem {
    private static final Logger LOG = LoggerFactory.getLogger(AccordionItem.class);

    private String sectionTitle;

    private String anchorID;

    private Boolean enableWhenOpen;
    
    private String layout;

    public AccordionItem(){
    	
    }
    
    public AccordionItem(String sectionTitle, String anchorID, Boolean enableWhenOpen, String layout) {
        super();
        LOG.info("Begin AccordionItem init");
        this.sectionTitle = sectionTitle;
        this.anchorID = anchorID;
        this.enableWhenOpen=enableWhenOpen;
        this.layout=layout;
        LOG.info("End AccordionItem init");
    }

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public String getAnchorID() {
		return anchorID;
	}

	public void setAnchorID(String anchorID) {
		this.anchorID = anchorID;
	}

	public Boolean getEnableWhenOpen() {
		if(enableWhenOpen==null){
			return false;
		}
		return enableWhenOpen;
	}

	public void setEnableWhenOpen(Boolean enableWhenOpen) {
		this.enableWhenOpen = enableWhenOpen;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	} 
	
	
}
