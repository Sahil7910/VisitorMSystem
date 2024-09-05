package com.distna.domain.visitor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="visitors")
public class Visitors 
{
	private int visitorId;
	private String visitorName; 
	private boolean regularVisitor=false;
	private boolean vipVisitor=false; 
	private String company;
	private String visitorType;
	private int location;
	private String designation;
	private String phoneNo; 
	private String mobileNo;
	private String emailId;
	private String address;
	private String remarks; 
	private int visitCount;
	private String visitorPhoto;
	private String visitorFingerPrint;
	private String profilePhoto;
	
	@Id
	@GeneratedValue
	@Column(name="visitorId")
	public int getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(int visitorId) {
		this.visitorId = visitorId;
	}
	
	@Column(name="visitorName")
	public String getVisitorName() {
		return visitorName;
	}
	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}
	
	@Column(name="regularVisitor")
	public boolean isRegularVisitor() {
		return regularVisitor;
	}
	public void setRegularVisitor(boolean regularVisitor) {
		this.regularVisitor = regularVisitor;
	}
	
	@Column(name="vipVisitor")
	public boolean isVipVisitor() {
		return vipVisitor;
	}
	public void setVipVisitor(boolean vipVisitor) {
		this.vipVisitor = vipVisitor;
	}
	
	
	@Column(name="company")
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
	@Column(name="visitorType")
	public String getVisitorType() {
		return visitorType;
	}
	public void setVisitorType(String visitorType) {
		this.visitorType = visitorType;
	}
	
	@Column(name="location")
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	
	
	@Column(name="designation")
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
	@Column(name="phoneNo")
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	
	@Column(name="mobileNo")
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	
	@Column(name="emailId")
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	@Column(name="remarks")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	@Column(name="visitCount")
	public int getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	
	
	@Column(name="visitorPhoto")
	public String getVisitorPhoto() {
		return visitorPhoto;
	}
	public void setVisitorPhoto(String visitorPhoto) {
		this.visitorPhoto = visitorPhoto;
	}
	
	@Column(name="visitorFingerPrint")
	public String getVisitorFingerPrint() {
		return visitorFingerPrint;
	}
	public void setVisitorFingerPrint(String visitorFingerPrint) {
		this.visitorFingerPrint = visitorFingerPrint;
	}
	
	@Column(name="profilePhoto")
	public String getProfilePhoto() { 
		return profilePhoto; 
	}
    public String setProfilePhoto(String base64Img) { 
    	 return this.profilePhoto = base64Img; 
    }

	
	
}
