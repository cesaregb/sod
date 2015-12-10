package com.example.util;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.exception.ParserException;
import com.example.model.DOMObject;

public class ParserHelper {
	/*
	 * We format the output for the UI, we need to encode the existing html and add some "on-top" tags for better display
	 * We could use the String String.replaceAll method that has a O(n) performance (I think) only iterates thru the string once. 
	 * But I wil do it with a StringBuilder and manually format the output to avoid the heavy String operations.  
	 * in case of we require validation for closing this is the void elements 
	 * 		area, base, br, col, embed, hr, img, input, keygen, link, menuitem, meta, param, source, track, wbr
	 * 
	 **/
	public static void prepareParseHTML(DOMObject domObj) {
		StringBuilder result = new StringBuilder();
		String html = domObj.getHtml();
		HashMap<String, Integer> tagsCount = new HashMap<String, Integer>();
		for (int i = 0; i < html.length(); i++) {
			if (html.charAt(i) == '<') {
				try{
					int clossingPos = html.indexOf('>', i); 
					String possibleTag = html.substring(i, clossingPos + 1);
					DomHelper helper = transformTag(possibleTag);
					i = clossingPos;
					result.append(helper.result); // should we add a break line? 
					if (!helper.tag.equals("NOT-TAG")){
						int tot = (tagsCount.get(helper.tag)!=null)?(tagsCount.get(helper.tag) + 1):1;
						tagsCount.put(helper.tag, tot);
					}else{
						
					}
				}catch(ParserException pe){
					// here we can check what to do we the validation error, at this moment I only going to add encode the char
					result.append(escapeHtml4(html.substring(i, i + 1)));
				}
			}else{
				result.append(html.charAt(i));
			}
		}
		domObj.setTagsCount(tagsCount);
		domObj.setParsedHTML(result.toString());
	}
	
	/**
	 * Helper method to get the next html tag. 
	 * This may still have issues with some use cases. 
	 * @param tagBegin string of concern. 
	 * @param i init index 
	 * @return DomHelper custom object functioning as callback for different items. 
	 * @throws ParserException
	 */
	private static DomHelper transformTag(String tag) throws ParserException{
		StringBuilder sb = new StringBuilder();
		int scriptPos = tag.toLowerCase().indexOf("</script>");
		if (scriptPos > 0){ 
			// if is an javascript code, we append whatever is in between and parse the script..
			String helper = tag.substring(0, scriptPos);
			sb.append(escapeHtml4(helper));
			tag = tag.substring(scriptPos, tag.length()); // set the closing script as the tag to continue with normall flow.. 
		}
		int a = (tag.indexOf(" ") > 0)?tag.indexOf(" "):Integer.MAX_VALUE;
		int b = (tag.indexOf(">") > 0)?tag.indexOf(">"):Integer.MAX_VALUE;
		int c = (tag.indexOf("<") > 0)?(tag.indexOf("<") + 1):Integer.MAX_VALUE;
		int endTagText = Math.min(a, b);
		
		if ((b == Integer.MAX_VALUE) || (b > c) || (endTagText == Integer.MAX_VALUE)){
			throw new ParserException(ParserException.TAG_ERROR, "Tag not closed. " + tag); 
		}
		//validate if is within the script tag
		
		boolean isClosing = false; 
		String tagName = tag.substring(1, endTagText);
		if (tagName.contains("/")){
			isClosing = true;
			tagName = tagName.substring(1);
		}
		
		Pattern p = Pattern.compile("^[a-z]\\w*$"); //pater to validate if the tag is a valid Tag. Catch any script declaration
		Matcher m = p.matcher(tagName);
		boolean invalid = !m.find();
		invalid = invalid || tagName.equals(" ");

		DomHelper helper = null;
		if (!invalid){
			sb.append("<span class='format_"+ tagName +"'>").append(escapeHtml4(tag)).append("</span>");
		}else{
			sb.append(escapeHtml4(tag));
		}
		if (isClosing || invalid){ 
			// Avoid adding clossing elements to the "COUNT", we can do it in different ways this dont hit the performance and is configurable.   
			helper = new DomHelper(sb.toString(), "NOT-TAG");  
		}else{
			helper = new DomHelper(sb.toString(), tagName);
		}
		return helper;
	}
	
	/**
	 * This method uses jsoup library to generate a nicer output. of the html
	 * @param html
	 * @return
	 * @throws Exception
	 */
	public static String formatHTML(String html) throws Exception{
//		Document doc = Jsoup.parse(html, "", Parser.xmlParser());
		Document doc = Jsoup.parse(html);
		return doc.toString();
	}
}

class DomHelper{
	String result; 
	String tag; 
	DomHelper(String result, String tag){
		this.result = result; 
		this.tag = tag;
	}
}