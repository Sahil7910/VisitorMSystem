package com.distna.service.devicemanagement;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.devicemanagement.UserInfo;

public class UserInfoDAOImpl implements UserInfoDAO {
	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
		
		public void setSessionDataFactory(SessionFactory sessionFactory) 
		{
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
			this.sessionFactory=sessionFactory;
		}
		
public List<UserInfo> getUserInfo() {
			
			return hibernateTemplate.find("from UserInfo");
		}

@SuppressWarnings("unchecked")
public List<UserInfo> getUserInfo(String enrollmentNo) {
	return hibernateTemplate.find("from UserInfo where enrollmentNo='"+enrollmentNo+"'");
}

public void deleteUserInfo(String enrollmentNo)
{
	List<UserInfo> userInfoList=hibernateTemplate.find("from UserInfo where enrollmentNo='"+enrollmentNo+"'");
	hibernateTemplate.deleteAll(userInfoList);
}
public void deleteAllUserInfo()
{
	List<UserInfo> userInfoList=hibernateTemplate.find("from UserInfo");
	hibernateTemplate.deleteAll(userInfoList);
}

}
