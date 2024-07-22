package com.distna.domain.calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="calcalendar")
public class ShiftDefinition 
{
	private int id; 
	private String dateField; 
	private String description; 
	private String theme; 
	private String timeField; 
	private String endTime; 
	private boolean dayEvent=false;
	private String endDate; 
	private String period; 
	private boolean recurrence=false;
	private int category=0; 
	private int company_id=0; 
	private int department_id=0; 
	private int location_id=0;
	private int employee=0; 
	private boolean overnight=false; 
	private boolean isDept=false; 
	private boolean isCompany=false; 
	private int shiftid=0; 
	private int isshift=0; 
	private boolean isshiftpattern=false; 
	private boolean ismodified=false;
	private boolean break1=false;
	private boolean break2=false;
	
	private boolean weeklyOff1=false;
	private boolean weeklyOff2=false;
	private String break1StartTime=""; 
	private String break1EndTime=""; 
	private String break2StartTime=""; 
	private String break2EndTime=""; 
	private String weeklyOff1Day=""; 
	private String weeklyOff2Day=""; 
	
	private String punchBeginBefore="0";
	private String punchEndAfter="0";
	private String graceTime="0";
	private String partialDay="0"; 
	private String partialDayStartTime="0";
	private String partialDayEndTime="0";
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name="DateField")
	public String getDateField() {
		return dateField;
	}
	public void setDateField(String dateField) {
		this.dateField = dateField;
	}
	
	@Column(name="Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	@Column(name="Theme")
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	
	@Column(name="TimeField")
	public String getTimeField() {
		return timeField;
	}
	public void setTimeField(String timeField) {
		this.timeField = timeField;
	}
	
	
	@Column(name="EndTime")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	
	@Column(name="DayEvent")
	public boolean isDayEvent() {
		return dayEvent;
	}
	public void setDayEvent(boolean dayEvent) {
		this.dayEvent = dayEvent;
	}
	
	
	@Column(name="EndDate")
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	@Column(name="Period")
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	
	
	@Column(name="Recurrence")
	public boolean isRecurrence() {
		return recurrence;
	}
	public void setRecurrence(boolean recurrence) {
		this.recurrence = recurrence;
	}
	
	
	@Column(name="Category")
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
	
	@Column(name="company_id")
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	
	
	@Column(name="department_id")
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	
	
	@Column(name="location_id")
	public int getLocation_id() {
		return location_id;
	}
	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}
	
	
	@Column(name="employee")
	public int getEmployee() {
		return employee;
	}
	public void setEmployee(int employee) {
		this.employee = employee;
	}
	
	
	@Column(name="overnight")
	public boolean isOvernight() {
		return overnight;
	}
	public void setOvernight(boolean overnight) {
		this.overnight = overnight;
	}
	
	
	@Column(name="isDept")
	public boolean isDept() {
		return isDept;
	}
	public void setDept(boolean isDept) {
		this.isDept = isDept;
	}
	
	
	@Column(name="isCompany")
	public boolean isCompany() {
		return isCompany;
	}
	public void setCompany(boolean isCompany) {
		this.isCompany = isCompany;
	}
	
	@Column(name="shiftid")
	public int getShiftid() {
		return shiftid;
	}
	public void setShiftid(int shiftid) {
		this.shiftid = shiftid;
	}
	
	@Column(name="isshift")
	public int getIsshift() {
		return isshift;
	}
	public void setIsshift(int isshift) {
		this.isshift = isshift;
	}
	
	@Column(name="isshiftpattern")
	public boolean isIsshiftpattern() {
		return isshiftpattern;
	}
	public void setIsshiftpattern(boolean isshiftpattern) {
		this.isshiftpattern = isshiftpattern;
	}
	
	@Column(name="ismodified")
	public boolean isIsmodified() {
		return ismodified;
	}
	public void setIsmodified(boolean ismodified) {
		this.ismodified = ismodified;
	}
	
	@Column(name="break1")
	public boolean isBreak1() {
		return break1;
	}
	public void setBreak1(boolean break1) {
		this.break1 = break1;
	}
	
	@Column(name="break2")
	public boolean isBreak2() {
		return break2;
	}
	public void setBreak2(boolean break2) {
		this.break2 = break2;
	}
	
	
	@Column(name="weeklyOff1")
	public boolean isWeeklyOff1() {
		return weeklyOff1;
	}
	public void setWeeklyOff1(boolean weeklyOff1) {
		this.weeklyOff1 = weeklyOff1;
	}
	@Column(name="weeklyOff2")
	public boolean isWeeklyOff2() {
		return weeklyOff2;
	}
	public void setWeeklyOff2(boolean weeklyOff2) {
		this.weeklyOff2 = weeklyOff2;
	}
	@Column(name="break1StartTime")
	public String getBreak1StartTime() {
		return break1StartTime;
	}
	public void setBreak1StartTime(String break1StartTime) {
		this.break1StartTime = break1StartTime;
	}
	@Column(name="break1EndTime")
	public String getBreak1EndTime() {
		return break1EndTime;
	}
	public void setBreak1EndTime(String break1EndTime) {
		this.break1EndTime = break1EndTime;
	}
	@Column(name="break2StartTime")
	public String getBreak2StartTime() {
		return break2StartTime;
	}
	public void setBreak2StartTime(String break2StartTime) {
		this.break2StartTime = break2StartTime;
	}
	@Column(name="break2EndTime")
	public String getBreak2EndTime() {
		return break2EndTime;
	}
	public void setBreak2EndTime(String break2EndTime) {
		this.break2EndTime = break2EndTime;
	}
	@Column(name="weeklyOff1Day")
	public String getWeeklyOff1Day() {
		return weeklyOff1Day;
	}
	public void setWeeklyOff1Day(String weeklyOff1Day) {
		this.weeklyOff1Day = weeklyOff1Day;
	}
	@Column(name="weeklyOff2Day")
	public String getWeeklyOff2Day() {
		return weeklyOff2Day;
	}
	public void setWeeklyOff2Day(String weeklyOff2Day) {
		this.weeklyOff2Day = weeklyOff2Day;
	}
	
	
	@Column(name="punchBeginBefore")
	public String getPunchBeginBefore() {
		return punchBeginBefore;
	}
	public void setPunchBeginBefore(String punchBeginBefore) {
		this.punchBeginBefore = punchBeginBefore;
	}
	
	
	@Column(name="punchEndAfter")
	public String getPunchEndAfter() {
		return punchEndAfter;
	}
	public void setPunchEndAfter(String punchEndAfter) {
		this.punchEndAfter = punchEndAfter;
	}
	
	@Column(name="graceTime")
	public String getGraceTime() {
		return graceTime;
	}
	public void setGraceTime(String graceTime) {
		this.graceTime = graceTime;
	}
	
	@Column(name="partialDay")
	public String getPartialDay() {
		return partialDay;
	}
	public void setPartialDay(String partialDay) {
		this.partialDay = partialDay;
	}
	
	@Column(name="partialDayStartTime")
	public String getPartialDayStartTime() {
		return partialDayStartTime;
	}
	public void setPartialDayStartTime(String partialDayStartTime) {
		this.partialDayStartTime = partialDayStartTime;
	}
	
	@Column(name="partialDayEndTime")
	public String getPartialDayEndTime() {
		return partialDayEndTime;
	}
	public void setPartialDayEndTime(String partialDayEndTime) {
		this.partialDayEndTime = partialDayEndTime;
	}
	
	
	
	
}
