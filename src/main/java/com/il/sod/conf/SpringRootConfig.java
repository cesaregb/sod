package com.il.sod.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*
 * Configuration initial point. 
 * setting the "paths" that spring is going to look for configuration, 
 * note that if we have a package within conf ie com.il.sod.cof.security is going to be added automaticately 
 * */
@EnableWebMvc
@Configuration
@ComponentScan({ "com.il.sod.services", "com.il.sod.conf" })
public class SpringRootConfig {
}
