package com.distna.domain.devicemanagement;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
public class CardDetails {

	@Id
	private String CardID;
	private String DeviceID;
	
	
	
	public CardDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCardID() {
		return CardID;
	}
	public void setCardID(String cardID) {
		CardID = cardID;
	}
	public String getDeviceID() {
		return DeviceID;
	}
	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}
	
	
	
	}
