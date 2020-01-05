package com.gmoodle.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmoodle.models.entity.Document;


public interface IDocumentServices {
	public List<Document> findAll();
	// Page the group list
	public Page<Document> findAll(Pageable pageable);
	public Document findById(Long id);
	public Document save(Document group);
	public void delete(Long id);
}
