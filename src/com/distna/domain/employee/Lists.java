package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lists")
public class Lists {
	
	private int id;
	private String category;
	private String label;
	private String name;
	private float numericValue;
	private int integerValue=0;
	private int order=0;
	private String formUsed;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="category")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Column(name="label")
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="numeric_value")
	public float getNumericValue() {
		return numericValue;
	}
	public void setNumericValue(float numericValue) {
		this.numericValue = numericValue;
	}
	
	@Column(name="integer_value")
	public int getIntegerValue() {
		return integerValue;
	}
	public void setIntegerValue(int integerValue) {
		this.integerValue = integerValue;
	}
	
	@Column(name="order")
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	@Column(name="form_used")
	public String getFormUsed() {
		return formUsed;
	}
	public void setFormUsed(String formUsed) {
		this.formUsed = formUsed;
	}
	
}
