package com.distna.utility;

import org.apache.commons.dbcp.BasicDataSource;

public class MyCustomBasicDataSource extends BasicDataSource {

	
	public MyCustomBasicDataSource() {
		super();
	}

	@Override
	public synchronized void setPassword(String password) {
		try {
			EncryptPassword encryptPassword=new EncryptPassword();
			super.setPassword(encryptPassword.decrypt(password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
