package com.distna.domain.calendar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="cal_employee_shifts")
public class ShiftAllocation {
	
	private int id;
	private int employee_id=0;
	private int shiftid=0;
	private String startdate;
	private String enddate; 
	private int shift_pattern_id=0;
	private int company=0;
	private int department=0;
	private String dateField; 
	private String description; 
	private String theme; 
	private String timeField; 
	private String endTime; 
	private String period; 
	private int dayEvent=0;
	private int recurrence=0;
	private int category=0;
	private int location_id=0;
	private int isshift=0;
	

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
	@Column(name="shiftid")
	public int getShiftid() {
		return shiftid;
	}

	public void setShiftid(int shiftid) {
		this.shiftid = shiftid;
	}
	
	
	@Column(name="shift_pattern_id")
	public int getShift_pattern_id() {
		return shift_pattern_id;
	}
	public void setShift_pattern_id(int shift_pattern_id) {
		this.shift_pattern_id = shift_pattern_id;
	}
	@Column(name="company")
	public int getCompany() {
		return company;
	}
	public void setCompany(int company) {
		this.company = company;
	}
	@Column(name="department")
	public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="theme")
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	@Column(name="timeField")
	public String getTimeField() {
		return timeField;
	}
	public void setTimeField(String timeField) {
		this.timeField = timeField;
	}
	@Column(name="endTime")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(name="period")
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	@Column(name="dayEvent")
	public int getDayEvent() {
		return dayEvent;
	}
	public void setDayEvent(int dayEvent) {
		this.dayEvent = dayEvent;
	}
	@Column(name="recurrence")
	public int getRecurrence() {
		return recurrence;
	}
	public void setRecurrence(int recurrence) {
		this.recurrence = recurrence;
	}
	@Column(name="category")
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	@Column(name="location_id")
	public int getLocation_id() {
		return location_id;
	}
	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}
	@Column(name="isshift")
	public int getIsshift() {
		return isshift;
	}
	public void setIsshift(int isshift) {
		this.isshift = isshift;
	}
	@Column(name="startdate")
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	@Column(name="enddate")
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	@Column(name="dateField")
	public String getDateField() {
		return dateField;
	}
	public void setDateField(String dateField) {
		this.dateField = dateField;
	}
	
	
}
