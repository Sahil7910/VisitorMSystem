package com.distna.domain.company;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="job_roles")
public class JobRoles {
	
	private DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
	private int id;
	private String roleName;
	private String description;
	private int parentRole=0;
	//private String createdOn=dateFormat.format(Calendar.getInstance().getTime());
	private Date createdOn=Calendar.getInstance().getTime();
	private int priority=0;
	private int status=0;
	private String jobCode;
	private int level=1;

	/*private DateFormat dateNow=new SimpleDateFormat("dd/MM/yyyy");
	private String dateNowString=dateNow.format(Calendar.getInstance());
	private Date date=new Date(dateNow.parse(dateNowString).getTime());
	dateNow.parse(dateNow.format(Calendar.getInstance().getTime()));
	private Timestamp createdOn1=new Timestamp();*/
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="role_name")
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="parent_role")
	public int getParentRole() {
		return parentRole;
	}
	public void setParentRole(int parentRole) {
		this.parentRole = parentRole;
	}
	/*@Column(name="createdOn")
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}*/
	
	@Column(name="priority")
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Column(name="job_code")
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	@Column(name="level")
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="created_On", nullable=false, length=19)
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
