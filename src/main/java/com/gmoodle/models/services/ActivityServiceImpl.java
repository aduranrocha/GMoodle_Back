package com.gmoodle.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmoodle.models.dao.IActivityDao;
import com.gmoodle.models.entity.Activity;

@Service
public class ActivityServiceImpl implements IActivityService{
	
	@Autowired
	private IActivityDao activityDao;
	
	@Override
	public List<Activity> findAll() {
		return (List<Activity>) activityDao.findAll();
	}

	@Override
	public Page<Activity> findAll(Pageable pageable) {
		return activityDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Activity findById(Long id) {
		return activityDao.findById(id).orElse(null);
	}

	@Override
	public Activity save(Activity activity) {
		return activityDao.save(activity);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		activityDao.deleteById(id);
	}
	
}
