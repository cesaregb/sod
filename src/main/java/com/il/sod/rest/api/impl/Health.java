package com.il.sod.rest.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.il.sod.exceptions.SODException;
import com.il.sod.rest.api.AbstractServices;
import com.il.sod.vo.MyDTO;

// RestController extending the /api prefiex 
@RestController
public class Health extends AbstractServices{
	private final Logger LOGGER = LoggerFactory.getLogger(Health.class);
	
	@RequestMapping(value = "/aaaaa", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<MyDTO> greetings() {
		return new ResponseEntity<MyDTO>(new MyDTO("algo", 2), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/error", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> a() throws Exception{
		throw new SODException("This is an error");
	}
	
	@RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<MyDTO> greeting() {
		LOGGER.info("My log from within the service");
		return new ResponseEntity<MyDTO>(new MyDTO("algo", 2), HttpStatus.OK);
    }
}
