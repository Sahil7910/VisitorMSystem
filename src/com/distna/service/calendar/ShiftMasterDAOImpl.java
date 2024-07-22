package com.distna.service.calendar;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.ShiftAllocation;
import com.distna.domain.calendar.ShiftDefinition;
import com.distna.domain.calendar.ShiftMaster;
import com.distna.domain.employee.Employee;

public class ShiftMasterDAOImpl implements ShiftMasterDAO
{
	private HibernateTemplate hibernateTemplate;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Override
	public void saveShiftMaster(ShiftMaster shiftMaster) 
	{
		hibernateTemplate.save(shiftMaster);
	}
	@Override
	public List<ShiftMaster> getShiftMasterList() {
		return hibernateTemplate.find("from ShiftMaster  where shiftname!='DefaultShift' and shiftcode!='DisDS'");
	}
	@Override
	public void deleteShiftMaster(int id) 
	{
		List<ShiftMaster> shiftMasterList=getShiftMasterList(id);
		hibernateTemplate.deleteAll(shiftMasterList);
		List<ShiftDefinition> shiftDefinitionList=hibernateTemplate.find("from ShiftDefinition where shiftid="+id);
		if(shiftDefinitionList.size()!=0)
		{
			hibernateTemplate.deleteAll(shiftDefinitionList);
		}
		
		List<ShiftAllocation> shiftAllocationList=hibernateTemplate.find("from ShiftAllocation where shiftid="+id);
		if(shiftAllocationList.size()!=0)
		{
			for(ShiftAllocation shiftAllocation:shiftAllocationList)
			{
				shiftAllocation.setShiftid(0);
			}
			hibernateTemplate.saveOrUpdateAll(shiftAllocationList);
		}
	}
	@Override
	public List<ShiftMaster> getShiftMasterList(int id) {
		return hibernateTemplate.find("from ShiftMaster where shiftid="+id);
	}
	@Override
	public void saveOrUpdateShiftMaster(ShiftMaster shiftMaster) 
	{
		hibernateTemplate.saveOrUpdate(shiftMaster);
	}
}
