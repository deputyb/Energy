package com.nvenergy.nvenergy.core.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.io.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;

@SlingServlet(paths = "/bin/sitePages", methods = "GET")
public class SitePages extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	private static final String RESOURCE_PATH = "/content/NVEnergy/en";

	private static final Logger logger = LoggerFactory.getLogger(NavigationElements.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
			logger.info("Entered the GET Method");

			ResourceResolver resourceResolver = request.getResourceResolver();

			Resource rootRes = resourceResolver.getResource(RESOURCE_PATH);

			Page rootPage = rootRes.adaptTo(Page.class);
			// Page rootPage = rootRes == null ? null :
			// rootRes.adaptTo(Page.class);
			int depthCheck = 100;
			boolean check = false;
			Iterator<Page> children = rootPage.listChildren(new PageFilter(), true);
			//int depth = rootPage.getDepth();
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			JSONWriter writer = new JSONWriter(response.getWriter());
			// writer.object();
			// writer.key("Titles Array");
			writer.array();

			int i = 0;
			while (children.hasNext()) {
				Page child = children.next();
				int depth = child.getDepth();
				if((depthCheck > depth) && i!=0){
					//writer.endObject();
					writer.endArray();
					writer.endObject();
					i=0;
					check = true;
				}
				writer.object();
				writer.key("title");
				writer.value(child.getTitle());
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
				
				Iterator<Page> childIterator = child.listChildren(new PageFilter(), true);
				
				if (childIterator.hasNext()) {
					logger.info("Entered the SubMenu Method");
					// writer.object();
					writer.key("Pages");
					writer.array();
					depthCheck =  depth+1;
					}
				else if(depthCheck == depth){
					writer.endObject();
				}
				else if(depthCheck > depth){
					writer.endObject();
					writer.endArray();
					writer.endObject();
					check = true;
				}
//				else if(i>0){					
//					writer.endObject();
//					writer.endArray();			
//				}
//				else{
//					writer.endArray();
//				}
				//writer.endObject();
				i++;
			}
			
			if(!check){
				writer.endArray();
				writer.endObject();
			}
			writer.endArray();	

			// writer.endObject();
		} catch (Exception e) {
			logger.error("Error:" + e);
		}

	}

}
 
