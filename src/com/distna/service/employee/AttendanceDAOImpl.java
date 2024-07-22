package com.distna.service.employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.Attendance;
import com.distna.domain.employee.AttendanceLogsBulkEntry;

public class AttendanceDAOImpl implements AttendanceDAO {

			
	
			private HibernateTemplate hibernateTemplate;
	
			public void setSessionDataFactory(SessionFactory sessionFactory) 
			{
				this.hibernateTemplate = new HibernateTemplate(sessionFactory);
			}
	
		
	@Override
	public List<Attendance> getAttendanceList(int employeeNo) {
		// TODO Auto-generated method stub
		
//		SimpleDateFormat Date12 = new SimpleDateFormat("d-M-yyyy");
//		String date1 = Date12.format(new Date());
		List<Attendance>attendanceList=hibernateTemplate.find("From Attendance where empCode='"+employeeNo+"'");
		return attendanceList;	
	}


	@Override
	public void saveAttendance(Attendance attendance) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(attendance);
	}


	@Override
	public void updateattendance(Attendance attendance) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(attendance);
		
	}
	

	
	
	
}
