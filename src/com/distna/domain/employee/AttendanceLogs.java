package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="attendance_logs")
public class AttendanceLogs {
	
	
	private int id;
	private int workID=0;
	private String recordDate;
	private int status=0;
	private int shift=0;
	private int location=0;
	private String recordTime;
	private String statusInString;
	private String Date1;
	
	
	
	
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
	@Column(name="Recorddate")
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
//	@Column(name="Signonthecard")
//	public String getSignOnTheCard() {
//		return signOnTheCard;
//	}
//	public void setSignOnTheCard(String signOnTheCard) {
//		this.signOnTheCard = signOnTheCard;
//	}
//	@Column(name="EquipmentID")
//	public String getEquipmentID() {
//		return equipmentID;
//	}
//	public void setEquipmentID(String equipmentID) {
//		this.equipmentID = equipmentID;
//	}
	
	
	
	@Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
//	@Column(name="machineWorkId")
//	public int getMachineWorkId() {
//		return machineWorkId;
//	}
//	public void setMachineWorkId(int machineWorkId) {
//		this.machineWorkId = machineWorkId;
//	}
//	@Column(name="from_ip")
//	public String getFromIp() {
//		return fromIp;
//	}
//	public void setFromIp(String fromIp) {
//		this.fromIp = fromIp;
//	}
	
	
	@Column(name="shift")
	public int getShift() {
		return shift;
	}
	public void setShift(int shift) {
		this.shift = shift;
	}
	@Column(name="Location")
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	@Column(name="Recordtime")
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	
	@Column(name="statusInString")
	public String getStatusInString() {
		return statusInString;
	}
	public void setStatusInString(String statusInString) {
		this.statusInString = statusInString;
	}
	public String getDate1() {
		return Date1;
	}
	public void setDate1(String date1) {
		Date1 = date1;
	}
	
}
