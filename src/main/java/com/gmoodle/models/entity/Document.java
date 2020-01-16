package com.gmoodle.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="document")
public class Document implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idDocument;
	
	@NotEmpty(message ="can not be empty")
	@Column(nullable=false)
	private String pathDoucument;
	
	@NotNull(message="can not be empty")
	@Column(nullable=false)
	private boolean isCheck;
	
	@NotNull(message="can not be empty")
	@Column(nullable=false)
	private boolean isEnableDocument;
	
	@Column(name = "titleDocument")
	private String titleDocument;
	
	@Column(name = "createAt")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	/**
	 * Relation ManyToOne from Document to Activity
	 * @JoinColumn: will add the column with that name into the actual table
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idActivity")
    private Activity activity;
	/**
	 * Relation ManyToOne from Document to User
	 * @JoinColumn: will add the column with that name into the actual table
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idUser")
    private Users users;
	
	/**
	 * @return the idDocument
	 */
	public Long getIdDocument() {
		return idDocument;
	}

	/**
	 * @param idDocument the idDocument to set
	 */
	public void setIdDocument(Long idDocument) {
		this.idDocument = idDocument;
	}

	/**
	 * @return the pathDoucument
	 */
	public String getPathDoucument() {
		return pathDoucument;
	}

	/**
	 * @param pathDoucument the pathDoucument to set
	 */
	public void setPathDoucument(String pathDoucument) {
		this.pathDoucument = pathDoucument;
	}

	/**
	 * @return the isCheack
	 */
	public boolean getIsCheck() {
		return isCheck;
	}

	/**
	 * @param isCheack the isCheack to set
	 */
	public void setIsCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	/**
	 * @return the isEnableDocument
	 */
	public boolean getIsEnableDocument() {
		return isEnableDocument;
	}

	/**
	 * @param isEnableDocument the isEnableDocument to set
	 */
	public void setIsEnableDocument(boolean isEnableDocument) {
		this.isEnableDocument = isEnableDocument;
	}
	
	
	/**
	 * @return the titleDocument
	 */
	public String getTitleDocument() {
		return titleDocument;
	}

	/**
	 * @param titleDocument the titleDocument to set
	 */
	public void setTitleDocument(String titleDocument) {
		this.titleDocument = titleDocument;
	}

	/**
	 * Method that gets the object of class Activity
	 * @return the activity
	 */
	public Map<String,Object> getActivity() {
		Map<String,Object> myActivityMap = new HashMap<>();
		
		myActivityMap.put("idActivity", activity.getIdActivity());
		myActivityMap.put("titleActivity", activity.getTitleActivity());
		
		return myActivityMap;
	}

	/**
	 * Method that sets the object of class Activity
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 *  Method that gets the object of class Users
	 * @return the users
	 */
	public Map<String,Object> getUsers() {
		Map<String,Object> myUserMap = new HashMap<>();
		
		myUserMap.put("idUser", users.getIdUser());
		myUserMap.put("username", users.getUsername());
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
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
