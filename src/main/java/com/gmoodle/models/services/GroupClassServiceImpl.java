package com.gmoodle.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmoodle.models.dao.IGroupClassDao;
import com.gmoodle.models.entity.groupClass;

@Service
public class GroupClassServiceImpl implements IGroupClassService{
	@Autowired
	private IGroupClassDao groupDao;

	@Override
	@Transactional(readOnly = true)
	public List<groupClass> findAll() {
		return (List<groupClass>) groupDao.findAll();
	}
	
	@Override
	public Page<groupClass> findAll(Pageable pageable) {
		return groupDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public groupClass findById(Long id) {
		return groupDao.findById(id).orElse(null);
	}
	
	@Override
	public groupClass save(groupClass group) {
		return groupDao.save(group);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		groupDao.deleteById(id);
	}

	@Override
	public groupClass findByNameGroup(String name) {
		return groupDao.findByNameGroup(name);
	}
	
}
