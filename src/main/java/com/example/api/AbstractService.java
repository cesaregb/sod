package com.example.api;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public abstract class AbstractService {
	
	protected ObjectMapper mapper;
	
	protected AbstractService() {
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
	}
	
	protected Response getEntityAsJSON(Object entity) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, entity);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.ok(writer.toString()).build();
	}
	
	protected <T> T getEntityfromJSON(String json, Class<T> entityClass) {
		try {
			return mapper.readValue(json, entityClass);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected Response restException(Exception e) {
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, e);
		} catch (JsonGenerationException e1) {
			e.printStackTrace();
		} catch (JsonMappingException e1) {
			e.printStackTrace();
		} catch (IOException e1) {
			e.printStackTrace();
		}
		return Response.status(HttpServletResponse.SC_PRECONDITION_FAILED).entity(writer.toString()).build();
	}
}
