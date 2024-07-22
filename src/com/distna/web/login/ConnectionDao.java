package com.distna.web.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class ConnectionDao {
	 static Connection conn = null;

	public   Connection getConnection() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/";// e.g. jdbc:mysql://localhost:3306/
		String dbName = "distna"; // here the name of Database.
		String uname = "root"; // username
		String pwd = "admin"; // password
		String cls = "com.mysql.jdbc.Driver"; // MySQL jdbc driver
		try {
			Class.forName(cls);
			conn = DriverManager.getConnection(url + dbName, uname, pwd);
			System.out.println("connected to Database");
		} catch (Exception e) { // TODO Auto-generated catch block
			System.out.println("SQL Exception occurred while getting connection object. \nDetails : " + e.getMessage());
			e.printStackTrace();
		}
		return conn;
	}
	
	 }
