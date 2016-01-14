package com.il.sod.test.model.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.il.sod.services.MyService;
import com.il.sod.test.config.SpringTestConfiguration;

public class ShopDaoImplTest extends SpringTestConfiguration{
 
	@Autowired
	MyService sampleService;
 
    @Test
    public void testDI(){
    	System.out.println("===> " + sampleService.getMyValue("this and that"));
    }
    
}