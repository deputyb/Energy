package com.nvenergy.nvenergy.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import com.google.gson.Gson;

@Model(adaptables = SlingHttpServletRequest.class)

public class ColumnTextField extends BaseComponent {

	@Inject
	@Via("resource")
	@Default(values = "")
	private String[] textItems;


	private List<TextItem> textItemList;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		try {
			textItemList = new ArrayList<TextItem>();
			for (int i = 0; i < textItems.length; i++) {
				String textItem = textItems[i];
				Gson gson = new Gson();
				TextItem item = gson.fromJson(textItem, TextItem.class);
				LOG.info("Item Value"+item.getCol1());
				textItemList.add(item);
			}
		} catch (Exception e) {
			LOG.info("ColumnTextField parsys exception: " + e.getMessage());

		}

	}

	public String[] getTextItems() {
		return textItems;
	}

	public void setTextItems(String[] textItems) {
		this.textItems = textItems;
	}

	public List<TextItem> getTextItemList() {
		return textItemList;
	}

	public void setTextItemList(List<TextItem> textItemList) {
		this.textItemList = textItemList;
	}
	
	

}
