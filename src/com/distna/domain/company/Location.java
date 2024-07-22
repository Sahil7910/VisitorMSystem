package com.distna.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.cglib.core.TinyBitSet;


@Entity
@Table(name="locations")
public class Location {
	
	private int id;
	private String location;
	private int sub_locationof=0;
	private String address1;
	private String address2;
	private int city=0;
	private int state=0;
	private int country=0;
	private String phone="";
	private String email="";
	private String website="";
	private String details;
	private String gps_location;
	private String postal_code;
	private String fax;
	private String allowed_ips;
	private String attention;
	private boolean active;
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="location")
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Column(name="sub_locationof")
	public int getSub_locationof() {
		return sub_locationof;
	}
	public void setSub_locationof(int sub_locationof) {
		this.sub_locationof = sub_locationof;
	}
	
	@Column(name="address1")
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	@Column(name="address2")
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	@Column(name="city")
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	
	@Column(name="state")
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	@Column(name="country")
	public int getCountry() {
		return country;
	}
	public void setCountry(int country) {
		this.country = country;
	}
	
	
	@Column(name="phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	@Column(name="mail")
	public void setEmail(String email) {
		this.email = email;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Column(name="website")
	public String getWebsite() {
		return website;
	}
	public String getEmail() {
		return email;
	}
	
	
	
	@Column(name="details")
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
	@Column(name="gps_location")
	public String getGps_location() {
		return gps_location;
	}
	public void setGps_location(String gps_location) {
		this.gps_location = gps_location;
	}
	
	
	@Column(name="postal_code")
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	
	
	@Column(name="fax")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	
	@Column(name="allowed_ips")
	public String getAllowed_ips() {
		return allowed_ips;
	}
	public void setAllowed_ips(String allowed_ips) {
		this.allowed_ips = allowed_ips;
	}
	
	@Column(name="attention")
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	
	@Column(name="active")
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
	
	

}
