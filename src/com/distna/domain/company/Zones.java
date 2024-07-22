package com.distna.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="zones")
public class Zones {
	private int id;
	private String zoneName;
	private String description;
	private String relativeToGmt;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="zone_name")
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name="relative_to_gmt")
	public String getRelativeToGmt() {
		return relativeToGmt;
	}
	public void setRelativeToGmt(String relativeToGmt) {
		this.relativeToGmt = relativeToGmt;
	}
	
}
