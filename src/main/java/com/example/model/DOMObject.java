package com.example.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DOMObject {
	private String url; 
	private String status = "1"; // 0 = error; 1 = success
	private String errorMessage;
	private String html;
	private String parsedHTML; // html updated for UI usage. 
	private Map<String, Integer> tagsCount;
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}
	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}
	/**
	 * @return the tagsCount
	 */
	public Map<String, Integer> getTagsCount() {
		return tagsCount;
	}
	/**
	 * @param tagsCount the tagsCount to set
	 */
	public void setTagsCount(HashMap<String, Integer> tagsCount) {
		this.tagsCount = tagsCount;
	}
	/**
	 * @return the parsedHTML
	 */
	public String getParsedHTML() {
		return parsedHTML;
	}
	/**
	 * @param parsedHTML the parsedHTML to set
	 */
	public void setParsedHTML(String parsedHTML) {
		this.parsedHTML = parsedHTML;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @param tagsCount the tagsCount to set
	 */
	public void setTagsCount(Map<String, Integer> tagsCount) {
		this.tagsCount = tagsCount;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
