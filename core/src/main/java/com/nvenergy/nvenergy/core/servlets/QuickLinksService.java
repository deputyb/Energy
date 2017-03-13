package com.nvenergy.nvenergy.core.servlets;

import java.io.IOException;
//import java.util.Iterator;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Property;
import com.nvenergy.nvenergy.core.services.QuickLinksConfig;

@SlingServlet(paths = "/bin/quickLinks", methods = "GET")
public class QuickLinksService extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(QuickLinksService.class);
	
	@Reference
	private QuickLinksConfig quickLinksConfig;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
			logger.info("Entered the GET Method");

			ResourceResolver resourceResolver = request.getResourceResolver();
			
			String resourcePath = quickLinksConfig.getQuickLinksPath();

			Resource rootRes = resourceResolver.getResource(resourcePath);

			if (rootRes != null) {
				Node rootNode = rootRes.adaptTo(Node.class);

				Iterator<Node> children = rootNode.getNodes();
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				JSONWriter writer = new JSONWriter(response.getWriter());
				writer.array();

				int i = 0;
				while (children.hasNext()) {
				    
				    
					Node child = children.next();
					writer.object();
					logger.debug("Inside the quicklinks loop");
					
					// writing quicklinks title to JSON
					writer.key("title");
                    logger.debug("title: " + child.getProperty("jcr:title"));
                    Property titleProperty = child.getProperty("jcr:title");
                    if (null != titleProperty)
                        writer.value(titleProperty.getString());
                    else
                        writer.value("");
                    
                    // writing quicklinks value/url to JSON
					writer.key("value");
					logger.debug("value: " + child.getProperty("value"));
                    Property valueProperty = child.getProperty("value");
                    if (null != valueProperty)
                        writer.value(valueProperty.getString());
                    else
                        writer.value("");
					
					// the following may not be needed for quicklinks... leaving in for now
					writer.key("order");
					writer.value(i);
					writer.key("route");
					writer.value(child.getPath());
					writer.key("type");
					writer.value("ContentPageComponent");
					writer.key("params");
					writer.object();
					writer.key("id");
					writer.value(child.getName());
					writer.endObject();
					writer.endObject();
					// node.save();
						i++;
				}
				writer.endArray();
			} else {
				logger.error("Resource is NULL");
			}
			// writer.endObject();
		} catch (Exception e) {
			logger.error("Error:" + e);

		}

	}

}
