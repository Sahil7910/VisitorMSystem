package com.distna.domain.devicemanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userinfo")
public class UserInfo {
	
	int id;
 	
	private String enrollmentNo;
	private String emp_name;
	private int fpIndex;
	private String tmpData;
	private int privilege;
	private String password;
	private String enabled;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="enrollmentNo")
	public String getEnrollmentNo() {
		return enrollmentNo;
	}
	public void setEnrollmentNo(String enrollmentNo) {
		this.enrollmentNo = enrollmentNo;
	}
	@Column(name="emp_name")
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	@Column(name="fpIndex")
	public int getFpIndex() {
		return fpIndex;
	}
	public void setFpIndex(int fpIndex) {
		this.fpIndex = fpIndex;
	}
	@Column(name="tmpData")
	public String getTmpData() {
		return tmpData;
	}
	public void setTmpData(String tmpData) {
		this.tmpData = tmpData;
	}
	
	@Column(name="privilege")
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="enabled")
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
}
