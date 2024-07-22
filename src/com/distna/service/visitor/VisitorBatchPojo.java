package com.distna.service.visitor;

public class VisitorBatchPojo {
	
	String batchId;
	int visitorId;
	String name;
	String cmpyName;
	String date;
	String intime;
	String visiting;
	String purpose;
	
	
	public String getVisiting() {
		return visiting;
	}
	public void setVisiting(String visiting) {
		this.visiting = visiting;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public int getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(int visitorId) {
		this.visitorId = visitorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCmpyName() {
		return cmpyName;
	}
	public void setCmpyName(String cmpyName) {
		this.cmpyName = cmpyName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
}
