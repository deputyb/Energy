package com.nvenergy.nvenergy.core.models;

public class LinkUtil {

	public static final String CONSTANT_EXTENSION_HTML = ".html";

	private LinkUtil() {
		// prevent instantiation
	}

	/**
	 * getPathfieldURL
	 *
	 * Takes the value from pathfield and performs logic s.t. it will always be
	 * a valid URL.
	 *
	 * @param path
	 *            - Input value from pathfield
	 * @return pageURL - the modified path
	 */
	public static String getPathfieldURL(String path) {
		StringBuffer pageURL = new StringBuffer(path);
		if (pageURL.toString().startsWith("/content") ) {
			final int index = pageURL.lastIndexOf(".");
			if(index < 0) {
				pageURL.append(".html");
			}
		} else if (!"".equalsIgnoreCase(pageURL.toString())
				&& !(pageURL.toString().startsWith("http://") || pageURL.toString().startsWith("https://"))) {

			if (pageURL.toString().startsWith("//")) {
				pageURL.replace(0, 2, "");
			} else if (pageURL.toString().startsWith("/")) {
				pageURL.insert(0, "http:/");
			} else if (!"#".equals(pageURL.toString())) {
				pageURL.insert(0, "http://");
			}
		}
		return pageURL.toString();
	}
//	public static String getRelativeURL(SlingHttpServletRequest request, String path) {
//		String url = "";
//		if (path != null && path.startsWith("/content")) {
//
//			if (GenericUtil.isAuthorMode()) {
//				url = getPathfieldURL(path);
//			} else {
//				url = request.getResourceResolver().map(request, path);
//				int index = url.lastIndexOf(".");
//				if(index < 0) {
//					url = url + CONSTANT_EXTENSION_HTML;
//				}
//				
//			}
//		}
//		return url;
//	}		
//	public static String getAbsoluteURL(SlingHttpServletRequest request, String path, Page page) {
//
//		if (path != null && path.startsWith("/content")) {
//			String url = "";
//			if (GenericUtil.isAuthorMode()) {
//				url = getPathfieldURL(path);
//			} else {
//				url = request.getResourceResolver().map(request, path);
//				Externalizer externalizer = request.getResourceResolver().adaptTo(Externalizer.class);
//				String language = WCMUseUtil.getCurrentLanguage(page);
//
//				if (StringUtils.isNotEmpty(language) && language.equalsIgnoreCase(GlobalConstants.LOCALE.en.name())) {
//					url = externalizer.externalLink(request.getResourceResolver(), "english", url);
//				} else if (StringUtils.isNotEmpty(language)
//						&& language.equalsIgnoreCase(GlobalConstants.LOCALE.es.name())) {
//					url = externalizer.externalLink(request.getResourceResolver(), "spanish", url);
//				} else {
//					url = externalizer.publishLink(request.getResourceResolver(), "http", url);
//				}
//			}
//			return url;
//		} else {
//			return getPathfieldURL(path);
//		}
//	}
}