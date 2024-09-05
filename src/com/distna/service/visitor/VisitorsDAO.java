package com.distna.service.visitor;

import java.util.List;

import com.distna.domain.visitor.VisitorType;
import com.distna.domain.visitor.Visitors;

public interface VisitorsDAO {
	
	public int saveVisitor(Visitors visitors);
	
	public void saveVisitorType(String visitorType);
	
	public List<VisitorType> getVisitorTypeList();
	
	public List<Visitors> getVisitors();
	
	public Visitors getVisitor(int visitorId);
	
	public void updateVisitor(Visitors visitors);
	
	public void deleteVisitor(int visitorId);
	
	public int getTodayvisitorCount();
	
	public int getpresentVisitorCount();
	
	public int getTotalVisitorCount();
	
	public int getAllowedMeetings(int employeeid);
}
