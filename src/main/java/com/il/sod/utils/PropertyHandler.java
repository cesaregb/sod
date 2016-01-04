package com.il.sod.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.il.sod.exceptions.SODException;

public class PropertyHandler {
	final static Logger LOGGER = LoggerFactory.getLogger(PropertyHandler.class);
	private static PropertyHandler instance = null;
	private static final String FILE_NAME = "registration_api.properties";
	private static final String FILE_CONFIG_NAME = "project-config.properties";

	private Properties props = null;

	private PropertyHandler() throws Exception {
		try {
			this.props = new Properties();
			this.props.putAll(readFile(FILE_NAME));
			this.props.putAll(readFile(FILE_CONFIG_NAME));
		} catch (IOException e) {
			LOGGER.error("Error reading properties", e);
			throw new SODException("Error reading properties", e);
		}
	}
	
	private static Properties readFile(String file) throws IOException{
		Properties p = new Properties();
		InputStream inputStream = PropertyHandler.class.getClassLoader().getResourceAsStream(file);
		p.load(inputStream);
		inputStream.close();
		inputStream = null;
		return p;
	}
	public static synchronized PropertyHandler getInstance(){
		if (instance == null){
			try{
				instance = new PropertyHandler();
			}catch(Exception e){
				LOGGER.error("Error reading properties", e);
			}
		}
		return instance;
	}

	public String getValue(String propKey) {
		return this.props.getProperty(propKey);
	}
}
