package com.distna.utility;

import java.lang.reflect.InvocationTargetException;

public class ReadRegistryValue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String value="";
		try {
			value = WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE,          //HKEY
				   "SOFTWARE\\MySQL AB\\MySQL Server 5.0",           //Key
				   "Location");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                                              //ValueName
			    System.out.println("Value = " + value);  
	}

}
