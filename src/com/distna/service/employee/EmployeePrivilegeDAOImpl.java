package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.EmployeePrivilege;

public class EmployeePrivilegeDAOImpl implements EmployeePrivilegeDAO
{
	private HibernateTemplate hibernateTemplate;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeePrivilege> getEmployeePrivilegeList() {
		return hibernateTemplate.find("from EmployeePrivilege");
	}


	@Override
	public void saveEmployeePrivilege(EmployeePrivilege employeePrivilege)
	{
		hibernateTemplate.save(employeePrivilege);
	}


	@Override
	public void updateEmployeePrivilege(EmployeePrivilege employeePrivilege) 
	{
		hibernateTemplate.update(employeePrivilege);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeePrivilege> getEmployeePrivilegeListByEmployeeId(
			int employeeId) {
		return hibernateTemplate.find("from EmployeePrivilege where employeeId="+employeeId);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeePrivilege> getEmployeePrivilegeListById(int id) {
		
		return hibernateTemplate.find("from EmployeePrivilege where id="+id);
	}


	@Override
	public void deleteEmployeePrivilegeListById(int id) {
	 	List<EmployeePrivilege> employeePrivilegesList= hibernateTemplate.find("from EmployeePrivilege where id="+id);
	 	hibernateTemplate.deleteAll(employeePrivilegesList);
	}
	
	
}
