package com.example.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map.Entry;

import com.example.exception.ParserException;

/*
 * reader based on oracle URLReader 
 */ 

public class URLReader {
	/*
	 * A valid and existing solution to perform lot of what its required its using the JSOUP library. 
	 * Getting the content and parsing it into xml (htlm) format could be achived with: 
	 	* Document doc = Jsoup.connect("http://example.com/").get(); 
	 	* String title = doc.title();
	 * Im trying to get it by hand.. 
	 * */
	@SuppressWarnings("all")
	public static String getURLContent(String url) throws Exception {
		// This method doesnt require lot of logic because its only going to be
		// performing GET operations
		
		System.out.println("in: getURLContent");
		StringBuilder sb = new StringBuilder();
		URL urlObjt = new URL(url);
		// add me for internal proxy 
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("www-proxy.us.oracle.com", 80));
		HttpURLConnection connection = (HttpURLConnection) urlObjt.openConnection(); // add proxy if required
		System.out.println("HTTP connection opened ");
		try {
			//redirect snipped 
			int status = connection.getResponseCode();
			if (status != HttpURLConnection.HTTP_OK) {
				if (status == HttpURLConnection.HTTP_MOVED_TEMP
					|| status == HttpURLConnection.HTTP_MOVED_PERM
						|| status == HttpURLConnection.HTTP_SEE_OTHER){
					
					String newUrl = connection.getHeaderField("Location");
					connection = (HttpURLConnection) new URL(newUrl).openConnection(); // add proxy if required 
					System.out.println("Request bein redirect... to: " + newUrl);
				}
			}
			
			String contentType = connection.getContentType();
			System.out.println("HTTP contentType: " + contentType);
			if (!contentType.toLowerCase().contains("text/html") && !contentType.toLowerCase().contains("text/plain")){
				throw new ParserException(ParserException.REQUEST_ERROR, "Content type not supported by parser '" + contentType + "'");
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			// Header information
			System.out.println("getting headers... ");
			for (Entry<String, List<String>> header : connection
					.getHeaderFields().entrySet()) {
				System.out.println(header.getKey() + "=" + header.getValue());
			}
			
			System.out.println("Writing the http response");
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine).append("\n"); // appending line...
			}
			in.close();
		} catch (UnknownHostException uknowHostE) {
			throw new ParserException(ParserException.REQUEST_ERROR, "Uknown Host: " + url);
			
		} catch (IOException exception) {
			InputStream error = connection.getErrorStream();
			int code = connection.getResponseCode();
			if (code > 400 && code < 500){
				throw new ParserException(ParserException.REQUEST_ERROR, "Client Request Error, response code: " + code);
			}else if (code > 500){
				throw new ParserException(ParserException.REQUEST_ERROR, "Server Error, response code: " + code);
			}else{
				if (error != null){
					BufferedReader in = new BufferedReader(new InputStreamReader(error));
					StringBuilder errorSB = new StringBuilder();
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						errorSB.append(inputLine).append("\n"); // appending line...
					}
					throw new ParserException(ParserException.REQUEST_ERROR, errorSB.toString());
				}else{
					throw new ParserException(ParserException.REQUEST_ERROR, exception);
				}
			}
			
		} catch (ParserException pe) {
			throw pe;
		} catch (Exception e) {
			throw new ParserException(ParserException.REQUEST_ERROR, e);
		}
		System.out.println("returning sb");
		return sb.toString();
	}

}