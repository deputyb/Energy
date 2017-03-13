package com.nvenergy.nvenergy.core.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextItem {

	private static final Logger LOG = LoggerFactory.getLogger(TextItem.class);

	private String col1;
	private String col2;

	public TextItem() {

	}

	public TextItem(String col1, String col2) {
		super();
		LOG.info("Begin Text Item");
		this.col1 = col1;
		this.col2 = col2;
		LOG.info("End Text Item");

	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

}
