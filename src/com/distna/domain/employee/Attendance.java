package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="attendance")
public class Attendance {
	
	private int recordCount;
	private int empCode;
	private String Date;
	private String In_Time;
	private String Out_Time;
	
	
	
	@Id
	@GeneratedValue
	@Column(name="recordCount")
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
	@Column(name="empCode")
	public int getEmpCode() {
		return empCode;
	}
	public void setEmpCode(int empCode) {
		this.empCode = empCode;
	}
	
	@Column(name="Date")
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	
	@Column(name="In_Time")
	public String getIn_Time() {
		return In_Time;
	}
	public void setIn_Time(String in_Time) {
		In_Time = in_Time;
	}
	
	
	@Column(name="Out_Time")
	public String getOut_Time() {
		return Out_Time;
	}
	public void setOut_Time(String out_Time) {
		Out_Time = out_Time;
	}
	

	
	
}
