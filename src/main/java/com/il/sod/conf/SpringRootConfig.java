package com.il.sod.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
 * Configuration initial point. 
 * setting the "paths" that spring is going to look for configuration, 
 * note that if we have a package within conf ie com.il.sod.cof.security is going to be added automaticately 
 * */
@Configuration
@ComponentScan({ "com.il.sod.conf" })
public class SpringRootConfig {
	
}
