package com.service.api.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.service.api.gateway.Utils.Jwtutils;

@Component
public class Filter extends AbstractGatewayFilterFactory<Filter.Config>
{
	
	@Autowired
	private RouteValidator routeValidator;

	public static class Config{
		
	}
	
	public Filter()
	{
		super(Config.class);
	}
	
	@Autowired
	private Jwtutils jwtutils;
	
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange,chain)->{
			if(routeValidator.isSecured.test(exchange.getRequest()))
			{
			    System.err.println("inside validator =====> "+exchange.getRequest().getPath());
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
				{
					throw new RuntimeException("authorization header missing..");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if(authHeader!=null && authHeader.startsWith("Bearer ")) //header prefix Bearer
				{
					authHeader = authHeader.substring(7);//remove bearer
					jwtutils.validateTocken(authHeader);
//					System.out.println("Verified.."+jwtutils.verifTockenInMemoryUserName(authHeader));
				}
			}
			return chain.filter(exchange);
		};
	}
	
	
}
