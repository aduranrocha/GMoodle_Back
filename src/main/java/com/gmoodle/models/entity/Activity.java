package com.gmoodle.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * @return the idActivity
	 */
	public Long getIdActivity() {
		return idActivity;
	}

	/**
	 * @param idActivity the idActivity to set
	 */
	public void setIdActivity(Long idActivity) {
		this.idActivity = idActivity;
	}

	/**
	 * @return the titleActivity
	 */
	public String getTitleActivity() {
		return titleActivity;
	}

	/**
	 * @param titleActivity the titleActivity to set
	 */
	public void setTitleActivity(String titleActivity) {
		this.titleActivity = titleActivity;
	}
	/**
	 * @return the instructions
	 */
	public String getInstructions() {
		return instructions;
	}

	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
