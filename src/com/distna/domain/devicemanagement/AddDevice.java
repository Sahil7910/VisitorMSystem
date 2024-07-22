package com.distna.domain.devicemanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="add_device")
public class AddDevice {
	
	private int id;
	private String deviceName;
	private String shortName;
	private String connectionType;
	private String serialNo;
	private String deviceDirection;
	private String baudRate;
	private String comPort;
	private String deviceType; 
	private String ipAddress; 
	
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="DeviceName")
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	@Column(name="ShortName")
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Column(name="SerialNo")
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	@Column(name="ConnectionType")
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	@Column(name="DeviceDirection")
	public String getDeviceDirection() {
		return deviceDirection;
	}
	public void setDeviceDirection(String deviceDirection) {
		this.deviceDirection = deviceDirection;
	}
	@Column(name="BaudRate")
	public String getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}
	@Column(name="ComPort")
	public String getComPort() {
		return comPort;
	}
	public void setComPort(String comPort) {
		this.comPort = comPort;
	}
	@Column(name="DeviceType")
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	@Column(name="IP")
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
