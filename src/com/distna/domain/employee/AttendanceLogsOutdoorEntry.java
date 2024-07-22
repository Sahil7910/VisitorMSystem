package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="attendance_outdoor_entry")
public class AttendanceLogsOutdoorEntry 
{
	private int id; 
	private int workID;
	private String recordDate;
	private int shift; 
	private String timeAsPerShftTimings;
	private int status;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name="WorkID")
	public int getWorkID() {
		return workID;
	}
	public void setWorkID(int workID) {
		this.workID = workID;
	}
	
	
	@Column(name="RecordDate")
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	
	
	@Column(name="shift")
	public int getShift() {
		return shift;
	}
	public void setShift(int shift) {
		this.shift = shift;
	}
	
	
	@Column(name="timeAsPerShftTimings")
	public String getTimeAsPerShftTimings() {
		return timeAsPerShftTimings;
	}
	public void setTimeAsPerShftTimings(String timeAsPerShftTimings) {
		this.timeAsPerShftTimings = timeAsPerShftTimings;
	}
	
	
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	
	
}
