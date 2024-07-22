package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="education")
public class Education {
	
	private int id;
	private int emp_id=0;
	private String emp_education;
	private String course_name;
	private String board_university;
	private String college;
	private String address;
	private int edu_city=0;
	private int edu_state=0; 
	private int edu_country=0;
	private String website="";
	private int duration=0;
	private String from_date;
	private String to_date;
	private String specialisation;
	private int percentage=0;
	private String remarks;
	private String attachment;
	private String created_by;
	private String created_on;
	private String edited_by;
	private String edited_on;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="employeeid")
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	
	@Column(name="qualification")
	public String getEmp_education() {
		return emp_education;
	}
	
	public void setEmp_education(String emp_education) {
		this.emp_education = emp_education;
	}
	
	@Column(name="course_name")
	public String getCourse_name() {
		return course_name;
	}
	
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	
	@Column(name="board_university")
	public String getBoard_university() {
		return board_university;
	}
	public void setBoard_university(String board_university) {
		this.board_university = board_university;
	}
	
	@Column(name="college")
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="city")
	public int getEdu_city() {
		return edu_city;
	}
	public void setEdu_city(int edu_city) {
		this.edu_city = edu_city;
	}
	
	@Column(name="state")
	public int getEdu_state() {
		return edu_state;
	}
	public void setEdu_state(int edu_state) {
		this.edu_state = edu_state;
	}
	
	@Column(name="country")
	public int getEdu_country() {
		return edu_country;
	}
	public void setEdu_country(int edu_country) {
		this.edu_country = edu_country;
	}
	
	
	@Column(name="website")
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Column(name="duration")
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	@Column(name="from_date")
	public String getFrom_date() {
		return from_date;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	
	@Column(name="to_date")
	public String getTo_date() {
		return to_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	
	@Column(name="specialisation")
	public String getSpecialisation() {
		return specialisation;
	}
	public void setSpecialisation(String specialisation) {
		this.specialisation = specialisation;
	}
	
	@Column(name="percentage")
	public int getPercentage() {
		return percentage;
	}
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	@Column(name="remarks")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Column(name="attachment")
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	@Column(name="created_by")
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	
	@Column(name="created_on")
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	
	@Column(name="edited_by")
	public String getEdited_by() {
		return edited_by;
	}
	public void setEdited_by(String edited_by) {
		this.edited_by = edited_by;
	}
	
	@Column(name="edited_on")
	public String getEdited_on() {
		return edited_on;
	}
	public void setEdited_on(String edited_on) {
		this.edited_on = edited_on;
	}
	
	
	
	
	

}
