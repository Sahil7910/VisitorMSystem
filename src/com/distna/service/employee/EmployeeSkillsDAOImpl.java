package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.EmployeeSkills;

public class EmployeeSkillsDAOImpl implements EmployeeSkillsDAO {

private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	@Override
	public int saveAndGetId(EmployeeSkills employeeSkills) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(employeeSkills);
		return employeeSkills.getId();
	}


	@Override
	public void saveEmployeeSkills(EmployeeSkills employeeSkills) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(employeeSkills);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeSkills> getEmployeeSkillsList() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from EmployeeSkills ");
	}


	@Override
	public void deleteEmployeeSkills(int id) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(hibernateTemplate.find("from EmployeeSkills where id="+id));
		
	}


	@Override
	public EmployeeSkills getEmployeeSkills(int id) {
		// TODO Auto-generated method stub
		return (EmployeeSkills) hibernateTemplate.find("from EmployeeSkills where id="+id).get(0);
	}


	@Override
	public void updateEmployeeSkills(EmployeeSkills employeeSkills) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(employeeSkills);
	}

}
