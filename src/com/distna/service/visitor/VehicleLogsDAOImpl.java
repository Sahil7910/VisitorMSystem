package com.distna.service.visitor;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.visitor.VehicleLogs;

public class VehicleLogsDAOImpl implements VehicleLogsDAO
{
	private HibernateTemplate hibernateTemplate;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Override
	public void saveVehicleLogs(VehicleLogs vehicleLogs) {
		hibernateTemplate.save(vehicleLogs);
	}

	@Override
	public void updateVehicleLogs(VehicleLogs vehicleLogs)
	{
		hibernateTemplate.saveOrUpdate(vehicleLogs);
	}

	@Override
	public void deleteVehicleLogs(int logId) {
		hibernateTemplate.deleteAll(hibernateTemplate.find("from VehicleLogs where logId="+logId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public VehicleLogs getVehicleLogs(int logId) {
		List<VehicleLogs> vehicleLogsList=hibernateTemplate.find("from VehicleLogs where logId="+logId);
		if(vehicleLogsList.size()>0)
		{
			return vehicleLogsList.get(0);
		}
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleLogs> getVehicleLogsList() {
		return hibernateTemplate.find("from VehicleLogs");
	}
	
	
}
