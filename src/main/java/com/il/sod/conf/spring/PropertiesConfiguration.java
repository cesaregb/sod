package com.il.sod.conf.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import com.il.sod.conf.Constants;

@Configuration
@PropertySources(value = { @PropertySource(value = "classpath:project-config.properties") }) // adding global properties to env.
public class PropertiesConfiguration {
	@Bean
    @Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
    public static PropertySourcesPlaceholderConfigurer properties() {
      String propertiesFilename = "config/application-dev.properties";
      return getPropertiesByFile(propertiesFilename);
    }

    @Bean
    @Profile(Constants.SPRING_PROFILE_PRODUCTION)
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
      String propertiesFilename = "config/application-prod.properties";
      return getPropertiesByFile(propertiesFilename);
    }
    
    public static PropertySourcesPlaceholderConfigurer getPropertiesByFile(String file) {
    	PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
    	configurer.setLocation(new ClassPathResource(file));
    	return configurer;
    }
}
