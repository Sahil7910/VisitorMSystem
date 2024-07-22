package com.distna.domain.leaves;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="allowed_leaves")
public class LeaveAllocation {
	
	private int id;
	private int employee_id=0; 
	private int typeofleave=0; 
	private String value;
	private String expiration_date;
	private String created_on; 
	private String description;
	private String approved_fromdate;
	
	private String approved_todate;
	private String fromdate;
	private String todate;
	private int allocationStatus;
	private int leaveApplicationId=0;
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
	
	@Column(name="employee_id")
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	
	@Column(name="leavetype")
	public int getTypeofleave() {
		return typeofleave;
	}
	public void setTypeofleave(int typeofleave) {
		this.typeofleave = typeofleave;
	}
	
	@Column(name="value")
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name="expiration_date")
	public String getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}
	
	@Column(name="created_on")
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="approved_fromdate")
	public String getApproved_fromdate() {
		return approved_fromdate;
	}
	public void setApproved_fromdate(String approved_fromdate) {
		this.approved_fromdate = approved_fromdate;
	}
	
	@Column(name="approved_todate")
	public String getApproved_todate() {
		return approved_todate;
	}
	public void setApproved_todate(String approved_todate) {
		this.approved_todate = approved_todate;
	}
	
	@Column(name="from_date")
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	
	@Column(name="to_date")
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	
	
	@Column(name="status")
	public int getAllocationStatus() {
		return allocationStatus;
	}
	public void setAllocationStatus(int allocationStatus) {
		this.allocationStatus = allocationStatus;
	}
	
	@Column(name="leaveApplicationId")
	public int getLeaveApplicationId() {
		return leaveApplicationId;
	}
	public void setLeaveApplicationId(int leaveApplicationId) {
		this.leaveApplicationId = leaveApplicationId;
	}
	
	@Column(name="employee_department_id")
	public int getEmployeeDepartmentId() {
		return employeeDepartmentId;
	}
	
	public void setEmployeeDepartmentId(int employeeDepartmentId) {
		this.employeeDepartmentId = employeeDepartmentId;
	}
	
	
	

}
