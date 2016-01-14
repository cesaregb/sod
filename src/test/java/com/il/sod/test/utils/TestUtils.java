package com.il.sod.test.utils;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.il.sod.utils.PropertyHandler;

@Ignore
public class TestUtils {
	@Test
	public void testProperties(){
		try{
			Assert.assertNotNull(PropertyHandler.getInstance().getValue("swagger.api.host"));
			Assert.assertTrue(PropertyHandler.getInstance().getValue("swagger.api.basepath").equals("/api/v1"));
			Assert.assertEquals("Error in property", PropertyHandler.getInstance().getValue("swagger.api.basepath"), "/api/v1");
			Assert.assertEquals("Error in property", PropertyHandler.getInstance().getValue("test-property"), "value");
		}catch(Exception e){
			Assert.fail("Assert Exception:" + e.getMessage());
		}
	}
}
