package com.gmoodle.models.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="groupClass")
public class groupClass {
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
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	@Column(nullable=true)
	@Temporal(TemporalType.DATE)
	private Date updateAt;
	
	@NotNull(message="can not be empty")
	@Column(nullable=false)
	private Short idNumberMax;
	
	@NotNull(message="can not be empty")
	@Column(nullable=false)
	private boolean isEnableGroup;

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

	public Short getIdNumberMax() {
		return idNumberMax;
	}

	public void setIdNumberMax(Short idNumberMax) {
		this.idNumberMax = idNumberMax;
	}

	public boolean isEnableGroup() {
		return isEnableGroup;
	}

	public void setEnableGroup(boolean isEnableGroup) {
		this.isEnableGroup = isEnableGroup;
	}
	
	private static final long serialVersionUID = 1L;
}
