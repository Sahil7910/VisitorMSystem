package com.distna.domain.leaves;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="outforwork")
public class OutForWork {
	
	int id;
	int employeeId;
	String toEmployees;
	String subject;
	String description;
	String appliedFromDate;
	String appliedToDate;
	String status;
	String approvedFromDate;
	String approvedToDate;
	String messageDate;
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="employeeId")
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	@Column(name="toEmployees")
	public String getToEmployees() {
		return toEmployees;
	}
	public void setToEmployees(String toEmployees) {
		this.toEmployees = toEmployees;
	}

	@Column(name="subject")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="appliedFromDate")
	public String getAppliedFromDate() {
		return appliedFromDate;
	}
	public void setAppliedFromDate(String appliedFromDate) {
		this.appliedFromDate = appliedFromDate;
	}
	@Column(name="appliedToDate")
	public String getAppliedToDate() {
		return appliedToDate;
	}
	public void setAppliedToDate(String appliedToDate) {
		this.appliedToDate = appliedToDate;
	}
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="approvedFromDate")
	public String getApprovedFromDate() {
		return approvedFromDate;
	}
	public void setApprovedFromDate(String approvedFromDate) {
		this.approvedFromDate = approvedFromDate;
	}
	@Column(name="approvedToDate")
	public String getApprovedToDate() {
		return approvedToDate;
	}
	public void setApprovedToDate(String approvedToDate) {
		this.approvedToDate = approvedToDate;
	}
	@Column(name="messageDate")
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
}
