package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.Employee;
import com.distna.domain.employee.EmployeeBank;
import com.distna.domain.employee.EmployeeSkills;

public class BankDAOImpl implements BankDAO {
	
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	

	@Override
	public int saveAndGetId(EmployeeBank employeeBank) {
		// TODO Auto-generated method stub
		
		hibernateTemplate.save(employeeBank);
		return employeeBank.getId();
	
	}

	@Override
	public void saveEmployeeBank(EmployeeBank employeeBank) {
		// TODO Auto-generated method stub
		
		hibernateTemplate.saveOrUpdate(employeeBank);
	}

	@Override
	public List<EmployeeBank> getEmployeeBankList() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from EmployeeBank");
	
	}

	@Override
	public void deleteEmployeeBank(int id) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(hibernateTemplate.find("from EmployeeBank where id="+id));
	}

	@Override
	public EmployeeBank getEmployeeBank(int id) {
		// TODO Auto-generated method stub
		return (EmployeeBank) hibernateTemplate.find("from EmployeeBank where id="+id).get(0);
	}
	

	@Override
	public EmployeeBank getEmployeeBankbyNo(Employee employee) {
		
		return (EmployeeBank) hibernateTemplate.find("from EmployeeBank where employee_id="+employee).get(0);
		
	}

	
	@Override
	public void updateEmployeeBank(EmployeeBank employeeBank) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(employeeBank);
	}

	

	
}
