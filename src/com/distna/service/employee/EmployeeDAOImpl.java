package com.distna.service.employee;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.CalTimes;
import com.distna.domain.employee.AttendanceLogsBulkEntry;
import com.distna.domain.employee.Employee;
import com.distna.domain.employee.EmployeeBank;
import com.distna.domain.employee.UserType;
import com.distna.domain.leaves.LeaveAllocation;
import com.lowagie.text.pdf.hyphenation.TernaryTree.Iterator;

public class EmployeeDAOImpl implements EmployeeDAO {

private HibernateTemplate hibernateTemplate;
private SessionFactory sessionFactory;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.sessionFactory=sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeeList() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Employee order by lastName");
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeeListBySupervisor(int supervisor) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Employee where supervisor="+supervisor);
	}

	@Override
	public int saveEmployeeAndGetId(Employee employee) {
		hibernateTemplate.save(employee);
		int currentId=employee.getEmployeeId();
		//Employee latestEmployee=hibernateTemplate.merge(employee);
		return currentId;
	}
	
	@Override
	public void saveOrUpdateEmployeeAndGetId(Employee employee) {
		
	try {
		List<EmployeeBank> employeeBank=hibernateTemplate.find("from EmployeeBank where employeeId="+employee.getEmployeeNo()+"'");
			employee.setBankName(employeeBank.get(0).getBankName());
			employee.setAccountNo(employeeBank.get(0).getAccountNo());
			employee.setBankBranchName(employeeBank.get(0).getBankBranchName());
			employee.setBranchFullAddress( employeeBank.get(0).getBranchFullAddress());
			employee.setIFSC_CODE(employeeBank.get(0).getIFSC_CODE());
			
			
	} catch (Exception e) {
		// TODO: handle exception
	}
		
		hibernateTemplate.saveOrUpdate(employee);	
	}


	
	
	
	
	
	
	
	@Override
	public void saveUser(Employee employees) 
	{
		hibernateTemplate.saveOrUpdate(employees);
	}

	@Override
	public boolean checkIfUserExist(String username,String password) 
	{
		boolean flag=false;
		List<Employee>employeesList=hibernateTemplate.find("from Employee where email='"+username+"'AND password='"+password+"'");
		if(employeesList.size()>0)
		{
			flag=true;
		}
		
		return flag;
	}
	
	
	
	public Employee getEmployeeByUserName(String username,String password)
	{
		List<Employee>employeesList=hibernateTemplate.find("from Employee where email='"+username+"'AND password='"+password+"'");
		if(employeesList.size()>0)
		{
			return employeesList.get(0);
		}
		else
			return null;
	}
	
	@Override
	public int checkUserType(String username,String password) 
	{
		
		List<Employee>employeesList=hibernateTemplate.find("from Employee where email='"+username+"'AND password='"+password+"'");
		if(employeesList.size()>0)
		{
			return employeesList.get(0).getAdminFlag();
		}
		else
			return 0;
	}
	@Override
	public int checkUserTypeById(int id) 
	{
		
		List<Employee>employeesList=hibernateTemplate.find("from Employee where employeeId="+id);
		if(employeesList.size()>0)
		{
			return employeesList.get(0).getAdminFlag();
		}
		else
			return 0;
	}


	@Override
	public List<Employee> getEmployeeList(String username) {
		return hibernateTemplate.find("from Employee where email='"+username+"'");
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeeList(int id) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Employee where employeeId="+id);
	}
	
	public List<Employee> getEmployeeListExcludingCurrentId(int employeeId)
	{
		return hibernateTemplate.find("from Employee where employeeId !="+employeeId);
	}
	@Override
	public Employee getEmployeeById(int id) {
		
		List<Employee> employeeList= hibernateTemplate.find("from Employee where employee_id="+id);
		
		if(employeeList.size()!=0)
		{
			return employeeList.get(0);
		}	
		else
		{
			return null;
		}
	}
	
	@Override
	public void updateUser(Employee employee)
	{
		hibernateTemplate.saveOrUpdate(employee);
		//hibernateTemplate.update(employee);
		/*Session session = sessionFactory.openSession();
		Employee s1 = (Employee )session.get(Employee.class, employee.getEmployeeId());
		session.close();

		//s1 is now detached.
		s1.setFirstName(employee.getFirstName());
		s1.setLastName(employee.getLastName());
		s1.setMaritalStatus(employee.getMaritalStatus());
		s1.setGender(employee.getGender());
		s1.setFatherhusbandName(employee.getFatherhusbandName());
		s1.setDateOfBirth(employee.getDateOfBirth());
		s1.setHomePhone(employee.getHomePhone());
		s1.setHomeEmail(employee.getHomeEmail());
		s1.setPersonalEmails(employee.getPersonalEmails());
		s1.setChildrenCount(employee.getChildrenCount());
		s1.setEmergencyContact(employee.getEmergencyContact());
		s1.setRelativesFriends(employee.getRelativesFriends());
		s1.setLanguages(employee.getLanguages());
		s1.setSkills(employee.getSkills());
		s1.setYearsExperience(employee.getYearsExperience());
		s1.setPassingYear(employee.getPassingYear());
		s1.setCollegeName(employee.getCollegeName());
		s1.setUniversityName(employee.getUniversityName());
		s1.setCurrentAddress(employee.getCurrentAddress());
		s1.setPermanentAddress(employee.getPermanentAddress());
		s1.setPermanentAddress2(employee.getPermanentAddress2());
		s1.setPermanentCountry(employee.getPermanentCountry());
		s1.setPermanentState(employee.getPermanentState());
		s1.setPermanentCity(employee.getPermanentCity());
		s1.setPermanentPostalCode(employee.getPermanentPostalCode());
		s1.setHomeDistance(employee.getHomeDistance());
		s1.setOnewayTime(employee.getOnewayTime());
		s1.setTravelMode(employee.getTravelMode());
		s1.setHomeGpsLocation(employee.getHomeGpsLocation());
		s1.setCompanyFriends(employee.getCompanyFriends());
		s1.setAllPhones(employee.getAllPhones());
		s1.setAllChatIds(employee.getAllChatIds());
		s1.setRediffChatId(employee.getRediffChatId());
		s1.setGtalkChatId(employee.getGtalkChatId());
		s1.setMsnChatId(employee.getMsnChatId());
		s1.setYahooChatId(employee.getYahooChatId());
		s1.setSkypeChatId(employee.getSkypeChatId());
		
		
		session = sessionFactory.openSession();
		Employee s3 = (Employee ) session.merge(s1);
		hibernateTemplate.saveOrUpdate(s3);
		session.close();*/
		
		
	}
	
	
	
	@Override
	public List<Employee> getEmployeeListById(int employeeid) {
		
		return hibernateTemplate.find("from Employee where employee_id="+employeeid);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteEmployee(int id) {
		 List<Employee> employees=getEmployeeListById(id);
		 List<LeaveAllocation> leaveList=hibernateTemplate.find("from LeaveAllocation where employee_id="+id);
		 
		 hibernateTemplate.deleteAll(leaveList);
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from AttendanceLogs where workID="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from AttendanceLogsBulkEntry where workID="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from AttendanceRecord where empCode="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from Breaks where employee_no="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from ShiftAllocation where employee_id="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from ShiftDefinition where employee="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from DateRange where employee_no="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from Education where emp_id="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from Assesment where empid="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from EmployeeExperiences where employeeId="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from LeaveApplication where employee_id="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from Projects where employeeId="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from EmployeeSkills where employeeId="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from MusterReport where employeeNo="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from OfficialTours where workID="+id));
		 
		 hibernateTemplate.deleteAll(hibernateTemplate.find("from EmployeePrivilege where employeeId="+id));
		 
		 /*hibernateTemplate.deleteAll(hibernateTemplate.find("from EmployeeMessages where messageFrom="+id));*/
		 
		 hibernateTemplate.deleteAll(employees);

		
	}
	
		@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeeListByEmployeeNo(int EmployeeNo) {
		
		return hibernateTemplate.find("from Employee where employee_no="+EmployeeNo);
	}
	
		@Override
	public void saveCalTimes() {
		
		Calendar calendar = Calendar.getInstance();
		int hour = 00;
		int minute = 05;
		int second = 00;
		calendar.set(Calendar.HOUR, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		DateFormat format=new SimpleDateFormat("HH:mm:ss");
		
		
		while(Calendar.HOUR!=00&&Calendar.MINUTE!=00)
			{
			if(calendar.getTime().getHours()==23&&calendar.getTime().getMinutes()==55)
			{
				break;
			}
			else
			{
				calendar.add(Calendar.MINUTE, 5);
				/*System.out.println(format.format(calendar.getTime()));*/
				CalTimes calTimes =new CalTimes();
				calTimes.setTime(format.format(calendar.getTime()));
				hibernateTemplate.save(calTimes);
			
			}
			}
		
	}


		@SuppressWarnings("unchecked")
		@Override
		public List<Employee> getEmployeeListByDepartment(int departmentId) {
			// TODO Auto-generated method stub
			return hibernateTemplate.find("from Employee where departmentId="+departmentId);
		}


		@Override
		public List<UserType> getUserType() {
			return hibernateTemplate.find("from UserType");
		}


		@Override
		public List<Employee> getEmployeesWithBirthdays() {
			Calendar currentCalendar=Calendar.getInstance();
			Date currentDate=currentCalendar.getTime();
			DateFormat dateFormat=new SimpleDateFormat("d-MM-yyyy");
			String currentDateString=dateFormat.format(currentDate);
			/*Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DATE, 1);*/
			/*Session session = sessionFactory.openSession();
			String query="SELECT e.employeeId,e.firstName,e.lastName,e.dateOfBirth from Employee e where STR_TO_DATE(e.dateOfBirth,'%d-%m') >= STR_TO_DATE('"+currentDateString+"','%d-%m')";
			List<Employee> listOfEmployee= session.createQuery(query).list();
			session.close();*/
			//String query="from Employee where STR_TO_DATE(dateOfBirth,'%d-%m-%Y') >= STR_TO_DATE('"+currentDateString+"','%d-%m-%Y') and STR_TO_DATE(dateOfBirth,'%d-%m-%Y') <= STR_TO_DATE('"+dateFormat.format(calendar.getTime())+"','%d-%m-%Y') order by STR_TO_DATE(dateOfBirth,'%d-%m-%Y')";
			String query="from Employee where date_of_birth like '%-"+currentDateString.split("-")[1]+"-%' order by STR_TO_DATE(dateOfBirth,'%d-%m-%Y')";
			List<Employee> listOfEmployee= hibernateTemplate.find(query);
			List<Employee> actualListOfEmployees=new ArrayList<Employee>();
			/*System.out.println(query);*/
			for(Employee employee:listOfEmployee)
			{
				if(Integer.parseInt(employee.getDateOfBirth().split("-")[0])>=Integer.parseInt(currentDateString.split("-")[0]))
				{
					actualListOfEmployees.add(employee);
				}
				
			}
			return actualListOfEmployees;
		}


		@Override
		public int getDepartmentIdByEmployeeId(int employeeId) {
			// TODO Auto-generated method stub
			List<Employee> employeeList= hibernateTemplate.find("from Employee where employeeId="+employeeId);
			Employee employee=new Employee();
			if(employeeList.size()>0)
			{
				employee=employeeList.get(0);
			}
			return employee.getDepartmentId();
		}
		@Override
			public Employee getEmployeeByID(int id) {
				// TODO Auto-generated method stub
			List<Employee> employeeList= hibernateTemplate.find("from Employee where employeeId="+id);
			Employee employee=new Employee();
			if(employeeList.size()>0)
			{
				employee=employeeList.get(0);
			}
				return employee;
			}

}
