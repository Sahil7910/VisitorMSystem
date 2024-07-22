package com.distna.service.charts;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class EmployeesPerDepartment {
	

	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;

	public void setSessionDataFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}
	
	
	private Map<Integer, Integer> leavesPerTypeMap;
	 private static final long serialVersionUID = 1L;
	 
	 

	    {
	        // set a theme using the new shadow generator feature available in
	        // 1.0.14 - for backwards compatibility it is not enabled by default
	        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
	                true));
	    }
	    
	    public EmployeesPerDepartment()
	    {
	    	
	    }
	    /**
	     * Creates a new demo instance.
	     *
	     * @param title  the frame title.
	     */
	    
	    

	    /**
	     * Returns a sample dataset.
	     *
	     * @return The dataset.
	     */
	    public void createDataset(HttpServletRequest request) {
	    	
	    	String query=null;
			
			ResultSet resultSet=null;
			
			Connection connection=null;
			
			Session session=sessionFactory.openSession();
			
			PreparedStatement preparedStatement=null;
			
			
			List<String> departmentNameList = new ArrayList<String>();
			
			List<Integer> employeesPerDepartmentList=new ArrayList<Integer>();
			
			connection=session.connection();

			query="SELECT de.name, count(emp.department_id) FROM employees as emp inner join departments as de on emp.department_id=de.id group by de.id";
				try {
					preparedStatement=connection.prepareStatement(query);
					resultSet=preparedStatement.executeQuery();

					while (resultSet.next()) {
						departmentNameList.add(resultSet.getString(1));
						employeesPerDepartmentList.add(resultSet.getInt(2));
						
					}
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			session.close();

			// row keys...
	        String series1 = "Leaves";

	        // create the dataset...
	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

	        for(int i=0; i<departmentNameList.size();i++)
	        {
	        	dataset.addValue(employeesPerDepartmentList.get(i), series1, departmentNameList.get(i));
	        }

	        JFreeChart chart = createChart(dataset, request);

	    }

	    /**
	     * Creates a sample chart.
	     *
	     * @param dataset  the dataset.
	     *
	     * @return The chart.
	     */
	    private static JFreeChart createChart(CategoryDataset dataset, HttpServletRequest request) {

	        // create the chart...
	        JFreeChart chart = ChartFactory.createBarChart(
	            "Employees Per Department",       // chart title
	            "Departments",               // domain axis label
	            "Number of Employees",                  // range axis label
	            dataset,                  // data
	            PlotOrientation.VERTICAL, // orientation
	            true,                     // include legend
	            true,                     // tooltips?
	            false                     // URLs?
	        );

	        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

	        // set the background color for the chart...
	        chart.setBackgroundPaint(Color.white);

	        // get a reference to the plot for further customisation...
	        CategoryPlot plot = (CategoryPlot) chart.getPlot();

	       
	        // set the range axis to display integers only...
	        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

	        // disable bar outlines...
	        BarRenderer renderer = (BarRenderer) plot.getRenderer();
	        renderer.setDrawBarOutline(false);

	        // set up gradient paints for series...
	        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.green,
	                0.0f, 0.0f, new Color(0, 0, 64));
	        /*GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green,
	                0.0f, 0.0f, new Color(0, 64, 0));
	        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,
	                0.0f, 0.0f, new Color(64, 0, 0));*/
	        renderer.setSeriesPaint(0, gp0);
	       /* renderer.setSeriesPaint(1, gp1);
	        renderer.setSeriesPaint(2, gp2);
*/
	        CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(
	                CategoryLabelPositions.createUpRotationLabelPositions(
	                        Math.PI / 6.0));
	        // OPTIONAL CUSTOMISATION COMPLETED.
	        
	        try {
				ChartUtilities.saveChartAsPNG(new File(request.getSession().getServletContext().getRealPath("Charts/EmployeesPerDepartment.png")), chart, 800, 500);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return chart;

	    }


}
