package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.Division;
import com.distna.domain.employee.Employee;

public class DivisionDAOImpl implements DivisionDAO{
	
private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void addDivision(Division division) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(division);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Division> getDivisionList() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Division");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteDivision(int id) {
		// TODO Auto-generated method stub
		List<Division> deleteDivisions=hibernateTemplate.find("from Division where divisionId="+id);
		hibernateTemplate.deleteAll(deleteDivisions);
		
		List<Employee> employeesList=hibernateTemplate.find("from Employee where division="+id);
		if(employeesList.size()!=0)
		{
			for(Employee employee:employeesList)
			{
				employee.setDivision(0);
			}
			hibernateTemplate.saveOrUpdateAll(employeesList);
		}
	}

	@Override
	public Division getDivision(int id) {
		// TODO Auto-generated method stub
		Division division=(Division) hibernateTemplate.find("from Division where divisionId="+id).get(0);
		return division;
	}

	
	@Override
	public void updateDivision(Division division) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(division);
	}
	

}
