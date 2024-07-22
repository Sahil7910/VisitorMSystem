package com.distna.web.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.distna.domain.employee.Employee;
import com.distna.service.employee.EmployeeDAO;
import com.distna.utility.EncryptPassword;



@Controller
public class LoginController {
	private ModelMap modelMap = new ModelMap();
	private EmployeeDAO employeeDAO;
	private EncryptPassword encryptPassword;

	@Autowired
	public LoginController(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@RequestMapping(value = "liekeycheck.htm", method = RequestMethod.GET)
	public ModelAndView liecheck(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return new ModelAndView("liekeycheck");
	}

	@RequestMapping(value = "keyerror.htm", method = RequestMethod.GET)
	public ModelAndView liecheckerror(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return new ModelAndView("keyerror");
	}

	@RequestMapping(value = "help.htm", method = RequestMethod.GET)
	public void help(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		// read the file name.
		try {
			File f = new File("c:\\Program Files\\Java\\jdk1.6.0_11\\bin\\Help.pdf");
			// set the content type(can be excel/word/powerpoint etc..)
			response.setContentType("application/pdf");
			// set the header and also the Name by which user will be prompted to save
			response.setHeader("Content-Disposition", "attachment;filename=\"Time and attendance Help.pdf\"");

			// get the file name
			String name = f.getName().substring(f.getName().lastIndexOf("/") + 1, f.getName().length());
			// OPen an input stream to the file and post the file contents thru the
			// servlet output stream to the client m/c

			InputStream in = new FileInputStream(f);
			ServletOutputStream outs = response.getOutputStream();

			int bit = 256;
			int i = 0;

			while ((bit) >= 0) {
				bit = in.read();
				outs.write(bit);
			}
			outs.flush();
			outs.close();
			in.close();
			// System.out.println("" +bit);
		} catch (IOException ioe) {
			ioe.printStackTrace(System.out);
		}
//			System.out.println( "\n" + i + " bytes sent.");
//			System.out.println( "\n" + f.length() + " bytes sent.");

	}

	@RequestMapping(value = "custform.htm", method = RequestMethod.GET)
	public ModelAndView custform(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		return new ModelAndView("custform");
	}

	@RequestMapping(value = "macerror.htm", method = RequestMethod.GET)
	public ModelAndView macerrorcontroller(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		return new ModelAndView("macerror");
	}

	@RequestMapping(value = "custform.htm", method = RequestMethod.POST)
	public ModelAndView custform1(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {
		String name = request.getParameter("name");
		String company = request.getParameter("company");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		System.out.println(name + " " + company);
		getmacid m = new getmacid();
		String liekey = m.getmac();
		System.out.println("liekey= " + liekey);

		String message = "Name : " + name + "\n" + "Company : " + company + "\n" + "Mobile : " + mobile + "\n"
				+ "Email : " + email + "\n" + "Product Key : " + liekey + "\n";

		System.out.println("Message " + message);

		emailsender e1 = new emailsender();
		// e1.sendFromGMail(message);

		Connection conn = null;
		Statement stmt = null;
		ConnectionDao d = new ConnectionDao();
		conn = d.getConnection();

		try {
			stmt = conn.createStatement();
			String sql1 = "insert into liekeys (keyval,status) values('" + liekey + "','0')";
			stmt = conn.createStatement();
			if (liekey.equals("nomac")) {
				return new ModelAndView(new RedirectView("macerror.htm"));
			} else {
				int i = stmt.executeUpdate(sql1);
				e1.sendFromGMail(message);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView(new RedirectView("liekeycheck.htm"));
	}

	@RequestMapping(value = "liekeycheck.htm", method = RequestMethod.POST)
	public ModelAndView liecheck1(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException {

		ConnectionDao d = new ConnectionDao();
		String k = request.getParameter("key");
		k = k.trim();
		System.out.println(k);
		// String SQL = "SELECT * FROM liekeys where keyval like '"+k+"' and status='1'
		// ;";
		// System.out.println("SQL : "+ SQL);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String temp, status;
		conn = d.getConnection();
		getmacid m = new getmacid();
		String liekey = m.getmac();
		if (k.equals(liekey)) {
			try {
				stmt = conn.createStatement();
				// rs = stmt.executeQuery(SQL);

				String sql1 = "update liekeys set status=1 where keyval like '" + k + "'";
				stmt = conn.createStatement();
				int i = stmt.executeUpdate(sql1);
				if (i > 0)
					System.out.println("found Key");
				else {
					System.out.println("wrong key");

					return new ModelAndView(new RedirectView("keyerror.htm"));
				}

			} catch (Exception e) {
				try {
					rs.close();
					stmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					// e1.printStackTrace();
				}

				// TODO Auto-generated catch block
				// e.printStackTrace();
			}

		} else {
			System.out.println("in else");
			return new ModelAndView(new RedirectView("keyerror.htm"));
		}
		System.out.println("in post");
		return new ModelAndView(new RedirectView("loginpage.htm"));
	}

	@RequestMapping(value = "registerUser.htm", method = RequestMethod.GET)
	public ModelAndView registerUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			Employee employee) {
		modelMap.addAttribute("userList", employeeDAO.getUserType());
		return new ModelAndView("registerUser", modelMap);
	}

	@RequestMapping(value = "submitRegisterUser.htm", method = RequestMethod.GET)
	public ModelAndView submitRegisterUser(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Employee employee, BindingResult bindingResult) throws Exception {
		encryptPassword = new EncryptPassword();
		String password = request.getParameter("password");
		String encryptedPassword = encryptPassword.encrypt(password);
		employee.setPassword(encryptedPassword);
		int id = employeeDAO.saveEmployeeAndGetId(employee);
		employee.setEmployeeNo(id);
		employeeDAO.saveUser(employee);
		return new ModelAndView(new RedirectView("registerUser.htm"));
	}

	/*
	 * @RequestMapping(value = "ManagerDashBoard.htm", method = RequestMethod.GET)
	 * public ModelAndView ManagerDashBoard(HttpServletRequest request,
	 * HttpServletResponse response, HttpSession session, Employee employee) {
	 * 
	 * return new ModelAndView("ManagerDashBoard", modelMap); }
	 * 
	 * @RequestMapping(value = "submit.htm", method = RequestMethod.GET) public
	 * ModelAndView submit(HttpServletRequest request, HttpServletResponse response,
	 * HttpSession session, Employee employee, BindingResult bindingResult) throws
	 * Exception {
	 * 
	 * return new ModelAndView(new RedirectView("ManagerDashBoard.htm")); }
	 */


	
}
