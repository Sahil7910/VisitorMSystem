package com.distna.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="states")
public class States {
	
 private int stateId;
 private String stateName;
 private int countryId=0;
 private String statewiselanguage;
 
 
 
 	@Id
	@GeneratedValue
	@Column(name="stateid")
public int getStateId() {
	return stateId;
}
public void setStateId(int stateId) {
	this.stateId = stateId;
}
@Column(name="state_name")
public String getStateName() {
	return stateName;
}
public void setStateName(String stateName) {
	this.stateName = stateName;
}
@Column(name="country_id")
public int getCountryId() {
	return countryId;
}
public void setCountryId(int countryId) {
	this.countryId = countryId;
}

 
@Column(name="languages")
public String getStatewiselanguage() {
	return statewiselanguage;
}
public void setStatewiselanguage(String statewiselanguage) {
	this.statewiselanguage = statewiselanguage;
}

}
