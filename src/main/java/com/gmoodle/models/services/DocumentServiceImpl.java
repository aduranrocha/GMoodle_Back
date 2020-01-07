package com.gmoodle.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmoodle.models.dao.IDocumentDao;
import com.gmoodle.models.entity.Document;

@Service
public class DocumentServiceImpl implements IDocumentService {
	@Autowired
	private IDocumentDao documentDao; 
	
	@Override
	public List<Document> findAll() {
		return (List<Document>) documentDao.findAll();
	}

	@Override
	public Page<Document> findAll(Pageable pageable) {
		return documentDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Document findById(Long id) {
		return documentDao.findById(id).orElse(null);
	}

	@Override
	public Document save(Document document) {
		return documentDao.save(document);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		documentDao.deleteById(id);
	}

}
