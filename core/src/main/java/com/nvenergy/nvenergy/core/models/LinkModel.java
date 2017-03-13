package com.nvenergy.nvenergy.core.models;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUsePojo;


public class LinkModel extends WCMUsePojo{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LinkModel.class);
	
	private static final String DEFAULT_TARGET_URL ="#";

    private String linktxt;
    
    private String linkurl;
     
    private String openWindow;
    
	Node currentNode;
	
	ResourceResolver resourceResolver;
    
    @Override
	public void activate() throws Exception {
    	LOGGER.info("inside Link Model activate method");
		currentNode = getResource().adaptTo(Node.class);
		resourceResolver = getResourceResolver();
		linktxt = get("linktxt", String.class);
		linkurl = get("linkurl", String.class);
		openWindow = get("openWindow", String.class);	
    	LOGGER.info("Open Window"+openWindow);

		
	}
    
    /**
	 * @return
	 */
	public String getTargetUrl() {
		String targetUrl = DEFAULT_TARGET_URL;
		try {
			if(currentNode.hasProperty(linkurl) &&
					resourceResolver.getResource(linkurl) != null){

				targetUrl = LinkUtil.getPathfieldURL(currentNode.getProperty(linkurl).getString());

			}
		} catch (RepositoryException e) {
			LOGGER.error("Repository exception",e);
		}

		return targetUrl;
	}

	/**
	 * @return
	 */
	public String getTarget() {
		String target = "_self";
		try {
			if(currentNode.hasProperty(openWindow)){
				target = "_blank";
			}
		} catch (RepositoryException e) {
			LOGGER.error("Repository exception",e);
		}

		return target;
	}


	public String getLinktxt() {
		return linktxt;
	}

	public void setLinktxt(String linktxt) {
		this.linktxt = linktxt;
	}

	public String getLinkurl() {
		return linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	public String getOpenWindow() {
		return openWindow;
	}

	public void setOpenWindow(String openWindow) {
		this.openWindow = openWindow;
	}

	
    
    


}
