package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.AttendanceLogs;
import com.distna.domain.employee.AttendanceRecord;

public class AttendanceRecordDAOImpl implements AttendanceRecordDAO {
private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AttendanceRecord> getAttendanceRecordList() {
		return hibernateTemplate.find("from AttendanceRecord");
	}

	@Override
	public List<AttendanceRecord> getEmployeeListByEmployeeNo(int employeeNo) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from AttendanceRecord where empCode="+employeeNo);
	}

	@Override
	public void saveAttendancerecord(AttendanceRecord attendanceRecord) {
		// TODO Auto-generated method stub
		List<AttendanceRecord> alreadyPresent=hibernateTemplate.find("from AttendanceRecord where date='"+ attendanceRecord.getDate() +"' and time='"+attendanceRecord.getTime()+"'");
		if(alreadyPresent.size()==0)
		{
		hibernateTemplate.saveOrUpdate(attendanceRecord);
		}
		
		
		
	}

	@Override
	public List<AttendanceRecord> getlastentryList(AttendanceRecord attendanceRecord) {
		// TODO Auto-generated method stub
		//return hibernateTemplate.find("from AttendanceRecord where empCode='" + employeeNo +"' ORDER BY date DESC LIMIT 1 ");
		return hibernateTemplate.find("from AttendanceRecord where empCode='" + attendanceRecord.getEmpCode() +"' and date='"+attendanceRecord.getDate()+"' ORDER BY date DESC LIMIT 1 ");
	}
	
	
}
