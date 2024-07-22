package com.distna.domain.leaves;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="leave_type")
public class LeaveType {
	
	private int id; 
	private String name;
	private String shortname;
	private int yearlyLimit;
	private int carryForwardLimit;
	private String applicableTo="all";
	private boolean paymentApplicable=true;
	private boolean allowNegativePayBalance;
	private String description;
	private int rank=0;
	
	
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
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="rank")
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	@Column(name="shortname")
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	
	
	@Column(name="yearlyLimit")
	public int getYearlyLimit() {
		return yearlyLimit;
	}
	public void setYearlyLimit(int yearlyLimit) {
		this.yearlyLimit = yearlyLimit;
	}
	
	@Column(name="carryForwardLimit")
	public int getCarryForwardLimit() {
		return carryForwardLimit;
	}
	public void setCarryForwardLimit(int carryForwardLimit) {
		this.carryForwardLimit = carryForwardLimit;
	}
	
	
	@Column(name="applicableTo")
	public String getApplicableTo() {
		return applicableTo;
	}
	public void setApplicableTo(String applicableTo) {
		this.applicableTo = applicableTo;
	}
	
	@Column(name="paymentApplicable")
	public boolean isPaymentApplicable() {
		return paymentApplicable;
	}
	public void setPaymentApplicable(boolean paymentApplicable) {
		this.paymentApplicable = paymentApplicable;
	}
	
	
	@Column(name="allowNegativePayBalance")
	public boolean isAllowNegativePayBalance() {
		return allowNegativePayBalance;
	}
	public void setAllowNegativePayBalance(boolean allowNegativePayBalance) {
		this.allowNegativePayBalance = allowNegativePayBalance;
	}
	
	
	

}
