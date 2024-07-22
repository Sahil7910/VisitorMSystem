package com.distna.domain.employee;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity

@Table(name="employee_bank")
public class EmployeeBank {

	
	private int id;
	private int employeeId=0;
	private String bankName;
	private String bankBranchName;
	private String branchFullAddress; 
	
	private String accountNo;
	private String IFSC_CODE;
//	private int pan_no;
//	//private String pan_no;




	private String attachment;
private String createdBy;
	
	private String createdOn=Calendar.getInstance().getTime().toString() ;
	
	private String editedBy;
	
	private String editedOn=Calendar.getInstance().getTime().toString();
	
	
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name="employee_id")
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	
	
	
	@Column(name="bank_Name")
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	
	@Column(name="Branch_Name")
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	
	
	@Column(name="Branch_Add")
	public String getBranchFullAddress() {
		return branchFullAddress;
	}
	public void setBranchFullAddress(String branchFullAddress) {
		this.branchFullAddress = branchFullAddress;
	}
	
	
	@Column(name="account_No")
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	
	@Column(name="IFSC_CODE")
	public String getIFSC_CODE() {
		return IFSC_CODE;
	}

	public void setIFSC_CODE(String iFSC_CODE) {
		IFSC_CODE = iFSC_CODE;
	}

	
	@Column(name="attachment")
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	


	
	
	
	@Column(name="created_by")
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_on")
	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="edited_by")
	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	@Column(name="edited_on")
	public String getEditedOn() {
		return editedOn;
	}

	public void setEditedOn(String editedOn) {
		this.editedOn = editedOn;
	}
	
//	@Column(name="Pan_No")
//	public int getPan_no() {
//		return pan_no;
//	}
//
//	public void setPan_no(int pan_no) {
//		this.pan_no = pan_no;
//	}

//	@Column(name="Pan_No")
//	public String getPan_no() {
//		return pan_no;
//	}
//
//	public void setPan_no(String pan_no) {
//		this.pan_no = pan_no;
//	}
	
	
}
