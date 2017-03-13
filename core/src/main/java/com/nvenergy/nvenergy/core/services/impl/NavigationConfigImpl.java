package com.nvenergy.nvenergy.core.services.impl;

import java.util.Map;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nvenergy.nvenergy.core.services.NavigationConfig;

@Component(immediate = true, metatype = true, label = "NVEnergy Navigation Configuration")
@Service(value = NavigationConfig.class)
public class NavigationConfigImpl implements NavigationConfig {

	private static final String DEFAULT_NAV_ROOT_PATH = "/content/NVEnergy/en";
	private static final Logger logger = LoggerFactory.getLogger(NavigationConfigImpl.class);

	@Property(label = "Root Path", description = "Provide the root path from where navigation elements have to be returned", value = DEFAULT_NAV_ROOT_PATH)
	private static final String NAV_ROOT_PATH = "nav_root_path";

	private String navigationRootPath;

	@Activate
	protected void activate(ComponentContext ctx) {
		@SuppressWarnings("unchecked")
		final Map<String, String> props = (Map<String, String>) ctx.getProperties();
		this.navigationRootPath = PropertiesUtil.toString(props.get(NAV_ROOT_PATH), DEFAULT_NAV_ROOT_PATH);

		logger.debug("Navigation Root Path" + navigationRootPath);

	}

	@Override
	public String getNavigationRootPath() {
		return navigationRootPath;
	}

}
