package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.Company;
import com.distna.domain.company.CompanyPolicies;
import com.distna.domain.company.Location;
import com.distna.domain.company.Workspaces;

public class LocationDAOImpl implements LocationDAO {

	private HibernateTemplate hibernateTemplate;
	
	
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
 	
	
	@Override
	public void saveLocation(Location location) {
		
		hibernateTemplate.saveOrUpdate(location);
	}



	@Override
	public List<Location> getLocation() {
		
		return hibernateTemplate.find("from Location");
	}



	@Override
	public void deleteLocation(int id) {
		
		List <Location> deleteLocationList =hibernateTemplate.find("from Location where id="+id);
		hibernateTemplate.deleteAll(deleteLocationList);
		
		List<CompanyPolicies> companyPoliciesList=hibernateTemplate.find("from CompanyPolicies where locationId="+id);
		if(companyPoliciesList.size()!=0)
		{
			for(CompanyPolicies companyPolicies:companyPoliciesList)
			{
				companyPolicies.setLocationId(0);
			}
			hibernateTemplate.saveOrUpdateAll(companyPoliciesList);
		}
		
		List<Workspaces> workspaceList=hibernateTemplate.find("from Workspaces where location="+id);
		if(workspaceList.size()!=0)
		{
			for(Workspaces workspaces:workspaceList)
			{
				workspaces.setLocation(0);
			}
			hibernateTemplate.saveOrUpdateAll(workspaceList);
		}
		
		List<Location> locationUpdateList=hibernateTemplate.find("from Location where sub_locationof="+id);
		if(locationUpdateList.size()!=0)
		{
			for(Location location:locationUpdateList)
			{
				location.setSub_locationof(0);
			}
			hibernateTemplate.saveOrUpdateAll(locationUpdateList);
		}
		
		List<Company> companyForBillingList=hibernateTemplate.find("from Company where billingLocation="+id);
		if(companyForBillingList.size()!=0)
		{
			for(Company company:companyForBillingList)
			{
				company.setBillingLocation(0);
			}
			hibernateTemplate.saveOrUpdateAll(companyForBillingList);
		}
		
		List<Company> companyForShippingList=hibernateTemplate.find("from Company where shippingLocation="+id);
		if(companyForShippingList.size()!=0)
		{
			for(Company company:companyForShippingList)
			{
				company.setShippingLocation(0);	
			}
			hibernateTemplate.saveOrUpdateAll(companyForShippingList);
		}
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Location> getLocationById(int id) {
		
		return hibernateTemplate.find("from Location where id="+id);
	}



	@Override
	public Location getLocationObject(int id) 
	{
		List<Location> locationList=hibernateTemplate.find("from Location where id="+id);
		return locationList.get(0);
	}
	
	

	
	
	
	
}
