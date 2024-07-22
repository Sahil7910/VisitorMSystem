package com.distna.domain.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;


@Entity
@Table(name="employees")
public class Employee {
	private int employeeId;
	private String firstName;
	private String lastName;
	private String email;
	private int departmentId=0;
	private String password;
	private int active=0;
	private int employeeNo=0;
	private String mobile="";
	private String introduction;
	private String photo;
	private int designation=0; 
	private String dateOfBirth;
	private String joiningDate;
	private String confirmationDate;
	private int allowedLeaves=0;
	private String leavesStartDate;
	private int supervisor=0;
	private String gender;
	private String maritalStatus;
	private String identificationCard; 
	private String permanentAddress;
	private String currentAddress;
	private String emergencyContact; 
	private String relativesFriends; 
	private String companyFriends;
	private int yearsExperience=0; 
	private String education;
	private String languages; 
	private int location=0;
	private String skills; 
	private int childrenCount=0;
	private String personalEmails; 
	private String allPhones="";
	private String identificationNo;
	private String resume;
	private String allChatIds; 
	private int employment_status=0; 
	private String privledge_by; 
	private String document1;
	private String document2; 
	private String document3; 
	private String document4; 
	private int homeDistance=0; 
	private int onewayTime=0;
	private String travelMode;
	private String homeGpsLocation; 
	private String panNo;
	private int workspace=0; 
	private String jobCode; 
	private int shiftDefaul=0; 
	private int currentCity=0;
	private int currentCountry=0; 
	private int permanentCity=0;
	private int permanentCountry=0; 
	private int currentState=0;
	private int permanentState=0; 
	private String currentAddress2;
	private String permanentAddress2;
	private String createdOn; 
	private String createdBy; 
	private String updatedOn; 
	private String updatedBy; 
	private String passwordResetDate; 
	private String middleName;
	private String fatherhusbandName; 
	private String currentPostalCode=""; 
	private String permanentPostalCode; 
	private String workPhone="";
	private String workPhoneExt; 
	private String homePhone="";
	private String faxPhone=""; 
	private String homeEmail;
	private String terminationDate; 
	private String notes;
	private int currency=0;
	private int timezone=0;
	private String timeFormat;
	private String groupId="";
	private boolean probation=false;
	private int gradeLevel=0;
	private String incomeTaxId; 
	private String drivingLicenseId;
	private String passportNo; 
	private String civilId;
	private String insuranceId; 
	private String stateFundId;
	private String stateInsuranceId; 
	private String socialSecurityId;
	private String healthInsuranceId; 
	private String otherNd;
	private String bankName;
	private String bankBranchName;
	private String branchFullAddress; 
	private String accountNo;
	private String IFSC_CODE;
	
	private String highestQualifications;
	private String passingYear; 
	private String collegeName; 
	private String universityName; 
	private String signatureScan; 
	
	private String photo2;
	private String photo3;
	private String secretQuestion1; 
	private String secretAnswer1; 
	private String secretQuestion2; 
	private String secretAnswer2; 
	private int division=0;
	
	private int adminFlag=0;
	private String rediffChatId;
	private String gtalkChatId;
	private String msnChatId;
	private String yahooChatId;
	private String skypeChatId;
	private int leavesUsed=0;
	
	
	
	
	@Id

