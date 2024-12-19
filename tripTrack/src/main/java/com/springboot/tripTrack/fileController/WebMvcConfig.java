package com.springboot.tripTrack.fileController;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/spring/repository/profile/**")
		.addResourceLocations("file:/Users/duchi/Documents/spring/repository/")
		.addResourceLocations("file:/Users/duchi/Documents/spring/repository/profile")
		.setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
	}
}
