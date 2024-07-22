package com.distna.service.leaves;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.leaves.LeaveApplication;

public class LeaveApplicationDAOImpl implements LeaveApplicationDAO {

	
private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}  
	
	@Override
	public void saveLeaveApplication(LeaveApplication leaveApplication) {
		hibernateTemplate.saveOrUpdate(leaveApplication);
		
	}

	@Override
	public List<LeaveApplication> getLeaveApplications() {
		
		return hibernateTemplate.find("from LeaveApplication");
	}

	@Override
	public List<LeaveApplication> getLeaveApplicationsById(int id) {
		
		return  hibernateTemplate.find("from LeaveApplication where id="+id);
	}

	@Override
	public List<LeaveApplication> getApplicationsByIdandStatus(int id) {
		
		return  hibernateTemplate.find("from LeaveApplication where leaveStatus=1 and id="+id);
	}

	@Override
	public void deleteLeaveApplications(int id) {
		List<LeaveApplication> leaveApplications=hibernateTemplate.find("from LeaveApplication where id="+id);
		hibernateTemplate.deleteAll(leaveApplications);
		
	}

	public List<LeaveApplication> getApplicationsByStatus(int statusid)
	{
		return  hibernateTemplate.find("from LeaveApplication where leaveStatus="+statusid);
	}
	
	public List<LeaveApplication> getLeaveApplicationsByEmployeeId(int empid)
	{
		return  hibernateTemplate.find("from LeaveApplication where employee_id="+empid);
	
	}
	
	
}
