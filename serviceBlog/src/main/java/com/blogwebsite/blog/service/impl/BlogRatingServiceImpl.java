package com.blogwebsite.blog.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogwebsite.blog.domain.BlogEntity;
import com.blogwebsite.blog.domain.BlogRating;
import com.blogwebsite.blog.repository.BlogRepo;
import com.blogwebsite.blog.repository.RatingRepo;

@Service
public class BlogRatingServiceImpl {

	@Autowired
	private RatingRepo ratingRepo;
	
	@Autowired
	private BlogRepo blogRepo;
	
	//take rating 
	//set and save if blog id and user exists...
	public String addRatings(Integer rating,Integer userId,Integer blogId)
	{

//	}
	return null;	
	}
}
