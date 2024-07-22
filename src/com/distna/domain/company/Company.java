package com.distna.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company {
	private int id;
	private String companyName;
	private String logo;
	private int billingLocation=0;
	private int shippingLocation=0;
	private String website="";
	private boolean displayLogo;
	private int firstFiscalMonth=0;
	private int defaultCurrency=0;
	private int defaultTimeZone=0;
	private String companyAddress;
	private int allowedLeaves=0;
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="company_name")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name="logo")
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Column(name="billing_location")
	public int getBillingLocation() {
		return billingLocation;
	}
	public void setBillingLocation(int billingLocation) {
		this.billingLocation = billingLocation;
	}
	
	@Column(name="shipping_location")
	public int getShippingLocation() {
		return shippingLocation;
	}
	public void setShippingLocation(int shippingLocation) {
		this.shippingLocation = shippingLocation;
	}
	
	@Column(name="website")
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	
	@Column(name="display_logo")
	public boolean isDisplayLogo() {
		return displayLogo;
	}
	public void setDisplayLogo(boolean displayLogo) {
		this.displayLogo = displayLogo;
	}
	
	
	@Column(name="first_fiscal_month")
	public int getFirstFiscalMonth() {
		return firstFiscalMonth;
	}
	public void setFirstFiscalMonth(int firstFiscalMonth) {
		this.firstFiscalMonth = firstFiscalMonth;
	}
	
	@Column(name="default_currency")
	public int getDefaultCurrency() {
		return defaultCurrency;
	}
	public void setDefaultCurrency(int defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}
	
	@Column(name="default_time_zone")
	public int getDefaultTimeZone() {
		return defaultTimeZone;
	}
	public void setDefaultTimeZone(int defaultTimeZone) {
		this.defaultTimeZone = defaultTimeZone;
	}
	@Column(name="company_address")
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	@Column(name="allowedLeaves")
	public int getAllowedLeaves() {
		return allowedLeaves;
	}
	public void setAllowedLeaves(int allowedLeaves) {
		this.allowedLeaves = allowedLeaves;
	}
}
