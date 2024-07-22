package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee_assessment")
public class Assesment {

	
	private int id;
	private int empid=0;
	private int position=0;
	private String personality;
	private String communication;
	private String knowledge;
	private String strength;
	private String weakness;
	private String company_name;
	private String reason;
	private int interviewer1=0;
	private String date1;
	private String remark1;
	private int interviewer2=0;
	private String date2;
	private String remark2;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="empid")
	public int getEmpid() {
		return empid;
	}
	public void setEmpid(int empid) {
		this.empid = empid;
	}
	
	@Column(name="position")
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	@Column(name="personality")
	public String getPersonality() {
		return personality;
	}
	public void setPersonality(String personality) {
		this.personality = personality;
	}
	
	@Column(name="communication")
	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
	}
	
	@Column(name="strength")
	public String getStrength() {
		return strength;
	}
	public void setStrength(String strength) {
		this.strength = strength;
	}
	
	@Column(name="weakness")
	public String getWeakness() {
		return weakness;
	}
	public void setWeakness(String weakness) {
		this.weakness = weakness;
	}
	
	@Column(name="company_name")
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
	@Column(name="reason")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	
	@Column(name="date1")
	public String getDate1() {
		return date1;
	}
	public void setDate1(String date1) {
		this.date1 = date1;
	}
	
	@Column(name="remark1")
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@Column(name="interviewer1")
	public int getInterviewer1() {
		return interviewer1;
	}
	public void setInterviewer1(int interviewer1) {
		this.interviewer1 = interviewer1;
	}
	
	@Column(name="interviewer2")
	public int getInterviewer2() {
		return interviewer2;
	}
	public void setInterviewer2(int interviewer2) {
		this.interviewer2 = interviewer2;
	}
	
	
	@Column(name="date2")
	public String getDate2() {
		return date2;
	}
	
	public void setDate2(String date2) {
		this.date2 = date2;
	}
	
	@Column(name="remark2")
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@Column(name="knowledge")
	public String getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}
	
	
	
	
	
	
}
