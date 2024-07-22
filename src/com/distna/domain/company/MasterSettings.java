package com.distna.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="master_settings")
public class MasterSettings 
{
	private int masterId; 
	private int dbBackupDays; 
	private String lastBackupDate; 
	private int attendanceDay; 
	private int attendanceMonth=99;
	private int diffBetPunch;
	private String employeeFolderPath;
	private String dbBackupPath;
	
	
	@Id
	@Column(name="masterId")
	public int getMasterId() {
		return masterId;
	}
	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}
	
	
	@Column(name="dbBackupDays")
	public int getDbBackupDays() {
		return dbBackupDays;
	}
	public void setDbBackupDays(int dbBackupDays) {
		this.dbBackupDays = dbBackupDays;
	}
	
	
	@Column(name="lastBackupDate")
	public String getLastBackupDate() {
		return lastBackupDate;
	}
	public void setLastBackupDate(String lastBackupDate) {
		this.lastBackupDate = lastBackupDate;
	}
	
	
	@Column(name="attendanceDay")
	public int getAttendanceDay() {
		return attendanceDay;
	}
	public void setAttendanceDay(int attendanceDay) {
		this.attendanceDay = attendanceDay;
	}
	
	
	
	@Column(name="attendanceMonth")
	public int getAttendanceMonth() {
		return attendanceMonth;
	}
	public void setAttendanceMonth(int attendanceMonth) {
		this.attendanceMonth = attendanceMonth;
	}
	
	
	@Column(name="diffBetPunch")
	public int getDiffBetPunch() {
		return diffBetPunch;
	}
	public void setDiffBetPunch(int diffBetPunch) {
		this.diffBetPunch = diffBetPunch;
	}
	
	
	@Column(name="employeeFolderPath")
	public String getEmployeeFolderPath() {
		return employeeFolderPath;
	}
	public void setEmployeeFolderPath(String employeeFolderPath) {
		this.employeeFolderPath = employeeFolderPath;
	}
	
	
	@Column(name="dbBackupPath")
	public String getDbBackupPath() {
		return dbBackupPath;
	}
	public void setDbBackupPath(String dbBackupPath) {
		this.dbBackupPath = dbBackupPath;
	}
	
	
	
	
	
}
