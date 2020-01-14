package com.gmoodle.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="activity")
public class Activity implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idActivity;
	
	@NotEmpty(message="can not be empty")
	@Size(min=4, max=44, message="the size must be between 4 and 44")
	@Column(nullable=false,unique=true)
	private String titleActivity;
	
	@Column(columnDefinition="LONGTEXT")
	private String instructions;
	
	/**
	 * Relation OneToMany from Activity to Document
	 * mappedBy is the table that will produce the relation
	 * The method will 
	 * return a list of the courses
	 * 
	 */
	@OneToMany(mappedBy="activity", cascade = CascadeType.ALL)
    private List<Document> document = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idCourse")
    private Course course;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idUser")
    private Users users;
	
	public Long getIdActivity() {
		return idActivity;
	}

	public void setIdActivity(Long idActivity) {
		this.idActivity = idActivity;
	}

	public String getTitleActivity() {
		return titleActivity;
	}

	public void setTitleActivity(String titleActivity) {
		this.titleActivity = titleActivity;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public Map<String,Object> getCourse() 
	{
		Map<String,Object> myCourseMap = new HashMap<>();
		myCourseMap.put("idCourse", course.getIdCourse());
		myCourseMap.put("nameCourse",course.getNameCourse());
		//myCourseMap.put("idUser",course.getUsers());
		return myCourseMap;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Map<String,Object> getUsers() {
		Map<String,Object> myUserMap = new HashMap<>();
		
		myUserMap.put("idUser", users.getIdUser());
		myUserMap.put("userName", users.getUsername());
		myUserMap.put("email", users.getEmail());
		
		return myUserMap;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	public List<Map<String, Object>> getDocument() {
		List<Map<String, Object>> myList = new ArrayList<>();
		for(Document d : document)
		{
			Map<String, Object> myMap = new HashMap<>();
			
			myMap.put("id", d.getIdDocument());
			myMap.put("path", d.getPathDoucument());
			
			myList.add(myMap);
		}
		return myList;
	}

	public void setDocument(List<Document> document) {
		this.document = document;
	}

	private static final long serialVersionUID = 1L;
}
