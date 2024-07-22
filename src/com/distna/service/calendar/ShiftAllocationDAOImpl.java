package com.distna.service.calendar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.ShiftAllocation;
import com.distna.domain.calendar.ShiftDefinition;

public class ShiftAllocationDAOImpl implements ShiftAllocationDAO {
	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}
	@Override
	public void saveShiftDefinitions(ShiftAllocation shiftAllocation) {
		hibernateTemplate.saveOrUpdate(shiftAllocation);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getShiftAllocatedEmployeeList() {
		Session session = sessionFactory.openSession();
		String query="SELECT DISTINCT employee_id from ShiftAllocation";
		List<Integer> listOfEmployee= session.createQuery(query).list();
		session.close();
		return listOfEmployee;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ShiftAllocation> getShiftAllocatedEmployeeList(int employeeId) {
		return hibernateTemplate.find("from ShiftAllocation where employee_id="+employeeId);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ShiftAllocation> getShiftAllocatedByShiftId(int shiftId) {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from ShiftAllocation where shiftid="+shiftId);
	}
	@Override
	public void clearAttRecords() {
		Session session = sessionFactory.openSession();
		Connection connection=session.connection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE attrecord");
			preparedStatement.executeUpdate();
			preparedStatement = connection.prepareStatement("TRUNCATE TABLE attendance_logs");
			preparedStatement.executeUpdate();
			preparedStatement = connection.prepareStatement("TRUNCATE TABLE attendance_logs_bulk_entry");
			preparedStatement.executeUpdate();
			preparedStatement = connection.prepareStatement("TRUNCATE TABLE attendance_logs_bulk_entry_report");
			preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*public void setTime(long time) {
		Session session = sessionFactory.openSession();
		Connection connection=session.connection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO lastaccessedtime (lastTime) VALUES ("+time+")");
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public long getTime() {
		Session session = sessionFactory.openSession();
		Connection connection=session.connection();
			List<Long> listOfEmployee= session.createQuery("select * from lastaccessedtime").list();
			session.close();
			return listOfEmployee.get(0);
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ShiftAllocation> getShiftAllocatedList() {
		return hibernateTemplate.find("from ShiftAllocation");
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String[] getShiftStartEndTime(int employeeId) {
		// TODO Auto-generated method stub
		List <ShiftAllocation> shiftAllocationList=hibernateTemplate.find("from ShiftAllocation where employee_id = "+employeeId);
		
		String shiftTime [] = new String [2];
		if(shiftAllocationList.size()>0)
		{
			List <ShiftDefinition> shiftDefinitionList=hibernateTemplate.find("from ShiftDefinition where shiftid="+shiftAllocationList.get(0).getShiftid());
			if(shiftDefinitionList.size()>0)
			{
				shiftTime[0]=shiftDefinitionList.get(0).getTimeField();
				shiftTime[1]=shiftDefinitionList.get(0).getEndTime();
			}
		}
		
		return shiftTime;
	}
}




