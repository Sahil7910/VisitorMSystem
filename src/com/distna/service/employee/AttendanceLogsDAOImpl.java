package com.distna.service.employee;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.employee.AttendanceLogs;

public class AttendanceLogsDAOImpl implements AttendanceLogsDAO {

	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void saveAttendanceLogs(AttendanceLogs attendanceLogs) {
		List<AttendanceLogs> alreadyPresent=hibernateTemplate.find("from AttendanceLogs where recordDate='"+attendanceLogs.getRecordDate()+"' and recordTime='"+attendanceLogs.getRecordTime()+"'");
		if(alreadyPresent.size()==0)
		{
		hibernateTemplate.saveOrUpdate(attendanceLogs);
		}
	}
	@Override
	public List<AttendanceLogs> getAttendanceLogsByMachineId(int machineId) {
		return hibernateTemplate.find("from AttendanceLogs where machineWorkId="+machineId);
	}

}
