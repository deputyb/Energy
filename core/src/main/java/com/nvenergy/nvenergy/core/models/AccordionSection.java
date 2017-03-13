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
public class AccordionSection extends BaseComponent {

	@Inject
	@Via("resource")
	@Default(values = "")
	private String[] accordionItems;
	@Inject
	@Via("resource")
	@Default(values = "")
	private String[] southaccordionItems;
	@Inject
	@Via("resource")
	@Default(values = "")
	private String[] northaccordionItems;
	@Inject
	@Via("resource")
	@Default(values = "")
	private String title;
	
    private String accordingId;

	
    private List<AccordionItem> listItems;
    private List<AccordionItem> southListItems;
    private List<AccordionItem> northListItems;
    
    @Override public void init() {
    	LOG.info("AccordionSection Component Model init Begin");
        try {
        	listItems = new ArrayList<AccordionItem>(); 
        	southListItems =  new ArrayList<AccordionItem>();
        	northListItems = new ArrayList<AccordionItem>();
	        buildAccordionItems(listItems,accordionItems);
	        buildAccordionItems(southListItems,southaccordionItems);
	        buildAccordionItems(northListItems,northaccordionItems);
	        
        } catch (Exception e) {
			LOG.info("AccordionItem parsys exception: " + e.getMessage());
		}
        LOG.info("AccordionSection Component Model init End");
    }

	private List<AccordionItem> buildAccordionItems(List<AccordionItem> pListItems, String[] pAccordionItems) {
		for(int i=0; i<pAccordionItems.length; i++){
			String accordionItem = pAccordionItems[i];
			Gson gson = new Gson();	            
			AccordionItem item = gson.fromJson(accordionItem, AccordionItem.class);
			pListItems.add(item);
			LOG.info(item.getSectionTitle());  	
		}
		return pListItems;
	}

//    public String getAnchorStr(int currentNum) {
//    	String anchor = "";
//		try {
//			String path = currentNode.getPath();
//	        if (StringUtils.isNotBlank(path)) {
//	            String[] pathStrs = path.split("");
//	            List<Integer> iss = Arrays.asList(pathStrs).stream().map(a -> a.codePointAt(0))
//	                    .collect(Collectors.toList());
//	            int count = iss.stream()
//	                    .reduce(0, (acc, element) -> (((acc << 5) - acc + element) & ((acc << 5) - acc + element)));
//	            count += currentNum;
//	            return anchor + count;
//	        }
//		} catch (RepositoryException e) {
//			 LOG.error("Parse currentNode has a error.", e);
//		}
//        return anchor + currentNum;
//    }
    
//    public static String getAnchorStr(int currentNum, String path) {
//		String anchor = "";
//		if (StringUtils.isNotBlank(path)) {
//			String[] pathStrs = path.split("");
//			List<Integer> iss = Arrays.asList(pathStrs).stream().map(a -> a.codePointAt(0))
//					.collect(Collectors.toList());
//			int count = iss.stream().reduce(0,
//					(acc, element) -> (((acc << 5) - acc + element) & ((acc << 5) - acc + element)));
//			count += currentNum;
//			return anchor + count;
//		}
//		return anchor + currentNum;
//	}

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<AccordionItem> getListItems() {
        return listItems;
    }

    public void setListItems(List<AccordionItem> listItems) {
        this.listItems = listItems;
    }

	public String getAccordingId() {
		return accordingId;
	}

	public void setAccordingId(String accordingId) {
		this.accordingId = accordingId;
	}

	public List<AccordionItem> getSouthListItems() {
		return southListItems;
	}

	public void setSouthListItems(List<AccordionItem> southListItems) {
		this.southListItems = southListItems;
	}

	public List<AccordionItem> getNorthListItems() {
		return northListItems;
	}

	public void setNorthListItems(List<AccordionItem> northListItems) {
		this.northListItems = northListItems;
	}
	
    
}
