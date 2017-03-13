package com.nvenergy.nvenergy.core.servlets;

import java.io.IOException;
//import java.util.Iterator;
import java.util.Iterator;
import java.util.Arrays;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.nvenergy.nvenergy.core.services.ErrorMessagesConfig;

@SlingServlet(paths = "/bin/errorMessage", methods = "GET")
public class ErrorMessage extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(ErrorMessage.class);
	
	@Reference
	private ErrorMessagesConfig errorMessagesConfig;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
			logger.info("Entered the GET Method");

			ResourceResolver resourceResolver = request.getResourceResolver();
			
			String resourcePath = errorMessagesConfig.getErrorMessagesNodePath();

			Resource rootRes = resourceResolver.getResource(resourcePath);

			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			JSONWriter writer = new JSONWriter(response.getWriter());
			writer.object();
			
			
			RequestParameter errorCodeParameter = request.getRequestParameter("errorcode");
			String errorCodeFromRequest = null;
			
			
			if (null != errorCodeParameter)
				errorCodeFromRequest = errorCodeParameter.getString();
			
			writer.key("errorcode");
			writer.value(errorCodeFromRequest);
			
			
			if (rootRes != null && errorCodeFromRequest != null && !errorCodeFromRequest.isEmpty()) 
			{
				Node errorMessagesListNode = rootRes.adaptTo(Node.class);
				
				//Get the child item nodes. Each item is an error message
				NodeIterator errorMessagesListIterator = errorMessagesListNode.getNodes();
				
				
			    while (errorMessagesListIterator.hasNext()) 
			    {
			    	Node currentNode = errorMessagesListIterator.nextNode();
			    	try
			    	{
			    		Property errorCodeProperty = currentNode.getProperty("jcr:title");
			    		String errorCode = "";
				    	Property errorMessageProperty = currentNode.getProperty("value");
				    	String errorMessage = "";
				    	
						if (null == errorCodeProperty || errorCodeProperty.getString().isEmpty())
							continue;
						
						errorCode = errorCodeProperty.getString();
						
						if (null == errorMessageProperty || errorMessageProperty.getString().isEmpty())
							errorMessage = "";
						else
							errorMessage = errorMessageProperty.getString();
						
						errorCode = errorCodeProperty.getString();
						
						logger.debug("Error Code 	= " + errorCode);
						logger.debug("Error Message 	= " + errorMessage);
				    	
						
				    	if (errorCode.equalsIgnoreCase(errorCodeFromRequest))
				    	{
			        		writer.key("errormessage");
		        			writer.value(errorMessage);
		        			break;
				        }
			    	}
			    	catch(PathNotFoundException e)
			    	{
			    		logger.debug("Path not found " + e.getMessage());
			    	}
			    	
			     }
			}
			else {
				logger.error("Resource is NULL");
			}
			writer.endObject();
		} 
		catch (Exception e) {
			logger.error("Error:" + e);
		}
	}

}
