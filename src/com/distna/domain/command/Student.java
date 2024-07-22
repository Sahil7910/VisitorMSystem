package com.distna.domain.command;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Eggsy - eggsy _at_ eggsylife.co.uk
 *
 */
@Entity
@Table(name="student")
public class Student{
	private int id;
	private String studentName = "";
	
	@Id
	@Column(name = "id")	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	@Column(name = "name")
	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}
