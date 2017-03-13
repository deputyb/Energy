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

import com.nvenergy.nvenergy.core.services.QuickLinksConfig;

@Component(immediate = true, metatype = true, label = "NVEnergy QuickLinks Configuration")
@Service(value = QuickLinksConfig.class)
public class QuickLinksConfigImpl implements QuickLinksConfig {

	private static final String DEFAULT_QUICKLINKS_PATH = "/etc/acs-commons/lists/quicklinks/jcr:content/list";
	private static final Logger logger = LoggerFactory.getLogger(QuickLinksConfigImpl.class);

	@Property(label = "QuickLinks Path", description = "Provide the path where the QuickLinks list elements can be returned", value = DEFAULT_QUICKLINKS_PATH)
	private static final String QUICKLINKS_PATH = "quicklinks_path";

	private String quicklinksPath;

	@Activate
	protected void activate(ComponentContext ctx) {
		@SuppressWarnings("unchecked")
		final Map<String, String> props = (Map<String, String>) ctx.getProperties();
		this.quicklinksPath = PropertiesUtil.toString(props.get(QUICKLINKS_PATH), DEFAULT_QUICKLINKS_PATH);

		logger.debug("QuickLinks Path: " + quicklinksPath);

	}

	@Override
	public String getQuickLinksPath() {
		return quicklinksPath;
	}

}
