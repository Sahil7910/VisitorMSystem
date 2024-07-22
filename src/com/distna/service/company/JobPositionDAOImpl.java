package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.JobPositions;

public class JobPositionDAOImpl implements JobPositionDAO {

	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public void saveJobPosition(JobPositions jobPositions) {
		hibernateTemplate.saveOrUpdate(jobPositions);
		
	}

	@Override
	public List<JobPositions> getJobPositions() {
		
		return hibernateTemplate.find("from JobPositions");
	}

	@Override
	public void deleteJobPosition(int id) {
		
		List<JobPositions> jobpositionList=hibernateTemplate.find("from JobPositions where id="+id);
		hibernateTemplate.deleteAll(jobpositionList);
	}

	@Override
	public List<JobPositions> getJobPositionsById(int id) {
		
		return hibernateTemplate.find("from JobPositions where id="+id);
	}

	
	
	
	
}
