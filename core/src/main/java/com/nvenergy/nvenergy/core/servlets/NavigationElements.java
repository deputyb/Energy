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

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.nvenergy.nvenergy.core.services.NavigationConfig;

@SlingServlet(paths = "/bin/navElements", methods = "GET")
public class NavigationElements extends SlingAllMethodsServlet {

	private static final long serialVersionUID = 1L;
	//private static final String RESOURCE_PATH = "/content/NVEnergy/en";

	private static final Logger logger = LoggerFactory.getLogger(NavigationElements.class);
	
	@Reference
	private NavigationConfig navigationConfig;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
			logger.info("Entered the GET Method");

			ResourceResolver resourceResolver = request.getResourceResolver();
			
			String resourcePath = navigationConfig.getNavigationRootPath();

			Resource rootRes = resourceResolver.getResource(resourcePath);

			if (rootRes != null) {
				Page rootPage = rootRes.adaptTo(Page.class);

				Iterator<Page> children = rootPage.listChildren(new PageFilter());
				// int depth = rootPage.getDepth();
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				JSONWriter writer = new JSONWriter(response.getWriter());
				// writer.object();
				// writer.key("Titles Array");
				writer.array();

				int i = 0;
				while (children.hasNext()) {
					Page child = children.next();

					if (!child.isHideInNav()) {
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

						// writer.object();

						// writer.endObject();
						Iterator<Page> childIterator = child.listChildren(new PageFilter());
						if (childIterator.hasNext()) {
							logger.info("Entered the SubMenu Method");

							// writer.object();

							writer.key("Pages");

							writer.array();

							int j = 0;
							while (childIterator.hasNext()) {

								Page childPage = childIterator.next();
								if (!childPage.isHideInNav()) {
									// writer.object();
									// writer.key(childPage.getTitle());
									// writer.value(childPage.getPath());
									// writer.endObject();

									writer.object();
									writer.key("title");
									writer.value(childPage.getTitle());
									writer.key("order");
									writer.value(j);
									writer.key("route");
									writer.value(childPage.getPath());
									writer.key("type");
									writer.value("ContentPageComponent");
									writer.key("params");
									writer.object();
									writer.key("id");
									writer.value(childPage.getName());
									writer.endObject();
									writer.endObject();

									j++;
								}

							}

							writer.endArray();
							// writer.endObject();

						}

						writer.endObject();
						// node.save();
						i++;
					}
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
