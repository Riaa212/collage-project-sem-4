package com.blogwebsite.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.blogwebsite.blog.domain.BlogEntity;
import com.blogwebsite.blog.domain.BlogRating;
import com.blogwebsite.blog.domain.Comment;
import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.CommentProxy;
import com.blogwebsite.blog.proxy.UserProxy;

public interface BlogService {
	//crud on blog
	
	//create blog
	public String createBlog(BlogProxy blogproxy,Integer userid); //user
	
	//delete blog
	public String deleteBlog(Integer id);
	
	//update blog
	public String updateBlog(BlogProxy blogProxy,Integer id);

	public List<BlogProxy> searchByBlogTitle(String title);
	
	//get all blogs
	public Page<BlogEntity> getAllBlogs(Integer pageNumber, Integer pageSize);
	
	public UserProxy getUserByUserId(Integer id);
	
	public List<Comment>  getAllCommentsByBlogId(Integer id);
	
	public BlogProxy getBlogById(Integer id);

	String addCommentToBlog(Integer blogId, CommentProxy commentProxy);
	
	public List<BlogProxy> searchBlogByTitleAndCategory(BlogProxy blogProxy);
	
	public List<BlogProxy> getBlogByUserId(Integer userid);
	
	public String addRating(Integer blogId,BlogRating rating);
}
