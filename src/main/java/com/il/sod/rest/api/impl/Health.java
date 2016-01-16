package com.il.sod.rest.api.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.il.sod.dao.ShopServiceDAO;
import com.il.sod.exceptions.SODException;
import com.il.sod.model.entities.Shop;
import com.il.sod.rest.api.AbstractServices;
import com.il.sod.services.MyService;
import com.il.sod.vo.MyDTO;

// RestController extending the /api prefiex 
@RestController
public class Health extends AbstractServices{
	private final Logger LOGGER = LoggerFactory.getLogger(Health.class);
	
	@Inject
	private Environment environment;
	
	@Autowired
	MyService myService;
	
	@Autowired
	private ShopServiceDAO shopServiceDAO;

	
	@Value("${test.property}")
	private String testProperty;
	
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
		LOGGER.info("My log from within the service: " + testProperty + "==" + environment.getProperty("global.property"));
		String myVal = testProperty + " - " + environment.getProperty("global.property"); 
		return new ResponseEntity<MyDTO>(new MyDTO("algo: " + myService.getMyValue(myVal), 2), 
				HttpStatus.OK);
    }
	
	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<MyDTO> create() {
		
	    Shop shop = new Shop();
	    shop.setEmplNumber(1);
	    shop.setName("this one");
	    shopServiceDAO.create(shop);
		
		return new ResponseEntity<MyDTO>(new MyDTO("created ", 2), 
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/read", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<MyDTO> read() {
		
		Shop shop = shopServiceDAO.findById(6);
		
		return new ResponseEntity<MyDTO>(new MyDTO("readed " + shop.getName(), 2), 
				HttpStatus.OK);
	}
}
