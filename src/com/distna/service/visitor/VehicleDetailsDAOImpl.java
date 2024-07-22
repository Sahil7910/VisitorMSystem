package com.distna.service.visitor;

import java.util.List;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.visitor.VehicleDetails;

public class VehicleDetailsDAOImpl implements VehicleDetailsDAO
{
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}
	
	@Override
	public void saveVehicleDetails(VehicleDetails vehicleDetails) {
		hibernateTemplate.save(vehicleDetails);
	}

	@Override
	public void updateVehicleDetails(VehicleDetails vehicleDetails) {
		hibernateTemplate.saveOrUpdate(vehicleDetails);
	}

	@Override
	public void deleteVehicleDetails(int vehicleId) {
		hibernateTemplate.deleteAll(hibernateTemplate.find("from VehicleDetails where vehicleId="+vehicleId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public VehicleDetails getVehicleDetails(int vehicleId) {
		List<VehicleDetails> vehicleDetailsList=hibernateTemplate.find("from VehicleDetails where vehicleId="+vehicleId);
		if(vehicleDetailsList.size()>0)
		{
			return vehicleDetailsList.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleDetails> getVehicleDetailsList()
	{
		return hibernateTemplate.find("from VehicleDetails");
	}

	@Override
	public List<String> getUniqueTypeVehicleList() {
		
		
		Session session=sessionFactory.openSession();
		String query="SELECT distinct vehicleType from VehicleDetails";
		List<String> listOfVehicleTYpe= session.createQuery(query).list();
		return listOfVehicleTYpe;
	}

}
