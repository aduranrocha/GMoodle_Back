package com.gmoodle.controllers;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmoodle.models.entity.groupClass;
import com.gmoodle.models.services.IGroupClassService;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/group") 
public class GroupClassRestController {
	@Autowired
	private IGroupClassService groupService;
	
	@GetMapping
	public List<groupClass> index(){
		return groupService.findAll();	
	}
	
	// Show all but with pages {number of pages}
	// 'numElem' its the number of element per page, 'page' number of the page
	@GetMapping("/page/{numElem}/{page}")
	public Page<groupClass> index(
			@PathVariable(value = "numElem") Integer numElem,
			@PathVariable(value = "page") Integer page){
		return groupService.findAll(PageRequest.of(page, numElem));	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		groupClass group = null;
		Map<String, Object> response = new HashMap<>();
		// In case the connection with the DB fail
		try {
			// The method that will find the group
			group = groupService.findById(id);		
		}catch(DataAccessException e) {
			response.put("message", "Error: Connecting with DB");
			// Get the Specific Cause
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// In case the number of ID doesn't exist
		if(group == null) {
			response.put("message", "The group with ID: ".concat(id.toString().concat(" doesn't exist")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<groupClass>(group, HttpStatus.OK);
	}
	

	@Secured({ "ROLE_ADMIN" })
	@PostMapping
	// @Valid validates the data @BindingResult error messages
	public ResponseEntity<?> create(@Valid @RequestBody groupClass group, BindingResult result) {
		groupClass groupNew = null;
		groupClass existGroup = null;
		Map<String, Object> response = new HashMap<>();
		// validate if it has mistakes
		if(result.hasErrors()) {
			// obtain the errors
			List<String> errors = result.getFieldErrors()
					.stream()
					// for each file error convert to String
					.map(err -> "The column '" + err.getField() +"' "+ err.getDefaultMessage())
					// return as a list
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		//Check if the group with that name already exist
		existGroup = groupService.findByNameGroup(group.getNameGroup());
		// send a response if group name exist
		if (existGroup != null) {
			response.put("error", "Group already exist!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {
			groupNew = groupService.save(group);
		} catch(DataAccessException e) {
			response.put("message", "Error: insterting data into DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The group has been CREATED successfuly!");
		response.put("cliente", groupNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody groupClass group, BindingResult result, @PathVariable Long id) {
		groupClass groupActual = groupService.findById(id);
		groupClass groupUpdate = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			// obtain the errors
			List<String> errors = result.getFieldErrors()
					.stream()
					// for each file error convert to String
					.map(err -> "The column '" + err.getField() +"' "+ err.getDefaultMessage())
					// return as a list
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		// In case the number of ID doesn't exist
		if (groupActual == null) {
			response.put("message","Error: Update fail, the group with ID:  ".concat(id.toString().concat(" doesn't exist")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			groupActual.setNameGroup(group.getNameGroup());
			groupActual.setSummaryGroup(group.getSummaryGroup());
			groupActual.setEnrolmentKey(group.getEnrolmentKey());
			groupActual.setNumberMax(group.getNumberMax());
			groupActual.setIsStartGroup(group.getIsStartGroup());
			groupActual.setIsEnableGroup(group.getIsEnableGroup());
			groupActual.setStartDateGroup(group.getStartDateGroup());
			groupActual.setEndDateGroup(group.getEndDateGroup());
			groupActual.setUpdateAt(new Date());
			
			groupUpdate = groupService.save(groupActual);
			
		}catch(DataAccessException e) { 
			response.put("message", "Error: updating data into DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The group has been UPDATE successfuly!");
		response.put("cliente", groupUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/{id}") 
	public ResponseEntity<?> delete(@PathVariable Long id) {		
		Map<String, Object> response = new HashMap<>();
		
		try {
		    groupService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "Error: deleting document in the DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "The group has been DELETED successfully!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	
}
