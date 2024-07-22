package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.EmployeeMessages;

public class EmployeeMessagesDAOImpl implements EmployeeMessagesDAO {
	
	
private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		
	}

	@Override
	public void saveEmployeeMessages(EmployeeMessages employeeMessages) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(employeeMessages);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getUnreadEmployeeMessagesCount(int employeeId) {
		return hibernateTemplate.find("from EmployeeMessages where employeeId="+employeeId+" and messageStatus="+0).size();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeMessages> getEmployeeMessagesByEmployeeId(int employeeId) {
		return hibernateTemplate.find("from EmployeeMessages where employeeId="+employeeId+" order by messageDate");
	}

	@SuppressWarnings("unchecked")
	@Override
	public EmployeeMessages getEmployeeMessagesById(int messageId) {
		return (EmployeeMessages)hibernateTemplate.find("from EmployeeMessages where id="+messageId).get(0);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void changeEmployeeMessagesStatus(int messageId) {
		EmployeeMessages employeeMessages=(EmployeeMessages)hibernateTemplate.find("from EmployeeMessages where id="+messageId).get(0);
		employeeMessages.setMessageStatus(true);
		hibernateTemplate.saveOrUpdate(employeeMessages);
	}
}
