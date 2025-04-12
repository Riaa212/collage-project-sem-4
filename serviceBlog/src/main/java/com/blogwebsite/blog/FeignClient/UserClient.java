package com.blogwebsite.blog.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.blogwebsite.blog.proxy.UserProxy;

import feign.Headers;

@FeignClient(name="UserService",url = "http://localhost:8087/",path = "/user",configuration = FiegnConfig.class)
public interface UserClient {

	@GetMapping("/getById/{id}")
//	 @Headers( "Content-Type: application/x-www-form-urlencoded" )
	public UserProxy getUserByUserId(@PathVariable("id") Integer id);
	
}
