package com.service.api.gateway.config;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

	RouteValidator()
	{
		System.out.println("is secured"+openApiEndPoints);
	}
	
	public static final List<String> openApiEndPoints=List.of(
//			"/jwt/generate", 
			"/user/loginReq",
			"/home",
			"/user/register",
//			"/jwt/home",
//			"/api-docs",
			"/eureka/**"
			);
	
	public Predicate<ServerHttpRequest> isSecured=
			requests ->{ return openApiEndPoints.
			stream().noneMatch(uri->requests.getURI().getPath().contains(uri));
					};
	
	
}
