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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmoodle.models.entity.Course;
import com.gmoodle.models.entity.Users;
import com.gmoodle.models.services.ICourseService;
import com.gmoodle.models.services.userservice.IUserService;

@RestController
@RequestMapping("/course") 
public class CourseRestController {
	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private IUserService userService;
	
	@GetMapping
	public List<Course> index(){
		return courseService.findAll();
		
	}
	
	// Show all but with pages {number of pages}
	@GetMapping("/page/{page}")
	public Page<Course> index(@PathVariable Integer page){
		return courseService.findAll(PageRequest.of(page, 3));	
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Course course = null;
		Map<String, Object> response = new HashMap<>();
		// In case the connection with the DB fail
		try {
			// The method that will find the group
			course = courseService.findById(id);		
		}catch(DataAccessException e) {
			response.put("message", "Error: Connecting with DB");
			// Get the Specific Cause
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// In case the number of ID doesn't exist
		if(course == null) {
			response.put("message", "The course with ID: ".concat(id.toString().concat(" doesn't exist")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Course>(course,HttpStatus.OK);
	}
	
	//@Secured({ "ROLE_ADMIN" })
	@PostMapping
	// @Valid validates the data @BindingResult error messages
	public ResponseEntity<?> create(@Valid @RequestBody Course course, BindingResult result) {
		Course courseNew = null;
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
		
		// Access to the ID of myUser
		Long idUser = (Long) course.getUsers().get("idUser");
		//
		Users courseUser = userService.findById(idUser);
		
		course.setUsers(courseUser);
		
		try {
			
			courseNew = courseService.save(course);
		} catch(DataAccessException e) {
			response.put("message", "Error: insterting data into DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The course has been CREATED successfuly!");
		response.put("cliente", courseNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
		Course courseActual = courseService.findById(id);
		Course courseUpdate = null;
		Users newUser = null;

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
		if (courseActual == null) {
			response.put("message","Error: Update fail, the group with ID:  ".concat(id.toString().concat(" doesn't exist")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			if (course.getUsers().get("idUser") != courseActual.getUsers().get("idUser"))
			{
				newUser = userService.findById((Long) course.getUsers().get("idUser"));
				
				courseActual.setUsers(newUser);
			}
		
			newUser = userService.findById(id);
			
			if (newUser == null || newUser.getIsEnabled() == false) {
				response.put("message", "Error: Update fail, the user with ID:  ".concat(id.toString().concat(" doesn't exist or is unable")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			courseActual.setNameCourse(course.getNameCourse());
			courseActual.setSummaryCourse(course.getSummaryCourse());
			courseActual.setIsEnableCourse(course.getIsEnableCourse());
			courseActual.setUpdateAt(new Date());
			//courseActual.setUsers(course.getUsers().get("idUser"));
			
			courseUpdate = courseService.save(courseActual);
			
		}catch(DataAccessException e) { 
			response.put("message", "Error: updating data into DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "The course has been UPDATE successfuly!");
		response.put("cliente", courseUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}
	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/{id}") 
	public ResponseEntity<?> delete(@PathVariable Long id) {		
		Map<String, Object> response = new HashMap<>();
		
		try {
		    courseService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "Error: deleting course in the DB");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "The course has been DELETED successfully!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
