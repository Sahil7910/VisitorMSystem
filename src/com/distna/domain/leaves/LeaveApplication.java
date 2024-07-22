package com.distna.domain.leaves;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="employee_leaves")
public class LeaveApplication {
	
	private int id;
	private int employee_id=0;
	private int employee_no=0; 
	private int employeeDepartmentId=0;
	private int department_id=0; 
	private String subject;
	private String from_date; 
	private float duration=0.0f; 
	private int leavetype=0; 
	private int leaveStatus=0;
	/* private int priority=0; */ 
	private String description; 
	private String created_on; 
	private String requested_by;
	private String approved_by;
	/*private int trig; */
	private String to_date; 
	private String half_day_session; 
	private String first_half_day; 
	private String last_half_day;
	private String approveFromdate;
	private String approveTodate;
	
	private String employeeType;
	private String multipleEmployeeIds;
	private String singleEmployeeId;
	
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
	
	@Column(name="employee_no")
	public int getEmployee_no() {
		return employee_no;
	}
	public void setEmployee_no(int employee_no) {
		this.employee_no = employee_no;
	}
	
	@Column(name="department_id")
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	
	@Column(name="leave_subject")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name="from_date")
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	
	@Column(name="duration")
	public float getDuration() {
		return duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}
	
	public int getLeavetype() {
		return leavetype;
	}
	public void setLeavetype(int leavetype) {
		this.leavetype = leavetype;
	}
	@Column(name="leaveStatus")
	public int getLeaveStatus() {
		return leaveStatus;
	}
	public void setLeaveStatus(int leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	
	/*
	 * @Column(name="priority") public int getPriority() { return priority; } public
	 * void setPriority(int priority) { this.priority = priority; }
	 */
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="created_on")
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	
	@Column(name="requested_by")
	public String getRequested_by() {
		return requested_by;
	}
	public void setRequested_by(String requested_by) {
		this.requested_by = requested_by;
	}
	
	@Column(name="approved_by")
	public String getApproved_by() {
		return approved_by;
	}
	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}
	
	/*@Column(name="trig")
	public int getTrig() {
		return trig;
	}
	public void setTrig(int trig) {
		this.trig = trig;
	}*/
	
	@Column(name="to_date")
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	
	@Column(name="half_day_session")
	public String getHalf_day_session() {
		return half_day_session;
	}
	public void setHalf_day_session(String half_day_session) {
		this.half_day_session = half_day_session;
	}
	
	@Column(name="first_half_day")
	public String getFirst_half_day() {
		return first_half_day;
	}
	public void setFirst_half_day(String first_half_day) {
		this.first_half_day = first_half_day;
	}
	
	@Column(name="last_half_day")	
	public String getLast_half_day() {
		return last_half_day;
	}
	public void setLast_half_day(String last_half_day) {
		this.last_half_day = last_half_day;
	}
	
	@Column(name="approveFromdate")
	public String getApproveFromdate() {
		return approveFromdate;
	}
	public void setApproveFromdate(String approveFromdate) {
		this.approveFromdate = approveFromdate;
	}
	
	@Column(name="approveTodate")
	public String getApproveTodate() {
		return approveTodate;
	}
	public void setApproveTodate(String approveTodate) {
		this.approveTodate = approveTodate;
	}
	
	@Column(name="employee_type")
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	
	@Column(name="multiple_employee_ids")
	public String getMultipleEmployeeIds() {
		return multipleEmployeeIds;
	}
	public void setMultipleEmployeeIds(String multipleEmployeeIds) {
		this.multipleEmployeeIds = multipleEmployeeIds;
	}
	
	@Column(name="single_employee_id")
	public String getSingleEmployeeId() {
		return singleEmployeeId;
	}
	public void setSingleEmployeeId(String singleEmployeeId) {
		this.singleEmployeeId = singleEmployeeId;
	}
	
	
	@Column(name="employee_department_id")
	public int getEmployeeDepartmentId() {
		return employeeDepartmentId;
	}
	public void setEmployeeDepartmentId(int employeeDepartmentId) {
		this.employeeDepartmentId = employeeDepartmentId;
	}
	
	
	
	

}
