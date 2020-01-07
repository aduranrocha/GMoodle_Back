package com.gmoodle.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmoodle.models.dao.ICourseDao;
import com.gmoodle.models.entity.Course;

@Service
public class CourseServiceImpl implements ICourseService{
	@Autowired
	private ICourseDao courseDao;

	@Override
	public List<Course> findAll() {
		return (List<Course>) courseDao.findAll();
	}

	@Override
	public Page<Course> findAll(Pageable pageable) {
		return courseDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Course findById(Long id) {
		return courseDao.findById(id).orElse(null);
	}

	@Override
	public Course save(Course group) {
		return courseDao.save(group);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		courseDao.deleteById(id);
	}

}
