package com.distna.service.visitor;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.visitor.VisitorType;
import com.distna.domain.visitor.Visitors;

public class VisitorsDAOImpl implements VisitorsDAO {
	
	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

		
		public void setSessionDataFactory(SessionFactory sessionFactory) 
		{
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
			this.sessionFactory=sessionFactory;
		}


		@Override
		public int saveVisitor(Visitors visitors) {
			// TODO Auto-generated method stub
			hibernateTemplate.save(visitors);
			return visitors.getVisitorId();
		}


		@Override
		public void saveVisitorType(String visitorTypeString) {
			// TODO Auto-generated method stub
			VisitorType visitorTypeObj=new VisitorType();
			
			visitorTypeObj.setVisitorTypeVariable(visitorTypeString);
			hibernateTemplate.save(visitorTypeObj);
		}


		@SuppressWarnings("unchecked")
		@Override
		public List<VisitorType> getVisitorTypeList() {
			// TODO Auto-generated method stub
			return hibernateTemplate.find("from VisitorType");
		}


		@SuppressWarnings("unchecked")
		@Override
		public List<Visitors> getVisitors() {
			// TODO Auto-generated method stub
			return hibernateTemplate.find("from Visitors");
		}


		@SuppressWarnings("unchecked")
		@Override
		public Visitors getVisitor(int visitorId) {
			// TODO Auto-generated method stub
			List<Visitors> visitorsList=hibernateTemplate.find("from Visitors where visitorId="+visitorId);
			if(visitorsList.size()>0)
			{
				return visitorsList.get(0);
			}
			return null;
		}


		@Override
		public void updateVisitor(Visitors visitors) {
			hibernateTemplate.saveOrUpdate(visitors);
		}


		@Override
		public void deleteVisitor(int visitorId) {
			hibernateTemplate.deleteAll(hibernateTemplate.find("from Visitors where visitorId="+visitorId));
		}


		@Override
		public int getTodayvisitorCount() {
			String hql= "Select COUNT(*) From VisitorLogs WHERE Date = CURDATE()";
			List<Long>result=(List<Long>) hibernateTemplate.find(hql);
			return result.get(0).intValue();
		}


		@Override
		public int getpresentVisitorCount() {
			String hql= "Select COUNT(*) From VisitorLogs WHERE isPresent = true";
			List<Long>result=(List<Long>) hibernateTemplate.find(hql);
			return result.get(0).intValue();
		}


		@Override
		public int getTotalVisitorCount() {
			String hql= "Select COUNT(*) From VisitorLogs";
			List<Long>result=(List<Long>) hibernateTemplate.find(hql);
			return result.get(0).intValue();
		}


		@Override
		public int getAllowedMeetings(int employeeid) {
			// TODO Auto-generated method stub
			String hql= "Select COUNT(*) From VisitorLogs WHERE employeeid =? AND approvalStatus='approved'";
			List<Long>result=(List<Long>) hibernateTemplate.find(hql,employeeid);
			return result.get(0).intValue();
		}


	


		

}
