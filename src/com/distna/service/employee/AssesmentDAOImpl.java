package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.Assesment;

public class AssesmentDAOImpl implements AssesmentDAO{

	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		
	}
	
	
	@Override
	public void saveEmployeeAssesment(Assesment assesment) {

		hibernateTemplate.saveOrUpdate(assesment);
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Assesment> getEmployeeAssesment() {
		
		return hibernateTemplate.find("from Assesment");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Assesment> getEmployeeAssesmentById(int id) {
		
		return hibernateTemplate.find("from Assesment where id="+id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Assesment getAssesmentByemployeeId(int emp_id) {

		List<Assesment> assesmentList=hibernateTemplate.find("from Assesment where empid="+emp_id);
		
		if(assesmentList.size()!=0)
		{
			return assesmentList.get(0);
		}	
		else
		{
			return null;
		}
	}

	@Override
	public void deleteAssesment(int id) {
		
	List<Assesment> assesments=hibernateTemplate.find("from Assesment where id="+id);
		hibernateTemplate.deleteAll(assesments);
	}

	
}
