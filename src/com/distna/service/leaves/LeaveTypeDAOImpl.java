package com.distna.service.leaves;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.DesignationLevel;
import com.distna.domain.leaves.LeaveType;

public class LeaveTypeDAOImpl implements LeaveTypeDAO{

	private HibernateTemplate hibernateTemplate;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	
	@Override
	public void saveLeaveType(LeaveType leaveType) {
		hibernateTemplate.saveOrUpdate(leaveType);
	}

	@Override
	public List<LeaveType> getleavetypes() {
		
		return hibernateTemplate.find("from LeaveType");
	}

	@Override
	public List<LeaveType> getLeaveTypeById(int id) {
		
		return  hibernateTemplate.find("from LeaveType where id="+id);
	}


	public void deleteLeaveType(int id) {
		List<LeaveType> leaveTypeList=hibernateTemplate.find("from LeaveType where id="+id);
		hibernateTemplate.deleteAll(leaveTypeList);
		
	}


	@Override
	public List<LeaveType> getLeaveTypeforRank(int rank) {
		
		return  hibernateTemplate.find("from LeaveType where rank="+rank);
	}
	

	@Override
	public boolean getLeaveTypeforRank(int rank,int id) 
	{
		boolean flag=true;
		List<LeaveType> leaveTypeList=getLeaveTypeforRank(rank);
		if(leaveTypeList.size()>0)
		{
			for (LeaveType leaveType : leaveTypeList) 
			{
				if(leaveType.getId()==id)
				{
					flag=true;
				}
				else
				{
					flag=false;
				}
			}
		}
		return flag;
	}


	@Override
	public String getLeaveTypeNameById(int leaveTypeId) {
		// TODO Auto-generated method stub
		LeaveType leaveType=new LeaveType();
		List<LeaveType> leaveTypeList=hibernateTemplate.find("from LeaveType where id="+leaveTypeId);
		if(leaveTypeList.size()>0)
		{
			leaveType=leaveTypeList.get(0);
		}
		
		return leaveType.getName();
	}
	

}
