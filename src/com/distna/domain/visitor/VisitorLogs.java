package com.distna.domain.visitor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="visitor_logs")
public class VisitorLogs 
{
	private int logId; 
	private int employeeId; 
	private String status; 
	private int gateNo; 
	private String purpose; 
	private String inTime; 
	private String outTime="SignOut"; 
	private String vehicle; 
	private String vehicleNo; 
	private String visitorCountType; 
	private int noOfPeople; 
	private String idCard; 
	private String badgeValidity;
	private String badgeRemarks; 
	private String materialsCarried; 
	private int visitorId;
	private String approvalStatus="pending"; 
	
	@Id
	@GeneratedValue
	@Column(name="logId")
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	
	@Column(name="employeeId")
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Column(name="gateNo")
	public int getGateNo() {
		return gateNo;
	}
	public void setGateNo(int gateNo) {
		this.gateNo = gateNo;
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
	
	
	@Column(name="vehicle")
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	
	
	@Column(name="vehicleNo")
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	
	@Column(name="visitorCountType")
	public String getVisitorCountType() {
		return visitorCountType;
	}
	public void setVisitorCountType(String visitorCountType) {
		this.visitorCountType = visitorCountType;
	}
	
	
	@Column(name="noOfPeople")
	public int getNoOfPeople() {
		return noOfPeople;
	}
	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}
	
	
	@Column(name="idCard")
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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
	
	
	
	@Column(name="visitorId")
	public int getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(int visitorId) {
		this.visitorId = visitorId;
	}
	
	@Column(name="approvalStatus")
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	
	
	
	
	
}
