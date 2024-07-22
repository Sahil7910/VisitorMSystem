package com.distna.service.charts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class ChartDAOImpl implements ChartDAO {
	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

	public void setSessionDataFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getLeavesPerLeaveType() {
		String query=null;
		
		ResultSet resultSet=null;
		
		Connection connection=null;
		
		Session session=sessionFactory.openSession();
		
		PreparedStatement preparedStatement=null;
		
		//query="select distinct employee_id FROM LeaveApplication";
		
		//List<Integer> empList = new ArrayList<Integer>();
		
		//List<Integer> empLeave=new ArrayList<Integer>();
		
		Map<Integer, Integer> leavesMap=new HashMap<Integer, Integer>();
		
		connection=session.connection();
		
		
		
			query="SELECT employee_id ,count(employee_id) as leaveCount FROM employee_leaves group by employee_id";
			try {
				preparedStatement=connection.prepareStatement(query);
				resultSet=preparedStatement.executeQuery();

				while (resultSet.next()) {
					//empList.add(resultSet.getInt(1));
					//empLeave.add(resultSet.getInt(2));
					leavesMap.put(resultSet.getInt(1),resultSet.getInt(2));
					
				}
				//leavesMap.put(empList, resultSet.getInt(1));
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		//System.out.println("id= "+empList.size()+" leaves= "+empLeave.size());
		
		
		
		session.close();
		
		return leavesMap;
		
	}


}
