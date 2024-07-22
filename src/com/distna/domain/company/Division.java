package com.distna.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="divisions")
public class Division {
	
	private int divisionId;
	
	private String divisionName;
	
	private String divisionCode;
	
	private String divisionDescription;
	
	private int divisionHead=0;
	
	private int locationId=0;
	
	
	

	@Id
	@GeneratedValue
	@Column(name="id")
	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	@Column(name="division_name")
	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	@Column(name="division_code")
	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	@Column(name="description")
	public String getDivisionDescription() {
		return divisionDescription;
	}

	public void setDivisionDescription(String divisionDescription) {
		this.divisionDescription = divisionDescription;
	}

	@Column(name="division_head")
	public int getDivisionHead() {
		return divisionHead;
	}

	public void setDivisionHead(int divisionHead) {
		this.divisionHead = divisionHead;
	}
	@Column(name="locationId")
	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	
	
	
	

}
