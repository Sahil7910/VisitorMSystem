package com.distna.domain.privileges;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="masterprivileges")
public class MasterPrivileges {
	
	private int id;
	private String privilegeName;
	private String privilegeStatus;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="privilegeName")
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	@Column(name="privilegeStatus")
	public String getPrivilegeStatus() {
		return privilegeStatus;
	}
	public void setPrivilegeStatus(String privilegeStatus) {
		this.privilegeStatus = privilegeStatus;
	}
	
}
