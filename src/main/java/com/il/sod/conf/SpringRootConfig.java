package com.il.sod.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.il.sod.services", "com.il.sod.conf", "com.il.sod.conf.security" })
public class SpringRootConfig {
}
