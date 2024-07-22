package com.distna.service.calendar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.CalCategory;
import com.distna.domain.calendar.ShiftAllocation;
import com.distna.domain.calendar.ShiftDefinition;
import com.distna.web.login.ConnectionDao;

public class ShiftDefinitionDAOImpl implements ShiftDefinitionDAO
{
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	@Override
	public List<CalCategory> getCategoryList() {
		return hibernateTemplate.find("from CalCategory");
	}
	
	@Override
	public List<ShiftDefinition> getShiftDefinitions() {
		return hibernateTemplate.find("from ShiftDefinition");
	}
	@Override
	public List<ShiftDefinition> getShiftDefinitionsByShiftId(int shiftId) {
		return hibernateTemplate.find("from ShiftDefinition where shiftid="+shiftId);
	}
	
	@Override
	public ShiftDefinition getShiftDefinitionObjByShiftId(int shiftId) {
		return (ShiftDefinition)hibernateTemplate.find("from ShiftDefinition where shiftid="+shiftId).get(0);
	}
	
	@Override
	public void saveShiftDefinitions(ShiftDefinition shiftDefinition) {
		hibernateTemplate.saveOrUpdate(shiftDefinition);
	}
	


	@Override
	public List<ShiftDefinition> getshftbreakList(int shiftid) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("break2StartTime from ShiftDefinition where shiftid="+shiftid);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getShiftBreakTime(int employeeNo) {
		// TODO Auto-generated method stub
		String BreakTime=null;
		
		List <ShiftAllocation> shiftAllocationList=hibernateTemplate.find("from ShiftAllocation where employee_id = "+employeeNo);	
	
		
		return hibernateTemplate.find("from ShiftDefinition where shiftid="+shiftAllocationList.get(0).getShiftid());

//			for( ShiftDefinition shiftTime:shiftDefinitionList ) {
//				//BreakTime= shiftTime.getBreak2EndTime();
//				//System.out.println("BreakTime:"+BreakTime);
//			Boolean	BreakTime1=shiftTime.isBreak2();
//				
//			}

	
	}
	
}
