package com.distna.domain.visitor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="visitor_type")
public class VisitorType {
	
	private int visitorTypeId;
	
	private String visitorTypeVariable;

	@Id
	@GeneratedValue
	@Column(name="visitorTypeId")
	public int getVisitorTypeId() {
		return visitorTypeId;
	}

	public void setVisitorTypeId(int visitorTypeId) {
		this.visitorTypeId = visitorTypeId;
	}

	
	
	@Column(name="visitorTypeVariable")	
	public String getVisitorTypeVariable() {
		return visitorTypeVariable;
	}

	public void setVisitorTypeVariable(String visitorTypeVariable) {
		this.visitorTypeVariable = visitorTypeVariable;
	}

	
	
	
	

}
