package com.distna.domain.employee;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="employee_skills")
public class EmployeeSkills {
	
	private int id;
	
	private int employeeId=0;
	
	private String skillName;
	
	private String skillType;
	
	private String skillLevel;
	
	private String description;
	
	private int experienceYear=0;
	
	private int experienceMonth=0;
	
	private String certification;
	
	private String attachment;
	
	private String createdBy;
	
	private String createdOn=Calendar.getInstance().getTime().toString() ;
	
	private String editedBy;
	
	private String editedOn=Calendar.getInstance().getTime().toString();
	
	
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	@Column(name="employee_id")
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name="skill_name")
	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Column(name="skill_type")
	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	@Column(name="skill_level")
	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="experience_year")
	public int getExperienceYear() {
		return experienceYear;
	}

	public void setExperienceYear(int experienceYear) {
		this.experienceYear = experienceYear;
	}

	@Column(name="experience_month")
	public int getExperienceMonth() {
		return experienceMonth;
	}

	public void setExperienceMonth(int experienceMonth) {
		this.experienceMonth = experienceMonth;
	}

	@Column(name="certification")
	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	@Column(name="attachment")
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@Column(name="created_by")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name="created_on")
	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name="edited_by")
	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	@Column(name="edited_on")
	public String getEditedOn() {
		return editedOn;
	}

	public void setEditedOn(String editedOn) {
		this.editedOn = editedOn;
	}
	
	
	
	
	

}
