package com.distna.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbBackup {
	
	String mysqlInstallLocation="";
	
	public boolean backupDB(String dbName, String dbUserName, String dbPassword, String path) {
		 
		
		try {
			mysqlInstallLocation=WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE,          //HKEY
					   "Program Filesx86\\MySQL\\MySQL Server 5.0",           //Key
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
		}
		
		
        String executeCmd = "\""+mysqlInstallLocation+"bin\\mysqldump\" -u " + dbUserName + " -p" + dbPassword + " " + dbName + " -r " +"\""+path+"\"";
        Process runtimeProcess;
        try {
 
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            InputStream inputStream = runtimeProcess.getInputStream();
            
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String printline="";
            while((printline=bufferedReader.readLine())!=null)
            {
            	System.out.println(printline);
            }
            
            int  processComplete=runtimeProcess.exitValue();
 
            if (processComplete == 0) {
                System.out.println("Backup created successfully");
              return true;
            } else {
                System.out.println("Could not create the backup");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
 
        return false;
    }
	
	
	
	
	 public boolean restoreDB(String dbName,String dbUserName, String dbPassword, String source) {
		 
			try {
				mysqlInstallLocation=WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE,          //HKEY
						   "Program Filesx86\\MySQL\\MySQL Server 5.0",           //Key
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
			}
		 
		 
		 
		 
	        String[] executeCmd = new String[]{ mysqlInstallLocation+"bin\\mysql",dbName,"--user=" + dbUserName, "--password=" + dbPassword, "-e", "source "+source};
	        createDatabase(dbName,dbUserName,dbPassword);
	        Process runtimeProcess;
	        try {
	 
	            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
	            int processComplete = runtimeProcess.waitFor();
	 
	            if (processComplete == 0) {
	                System.out.println("Backup restored successfully");
	                return true;
	            } else {
	                System.out.println("Could not restore the backup");
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	 
	        return false;
	    }  
	      
	        public int createDatabase(String dbName,String dbUserName, String dbPassword) {
	            {
	               int result=0; 
	                try{
	             Class.forName("com.mysql.jdbc.Driver");
	             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",dbUserName,dbPassword);
	             String sql = "CREATE DATABASE "+dbName;
	             Statement stmt = conn.createStatement();
	             result=stmt.executeUpdate(sql);
	             stmt.close();
	             conn.close();
	             }
	             catch(SQLException sqlException){
	                if (sqlException.getErrorCode() == 1007) {
	                   // Database already exists error
	                    result=2;
	                   System.out.println(sqlException.getMessage());
	               } else {
	                   // Some other problems, e.g. Server down, no permission, etc
	                   sqlException.printStackTrace();
	                } 
	             }
	             catch(Exception e)
	             {
	                 e.printStackTrace();
	             }
	                return result;
	            }
	        }
	        
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public static void main(String [] args)
	{
		DbBackup dbBackup=new DbBackup();
		dbBackup.backupDB("distna", "root", "admin" , "E:\\distnaaaaaa.sql");
	
	}
*/
}
