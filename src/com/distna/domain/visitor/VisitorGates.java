package com.distna.domain.visitor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="visitor_gates")
public class VisitorGates {
	
	int id;
	String visitorGates;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name="gateNumber")
	public String getVisitorGates() {
		return visitorGates;
	}
	public void setVisitorGates(String visitorGates) {
		this.visitorGates = visitorGates;
	}
	
}
