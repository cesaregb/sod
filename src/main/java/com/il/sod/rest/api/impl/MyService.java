package com.il.sod.rest.api.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.il.sod.dtos.MyDTO;
import com.il.sod.rest.api.AbstractServices;

@RestController
public class MyService extends AbstractServices{

	@RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<MyDTO> greeting() {
		return new ResponseEntity<MyDTO>(new MyDTO("algo", 2), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/a", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> a() {
		return new ResponseEntity<String>("a", HttpStatus.OK);
	}
}
