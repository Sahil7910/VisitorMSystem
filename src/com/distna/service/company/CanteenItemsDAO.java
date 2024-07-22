package com.distna.service.company;

import java.util.List;

import com.distna.domain.company.CanteenItems;

public interface CanteenItemsDAO {
	
	public void saveCanteenItems(CanteenItems canteenItems);
	
	public List<CanteenItems> getCanteenItemsList();
	
	public CanteenItems getCanteenItem(int canteenItemid);
	
	public void updateCanteenItems(CanteenItems canteenItems);
	
	public void deleteCanteenItems(int canteenItemid);
	

}
