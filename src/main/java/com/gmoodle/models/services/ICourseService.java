package com.gmoodle.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmoodle.models.entity.Course;

public interface ICourseService {
	public List<Course> findAll();
	// Page the group list
	public Page<Course> findAll(Pageable pageable);
	public Course findById(Long id);
	public Course save(Course group);
	public void delete(Long id);
}
