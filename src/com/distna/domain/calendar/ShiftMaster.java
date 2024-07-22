package com.distna.domain.calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shift_mast")
public class ShiftMaster 
{
	private int shiftid; 
	private String shiftcode;
	private String shiftname; 
	private boolean breakshift; 
	private boolean nightshift; 
	private String startTime1; 
	private String endTime1; 
	private String startTime2; 
	private String endTime2; 
	private String lunchDeduction; 
	private String WorkingHrs; 
	private boolean isActive; 
	private int category=0; 
	private String description; 
	private String record_Creation_Date; 
	private int record_Created_By=0; 
	private String record_Modify_Date; 
	private int record_Modified_By=0; 
	private String from_ip;
	
	@Id
	@GeneratedValue
	@Column(name="shiftid")
	public int getShiftid() {
		return shiftid;
	}
	public void setShiftid(int shiftid) {
		this.shiftid = shiftid;
	}
	
	@Column(name="shiftcode")
	public String getShiftcode() {
		return shiftcode;
	}
	public void setShiftcode(String shiftcode) {
		this.shiftcode = shiftcode;
	}
	
	@Column(name="shiftname")
	public String getShiftname() {
		return shiftname;
	}
	public void setShiftname(String shiftname) {
		this.shiftname = shiftname;
	}
	
	@Column(name="breakshift")
	public boolean isBreakshift() {
		return breakshift;
	}
	public void setBreakshift(boolean breakshift) {
		this.breakshift = breakshift;
	}
	
	@Column(name="nightshift")
	public boolean isNightshift() {
		return nightshift;
	}
	public void setNightshift(boolean nightshift) {
		this.nightshift = nightshift;
	}
	
	
	@Column(name="workingHrs")
	public String getWorkingHrs() {
		return WorkingHrs;
	}
	public void setWorkingHrs(String workingHrs) {
		WorkingHrs = workingHrs;
	}
	
	@Column(name="isActive")
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	@Column(name="from_ip")
	public String getFrom_ip() {
		return from_ip;
	}
	public void setFrom_ip(String from_ip) {
		this.from_ip = from_ip;
	}
	
	@Column(name="startTime1")
	public String getStartTime1() {
		return startTime1;
	}
	public void setStartTime1(String startTime1) {
		this.startTime1 = startTime1;
	}
	
	@Column(name="endTime1")
	public String getEndTime1() {
		return endTime1;
	}
	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}
	
	@Column(name="startTime2")
	public String getStartTime2() {
		return startTime2;
	}
	public void setStartTime2(String startTime2) {
		this.startTime2 = startTime2;
	}
	
	@Column(name="endTime2")
	public String getEndTime2() {
		return endTime2;
	}
	public void setEndTime2(String endTime2) {
		this.endTime2 = endTime2;
	}
	
	@Column(name="lunchDeduction")
	public String getLunchDeduction() {
		return lunchDeduction;
	}
	public void setLunchDeduction(String lunchDeduction) {
		this.lunchDeduction = lunchDeduction;
	}
	
	@Column(name="category")
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="record_Creation_Date")
	public String getRecord_Creation_Date() {
		return record_Creation_Date;
	}
	public void setRecord_Creation_Date(String record_Creation_Date) {
		this.record_Creation_Date = record_Creation_Date;
	}
	
	@Column(name="record_Created_By")
	public int getRecord_Created_By() {
		return record_Created_By;
	}
	public void setRecord_Created_By(int record_Created_By) {
		this.record_Created_By = record_Created_By;
	}
	
	@Column(name="record_Modify_Date")
	public String getRecord_Modify_Date() {
		return record_Modify_Date;
	}
	public void setRecord_Modify_Date(String record_Modify_Date) {
		this.record_Modify_Date = record_Modify_Date;
	}
	
	
	@Column(name="record_Modified_By")
	public int getRecord_Modified_By() {
		return record_Modified_By;
	}
	public void setRecord_Modified_By(int record_Modified_By) {
		this.record_Modified_By = record_Modified_By;
	}
		
}
