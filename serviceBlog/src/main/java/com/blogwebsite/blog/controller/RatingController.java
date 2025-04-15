package com.blogwebsite.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogwebsite.blog.service.impl.BlogRatingServiceImpl;

@RestController
@RequestMapping("/rating")
public class RatingController {

	@Autowired
	private BlogRatingServiceImpl ratingS;

	@PostMapping("/addRatings/{rating}/{userId}/{blogId}")
	public ResponseEntity<?> addRatings(@PathVariable("rating") Integer rating,
			@PathVariable("userId")	Integer userId,
			@PathVariable("blogId") Integer blogId)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingS.addRatings(rating,userId,blogId));
	}
}
