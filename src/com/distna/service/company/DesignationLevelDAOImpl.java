package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import com.distna.domain.company.DesignationLevel;



public class DesignationLevelDAOImpl implements DesignationLevelDAO {

private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public int getDesignationLevelCurrentId() {
		List<DesignationLevel> designationLevelList=hibernateTemplate.find("from DesignationLevel");
		if(designationLevelList.size()==0)
			return 0;
		else
		{
			DesignationLevel lastObj=designationLevelList.get(designationLevelList.size()-1);
			return(lastObj.getId()+1);
		}
	}
	@Override
	public void saveDesignationLevel(DesignationLevel designationLevel) {
		hibernateTemplate.save(designationLevel);
	}

	@Override
	public void saveOrUpdateDesignationLevel(DesignationLevel designationLevel) {
		hibernateTemplate.saveOrUpdate(designationLevel);
	}
	
	@Override
	public List<DesignationLevel> getDesignationLevelList() {
		return hibernateTemplate.find("from DesignationLevel");
	}

	@Override
	public void deleteDesignationLevel(int id) {
		List<DesignationLevel> designationLevelList=hibernateTemplate.find("from DesignationLevel where id="+id);
		if(designationLevelList.size()!=0)
		{
			hibernateTemplate.delete(designationLevelList.get(0));
		}	
	}

	@Override
	public DesignationLevel getDesignationLevelById(int id) {
		List<DesignationLevel> designationLevelList=hibernateTemplate.find("from DesignationLevel where id="+id);
		if(designationLevelList.size()!=0)
		{
			return designationLevelList.get(0);
		}	
		else
		{
			return null;
		}
	}
	
	
	public List<DesignationLevel> getDesignationLevel()
	{
		return hibernateTemplate.find("from DesignationLevel");
		
		
	}

	@Override
	public boolean getDesignationLevelforRank(int rank,int id) 
	{
		boolean flag=true;
		List<DesignationLevel> designationList=getDesignationLevelforRank(rank);
		if(designationList.size()>0)
		{
			for (DesignationLevel designationLevel : designationList) 
			{
				if(designationLevel.getId()==id)
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
	public List<DesignationLevel> getDesignationLevelforRank(int rank) {
		return  hibernateTemplate.find("from DesignationLevel where rank ="+rank);
	}
	
}
