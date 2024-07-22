package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="attrecord")
public class AttendanceRecord {
	private int recordCount;
	private int empCode=0;
	private String checkInOut;
	private String date;
	private String time;
//	private String deviceIP;
//	private String deviceSerialNo;
	private int status=0;
	private String dateTime;
	
	
	@Id
	@Column(name="recordCount")
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	@Column(name="empCode")
	public int getEmpCode() {
		return empCode;
	}
	public void setEmpCode(int empCode) {
		this.empCode = empCode;
	}
	@Column(name="checkInOut")
	public String getCheckInOut() {
		return checkInOut;
	}
	public void setCheckInOut(String checkInOut) {
		this.checkInOut = checkInOut;
	}
	@Column(name="date")
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(name="time")
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
//	@Column(name="deviceIP")
//	public String getDeviceIP() {
//		return deviceIP;
//	}
//	public void setDeviceIP(String deviceIP) {
//		this.deviceIP = deviceIP;
//	}
//	@Column(name="deviceSerialNo")
//	public String getDeviceSerialNo() {
//		return deviceSerialNo;
//	}
//	public void setDeviceSerialNo(String deviceSerialNo) {
//		this.deviceSerialNo = deviceSerialNo;
//	}
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Column(name="dateTime")
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
