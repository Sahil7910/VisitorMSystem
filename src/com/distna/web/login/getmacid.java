package com.distna.web.login;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class getmacid {

	public String getmac()
	{
		InetAddress ip;
		String hex="nomac";
	    try {

	        ip = InetAddress.getLocalHost();
	        System.out.println("Current IP address : " + ip.getHostAddress());

	        NetworkInterface network = NetworkInterface.getByInetAddress(ip);

	        byte[] mac = network.getHardwareAddress();

	        System.out.print("Current MAC address : ");

	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < mac.length; i++) {
	            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "" : ""));        
	        }
	         hex=sb.toString();
	        //hex=hex.Replace(":", "");
	        System.out.println(sb.toString());

	        
	    } catch (UnknownHostException e) {

	       // e.printStackTrace();

	    } catch (SocketException e){

	      //  e.printStackTrace();

	    }
	    
	    catch(NullPointerException n)
	    {
	    	//hex="nomac";
	    	System.out.println("\n Null pointer exception");
	    	//n.printStackTrace();
	    }
	    return hex;
	    }
		
	
	
}
