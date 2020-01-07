package com.gmoodle.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmoodle.models.entity.Activity;

public interface IActivityDao extends JpaRepository<Activity, Long>{

}
