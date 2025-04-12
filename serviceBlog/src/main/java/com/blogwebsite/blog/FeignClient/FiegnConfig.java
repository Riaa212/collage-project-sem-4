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
	        return "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYWhpQGdtYWlsLmNvbSIsImlhdCI6MTc0NDI4NjA0OCwiZXhwIjoxNzQ0MzA0MDQ4fQ.qL4ybQdXOYAyGFDuGE9E9yA-IjLXcA3NsO1mDgWRd4E";
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