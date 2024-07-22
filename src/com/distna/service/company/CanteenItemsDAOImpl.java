package com.distna.service.company;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.company.CanteenItems;

public class CanteenItemsDAOImpl implements CanteenItemsDAO {

	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

		
		public void setSessionDataFactory(SessionFactory sessionFactory) 
		{
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
			this.sessionFactory=sessionFactory;
		}
	
	@Override
	public void saveCanteenItems(CanteenItems canteenItems) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(canteenItems);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CanteenItems> getCanteenItemsList() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from CanteenItems");
	}

	@SuppressWarnings("unchecked")
	@Override
	public CanteenItems getCanteenItem(int canteenItemid) {
		// TODO Auto-generated method stub
		List<CanteenItems> canteenItemsList=hibernateTemplate.find("from CanteenItems where itemsId="+canteenItemid);
		if(canteenItemsList.size()>0)
		{
			return canteenItemsList.get(0);
		}
		return null;
	}

	@Override
	public void updateCanteenItems(CanteenItems canteenItems) {
		// TODO Auto-generated method stub
		hibernateTemplate.saveOrUpdate(canteenItems);
	}

	@Override
	public void deleteCanteenItems(int canteenItemid) {
		// TODO Auto-generated method stub
		hibernateTemplate.deleteAll(hibernateTemplate.find("from CanteenItems where itemsId="+canteenItemid));
	}

}
