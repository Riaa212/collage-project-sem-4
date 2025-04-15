package com.blogwebsite.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogwebsite.blog.domain.BlogRating;

public interface RatingRepo extends JpaRepository<BlogRating,Integer>
{

}
