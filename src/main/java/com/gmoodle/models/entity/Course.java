package com.gmoodle.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Course implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCourse;
	
	@NotEmpty(message="can not be empty")
	@Size(min=4, max=44, message="the size must be between 4 and 44")
	@Column(nullable=false,unique=true)
	private String nameCourse;
	
	@NotEmpty(message ="can not be empty")
	@Size(min=10, max=150, message="the size must be between 10 and 150")
	@Column(nullable=false)
	private String summaryCourse;
	
	@NotNull(message ="can not be empty")
	@Column(name="createAt")
	@Temporal(TemporalType.DATE)
	private Date createAt;
}
