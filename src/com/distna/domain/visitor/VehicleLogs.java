package com.distna.domain.visitor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicle_logs")
public class VehicleLogs 
{
	private int logId;
	private String status; 
	private String purpose;
	private String inTime;
	private String outTime; 
	private int concernPerson;
	private int gateNo; 
	private String badgeValidity; 
	private String badgeRemarks; 
	private String materialsCarried; 
	private int vehicleId;
	
	@Id
	@GeneratedValue
	@Column(name="logId")
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="purpose")
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	@Column(name="inTime")
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	
	@Column(name="outTime")
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	
	@Column(name="concernPerson")
	public int getConcernPerson() {
		return concernPerson;
	}
	public void setConcernPerson(int concernPerson) {
		this.concernPerson = concernPerson;
	}
	
	
	@Column(name="gateNo")
	public int getGateNo() {
		return gateNo;
	}
	public void setGateNo(int gateNo) {
		this.gateNo = gateNo;
	}
	
	
	@Column(name="badgeValidity")
	public String getBadgeValidity() {
		return badgeValidity;
	}
	public void setBadgeValidity(String badgeValidity) {
		this.badgeValidity = badgeValidity;
	}
	
	@Column(name="badgeRemarks")
	public String getBadgeRemarks() {
		return badgeRemarks;
	}
	public void setBadgeRemarks(String badgeRemarks) {
		this.badgeRemarks = badgeRemarks;
	}
	
	
	@Column(name="materialsCarried")
	public String getMaterialsCarried() {
		return materialsCarried;
	}
	public void setMaterialsCarried(String materialsCarried) {
		this.materialsCarried = materialsCarried;
	}
	
	@Column(name="vehicleId")
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	
	
	
	
}
