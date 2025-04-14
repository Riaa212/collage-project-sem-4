package com.blogwebsite.blog.FeignClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class FiegnConfig {

	
	  @Bean
	    public RequestInterceptor requestInterceptor() {
	        return requestTemplate -> {
	            requestTemplate.header("Authorization", "Bearer " + getAuthToken());
	        };
	    }

	    private String getAuthToken() {
	        // Retrieve your token here
	        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaXlhQGdtYWlsLmNvbSIsImlhdCI6MTc0NDU1MzgyOCwiZXhwIjoxNzQ0NTcxODI4fQ.9nsfzylBCvD2cl0jWy0Jvyh4ANfArg_4SkEZF-SHiso";
	    }
}

//@Configuration
//public class FeignConfig {
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            requestTemplate.header("Authorization", "Bearer " + getAuthToken());
//        };
//    }
//    
//    private String getAuthToken() {
//        // Retrieve your token here
//        return "your-auth-token";
//    }
//}