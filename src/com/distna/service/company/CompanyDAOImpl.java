package com.distna.service.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.ShiftMaster;
import com.distna.domain.company.Company;

public class CompanyDAOImpl implements CompanyDAO {

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

	public void setSessionDataFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int getCompanyCurrentId() {
		@SuppressWarnings("unchecked")
		List<Company> companyList = hibernateTemplate.find("from Company");
		if (companyList.size() == 0)
			return 0;
		else {
			Company lastObj = companyList.get(companyList.size() - 1);
			return (lastObj.getId());
		}
	}

	@Override
	public void saveOrUpdateCompany(Company company) {
		if (hibernateTemplate.find("from ShiftMaster where shiftname='DayShift' and shiftcode='DisDay'")
				.size() == 0) {
			ShiftMaster shiftMaster = new ShiftMaster();
			shiftMaster.setShiftname("DayShift");
			shiftMaster.setShiftcode("DisDay");
			shiftMaster.setActive(true);
			hibernateTemplate.save(shiftMaster);
		}
		hibernateTemplate.saveOrUpdate(company);
	}

	@Override
	public void saveCompany(Company company) {
		hibernateTemplate.save(company);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getCompanies() {

		/*
		 * Session session = sessionFactory.openSession(); String
		 * query="SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA='distna' AND TABLE_NAME='divisions'"
		 * ; List<Integer> value= session.createQuery(query).list(); session.close();
		 */

		return hibernateTemplate.find("from Company");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteCompany(int id) {
		List<Company> companiesList = hibernateTemplate.find("from Company where id=" + id);
		if (companiesList.size() != 0) {
			hibernateTemplate.delete(companiesList.get(0));
			Session session = sessionFactory.openSession();
			/*
			 * String query="truncate table employees";
			 * session.createQuery(query).executeUpdate();
			 */
			try {
				Connection connection = session.connection();
				PreparedStatement preparedStatement = connection.prepareStatement("TRUNCATE TABLE employees");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE policies");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE departments");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE designation");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE designation_level");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE divisions");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE job_positions");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE job_roles");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE locations");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE priority");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE workspaces");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE employee_assessment");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE attendance_logs");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE attendance_logs_bulk_entry");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE attrecord");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE education");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE employee_experiences");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE employee_messages");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE employee_privilege");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE employee_skills");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE lists");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE employee_projects");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE breaks");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE allowed_leaves");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE employee_leaves");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE leave_type");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE official_tours");
				preparedStatement.executeUpdate();
				/*
				 * preparedStatement = connection.prepareStatement("TRUNCATE TABLE statuses");
				 * preparedStatement.executeUpdate();
				 */
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE calcategory");
				preparedStatement.executeUpdate();
				/*
				 * preparedStatement = connection.prepareStatement("TRUNCATE TABLE caltimes");
				 * preparedStatement.executeUpdate();
				 */
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE daterange");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE cal_holidays");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE musterreport");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE cal_employee_shifts");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE calcalendar");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE shift_mast");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE add_device");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE cities");
				preparedStatement.executeUpdate();
				preparedStatement = connection.prepareStatement("TRUNCATE TABLE contractor");
				preparedStatement.executeUpdate();
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Company getCompanyById(int id) {

		List<Company> companiesList = hibernateTemplate.find("from Company where id=" + id);
		if (companiesList.size() != 0) {
			return companiesList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int getCompanyCount() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from Company").size();
	}

}
