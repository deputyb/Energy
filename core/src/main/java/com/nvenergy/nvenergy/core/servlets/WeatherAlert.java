package com.nvenergy.nvenergy.core.servlets;

import java.io.IOException;
//import java.util.Iterator;
import java.util.Iterator;
import java.util.Arrays;
import javax.jcr.Node;
import javax.jcr.Property;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.nvenergy.nvenergy.core.services.WeatherAlertConfig;

@SlingServlet(paths = "/bin/weatherAlert", methods = "GET")
public class WeatherAlert extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(WeatherAlert.class);
	
	@Reference
	private WeatherAlertConfig weatherAlertConfig;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
			logger.info("Entered the GET Method");

			ResourceResolver resourceResolver = request.getResourceResolver();
			
			String resourcePath = weatherAlertConfig.getWeatherAlertsNodePath();

			Resource rootRes = resourceResolver.getResource(resourcePath);

			if (rootRes != null) {
				Node weatherAlertComponentNode = rootRes.adaptTo(Node.class);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				JSONWriter writer = new JSONWriter(response.getWriter());
				writer.object();
				
				Property alertEnabledProperty = weatherAlertComponentNode.getProperty("isAlertEnabled");
				boolean isAlertEnabled = false;
				
				if (null != alertEnabledProperty)
					isAlertEnabled = alertEnabledProperty.getBoolean();
				
				logger.debug("Is alert enabled " + isAlertEnabled);
				
				if (isAlertEnabled)
				{
					logger.debug("Inside the is alert enabled loop");
					writer.key("title");
					logger.debug("title: " + weatherAlertComponentNode.getProperty("alerttitle"));
					Property alertTitleProperty = weatherAlertComponentNode.getProperty("alerttitle");
					if (null != alertTitleProperty)
						writer.value(alertTitleProperty.getString());
					else
						writer.value("");
					writer.key("message");
					Property alertMessageProperty = weatherAlertComponentNode.getProperty("alertmessage");
					
					if (null != alertMessageProperty)
						writer.value(alertMessageProperty.getString());
					else
						writer.value("");
					
					logger.debug("title: " + weatherAlertComponentNode.getProperty("alertmessage"));
				}
				
				writer.endObject();

			}else {
				logger.error("Resource is NULL");
			}
			// writer.endObject();
		} 
		catch (Exception e) {
			logger.error("Error:" + e);
		}
	}

}
