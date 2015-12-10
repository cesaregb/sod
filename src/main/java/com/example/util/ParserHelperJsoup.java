package com.example.util;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.model.DOMObject;
import com.example.service.URLReader;

public class ParserHelperJsoup {
	
	HashMap<String, Integer> tagsCount = new HashMap<String, Integer>();
	
	public static void prepareParseHTML(DOMObject domObj) throws Exception{
		Document doc = null;
		boolean proxy = true;
		if (!proxy){
			doc = Jsoup.connect(domObj.getUrl()).get();
		}else{
			System.setProperty("http.proxyHost", "www-proxy.us.oracle.com"); //setting used by josup Jsoup.connect(domObj.getUrl()).get(); 
			System.setProperty("http.proxyPort", "80"); //setting used by josup Jsoup.connect(domObj.getUrl()).get(); 
			String result = URLReader.getURLContent(domObj.getUrl());
			doc = Jsoup.parse(result);
		}
		domObj.setHtml(doc.html());
		ParserHelperJsoup parser = new ParserHelperJsoup();
		parser.tagsCount = new HashMap<String, Integer>();
		domObj.setTagsCount(parser.tagsCount);
		domObj.setParsedHTML(parser.htmlTags(doc).toString());
	}
	
	private String htmlTags(Document doc) {
        StringBuilder sb = new StringBuilder();
        htmlTags(doc.children(), sb);
        return sb.toString();
    }
	
	private void htmlTags(Elements elements, StringBuilder sb) {
		for(Element el : elements) {
            if(sb.length() > 0){
                sb.append("\n");
            }
            //adding item to hash map. 
            String tag = el.tagName();
            int tot = (this.tagsCount.get(tag)!=null)?(this.tagsCount.get(tag) + 1):1;
			this.tagsCount.put(tag, tot);
			
			String elementClass = "class='format_" + tag + "'";
			String attributes = "";
			for (Attribute attribute : el.attributes()) {
				attributes += " " + attribute.getKey() + "='"+attribute.getValue()+"'";
			}
			String constructedTag = "<" + tag + attributes + ">";
			sb.append("<span "+elementClass+">").append(escapeHtml4(constructedTag)).append("</span>").append(el.ownText());
            htmlTags(el.children(), sb);
            if (!el.tag().isSelfClosing()){
            	String closingTag = "</" + el.nodeName()+">";
            	sb.append("<span "+elementClass+">").append(escapeHtml4(closingTag)).append("</span>");
            }
        }
    }
}

