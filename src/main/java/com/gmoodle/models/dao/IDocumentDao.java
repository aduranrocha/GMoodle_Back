package com.gmoodle.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmoodle.models.entity.Document;


public interface IDocumentDao extends JpaRepository<Document, Long>{

}
