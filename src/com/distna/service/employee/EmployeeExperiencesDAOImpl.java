package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.EmployeeExperiences;

public class EmployeeExperiencesDAOImpl implements EmployeeExperiencesDAO {

	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	@Override
	public int saveAndGetId(EmployeeExperiences employeeExperiences) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(employeeExperiences);
		return employeeExperiences.getId();
	}


	@Override
	public void saveExperience(EmployeeExperiences employeeExperiences) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(employeeExperiences);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeExperiences> getExperienceList() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from EmployeeExperiences order by employeeId");
	}


	@Override
	public void deleteExperience(int id) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(hibernateTemplate.find("from EmployeeExperiences where id="+id));
	}


	@Override
	public EmployeeExperiences getExterience(int id) {
		// TODO Auto-generated method stub
		return (EmployeeExperiences) hibernateTemplate.find("from EmployeeExperiences where id="+id).get(0);
	}

}
