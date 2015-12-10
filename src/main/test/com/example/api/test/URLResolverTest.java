package com.example.api.test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.example.exception.ParserException;
import com.example.model.DOMObject;
import com.example.service.URLReader;
import com.example.threads.Executor;
import com.example.util.ParserHelper;
import com.example.util.ParserHelperJsoup;

/**
 * @author cesagonz
 * Test cases not well implemented... at the moment.. 
 */
public class URLResolverTest{
	
	/**
	 * @param args
	 * Test the complete approach of the "manual" implementation. 
	 * Using manual http reader. Implementing http requester and connection. 
	 * Using manual parsing, Logic for parsing html elements.  
	 */
	@Test
	@SuppressWarnings("all")
	public void testManualParser(){
		try{
			String result = URLReader.getURLContent("http://slackasdadadasdasd.com/");
			System.out.println("back in main thread... ");
			String html = ParserHelper.formatHTML(result);
//			System.out.println("html: " + html);
			DOMObject response = new DOMObject();
			response.setUrl("http://www.google.com");
			response.setHtml(html);
			ParserHelper.prepareParseHTML(response); // pass the object by reference since it will be updated within the method...
			Iterator it = response.getTagsCount().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				System.out.println(pair.getKey() + " = " + pair.getValue());
				it.remove(); // avoids a ConcurrentModificationException
			}
//			System.out.println(response.getParsedHTML());
		}catch(MalformedURLException  urlE){
			System.out.println("Error: Malformed URL. " + urlE.getMessage());
		}catch(ParserException pe){
			System.out.println("Error: " + pe.getMessage());
		}catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * @param args
	 * Test simple html parser.. 
	 */
	@Test
	@SuppressWarnings("all")
	public void testManualPrepate (){
		String html = "<html><body><div class\"myClass\">bla bla bla<img src='blabla.jpg' /></div></body></html>";
		DOMObject response = new DOMObject();
		response.setUrl("url");
		response.setHtml(html);
		ParserHelper.prepareParseHTML(response); // pass the object by reference since it will be updated within the method...
		Iterator it = response.getTagsCount().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
		System.out.println("response: " + response.getParsedHTML());
	}
	
	/**
	 * @param args
	 * Test of the Executur 
	 * Multy thread approach, only test the HTTP Requester 
	 */
	@Test
	@SuppressWarnings("all")
	public void testMultiThreadApproach (){
		List<String> urls = new ArrayList<String>();
		urls.add("http://www.slack.com");
		urls.add("http://www.yahoo.com");
		urls.add("http://www.google.com");
		try{
			Executor.execute(urls.toArray(new String[urls.size()]));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	@SuppressWarnings("all")
	public void testJsoupParser (){
		try{
			String url = "http://slackasdadadasdasd.com/";
			DOMObject domObj = new DOMObject();
			domObj.setUrl(url);
			ParserHelperJsoup.prepareParseHTML(domObj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
