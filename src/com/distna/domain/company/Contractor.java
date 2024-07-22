package com.distna.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name="contractor")
public class Contractor {
	
	
 	private int contractorId;
 	@NotBlank
 	@NotNull
 	private String contractorName;
 	@Length(max=4,min=2,message="Code Can Only Contain Two to Four Charactors")
 	private String code;
	private String description;
 	private int location=0;
 	private int division=0;
 	@NotNull @Min(1)
 	private int department=0;
 	private String contactPerson;
 	@Length(max=10,min=10,message="Phone Number's Length Not Valid")
 	@Pattern(regexp = "^[0-9]+$", message = "Phone Number Not Valid")
	private String contactNo;
	private String designationOfPerson;
	@Email
	private String emailId;
	@Id
	@GeneratedValue
	@Column(name="contractorId")
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	@Column(name="contractorName")
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	@Column(name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="location")
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	@Column(name="division")
	public int getDivision() {
		return division;
	}
	public void setDivision(int division) {
		this.division = division;
	}
	@Column(name="department")
	public int getDepartment() {
		return department;
	}
	public void setDepartment(int department) {
		this.department = department;
	}
	@Column(name="contactPerson")
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	@Column(name="contactNo")
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	@Column(name="designationOfPerson")
	public String getDesignationOfPerson() {
		return designationOfPerson;
	}
	public void setDesignationOfPerson(String designationOfPerson) {
		this.designationOfPerson = designationOfPerson;
	}
	@Column(name="emailId")
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
