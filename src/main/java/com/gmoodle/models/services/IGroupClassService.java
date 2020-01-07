package com.gmoodle.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmoodle.models.entity.groupClass;

public interface IGroupClassService {
	public List<groupClass> findAll();
	// Page the group list
	public Page<groupClass> findAll(Pageable pageable);
	public groupClass findById(Long id);
	public groupClass save(groupClass group);
	public void delete(Long id);

}
