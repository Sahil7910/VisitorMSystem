package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.AttendanceLogsOutdoorEntry;

public class AttendanceLogsOutdoorEntryDAOImpl implements AttendanceLogsOutdoorEntryDAO
{
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}
	
	
	@Override
	public void saveOutdoorEntry(AttendanceLogsOutdoorEntry attendanceLogsOutdoorEntry)
	{
		hibernateTemplate.save(attendanceLogsOutdoorEntry);
	}


	@Override
	public List<AttendanceLogsOutdoorEntry> getAttendanceOutEntryList(int employeeId) {
		return hibernateTemplate.find("from AttendanceLogsOutdoorEntry where workID="+employeeId);
	}


	@Override
	public boolean isRecordAvailable(String recorddate,int employeeId)
	{
		List<AttendanceLogsOutdoorEntry> attendanceLogsOutdoorEntries=hibernateTemplate.find("from AttendanceLogsOutdoorEntry where recordDate='"+recorddate+"' and workID="+employeeId);

		//List<AttendanceLogsOutdoorEntry> attendanceLogsOutdoorEntries=hibernateTemplate.
		if(attendanceLogsOutdoorEntries.size()==0)
		{
			return true;
		}
		return false;
	}


	@Override
	public List<AttendanceLogsOutdoorEntry> getAttendanceOutEntryList() 
	{
		return hibernateTemplate.find("from AttendanceLogsOutdoorEntry");
	}
}
