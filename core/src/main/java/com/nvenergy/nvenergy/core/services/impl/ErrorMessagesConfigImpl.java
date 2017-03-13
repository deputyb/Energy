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

import com.nvenergy.nvenergy.core.services.ErrorMessagesConfig;

@Component(immediate = true, metatype = true, label = "NVEnergy Error Messages Configuration")
@Service(value = ErrorMessagesConfig.class)
public class ErrorMessagesConfigImpl implements ErrorMessagesConfig {

	private static final String DEFAULT_ERROR_MESSAGES_NODE_PATH = "/etc/acs-commons/lists/errormessages/jcr:content/list";
	private static final Logger logger = LoggerFactory.getLogger(ErrorMessagesConfigImpl.class);

	@Property(label = "Default Error Messages Node Path", description = "Provide the path to the list node that holds all the error messages as items", value = DEFAULT_ERROR_MESSAGES_NODE_PATH)
	private static final String ERROR_MESSAGES_NODE_PATH = "error_messages_node_path";

	private String errorMessagesNodePath;

	@Activate
	protected void activate(ComponentContext ctx) {
		@SuppressWarnings("unchecked")
		final Map<String, String> props = (Map<String, String>) ctx.getProperties();
		this.errorMessagesNodePath = PropertiesUtil.toString(props.get(ERROR_MESSAGES_NODE_PATH), DEFAULT_ERROR_MESSAGES_NODE_PATH);

		logger.info("Error Messages Node Path" + errorMessagesNodePath);
	}

	@Override
	public String getErrorMessagesNodePath() {
		return errorMessagesNodePath;
	}

}
