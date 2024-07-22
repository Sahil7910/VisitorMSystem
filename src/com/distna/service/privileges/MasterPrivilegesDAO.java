package com.distna.service.privileges;

import java.util.List;

import com.distna.domain.privileges.MasterPrivileges;

public interface MasterPrivilegesDAO {
	
	public List<MasterPrivileges> getMasterPrivileges();
	public List<MasterPrivileges> getMasterPrivilegesByPrivilege(String privilegeName);
	public void savaList(List<MasterPrivileges> masterPrivileges);
}
