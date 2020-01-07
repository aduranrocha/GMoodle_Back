package com.gmoodle.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmoodle.models.entity.Course;

public interface ICourseDao extends JpaRepository<Course, Long> {

}
