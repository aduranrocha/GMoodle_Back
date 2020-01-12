package com.gmoodle.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmoodle.models.entity.Activity;
import com.gmoodle.models.entity.Course;
import com.gmoodle.models.entity.Users;
import com.gmoodle.models.services.IActivityService;


@RestController
@RequestMapping("/activity") 
public class ActivityRestController {
	@Autowired
	private IActivityService activityService;
	
	
	@GetMapping
	public ResponseEntity<?> index(){
		List<Activity> a = new ArrayList<>();
		a = activityService.findAll();
		
		return new ResponseEntity<List<Activity>>(a, HttpStatus.OK);
	}
	/*
	 * Me pregunto si el find sera exclusivo del maestro
	 * ya que alumno tambien quiero verlo
	 * */
	@GetMapping("/page/{page}")
	public Page<Activity> index(@PathVariable Integer page){
		return activityService.findAll(PageRequest.of(page, 10));	
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Activity activity = null;
		Map<String, Object> response = new HashMap<>();
		// In case the connection with the DB fail
		try {
			// The method that will find the group
			activity = activityService.findById(id);		
		}catch(DataAccessException e) {
			response.put("message", "Error: Connecting with DB");
			// Get the Specific Cause
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// In case the number of ID doesn't exist
		if(activity == null) {
			response.put("message", "The activity with ID: ".concat(id.toString().concat(" doesn't exist")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Activity>(activity,HttpStatus.OK);
	}
	
	@Secured({ "ROLE_TEACHER", "ROLE_ADMIN" })
	@PostMapping
	// @Valid validates the data @BindingResult error messages
	public ResponseEntity<?> create(@Valid @RequestBody Activity activity, BindingResult result) {
		Activity activityNew = null;
		Activity exist = null;
		
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
		
		exist = activityService.findByTitleActivity(activity.getTitleActivity());
		
		if (exist != null)
		{
			response.put("error", "Activity already exist!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
				
		try {
			activityNew = activityService.save(activity);
		} catch(DataAccessException e) {
			response.put("message", "Error: insterting data into DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The activity has been CREATED successfully!");
		response.put("activity", activityNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({ "ROLE_TEACHER","ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Activity activity, BindingResult result, @PathVariable Long id) {
		Activity activityActual = activityService.findById(id);
		Activity activityUpdate = null;
		Users newUser = null;
		Course newCourse = null;

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
		if (activityActual == null) {
			response.put("message","Error: Update fail, the activity with ID:  ".concat(id.toString().concat(" doesn't exist")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			activityActual.setTitleActivity(activity.getTitleActivity());
			activityActual.setInstructions(activity.getInstructions());
			
			activityUpdate = activityService.save(activityActual);
			
		}catch(DataAccessException e) { 
			response.put("message", "Error: updating data into DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The activity has been UPDATE successfully!");
		response.put("cliente", activityUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	@Secured({ "ROLE_TEACHER" })
	@DeleteMapping("/{id}") 
	public ResponseEntity<?> delete(@PathVariable Long id) {		
		Map<String, Object> response = new HashMap<>();
		
		try {
			activityService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "Error: deleting activity in the DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "The activity has been DELETED successfully!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
