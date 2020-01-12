package com.gmoodle.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmoodle.models.entity.Activity;


public interface IActivityService {
	public List<Activity> findAll();
	// Page the group list
	public Page<Activity> findAll(Pageable pageable);
	public Activity findById(Long id);
	public Activity findByTitleActivity(String title);
	public Activity save(Activity activity);
	public void delete(Long id);
}
