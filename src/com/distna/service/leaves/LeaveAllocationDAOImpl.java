package com.distna.service.leaves;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.leaves.LeaveAllocation;
import com.distna.domain.leaves.LeaveApplication;

public class LeaveAllocationDAOImpl implements LeaveAllocationDAO {

	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	@Override
	public void saveLeaveAllocation(LeaveAllocation leaveAllocation) {
		//hibernateTemplate.merge(leaveAllocation);
		hibernateTemplate.save(leaveAllocation);
		
	}

	@Override
	public List<LeaveAllocation> getLeaveAllocation() {
		
		return hibernateTemplate.find("from LeaveAllocation");
	}

	@Override
	public List<LeaveAllocation> getLeaveAllocationById(int id) {
		
		return hibernateTemplate.find("from LeaveAllocation where id="+id);
	}

	@Override
	public void deleteLeaveAllocation(int id) {
		
		List<LeaveAllocation> leaveAllocations=hibernateTemplate.find("from LeaveAllocation where id="+id);
		hibernateTemplate.deleteAll(leaveAllocations);
	}


	@Override
	public void updateLeaveAllocation(LeaveAllocation leaveAllocation) {
		
		hibernateTemplate.saveOrUpdate(leaveAllocation);
	}
	
	/*@Override
	public List<LeaveApplication> getApplicationsByIdandStatus(int id) {
		
		return  hibernateTemplate.find("from LeaveApplication where status=1 and id="+id);
	}*/

}
