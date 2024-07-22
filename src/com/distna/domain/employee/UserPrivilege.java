package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userprivileges")
public class UserPrivilege 
{
	private int id;
	private String Name;
	private String privilege;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	/*
	 * @Column(name="Name") public String getName() { return Name; } public void
	 * setName(String Name) { this.Name = Name; }
	 */
	

	@Column(name="privilege")
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
}
