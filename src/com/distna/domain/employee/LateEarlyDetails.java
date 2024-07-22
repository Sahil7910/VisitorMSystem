package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class LateEarlyDetails {

	
	//id, location, late_in, late_out, early_in, early_out;
	private int id;
	private int location;
	private String Late_In;
	private String Late_Out;
	private String Early_In;
	private String Early_Out;
	
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="location")
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	
	@Column(name="late_in")
	public String getLate_In() {
		return Late_In;
	}
	public void setLate_In(String late_In) {
		Late_In = late_In;
	}
	
	@Column(name="late_out")
	public String getLate_Out() {
		return Late_Out;
	}
	public void setLate_Out(String late_Out) {
		Late_Out = late_Out;
	}
	
	@Column(name="early_in")
	public String getEarly_In() {
		return Early_In;
	}
	public void setEarly_In(String early_In) {
		Early_In = early_In;
	}
	
	@Column(name="early_out")
	public String getEarly_Out() {
		return Early_Out;
	}
	public void setEarly_Out(String early_Out) {
		Early_Out = early_Out;
	}
	
	
	
}
