package com.blogwebsite.blog.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BlogRating {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
//	
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="blog_id")
//	private BlogEntity blog;
	
	private Integer userId;
	private Integer rating;
	
}
