 package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.Education;
import com.distna.domain.employee.Employee;

public class EducationDAOImpl implements EducationDAO {

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
		
		public void setSessionDataFactory(SessionFactory sessionFactory) 
		{
			this.sessionFactory=sessionFactory;
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
			
		}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Education> getEducationList() {
		
		return hibernateTemplate.find("from Education");
	}

	@Override
	public void saveEducation(Education education) {
		hibernateTemplate.saveOrUpdate(education);
		
	}

	@Override
	public Education getEducationByEmployeeId(int employee_id) {
		
		List<Education> educationList= hibernateTemplate.find("from Education where emp_id="+employee_id);
		
		if(educationList.size()!=0)
		{
			return educationList.get(0);
		}	
		else
		{
			return null;
		}
	}
	
	
	
	@Override
	public List<Education> getEducationListByEmployeeId(int employee_id) {
		
		List<Education> educationList= hibernateTemplate.find("from Education where emp_id="+employee_id);
		return educationList;
		
	}
	
	
	

	@Override
	public void deleteEducation(int id) {
		List<Education> educationList=hibernateTemplate.find("from Education where emp_id="+id);
		hibernateTemplate.deleteAll(educationList);
	}

	@Override
	public List<Employee> getEducationById(int id) {
		
		return hibernateTemplate.find("from Education where id="+id);
	}

	

	
}
