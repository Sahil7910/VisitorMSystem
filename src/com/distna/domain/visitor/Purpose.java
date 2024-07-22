package com.distna.domain.visitor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="visitor_purpose")
public class Purpose {
int id;
String Purpose;

@Id
@GeneratedValue
@Column(name="id")
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
@Column(name="purpose")
public String getPurpose() {
	return Purpose;
}
public void setPurpose(String purpose) {
	Purpose = purpose;
}

}
