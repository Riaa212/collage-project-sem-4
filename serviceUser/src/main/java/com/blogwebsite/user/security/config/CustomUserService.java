package com.blogwebsite.user.security.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.blogwebsite.user.domain.UserEntity;
import com.blogwebsite.user.repository.UserRepo;

@Component(value = "bean from custom user service")
public class CustomUserService implements UserDetailsService
{

	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UserEntity> byEmail = userRepo.findByEmail(email);
		
		if(byEmail==null)
		{
			throw new UsernameNotFoundException("user not found");	
		}
		
		return new CustomUser(byEmail.get());
	}	
}
