package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="attendance_logs_bulk_entry")
public class AttendanceLogsBulkEntry {

	private int id; 
    private int workID=0; 
	//private String employee_id;
    private String recordDate;
   // private String equipmentId;
    private int status=0;
    //private String fromIp;
    private int shift=0;
    private int location=0;
    private String inTime="0";
    private String outTime="0";
    private String breakIn="0";
    private String breakOut="0";
    
    private String breakIn2="0";
    private String breakOut2="0";
    
    private String timeAsPerShftTimings;
    private int overNight=0;
    private String startDateAndTime="0";
    private String endDateAndTime="0";
    
//    private String earlyComing;
//    private String lateComing;
//    private String earlyGoing;
//    private String lateGoing;
    
    private boolean exceptionFlag=true;
    
private String startDateAndTimeGrace="0";
	
	
	
    
	@Id
    @GeneratedValue
    @Column(name="id")
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	 @Column(name="WorkID")
	public int getWorkID() {
		return workID;
	}
	public void setWorkID(int workID) {
		this.workID = workID;
	}
	 @Column(name="Recorddate")
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
//
//	/*
//	 * @Column(name="EquipmentID") public String getEquipmentId() { return
//	 * equipmentId; } public void setEquipmentId(String equipmentId) {
//	 * this.equipmentId = equipmentId;
//	 */	}
	 @Column(name="status")
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
//	 @Column(name="from_ip")
//	public String getFromIp() {
//		return fromIp;
//	}
//	public void setFromIp(String fromIp) {
//		this.fromIp = fromIp;
//	}
	 @Column(name="shift")
	public int getShift() {
		return shift;
	}
	public void setShift(int shift) {
		this.shift = shift;
	}
	 @Column(name="Location")
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	 @Column(name="inTime")
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	 @Column(name="outTime")
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	 @Column(name="breakIn")
	public String getBreakIn() {
		return breakIn;
	}
	public void setBreakIn(String breakIn) {
		this.breakIn = breakIn;
	}
	 @Column(name="breakOut")
	public String getBreakOut() {
		return breakOut;
	}
	public void setBreakOut(String breakOut) {
		this.breakOut = breakOut;
	}
	
	
	@Column(name="breakIn2")
	 public String getBreakIn2() {
		return breakIn2;
	}
	public void setBreakIn2(String breakIn2) {
		this.breakIn2 = breakIn2;
	}
	
	 @Column(name="breakOut2")
	public String getBreakOut2() {
		return breakOut2;
	}
	public void setBreakOut2(String breakOut2) {
		this.breakOut2 = breakOut2;
	}
	
	
	@Column(name="timeAsPerShftTimings")
	public String getTimeAsPerShftTimings() {
		return timeAsPerShftTimings;
	}
	public void setTimeAsPerShftTimings(String timeAsPerShftTimings) {
		this.timeAsPerShftTimings = timeAsPerShftTimings;
	}
	
	 @Column(name="overNight")
	 public int getOverNight() {
			return overNight;
		}
		public void setOverNight(int overNight) {
			this.overNight = overNight;
		}
		
		 @Column(name="startDateAndTime")
		public String getStartDateAndTime() {
			return startDateAndTime;
		}
		public void setStartDateAndTime(String startDateAndTime) {
			this.startDateAndTime = startDateAndTime;
		}
		
		 @Column(name="endDateAndTime")
		public String getEndDateAndTime() {
			return endDateAndTime;
		}
		public void setEndDateAndTime(String endDateAndTime) {
			this.endDateAndTime = endDateAndTime;
		}
		
		
		
//		
//		@Column(name="earlyComing")
//		 public String getEarlyComing() {
//			return earlyComing;
//		}
//		public void setEarlyComing(String earlyComing) {
//			this.earlyComing = earlyComing;
//		}
//		
//		@Column(name="lateComing")
//		public String getLateComing() {
//			return lateComing;
//		}
//		public void setLateComing(String lateComing) {
//			this.lateComing = lateComing;
//		}
		
//		@Column(name="earlyGoing")
//		public String getEarlyGoing() {
//			return earlyGoing;
//		}
//		public void setEarlyGoing(String earlyGoing) {
//			this.earlyGoing = earlyGoing;
//		}
//		
//		@Column(name="lateGoing")
//		public String getLateGoing() {
//			return lateGoing;
//		}
//		public void setLateGoing(String lateGoing) {
//			this.lateGoing = lateGoing;
//		}
		
		
		@Column(name="exceptionFlag")
		public boolean isExceptionFlag() {
			return exceptionFlag;
		}
		public void setExceptionFlag(boolean exceptionFlag) {
			this.exceptionFlag = exceptionFlag;
		}
		
		@Column(name="startDateAndTimeGrace")
		public String getStartDateAndTimeGrace() {
			return startDateAndTimeGrace;
		}
		public void setStartDateAndTimeGrace(String startDateAndTimeGrace) {
			this.startDateAndTimeGrace = startDateAndTimeGrace;
		}
		
		
}
