package com.gmoodle.models.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmoodle.models.entity.Course;

public interface ICourseDao extends JpaRepository<Course, Long> {
	public Optional<Course> findById(Long idUser);
}
