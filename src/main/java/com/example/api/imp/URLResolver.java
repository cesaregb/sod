package com.example.api.imp;

import java.net.MalformedURLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.api.AbstractService;
import com.example.exception.ParserException;
import com.example.model.DOMObject;
import com.example.service.URLReader;
import com.example.util.ParserHelper;
import com.example.util.ParserHelperJsoup;

@Path("/url_resolver")
public class URLResolver extends AbstractService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	// consumed by /url_resolver?url=www.mysite.com
	public Response parseHTML(@QueryParam("url") String url){
		DOMObject response = new DOMObject();
		try{
			String result = URLReader.getURLContent(url);
			String html = ParserHelper.formatHTML(result);
			response.setUrl(url);
			response.setHtml(html);
			ParserHelper.prepareParseHTML(response); // pass the object by reference since it will be updated within the method...
		}catch(MalformedURLException  urlE){
			response.setStatus("0");
			response.setErrorMessage("Error: Malformed URL. " + urlE.getMessage());
		}catch(ParserException pe){
			response.setStatus("0");
			response.setErrorMessage("Error: " + pe.getMessage());
		}catch(Exception e){
			response.setStatus("0");
			response.setErrorMessage("Something went wrong with the parser... ");
		}
		return getEntityAsJSON(response);
	}
	
	@GET
	@Path("/jsoup")
	@Produces(MediaType.APPLICATION_JSON)
	// consumed by /url_resolver/jsoup?url=www.mysite.com
	public Response parseJsoupHTML(@QueryParam("url") String url){
		DOMObject response = new DOMObject();
		try{
			response.setUrl(url);
			ParserHelperJsoup.prepareParseHTML(response);
		}catch(MalformedURLException  urlE){
			response.setStatus("0");
			response.setErrorMessage("Error: Malformed URL. " + urlE.getMessage());
		}catch(ParserException pe){
			response.setStatus("0");
			response.setErrorMessage("Error: " + pe.getMessage());
		}catch(Exception e){
			response.setStatus("0");
			response.setErrorMessage("Something went wrong with the parser... ");
		}
		return getEntityAsJSON(response);
	}
}

