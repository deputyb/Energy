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

import com.nvenergy.nvenergy.core.services.WeatherAlertConfig;

@Component(immediate = true, metatype = true, label = "NVEnergy Weather Alert Configuration")
@Service(value = WeatherAlertConfig.class)
public class WeatherAlertConfigImpl implements WeatherAlertConfig {

	private static final String DEFAULT_WEATHER_ALERTS_NODE_PATH = "/content/NVEnergy/en/systempages/weatheralert/jcr:content/content/weatheralert";
	private static final Logger logger = LoggerFactory.getLogger(WeatherAlertConfigImpl.class);

	@Property(label = "Default Weather Alerts Page", description = "Provide the path to the page that holds all the weather alert messages", value = DEFAULT_WEATHER_ALERTS_NODE_PATH)
	private static final String WEATHER_ALERTS_NODE_PATH = "weather_alerts_node_path";

	private String weatherAlertsNodePath;

	@Activate
	protected void activate(ComponentContext ctx) {
		@SuppressWarnings("unchecked")
		final Map<String, String> props = (Map<String, String>) ctx.getProperties();
		this.weatherAlertsNodePath = PropertiesUtil.toString(props.get(WEATHER_ALERTS_NODE_PATH), DEFAULT_WEATHER_ALERTS_NODE_PATH);

		logger.info("Weather Alerts Node Path" + weatherAlertsNodePath);
	}

	@Override
	public String getWeatherAlertsNodePath() {
		return weatherAlertsNodePath;
	}

}
