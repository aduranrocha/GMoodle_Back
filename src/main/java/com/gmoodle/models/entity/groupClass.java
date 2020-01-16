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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="groupClass")
public class groupClass implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idGroup;
	
	@NotEmpty(message="can not be empty")
	@Size(min=4, max=44, message="the size must be between 4 and 44")
	@Column(nullable=false,unique=true)
	private String nameGroup;
	
	@NotEmpty(message ="can not be empty")
	@Size(min=10, max=150, message="the size must be between 10 and 150")
	@Column(nullable=false)
	private String summaryGroup;
	
	@NotEmpty(message="can not be empty")
	@Size(max=45, message="maximo de carateres 45")
	@Column(nullable=false)
	private String enrolmentKey;
	
	@NotNull(message ="can not be empty")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	@Column(nullable=true)
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private Date updateAt;
	//Save the number max of students in the course
	@NotNull(message="can not be empty")
	@Column(nullable=false)
	private byte numberMax;
	// It will count the number of students already add
	//@NotNull(message="can not be empty")
	@Column(nullable=false)
	private byte countNumber;
	// Validates 5 days after from the startDateCourse
	//@NotNull(message="can not be empty")
	@Column(nullable=false)
	private boolean isStartGroup;
	
	//@NotNull(message="can not be empty")
	@Column(nullable=false)
	private boolean isEnableGroup;
	
	@NotNull(message ="can not be empty")
	@Temporal(TemporalType.DATE)
	private Date startDateGroup;
	
	@NotNull(message ="can not be empty")
	@Temporal(TemporalType.DATE)
	private Date endDateGroup;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "group_course", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "course_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "group_id", "course_id" }) })
	private List<Course> course = new ArrayList<>();
	/**
	 * Relation @OneToMany between the user and the group
	 */
	@OneToMany(mappedBy="group", cascade = CascadeType.ALL)
	private List<Users> user = new ArrayList<>();
	
	public Long getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Long idGroup) {
		this.idGroup = idGroup;
	}

	public String getNameGroup() {
		return nameGroup;
	}

	public void setNameGroup(String nameGroup) {
		this.nameGroup = nameGroup;
	}

	public String getSummaryGroup() {
		return summaryGroup;
	}

	public void setSummaryGroup(String summaryGroup) {
		this.summaryGroup = summaryGroup;
	}

	public String getEnrolmentKey() {
		return enrolmentKey;
	}

	public void setEnrolmentKey(String enrolmentKey) {
		this.enrolmentKey = enrolmentKey;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public byte getNumberMax() {
		return numberMax;
	}

	public void setNumberMax(byte numberMax) {
		this.numberMax = numberMax;
	}

	public boolean getIsEnableGroup() {
		return isEnableGroup;
	}

	public void setIsEnableGroup(boolean isEnableGroup) {
		this.isEnableGroup = isEnableGroup;
	}
	public byte getCountNumber() {
		return countNumber;
	}

	public void setCountNumber(byte countNumber) {
		this.countNumber = countNumber;
	}

	public boolean getIsStartGroup() {
		return isStartGroup;
	}

	public void setIsStartGroup(boolean isStartGroup) {
		this.isStartGroup = isStartGroup;
	}

	/**
	 * @return the startDateCourse
	 */
	public Date getStartDateGroup() {
		return startDateGroup;
	}

	/**
	 * @param startDateCourse the startDateCourse to set
	 */
	public void setStartDateGroup(Date startDateGroup) {
		this.startDateGroup = startDateGroup;
	}
	
	/**
	 * @return the endDateCourse
	 */
	public Date getEndDateGroup() {
		return endDateGroup;
	}

	/**
	 * @param endDateCourse the endDateCourse to set
	 */
	public void setEndDateGroup(Date endDateGroup) {
		this.endDateGroup = endDateGroup;
	}
	
	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	public List<Map<String,Object>> getUser() {
		List<Map<String,Object>> usersList = new ArrayList<>();
		for (Users u:user) {
			Map<String,Object> myUserMap = new HashMap<>();
			myUserMap.put("iduser", u.getIdUser());
			myUserMap.put("name", u.getName());
			
			usersList.add(myUserMap);
		}
		return usersList;
	}

	public void setUser(List<Users> user) {
		this.user = user;
	}

	private static final long serialVersionUID = 1L;
}
