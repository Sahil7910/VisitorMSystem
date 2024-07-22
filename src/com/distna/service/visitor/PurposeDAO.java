package com.distna.service.visitor;

import java.util.List;

import com.distna.domain.visitor.Purpose;

public interface PurposeDAO {
	public List<Purpose> getPurposeList();
	public void savePurposeList(Purpose purpose);

}
