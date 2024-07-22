package com.distna.service.devicemanagement;

import java.util.List;

import com.distna.domain.devicemanagement.UserInfo;

public interface UserInfoDAO {
	public List<UserInfo> getUserInfo();
	public List<UserInfo> getUserInfo(String enrollmentNo) ;
	public void deleteUserInfo(String enrollmentNo) ;
	public void deleteAllUserInfo() ;
}
