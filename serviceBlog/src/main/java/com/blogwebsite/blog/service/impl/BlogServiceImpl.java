package com.blogwebsite.blog.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.blogwebsite.blog.FeignClient.UserClient;
import com.blogwebsite.blog.domain.BlogEntity;
import com.blogwebsite.blog.domain.Category;
import com.blogwebsite.blog.domain.Comment;
import com.blogwebsite.blog.proxy.BlogProxy;
import com.blogwebsite.blog.proxy.CommentProxy;
import com.blogwebsite.blog.proxy.UserProxy;
import com.blogwebsite.blog.repository.BlogRepo;
import com.blogwebsite.blog.repository.CategoryRepo;
import com.blogwebsite.blog.service.BlogService;
import com.blogwebsite.blog.utils.Helper;

@Service
public class BlogServiceImpl implements BlogService
{
	
	
	@Autowired
	private BlogRepo blogRepo;

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private Helper helper;

	
	@Autowired
	private UserClient userClient;
	
	
	//create blog by user id
	@Override
	public String createBlog(BlogProxy blogproxy,Integer userid) {
		
		BlogEntity convertBlogProxyToEntity = helper.convert(blogproxy,BlogEntity.class);
		
		Optional<Category> categoryId = categoryRepo.findById(blogproxy.getCategory().getId());
		
		System.err.println(categoryId);
		
		UserProxy user = userClient.getUserByUserId(userid);
		
		if(user!=null)
		{
			convertBlogProxyToEntity.setUser_id(userid);
			convertBlogProxyToEntity.getCategory().setId(categoryId.get().getId());
			System.err.println("------------------"+categoryId.get().getId());
			blogRepo.save(convertBlogProxyToEntity);
		}
		return "saved successfully";
	}

	//delete blog by blog id
	@Override
	public String deleteBlog(Integer id) {
		blogRepo.deleteById(id);
		return "deleted successfully";
	}

	
	//update blog by blog id
	@Override
	public String updateBlog(BlogProxy blogProxy, Integer id) {
		Optional<BlogEntity> byId = blogRepo.findById(id);
		
		if(byId.isPresent())
		{
			BlogEntity blogEntity = byId.get();
			blogEntity.setTitle(blogProxy.getTitle());
			blogEntity.setContent(blogProxy.getContent());
//			blogProxy.getCategory_id().setId(blogEntity.getCategory_id().getId());
			blogRepo.save(blogEntity);
		}
		return "updated successfully";
	}

	
	//search blog by blog title
	@Override
	public List<BlogProxy> searchByBlogTitle(String title) {
		List<BlogEntity> byTitle = blogRepo.findByTitle(title);
		return helper.convertList(byTitle,BlogProxy.class);
	}

	//get all blogs within pagination
	@Override
	public Page<BlogEntity> getAllBlogs(Integer pageNumber, Integer pageSize) {
		
//		List<BlogEntity> all = blogRepo.findAll();
		Page<BlogEntity> page = blogRepo.findAll(PageRequest.of(pageNumber, pageSize));
		System.err.println("page===>"+page+"\nPage Number==>"+pageNumber+"\nPage Size==>"+pageSize);
		return page;
//		return helper.page(page, BlogProxy.class);
	}
	
	
	public UserProxy getUserByUserId(Integer id)
	{
	return	userClient.getUserByUserId(id);
	}
	
	//add comment on blog
	@Override // working
	public String addCommentToBlog(Integer blogId,CommentProxy commentProxy)
	{
		Optional<BlogEntity> blogbyId = blogRepo.findById(blogId);
		blogbyId.get().setUser_id(commentProxy.getUserId());
		blogbyId.get().getComments().add(helper.convert(commentProxy, Comment.class));
		blogRepo.save(blogbyId.get());
		return "add comment succefully";
	}
	
	//get comments by Blog id
	@Override
	public List<CommentProxy> getCommentsByBlogId(Integer blogId)
	{
		Optional<BlogEntity> byId = blogRepo.findById(blogId);
//		 List<Comment> byBlogId = commentRepo.findByBlogId(blogId);
		List<Comment> comments = byId.get().getComments();
		return helper.convertList(comments, CommentProxy.class);
		
	}
	
	//get blog by id - working
	@Override
	public BlogProxy getBlogById(Integer id)
	{
		Optional<BlogEntity> byId = blogRepo.findById(id);
		return helper.convert(byId, BlogProxy.class);			
	}

	@Override
	public List<BlogProxy> searchBlogByTitleAndCategory(BlogProxy blogProxy)  {//working
		//get title if exists in db
		List<BlogEntity> byTitle = blogRepo.findByTitle(blogProxy.getTitle());
		
		//get category name if exists in db
		Category byCategoryName = categoryRepo.findByCategoryName(blogProxy.getCategory().getCategoryName());

		//get verify both title and category exist 
//		boolean equals = byTitle.get(0).getCategory().getCategoryName().equals(byCategoryName.getCategoryName());
//		
//		if(equals)
//		{
//		List<BlogProxy> convertList = helper.convertList(byTitle,BlogProxy.class);
//		return convertList;
//		}
		List<BlogProxy> convertList = helper.convertList(byTitle,BlogProxy.class);
		return convertList;
	}

	
	@Override
	public List<BlogProxy> getBlogByUserId(Integer userid) {
		
		//System.err.println("get Blog by user id called..............");

		List<BlogEntity> all = blogRepo.findAll();
		
		//System.err.println("user id=======>"+all.stream().filter(a->a.getUser_id().equals(userid)));
		
		List<BlogEntity> list = all.stream().filter(a->a.getUser_id().equals(userid)).toList();
		
		System.err.print(list);
		
		return helper.convertList(list, BlogProxy.class);
	}
}
