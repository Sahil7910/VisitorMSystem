package com.distna.service.charts;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JPanel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class PresenceAbscencePercentage {
	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	
	public void setSessionDataFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}
	
	
	public void createDemoPanel(HttpServletRequest request , int workId, String dateFrom, String dateTo, int departmentId, String selectedcheckbox, String empReportType)
    {
            JFreeChart jfreechart = createChart(createDataset(workId, dateFrom, dateTo, departmentId, selectedcheckbox, empReportType));
            //return new ChartPanel(jfreechart);
            File oldFile=new File(request.getSession().getServletContext().getRealPath("Charts/PresenceAbsencePercentage.png"));
            if (oldFile.exists())
            {
            oldFile.delete();	
            }
            
            try {
				ChartUtilities.saveChartAsPNG(new File(request.getSession().getServletContext().getRealPath("Charts/PresenceAbsencePercentage.png")), jfreechart, 800, 500);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
	
	
    private TableXYDataset createDataset(int workId, String dateFrom, String dateTo, int departmentId, String selectedcheckbox, String empReportType)
    {
    	
            DefaultTableXYDataset defaulttablexydataset = new DefaultTableXYDataset();
            XYSeries xyseriesPresent = new XYSeries("Present", true, false);
            XYSeries xyseriesAbsent = new XYSeries("Absent", true, false);
            DateFormat dateFromFormat=new SimpleDateFormat("dd-MM-yyyy");
            DateFormat dateFormat=new SimpleDateFormat("M");
            String month="";
			try {
				month = dateFormat.format(dateFromFormat.parse(dateFrom));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
           /* String month="";
            
            if(Integer.parseInt(dateFrom.split("-")[1])>9)
            {
            	month=dateFrom.split("-")[1];
            }
            else
            {
            	month=dateFrom.split("-")[1].substring(1);
            }
            */
            String query=null;
    		
    		ResultSet resultSet=null;
    		
    		Connection connection=null;
    		
    		Session session=sessionFactory.openSession();
    		
    		PreparedStatement preparedStatement=null;
    		
    		connection=session.connection();
    		
    		if(empReportType.equals("single"))
    		{
    			query="SELECT empNo, presentCount,absentCount  FROM musterreport where musterMonth='"+month+"' and musterYear='"+dateFrom.split("-")[2]+"' and empNo="+workId;
    		}
    		else if(empReportType.equals("multiple"))
    		{	
    			
    		String selectedEmployees=selectedcheckbox.substring(0,selectedcheckbox.length()-1);
    		query="SELECT empNo, presentCount,absentCount  FROM musterreport where musterMonth='"+month+"' and musterYear='"+dateFrom.split("-")[2]+"' and empNo IN("+selectedEmployees+")";
    		}
    		else
    		{
    			query="SELECT empNo, presentCount,absentCount  FROM musterreport where musterMonth='"+month+"' and musterYear='"+dateFrom.split("-")[2]+"'";
    		}
            
            
            
            try {
				preparedStatement=connection.prepareStatement(query);
				resultSet=preparedStatement.executeQuery();
				while (resultSet.next()) {
					
					String employeeId=""+resultSet.getInt("empNo");
					String presentCount=""+resultSet.getInt("presentCount");
					String absentCount=""+resultSet.getInt("absentCount");
					
					xyseriesPresent.add(Double.parseDouble(employeeId) , Double.parseDouble(presentCount));
					 xyseriesAbsent.add(Double.parseDouble(employeeId) , Double.parseDouble(absentCount));
				}
				defaulttablexydataset.addSeries(xyseriesPresent);
				defaulttablexydataset.addSeries(xyseriesAbsent);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            
            return defaulttablexydataset;
    }
    
    
    private JFreeChart createChart(TableXYDataset tablexydataset)
    {
            NumberAxis numberaxis = new NumberAxis("Employee Ids");
            numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            NumberAxis numberaxis1 = new NumberAxis("Attendance");
            numberaxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            StackedXYBarRenderer stackedxybarrenderer = new StackedXYBarRenderer(0.10000000000000001D);
            stackedxybarrenderer.setDrawBarOutline(false);
            XYPlot xyplot = new XYPlot(tablexydataset, numberaxis, numberaxis1, stackedxybarrenderer);
            JFreeChart jfreechart = new JFreeChart("Present Absent Ratio", xyplot);
            ChartUtilities.applyCurrentTheme(jfreechart);
            return jfreechart;
    }

}
