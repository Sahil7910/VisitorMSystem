package com.distna.domain.visitor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicle_details")
public class VehicleDetails
{
	private int vehicleId;
	private String vehicleNumber;
	private String vehicleBrand; 
	private String vehicleModel;
	private String vehicleEdition; 
	private String vehicleType;
	private String ownerName; 
	private String ownerPhoneNo;
	
	@Id
	@GeneratedValue
	@Column(name="vehicleId")
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	@Column(name="vehicleNumber")
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	
	@Column(name="vehicleBrand")
	public String getVehicleBrand() {
		return vehicleBrand;
	}
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	
	
	@Column(name="vehicleModel")
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	
	
	@Column(name="vehicleEdition")
	public String getVehicleEdition() {
		return vehicleEdition;
	}
	public void setVehicleEdition(String vehicleEdition) {
		this.vehicleEdition = vehicleEdition;
	}
	
	
	@Column(name="vehicleType")
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
	@Column(name="ownerName")
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	
	@Column(name="ownerPhoneNo")
	public String getOwnerPhoneNo() {
		return ownerPhoneNo;
	}
	public void setOwnerPhoneNo(String ownerPhoneNo) {
		this.ownerPhoneNo = ownerPhoneNo;
	}
	
	
	
	
	
	
}
