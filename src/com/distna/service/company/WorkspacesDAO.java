package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.Workspaces;

public interface WorkspacesDAO {
	public void saveWorkspace(Workspaces workspaces);
	public List<Workspaces> getAllWorkspaces();
	public void deleteWorkspace(int id);
	public Workspaces getWorkspaceById(int id);
	public void saveOrUpdateWorkspace(Workspaces workspaces);
	

}
