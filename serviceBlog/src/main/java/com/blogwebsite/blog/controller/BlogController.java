package com.blogwebsite.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogwebsite.blog.domain.BlogEntity;
import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.CommentProxy;
import com.blogwebsite.blog.service.impl.BlogServiceImpl;

@RestController
@RequestMapping("/blog")
@CrossOrigin(origins = "http://localhost:4200")
public class BlogController {

	@Autowired
	private BlogServiceImpl blogImpl;
	
	//create blog
	@PostMapping("/save/{id}") //working -user
	public ResponseEntity<?> saveBlog(@RequestBody BlogProxy blogProxy,@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.createBlog(blogProxy,id));
	}
	
	//delete blog
	@DeleteMapping("/delete/{id}") //working -user
	public ResponseEntity<?> deleteBlog(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.deleteBlog(id));
	}
	
	//update blog
	@PutMapping("/update/{id}")  //working -user
	public ResponseEntity<?> updateBlog(@RequestBody BlogProxy blogProxy,@PathVariable Integer id)
	{
		return ResponseEntity.status(HttpStatus.CREATED).body(blogImpl.updateBlog(blogProxy, id));
	}
	
	//search blog by title 
	@GetMapping("/searchBlogByTitle/{title}") //working -user
	public ResponseEntity<?> searchBlogByTitle(@PathVariable("title") String title)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.searchByBlogTitle(title));
	}
	
	//get all blogs
	@GetMapping("/getAllBlogs") //working -user
	public ResponseEntity<?> getAllBlog(@RequestParam(value="page",defaultValue = "0") Integer pageNumber,@RequestParam(value = "size",defaultValue = "3") Integer pageSize)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getAllBlogs(pageNumber,pageSize));
	}
	
	
	//get user by id for user id reference 
	@GetMapping("/getUserById/{id}") //working - to store user id in blog table
	public ResponseEntity<?> getUserById(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getUserByUserId(id));
	}
	
	//add comment on blog - working
	@PostMapping("/AddComment/{id}")
	public ResponseEntity<?> addComment(@PathVariable("id") Integer blogid,@RequestBody CommentProxy commentProxy)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.addCommentToBlog(blogid, commentProxy));
	}
	
	
	//get only comment by blog id - working
	@GetMapping("/getCommentsByBlogId/{id}")
	public ResponseEntity<?> getCommentsByBlogId(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getAllCommentsByBlogId(id));
	}
	

	//get blog by id - working
	@GetMapping("/getBlogById/{id}") //show all comments on blog by id
	public ResponseEntity<?> getBlogById1(@PathVariable("id") Integer id)
	{
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogImpl.getBlogById(id));
	}
	
	@PostMapping("/searchBlogByTitleAndCategory") //working [added- 22-03-2025]
	public ResponseEntity<?> searchByBlogTitleAndCategoryName(@RequestBody BlogProxy blogProxy)
	{
		return ResponseEntity.status(HttpStatus.OK).body(blogImpl.searchBlogByTitleAndCategory(blogProxy));
	}
	
	@GetMapping("/getBlogUserById/{id}")
	public ResponseEntity<?> getBlogByUserId(@PathVariable("id") Integer userId)
	{
		return ResponseEntity.status(HttpStatus.OK).body(blogImpl.getBlogByUserId(userId));
	}
	
	 @PostMapping("/blogs")
	 public ResponseEntity<?> createBlog(
//	     @RequestPart("title") String title,
//	     @RequestPart("content") String content,
//	     @RequestPart("") String category
		@ModelAttribute("blog") BlogEntity blogEntity,
	     @RequestPart("images") List<MultipartFile> images
	 ) {
	     return ResponseEntity.status(HttpStatus.OK).body(blogImpl.createBlog(images,blogEntity));
	 }
	 
	 @DeleteMapping("/deleteCommentById/{id}")
	 public ResponseEntity<?> deleteCommentById(@PathVariable("id") Integer commentId)
	 {
		 return ResponseEntity.status(HttpStatus.OK).body(blogImpl.deletecommentById(commentId));
	 }
}
