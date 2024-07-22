package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.ShiftAllocation;
import com.distna.domain.company.CompanyPolicies;
import com.distna.domain.company.Departments;
import com.distna.domain.company.Designation;
import com.distna.domain.company.Location;
import com.distna.domain.company.Workspaces;
import com.distna.domain.employee.Employee;

public class DepartmentDAOImpl implements DepartmentDAO {

	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public void saveDepartments(Departments departments) {
		
		hibernateTemplate.saveOrUpdate(departments);
	}

	@Override
	public List<Departments> getDepartment() {
		
		return hibernateTemplate.find("from Departments");
	}

	@Override
	public List<Departments> getDepartmentById(int id) {
		return hibernateTemplate.find("from Departments where id="+id);
	}

	@Override
	public void deleteDepartments(int id) {
		
		List<Departments> departments=hibernateTemplate.find("from Departments where id="+id);
		hibernateTemplate.deleteAll(departments);
		
		List<Designation> designationsList=hibernateTemplate.find("from Designation where department_id="+id);
		if(designationsList.size()!=0)
		{
			for(Designation designation:designationsList)
			{
				designation.setDepartment_id(0);
			}
			hibernateTemplate.saveOrUpdateAll(designationsList);
		}
		
		List<CompanyPolicies> companyPoliciesList=hibernateTemplate.find("from CompanyPolicies where departmentId="+id);
		if(companyPoliciesList.size()!=0)
		{
			for(CompanyPolicies companyPolicies:companyPoliciesList)
			{
				companyPolicies.setDepartmentId(0);
			}
			hibernateTemplate.saveOrUpdateAll(companyPoliciesList);
		}
		
		List<Employee> employeesList=hibernateTemplate.find("from Employee where departmentId="+id);
		if(employeesList.size()!=0)
		{
			for(Employee employee:employeesList)
			{
				employee.setDepartmentId(0);
			}
			hibernateTemplate.saveOrUpdateAll(employeesList);
		}
		
		List<Workspaces> workspaceList=hibernateTemplate.find("from Workspaces where department="+id);
		if(workspaceList.size()!=0)
		{
			for(Workspaces workspaces:workspaceList)
			{
				workspaces.setDepartment(0);
			}
			hibernateTemplate.saveOrUpdateAll(workspaceList);
		}
		
		List<ShiftAllocation> shiftAllocationList=hibernateTemplate.find("from ShiftAllocation where department="+id);
		if(shiftAllocationList.size()!=0)
		{
			for(ShiftAllocation shiftAllocation:shiftAllocationList)
			{
				shiftAllocation.setDepartment(0);
			}
			hibernateTemplate.saveOrUpdateAll(shiftAllocationList);
		}
		
		
		
	}

	@Override
	public List<Departments> getDepartmentWithoutCurrentDept(int id) {
		return hibernateTemplate.find("from Departments where id !="+id);
	}

	@Override
	public String getDepartmentNameById(int departmentId) {
		// TODO Auto-generated method stub
		List<Departments> departmentsList=hibernateTemplate.find("from Departments where id="+departmentId);
		Departments departments=new Departments();
		if(departmentsList.size()>0)
		{
			departments=departmentsList.get(0);
			
		}
		return departments.getName();
	}
	
	
	
	
	
	

	
}
