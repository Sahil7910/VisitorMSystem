package com.distna.service.devicemanagement;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.devicemanagement.AddDevice;

public class AddDeviceDAOImpl implements AddDeviceDAO {

	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
		
		public void setSessionDataFactory(SessionFactory sessionFactory) 
		{
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
			this.sessionFactory=sessionFactory;
		}
	@Override
	public void saveOrUpdateDevice(AddDevice addDevice) {
		hibernateTemplate.saveOrUpdate(addDevice);
	}
	
	@Override
	public List<AddDevice> getDeviceList() {
		return hibernateTemplate.find("from AddDevice");
	}
	@Override
	public AddDevice getDeviceInfoByIp(String ipAddress) {
		
		List<AddDevice> addDeviceList=hibernateTemplate.find("from AddDevice where ipAddress='"+ipAddress+"'");
		return addDeviceList.get(0);
	}
	@Override
	public List<AddDevice> getDeviceInfoListByIp(String ipAddress) {
		// TODO Auto-generated method stub
		List<AddDevice> addDeviceList=hibernateTemplate.find("from AddDevice where ipAddress='"+ipAddress+"'");
		return addDeviceList;
	}
	@Override
	public void deleteAttendanceDevice(String ipAddress) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(hibernateTemplate.find("from AddDevice where ipAddress='"+ipAddress+"'"));
	}

}
