package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="employee_messages")
public class EmployeeMessages {
	
	private int id;
	
	private int employeeId=0;
	
	private int messageFrom;
	
	private String messageSubject;
	
	private String messageBody;
	
	private boolean messageStatus=false;
	
	private String messageDate;
	
	private boolean memo=false;
	
	private int employeeDepartmentId=0;

	
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


	@Column(name="messageSubject")
	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	@Column(name="messageBody")
	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	@Column(name="messageStatus")
	public boolean isMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(boolean messageStatus) {
		this.messageStatus = messageStatus;
	}

	@Column(name="messageDate")
	public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	
	@Column(name="messageFrom")
	public int getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(int messageFrom) {
		this.messageFrom = messageFrom;
	}

	@Column(name="isMemo")
	public boolean isMemo() {
		return memo;
	}

	public void setMemo(boolean memo) {
		this.memo = memo;
	}

	
	@Column(name="employeeDepartmentId")
	public int getEmployeeDepartmentId() {
		return employeeDepartmentId;
	}

	public void setEmployeeDepartmentId(int employeeDepartmentId) {
		this.employeeDepartmentId = employeeDepartmentId;
	}

	

	
	
	

}
