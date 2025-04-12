package com.blogwebsite.user.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogwebsite.user.FeignClient.BlogClient;
import com.blogwebsite.user.FeignClient.CategoryClient;
import com.blogwebsite.user.domain.LoginRequest;
import com.blogwebsite.user.domain.LoginResponse;
import com.blogwebsite.user.domain.UserEntity;
import com.blogwebsite.user.proxy.BlogProxy;
import com.blogwebsite.user.proxy.CommentProxy;
import com.blogwebsite.user.proxy.UserProxy;
import com.blogwebsite.user.repository.UserRepo;
import com.blogwebsite.user.security.config.JwtService;
import com.blogwebsite.user.service.UserService;
import com.blogwebsite.user.utils.Helper;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService
{

	  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private Helper helper;
	
	@Autowired
	private CategoryClient categoryClient;
	
	@Autowired
	private BlogClient blogClient;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	private AuthenticationManager authmanager;
	
	@Autowired
	private JwtService jwtService;
	//private final String blogUrl="http://localhost:8088/blog/";
	
	@Override
	public String registerUser(UserProxy user) {
//		emp.setPassword(passwordEncoder.encode(emp.getPassword()));
		user.setRole("ROLE_USER");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(helper.convert(user, UserEntity.class));
		return "register successfully";
	}

	@Override
	public String deleteUser(Integer id) {
			userRepo.deleteById(id);
		return "user deleted successfully";
	}

	@Override
	public String createBlog(BlogProxy blog,Integer id) {
		blogClient.createBlog(blog, id);
		return "saved";
	}

	@Override
	public String deleteBlog(Integer id) {
		blogClient.deleteBlog(id);
		return null;
	}

	@Override
	public String updateBlog(BlogProxy blog,Integer id) {
		blogClient.updateBlog(blog, id);
		return "updated...";
	}

	@Override
	public List<UserProxy> getAllUser() {
		return helper.convertList(userRepo.findAll(), UserProxy.class);
	}

	@Override
	public UserProxy getUserByUserName(String userName) {
		return helper.convert(userRepo.findByUserName(userName), UserProxy.class);
	}

	@Override //search blog by user
	public List<BlogProxy> searchBlogByTitle(String title) {
		
		return blogClient.searchBlogByTitle(title);
	}

	@Override
	public UserProxy updateUserById(Integer id, UserProxy user) {
		
		Optional<UserEntity> byId = userRepo.findById(id);
		
		if(byId.isPresent())
		{
			UserEntity userEntity = byId.get();
			userEntity.setUserName(user.getUserName());
			userEntity.setEmail(user.getEmail());
			userEntity.setBio(user.getBio());
//			userEntity.setPassword(user.getPassword());
			userRepo.save(userEntity);
		}
		return user;
	}

	
	@Override
	@Transactional
	public UserProxy getUserByUserId(Integer id) //working
	{
		UserEntity byId = userRepo.getByUserId(id);
		 logger.error("Procedure call completed. Retrieved entity: {}", byId);
		return helper.convert(byId, UserProxy.class);
	}

	@Override
	public List<BlogProxy> getAllBlogs() //working
	{
		return 	blogClient.getAllBlogs();
	}

	@Override
	public List<BlogProxy> searchBlogByCategory(String category) //working
	{
		return categoryClient.searchBlogByCategory(category);
	}

	@Override
	public String addComment(Integer id, CommentProxy commentProxy) {
		blogClient.addComment(id, commentProxy);
		return "comment added successfully";
	}

	@Override
	public List<BlogProxy> searchByBlogTitleAndCategoryName(BlogProxy blogProxy) {
		return blogClient.searchByBlogTitleAndCategoryName(blogProxy);
	}
	
	
	//send otp by email
	@Override
	public String sendOtpByEmail(UserEntity user)
	{
		String otp=null;
		Optional<UserEntity> byEmail = userRepo.findByEmail(user.getEmail());
		System.err.println("Mail............"+byEmail);
		if(byEmail.isPresent())
		{
			UserEntity userEntity = byEmail.get();
			otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
			System.out.println("OTP===>"+otp);	
			byEmail.get().setOtp(otp);
			System.err.println("setting otp............"+byEmail);
			userRepo.save(userEntity);
			
		}
		return otp;
	}
	
	public String testOtp(String email)
	{
		System.err.println("generate otp method............");
		String otp=null;
		Optional<UserEntity> byEmail = userRepo.findByEmail(email);
		if(byEmail.isPresent())
		{
			UserEntity userobj=byEmail.get();
			otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
			System.out.println("OTP===>"+otp);	
			userobj.setOtp(otp);
			userRepo.save(userobj);
		}
		return otp;
	}
	
	
	
	public String generateTocken(UserEntity user)
	{
//		Emp findByUserName = empRepo.findByUserName(emp.getUserName());
		
		Optional<UserEntity> byEmail = userRepo.findByEmail(user.getEmail());
		
//		System.out.println(findByUserName);
//		System.out.println(findByUserName.getPassword());
		//System.out.println(emp.getPassword());
		boolean matches = passwordEncoder.matches(user.getPassword(),byEmail.get().getPassword());
		
		System.out.println("Matches Password:"+matches);
		if(!matches)
		{
		return "user not found";
		}
		return jwtService.genearteTocken(user.getUserName());
	}
//	@Override
	public LoginResponse login(LoginRequest loginRequest)
	{
		//System.out.println("login response from emp service called..");
		Authentication authentication=new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
		
		Authentication verified = authmanager.authenticate(authentication);
		
		//verified.getAuthorities().stream().forEach(a->System.out.println("Authorities:"+a));
		if(!verified.isAuthenticated())
		{
			//System.out.println("user not found");
			//System.err.println("user not found");
			//throw new BadCredicialException(null, null);
			//throw new BadCredentialsException("Bad credentials...");
			System.out.println("bad credials..");
			//throw new ErrorResponse("bad credentials",404);
		}
		System.err.println("authenticated....."+loginRequest.getEmail()+"\n"+loginRequest.getPassword());
		 return new LoginResponse(loginRequest.getEmail(),jwtService.genearteTocken(loginRequest.getEmail()),(List<SimpleGrantedAuthority>) verified.getAuthorities());
	}
	
	public UserEntity  resetPwd(String email,String updatedpwd,String otp)
	{
		UserEntity userEntity=null;
		Optional<UserEntity> byEmail = userRepo.findByEmail(email);
		if(byEmail.isPresent())
		{
			userEntity = byEmail.get();
			
			if(otp.equals(userEntity.getOtp()))
			{
				userEntity.setPassword(passwordEncoder.encode(updatedpwd));
				userRepo.save(userEntity);
			}
		}
		return userEntity;
	}
	
	@Override
	public String forgetPwd(UserEntity user)
	{
		System.err.println("forget password calledd........");
		Optional<UserEntity> byEmail = userRepo.findByEmail(user.getEmail());
	
		
		if(byEmail.isPresent())
		{
			Optional<UserEntity> byOtp = userRepo.findByOtp(user.getOtp());
			
			if(byOtp.isPresent())
			{				
				//update password
				System.err.println("update....................."+byOtp.get().getEmail());
				byOtp.get().setPassword(passwordEncoder.encode(user.getPassword()));
				System.err.println("by otp===>"+byOtp);
				userRepo.save(byOtp.get());
				System.err.println("after saved successfully.....");
				return "password updated successfully";
			}
		}
		return "Something went wrong!!!!";
	}

	//get user by email
	@Override
	public UserEntity getByEmail(String email) {
		
		Optional<UserEntity> byEmail = userRepo.findByEmail(email);
		if(byEmail.isPresent())
		{
			UserEntity userEntity = byEmail.get();
			return userEntity;
		}
		return null;
	}
	
}
