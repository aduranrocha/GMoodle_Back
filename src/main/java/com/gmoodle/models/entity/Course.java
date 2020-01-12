package com.gmoodle.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="course")
public class Course implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCourse;
	
	@NotEmpty(message="can not be empty")
	@Size(min=4, max=44, message="the size must be between 4 and 44")
	@Column(nullable=false)
	private String nameCourse;
	
	@NotEmpty(message ="can not be empty")
	@Size(min=10, max=150, message="the size must be between 10 and 150")
	@Column(nullable=false, columnDefinition = "longtext")
	private String summaryCourse;
	
	@NotNull(message ="can not be empty")
	@Column(name="createAt")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@NotNull(message ="can not be empty")
	@Temporal(TemporalType.DATE)
	private Date startDateCourse;
	
	@NotNull(message ="can not be empty")
	@Temporal(TemporalType.DATE)
	private Date endDateCourse;
	
	@NotNull(message="can not be empty")
	@Column(nullable=false)
	private boolean isEnableCourse;

	@Column(nullable=true)
	@Temporal(TemporalType.DATE)
	private Date updateAt;
	
	@NotNull(message="can not be empty")
	@Column(nullable=false)
	private Long createById;
	
	//column that will connect with the other table
	@OneToMany(mappedBy="course", cascade = CascadeType.ALL)
	private List<Activity> activity = new ArrayList<>();
	
	/**
	 * Relation ManyToOne from Document to Activity
	 * @JoinColumn: will add the column with that name into the actual table
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idUser")
    private Users users;
	
	private static final long serialVersionUID = 1L;

	/**
	 * @return the idCourse
	 */
	public Long getIdCourse() {
		return idCourse;
	}

	/**
	 * @param idCourse the idCourse to set
	 */
	public void setIdCourse(Long idCourse) {
		this.idCourse = idCourse;
	}

	/**
	 * @return the nameCourse
	 */
	public String getNameCourse() {
		return nameCourse;
	}

	/**
	 * @param nameCourse the nameCourse to set
	 */
	public void setNameCourse(String nameCourse) {
		this.nameCourse = nameCourse;
	}

	/**
	 * @return the summaryCourse
	 */
	public String getSummaryCourse() {
		return summaryCourse;
	}

	/**
	 * @param summaryCourse the summaryCourse to set
	 */
	public void setSummaryCourse(String summaryCourse) {
		this.summaryCourse = summaryCourse;
	}

	/**
	 * @return the createAt
	 */
	public Date getCreateAt() {
		return createAt;
	}

	/**
	 * @param createAt the createAt to set
	 */
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	/**
	 * @return the startDateCourse
	 */
	public Date getStartDateCourse() {
		return startDateCourse;
	}

	/**
	 * @param startDateCourse the startDateCourse to set
	 */
	public void setStartDateCourse(Date startDateCourse) {
		this.startDateCourse = startDateCourse;
	}

	/**
	 * @return the endDateCourse
	 */
	public Date getEndDateCourse() {
		return endDateCourse;
	}

	/**
	 * @param endDateCourse the endDateCourse to set
	 */
	public void setEndDateCourse(Date endDateCourse) {
		this.endDateCourse = endDateCourse;
	}

	/**
	 * @return the isEnableCourse
	 */
	public boolean getIsEnableCourse() {
		return isEnableCourse;
	}

	/**
	 * @param isEnableCourse the isEnableCourse to set
	 */
	public void setIsEnableCourse(boolean isEnableCourse) {
		this.isEnableCourse = isEnableCourse;
	}

	/**
	 * @return the updateAt
	 */
	public Date getUpdateAt() {
		return updateAt;
	}

	/**
	 * @param updateAt the updateAt to set
	 */
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	/**
	 * @return the createById
	 */
	public Long getCreateById() {
		return createById;
	}

	/**
	 * @param createById the createById to set
	 */
	public void setCreateById(Long createById) {
		this.createById = createById;
	}
	
	public Map<String,Object> getUsers() {
		Map<String,Object> myUserMap = new HashMap<>();
		
		myUserMap.put("idUser", users.getIdUser());
		myUserMap.put("userName", users.getUsername());
		myUserMap.put("email", users.getEmail());
		
		return myUserMap;
	}

	/**
	 *  Method that sets the object of class Users
	 * @param users the users to set
	 */
	public void setUsers(Users users) {
		this.users = users;
	}

}
