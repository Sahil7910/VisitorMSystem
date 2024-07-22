package com.distna.domain.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="canteen_timings")
public class CanteenTimings {
	
	private int timingId;
	
	private String timingName;
	
	private String shortName;
	
	private String startTime;
	
	private String endTime;
	
	private int defaultItem;

	@Id
	@GeneratedValue
	@Column(name="timingId")
	public int getTimingId() {
		return timingId;
	}

	public void setTimingId(int timingId) {
		this.timingId = timingId;
	}

	@Column(name="timingName")
	public String getTimingName() {
		return timingName;
	}

	public void setTimingName(String timingName) {
		this.timingName = timingName;
	}

	@Column(name="shortName")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name="startTime")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name="endTime")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name="defaultItem")
	public int getDefaultItem() {
		return defaultItem;
	}

	public void setDefaultItem(int defaultItem) {
		this.defaultItem = defaultItem;
	}
	
	
	

}
