package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.MasterSettings;

public interface MasterSettingsDAO 
{
	public void saveOrUpdateMasterSettings(MasterSettings masterSettings);
	public List<MasterSettings> getMasterSettings();
}
