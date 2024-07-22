package com.distna.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="departments")
public class Departments {
	
	private int id;
	private String name;
	private int head=0;
	private int division=0;
	
	private String email;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="head")
	public int getHead() {
		return head;
	}
	public void setHead(int head) {
		this.head = head;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="division")
	public int getDivision() {
		return division;
	}
	public void setDivision(int division) {
		this.division = division;
	}
}
