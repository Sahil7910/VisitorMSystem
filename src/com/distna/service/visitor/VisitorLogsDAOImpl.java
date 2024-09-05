package com.distna.service.visitor;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.visitor.VisitorLogs;
import com.distna.domain.visitor.Visitors;

public class VisitorLogsDAOImpl implements VisitorLogsDAO
{
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void saveVisitorLogs(VisitorLogs visitorLogs)
	{
		hibernateTemplate.save(visitorLogs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitorLogs> getVisitorLogsList() {
		return hibernateTemplate.find("from VisitorLogs order by STR_TO_DATE(inTime,'%d-%m-%Y %H:%i:%s') desc");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitorLogs> getVisitorLogsList(int logId) {
		return hibernateTemplate.find("from VisitorLogs where logId="+logId);
	}

	@Override
	public void updateVisitorLogs(VisitorLogs visitorLogs)
	{
		hibernateTemplate.saveOrUpdate(visitorLogs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteVisitorLogs(int logId) 
	{
		List<VisitorLogs> visitorLogs=hibernateTemplate.find("from VisitorLogs where logId="+logId);
		hibernateTemplate.deleteAll(visitorLogs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitorLogs> getVisitorLogsListAccEmployee(int employeeId) {
		return hibernateTemplate.find("from VisitorLogs where employeeId="+employeeId+" and approvalStatus='pending' ");
	}

	/*
	 * @Override public List<VisitorLogs> getVisitorLogsApprovedEmployeeList() {
	 * return hibernateTemplate.
	 * find("from VisitorLogs ORDER BY STR_TO_DATE(inTime,'%d-%m-%Y') desc"); }
	 */

	@Override
	public void updateVisitorLogs(int logId) {
		List<VisitorLogs> visitorLogs=hibernateTemplate.find("from VisitorLogs where logId="+logId);
		for (VisitorLogs visitorLog : visitorLogs) {
			visitorLog.setApprovalStatus("allowed");
		}
		hibernateTemplate.saveOrUpdateAll(visitorLogs);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitorLogs> getVisitorLogsByVisitorName(String visitorName) {
		// TODO Auto-generated method stub
		List<Visitors> visitorsList=hibernateTemplate.find("from Visitors where visitorName LIKE '"+visitorName+"%'");
		
		String visitorIds="";
		
		if(visitorsList.size()>0)
		{
			for (Visitors visitors : visitorsList) {
				visitorIds=visitorIds.concat(visitors.getVisitorId()+",");
			}
		visitorIds=visitorIds.substring(0,visitorIds.length()-1);
		List<VisitorLogs> visitorLogs=hibernateTemplate.find("from VisitorLogs where visitorId in ("+visitorIds+")");
		return visitorLogs;
		}
		
		return null;
	}


	@Override
	public List<VisitorLogs> getVisitorLogsApprovedEmployeeList(int employeeId) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from VisitorLogs where employeeId="+employeeId+" and approvalStatus='approved' and Date = CURDATE() ");
	}

	@Override
	public List<VisitorLogs> getVisitorLogsApprovedEmployeeList() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from VisitorLogs ORDER BY STR_TO_DATE(inTime,'%d-%m-%Y') desc");
	}
	
}
