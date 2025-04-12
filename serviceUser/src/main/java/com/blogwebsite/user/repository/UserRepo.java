package com.blogwebsite.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.blogwebsite.user.domain.UserEntity;

import feign.Param;

public interface UserRepo extends JpaRepository<UserEntity,Integer>
{
	public UserEntity findByUserName(String name);
	
	public Optional<UserEntity> findByEmail(String email);
	
	public Optional<UserEntity> findByOtp(String otp);
	
	@Procedure(value = "getById")
	public UserEntity getByUserId(@Param("in_id") Integer in_id);
}
