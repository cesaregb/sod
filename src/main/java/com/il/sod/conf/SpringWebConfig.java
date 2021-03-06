package com.il.sod.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.il.sod.spring.viewresolver.JsonViewResolver;

@Configuration
@EnableWebMvc // enable web mvc annotations. discover Controllers and RestControllers
@EnableAspectJAutoProxy // we set the aspectJ annotation here to be setted in the same context as the controllers. 
@ComponentScan({"com.il.sod.controllers", 
	"com.il.sod.rest.api", 
	"com.il.sod.exceptions", 
	"com.il.sod.aop"})
public class SpringWebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// where we will have our "static" resources .jpg, .css, etc. 
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	// configuration of view resolvers 
	//http://websystique.com/springmvc/spring-4-mvc-contentnegotiatingviewresolver-example/
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(false).defaultContentType(MediaType.APPLICATION_JSON);
    }
 
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
        resolvers.add(jspViewResolver());
        resolvers.add(jsonViewResolver());
        resolver.setViewResolvers(resolvers);
        return resolver;
    }
 
    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }
 
    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new LocalInterceptor());
//		registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
//	}
}