	@Column(name="employee_id")
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	@Column(name="first_name")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="department_id")
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="active")
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	@Column(name="employee_no")
	public int getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(int employeeNo) {
		this.employeeNo = employeeNo;
	}
	@Column(name="mobile")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name="introduction")
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@Column(name="photo")
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	@Column(name="designation")
	public int getDesignation() {
		return designation;
	}
	public void setDesignation(int designation) {
		this.designation = designation;
	}
	@Column(name="date_of_birth")
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	@Column(name="joining_date")
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	
	@Column(name="confirmation_date")
	public String getConfirmationDate() {
		return confirmationDate;
	}
	public void setConfirmationDate(String confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
	
	
	
	@Column(name="allowed_leaves")
	public int getAllowedLeaves() {
		return allowedLeaves;
	}
	public void setAllowedLeaves(int allowedLeaves) {
		this.allowedLeaves = allowedLeaves;
	}
	@Column(name="leaves_start_date")
	public String getLeavesStartDate() {
		return leavesStartDate;
	}
	public void setLeavesStartDate(String leavesStartDate) {
		this.leavesStartDate = leavesStartDate;
	}
	@Column(name="supervisor")
	public int getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}
	@Column(name="gender")
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Column(name="marital_status")
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	@Column(name="identification_card")
	public String getIdentificationCard() {
		return identificationCard;
	}
	public void setIdentificationCard(String identificationCard) {
		this.identificationCard = identificationCard;
	}
	@Column(name="permanent_address")
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	@Column(name="current_address")
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	@Column(name="emergency_contact")
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	@Column(name="relatives_friends")
	public String getRelativesFriends() {
		return relativesFriends;
	}
	public void setRelativesFriends(String relativesFriends) {
		this.relativesFriends = relativesFriends;
	}
	@Column(name="company_friends")
	public String getCompanyFriends() {
		return companyFriends;
	}
	public void setCompanyFriends(String companyFriends) {
		this.companyFriends = companyFriends;
	}
	@Column(name="yrs_experience")
	public int getYearsExperience() {
		return yearsExperience;
	}
	public void setYearsExperience(int yearsExperience) {
		this.yearsExperience = yearsExperience;
	}
	@Column(name="education")
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	@Column(name="languages")
	public String getLanguages() {
		return languages;
	}
	public void setLanguages(String languages) {
		this.languages = languages;
	}
	@Column(name="location")
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	@Column(name="skills")
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	@Column(name="children_count")
	public int getChildrenCount() {
		return childrenCount;
	}
	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}
	@Column(name="personal_emails")
	public String getPersonalEmails() {
		return personalEmails;
	}
	public void setPersonalEmails(String personalEmails) {
		this.personalEmails = personalEmails;
	}
	@Column(name="all_phones")
	public String getAllPhones() {
		return allPhones;
	}
	public void setAllPhones(String allPhones) {
		this.allPhones = allPhones;
	}
	@Column(name="identification_no")
	public String getIdentificationNo() {
		return identificationNo;
	}
	public void setIdentificationNo(String identificationNo) {
		this.identificationNo = identificationNo;
	}
	@Column(name="resume")
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	@Column(name="all_chat_ids")
	public String getAllChatIds() {
		return allChatIds;
	}
	public void setAllChatIds(String allChatIds) {
		this.allChatIds = allChatIds;
	}
	@Column(name="employment_status")
	public int getEmployment_status() {
		return employment_status;
	}
	public void setEmployment_status(int employment_status) {
		this.employment_status = employment_status;
	}
	@Column(name="privledge_by")
	public String getPrivledge_by() {
		return privledge_by;
	}
	public void setPrivledge_by(String privledge_by) {
		this.privledge_by = privledge_by;
	}
	@Column(name="document1")
	public String getDocument1() {
		return document1;
	}
	public void setDocument1(String document1) {
		this.document1 = document1;
	}
	@Column(name="document2")
	public String getDocument2() {
		return document2;
	}
	public void setDocument2(String document2) {
		this.document2 = document2;
	}
	@Column(name="document3")
	public String getDocument3() {
		return document3;
	}
	public void setDocument3(String document3) {
		this.document3 = document3;
	}
	@Column(name="document4")
	public String getDocument4() {
		return document4;
	}
	public void setDocument4(String document4) {
		this.document4 = document4;
	}
	@Column(name="home_distance")
	public int getHomeDistance() {
		return homeDistance;
	}
	public void setHomeDistance(int homeDistance) {
		this.homeDistance = homeDistance;
	}
	@Column(name="oneway_time")
	public int getOnewayTime() {
		return onewayTime;
	}
	public void setOnewayTime(int onewayTime) {
		this.onewayTime = onewayTime;
	}
	@Column(name="travel_mode")
	public String getTravelMode() {
		return travelMode;
	}
	public void setTravelMode(String travelMode) {
		this.travelMode = travelMode;
	}
	@Column(name="home_gpslocation")
	public String getHomeGpsLocation() {
		return homeGpsLocation;
	}
	public void setHomeGpsLocation(String homeGpsLocation) {
		this.homeGpsLocation = homeGpsLocation;
	}
	@Column(name="pan_no")
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	@Column(name="workspace")
	public int getWorkspace() {
		return workspace;
	}
	public void setWorkspace(int workspace) {
		this.workspace = workspace;
	}
	@Column(name="job_code")
	public String getJobCode() {
		return jobCode;
	}
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}
	@Column(name="shift_default")
	public int getShiftDefaul() {
		return shiftDefaul;
	}
	public void setShiftDefaul(int shiftDefaul) {
		this.shiftDefaul = shiftDefaul;
	}
	@Column(name="current_city")
	public int getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(int currentCity) {
		this.currentCity = currentCity;
	}
	@Column(name="current_country")
	public int getCurrentCountry() {
		return currentCountry;
	}
	public void setCurrentCountry(int currentCountry) {
		this.currentCountry = currentCountry;
	}
	@Column(name="permanent_city")
	public int getPermanentCity() {
		return permanentCity;
	}
	
	public void setPermanentCity(int permanentCity) {
		this.permanentCity = permanentCity;
	}
	@Column(name="permanent_country")
	public int getPermanentCountry() {
		return permanentCountry;
	}
	public void setPermanentCountry(int permanentCountry) {
		this.permanentCountry = permanentCountry;
	}
	@Column(name="current_state")
	public int getCurrentState() {
		return currentState;
	}
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}
	@Column(name="permanent_state")
	public int getPermanentState() {
		return permanentState;
	}
	public void setPermanentState(int permanentState) {
		this.permanentState = permanentState;
	}
	@Column(name="current_address2")
	public String getCurrentAddress2() {
		return currentAddress2;
	}
	public void setCurrentAddress2(String currentAddress2) {
		this.currentAddress2 = currentAddress2;
	}
	@Column(name="permanent_address2")
	public String getPermanentAddress2() {
		return permanentAddress2;
	}
	public void setPermanentAddress2(String permanentAddress2) {
		this.permanentAddress2 = permanentAddress2;
	}
	@Column(name="created_on")
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	@Column(name="created_by")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name="updated_on")
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Column(name="updated_by")
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name="password_reset_date")
	public String getPasswordResetDate() {
		return passwordResetDate;
	}
	public void setPasswordResetDate(String passwordResetDate) {
		this.passwordResetDate = passwordResetDate;
	}
	@Column(name="middle_name")
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@Column(name="father_husband_name")
	public String getFatherhusbandName() {
		return fatherhusbandName;
	}
	public void setFatherhusbandName(String fatherhusbandName) {
		this.fatherhusbandName = fatherhusbandName;
	}
	@Column(name="current_postal_code")
	public String getCurrentPostalCode() {
		return currentPostalCode;
	}
	public void setCurrentPostalCode(String currentPostalCode) {
		this.currentPostalCode = currentPostalCode;
	}
	@Column(name="permanent_postal_code")
	public String getPermanentPostalCode() {
		return permanentPostalCode;
	}
	public void setPermanentPostalCode(String permanentPostalCode) {
		this.permanentPostalCode = permanentPostalCode;
	}
	@Column(name="work_phone")
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	@Column(name="work_phone_ext")
	public String getWorkPhoneExt() {
		return workPhoneExt;
	}
	
	public void setWorkPhoneExt(String workPhoneExt) {
		this.workPhoneExt = workPhoneExt;
	}
	@Column(name="home_phone")
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	@Column(name="fax_phone")
	public String getFaxPhone() {
		return faxPhone;
	}
	public void setFaxPhone(String faxPhone) {
		this.faxPhone = faxPhone;
	}
	@Column(name="home_email")
	public String getHomeEmail() {
		return homeEmail;
	}
	public void setHomeEmail(String homeEmail) {
		this.homeEmail = homeEmail;
	}
	@Column(name="termination_date")
	public String getTerminationDate() {
		return terminationDate;
	}
	public void setTerminationDate(String terminationDate) {
		this.terminationDate = terminationDate;
	}
	@Column(name="notes")
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	@Column(name="currency")
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	@Column(name="timezone")
	public int getTimezone() {
		return timezone;
	}
	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}
	@Column(name="time_format")
	public String getTimeFormat() {
		return timeFormat;
	}
	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
	@Column(name="group_id")
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	@Column(name="probation")
	public boolean isProbation() {
		return probation;
	}
	public void setProbation(boolean probation) {
		this.probation = probation;
	}
	@Column(name="grade_level")
	public int getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(int gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	@Column(name="income_tax_id")
	public String getIncomeTaxId() {
		return incomeTaxId;
	}
	public void setIncomeTaxId(String incomeTaxId) {
		this.incomeTaxId = incomeTaxId;
	}
	@Column(name="driving_license_id")
	public String getDrivingLicenseId() {
		return drivingLicenseId;
	}
	public void setDrivingLicenseId(String drivingLicenseId) {
		this.drivingLicenseId = drivingLicenseId;
	}
	@Column(name="passport_no")
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	@Column(name="civil_id")
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	@Column(name="insurance_id")
	public String getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}
	@Column(name="state_fund_id")
	public String getStateFundId() {
		return stateFundId;
	}
	public void setStateFundId(String stateFundId) {
		this.stateFundId = stateFundId;
	}
	@Column(name="state_insurance_id")
	public String getStateInsuranceId() {
		return stateInsuranceId;
	}
	public void setStateInsuranceId(String stateInsuranceId) {
		this.stateInsuranceId = stateInsuranceId;
	}
	@Column(name="social_security_id")
	public String getSocialSecurityId() {
		return socialSecurityId;
	}
	public void setSocialSecurityId(String socialSecurityId) {
		this.socialSecurityId = socialSecurityId;
	}
	@Column(name="health_insurance_id")
	public String getHealthInsuranceId() {
		return healthInsuranceId;
	}
	public void setHealthInsuranceId(String healthInsuranceId) {
		this.healthInsuranceId = healthInsuranceId;
	}
	@Column(name="other_id")
	public String getOtherNd() {
		return otherNd;
	}
	public void setOtherNd(String otherNd) {
		this.otherNd = otherNd;
	}
	@Column(name="bank_name")
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Column(name="bank_branch_name")
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	@Column(name="branch_full_address")
	public String getBranchFullAddress() {
		return branchFullAddress;
	}
	public void setBranchFullAddress(String branchFullAddress) {
		this.branchFullAddress = branchFullAddress;
	}
	@Column(name="account_no")
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	@Column(name="IFSC_CODE")
	public String getIFSC_CODE() {
		return IFSC_CODE;
	}
	public void setIFSC_CODE(String IFSC_CODE) {
		IFSC_CODE = IFSC_CODE;
	}
	@Column(name="highest_qualifications")
	public String getHighestQualifications() {
		return highestQualifications;
	}
	public void setHighestQualifications(String highestQualifications) {
		this.highestQualifications = highestQualifications;
	}
	@Column(name="passing_year")
	public String getPassingYear() {
		return passingYear;
	}
	public void setPassingYear(String passingYear) {
		this.passingYear = passingYear;
	}
	@Column(name="college_name")
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	@Column(name="university_name")
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	@Column(name="signature_scan")
	public String getSignatureScan() {
		return signatureScan;
	}
	public void setSignatureScan(String signatureScan) {
		this.signatureScan = signatureScan;
	}

	/*
	 * @Column(name="finger_scan1") public String getFinger_scan1l() { return
	 * finger_scan1l; } public void setFinger_scan1l(String finger_scan1l) {
	 * this.finger_scan1l = finger_scan1l; }
	 * 
	 * @Column(name="finger_scan2") public String getFingerScan2() { return
	 * fingerScan2; } public void setFingerScan2(String fingerScan2) {
	 * this.fingerScan2 = fingerScan2;
	 
	}*/
	@Column(name="photo2")
	public String getPhoto2() {
		return photo2;
	}
	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	@Column(name="photo3")
	public String getPhoto3() {
		return photo3;
	}
	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}
	@Column(name="secret_question1")
	public String getSecretQuestion1() {
		return secretQuestion1;
	}
	public void setSecretQuestion1(String secretQuestion1) {
		this.secretQuestion1 = secretQuestion1;
	}
	@Column(name="secret_answer1")
	public String getSecretAnswer1() {
		return secretAnswer1;
	}
	public void setSecretAnswer1(String secretAnswer1) {
		this.secretAnswer1 = secretAnswer1;
	}
	@Column(name="secret_question2")
	public String getSecretQuestion2() {
		return secretQuestion2;
	}
	public void setSecretQuestion2(String secretQuestion2) {
		this.secretQuestion2 = secretQuestion2;
	}
	@Column(name="secret_answer2")
	public String getSecretAnswer2() {
		return secretAnswer2;
	}
	public void setSecretAnswer2(String secretAnswer2) {
		this.secretAnswer2 = secretAnswer2;
	}
	@Column(name="division")
	public int getDivision() {
		return division;
	}
	public void setDivision(int division) {
		this.division = division;
	}
	
	@Column(name="adminFlag")
	public int getAdminFlag() {
		return adminFlag;
	}
	public void setAdminFlag(int adminFlag) {
		this.adminFlag = adminFlag;
	}
	
	@Column(name="rediffChatId")
	public String getRediffChatId() {
		return rediffChatId;
	}
	public void setRediffChatId(String rediffChatId) {
		this.rediffChatId = rediffChatId;
	}
	
	@Column(name="gtalkChatId")
	public String getGtalkChatId() {
		return gtalkChatId;
	}
	public void setGtalkChatId(String gtalkChatId) {
		this.gtalkChatId = gtalkChatId;
	}
	
	@Column(name="msnChatId")
	public String getMsnChatId() {
		return msnChatId;
	}
	public void setMsnChatId(String msnChatId) {
		this.msnChatId = msnChatId;
	}
	
	@Column(name="yahooChatId")
	public String getYahooChatId() {
		return yahooChatId;
	}
	public void setYahooChatId(String yahooChatId) {
		this.yahooChatId = yahooChatId;
	}
	
	@Column(name="skypeChatId")
	public String getSkypeChatId() {
		return skypeChatId;
	}
	public void setSkypeChatId(String skypeChatId) {
		this.skypeChatId = skypeChatId;
	}
	@Column(name="leavesUsed")
	public int getLeavesUsed() {
		return leavesUsed;
	}
	public void setLeavesUsed(int leavesUsed) {
		this.leavesUsed = leavesUsed;
	}
	
	
}
