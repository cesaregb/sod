package com.il.sod.test.model.entities;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.il.sod.dao.ShopServiceDAO;
import com.il.sod.services.MyService;
import com.il.sod.test.config.SpringTestConfiguration;

public class ShopDaoImplTest extends SpringTestConfiguration{
 
	@Autowired
	MyService sampleService;
	
	@Autowired
	private ShopServiceDAO shopServiceDAO;
 
    @Test
    public void testDI(){
    	System.out.println("===> " + shopServiceDAO.findById(6));
    }
    
}