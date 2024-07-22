package com.distna.service.visitor;

import java.util.List;

import com.distna.domain.visitor.VisitorGates;

public interface VisitorGatesDAO {
	public  List<VisitorGates> getVisitorGatesList();
	public  void saveVisitorGatesList(VisitorGates visitorGates);
}
