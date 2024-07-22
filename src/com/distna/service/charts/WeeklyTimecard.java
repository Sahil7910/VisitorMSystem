package com.distna.service.charts;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JPanel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.YIntervalRenderer;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class WeeklyTimecard {
	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	private String employeeName=null;

	public void setSessionDataFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}
	
	
	public void createDemoPanel(HttpServletRequest request, int workId, String fromDate, String toDate )
    {
            JFreeChart jfreechart = createChart(createDataset(workId, fromDate, toDate));
            File oldFile=new File(request.getSession().getServletContext().getRealPath("Charts/WeeklyTimecard.png"));
            if (oldFile.exists())
            {
            oldFile.delete();	
            }
            try {
				ChartUtilities.saveChartAsPNG(new File(request.getSession().getServletContext().getRealPath("Charts/WeeklyTimecard.png")), jfreechart, 800, 500);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
	
	
	public void createDemoPanel(HttpServletRequest request,String name,int workId, String fromDate, String toDate )
    {
            JFreeChart jfreechart = createChart(createDataset(workId, fromDate, toDate));
            File oldFile=new File(request.getSession().getServletContext().getRealPath("Charts/WeeklyTimecard_"+name+".png"));
            if (oldFile.exists())
            {
            oldFile.delete();	
            }
            try {
				ChartUtilities.saveChartAsPNG(new File(request.getSession().getServletContext().getRealPath("Charts/WeeklyTimecard_"+name+".png")), jfreechart, 800, 500);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
	
	private IntervalXYDataset createDataset(int workId, String fromDate, String toDate)
    {
		
		YIntervalSeries yintervalseries = new YIntervalSeries("Time Difference");
		
		
		String [] splitCheckIn=null;
		
		String [] splitCheckOut=null;
		
		String checkIn=null;
		
		String checkOut=null;
		
		String query=null;
		
		ResultSet resultSet=null;
		
		Connection connection=null;
		
		Session session=sessionFactory.openSession();
		
		PreparedStatement preparedStatement=null;
		
		connection=session.connection();
		
		query="select A.WorkID,A.Recorddate,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',1)as CheckIn,SUBSTRING_INDEX(A.timeAsPerShftTimings,'~',-1)as CheckOut,E.first_name as FirstName,E.last_name as LastName from attendance_logs_bulk_entry A inner join  shift_mast S on A.shift=S.shiftid inner join employees E on A.WorkID=E.employee_no where  A.WorkID="+workId+" and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') >= STR_TO_DATE('"+fromDate+"','%d-%m-%Y') and STR_TO_DATE(A.Recorddate,'%d-%m-%Y') <= STR_TO_DATE('"+toDate+"','%d-%m-%Y') order by STR_TO_DATE(A.Recorddate,'%d-%m-%Y')";
		
		try {
			preparedStatement=connection.prepareStatement(query);
			resultSet=preparedStatement.executeQuery();
			while (resultSet.next()) {
			
				employeeName=resultSet.getString("FirstName")+" "+resultSet.getString("LastName");
				
				splitCheckIn=resultSet.getString("CheckIn").toString().split(":");
				splitCheckOut=resultSet.getString("CheckOut").toString().split(":");
				
				checkIn=splitCheckIn[0]+"."+(Integer.parseInt(splitCheckIn[1])*5/3);
				checkOut=splitCheckOut[0]+"."+(Integer.parseInt(splitCheckOut[1])*5/3);
				
				yintervalseries.add(resultSet.getInt("Recorddate"), 1, Double.parseDouble(checkIn), Double.parseDouble(checkOut));
				
			}

		}
		
		catch (Exception e) {
			// TODO: handle exception
		}
			
		
			
		
           /* double d = 100D;
            
            
            for (int i = 0; i < 100; i++)
            {
                    d += Math.random() - 0.48999999999999999D;
            }*/

            YIntervalSeriesCollection yintervalseriescollection = new YIntervalSeriesCollection();
            yintervalseriescollection.addSeries(yintervalseries);
            return yintervalseriescollection;
    }
	
	private JFreeChart createChart(IntervalXYDataset intervsalxydataset)
    {
            JFreeChart jfreechart = ChartFactory.createScatterPlot("Timecard chart for "+employeeName, "Date", "Time (24 Hrs)", intervsalxydataset, PlotOrientation.VERTICAL, true, true, false);
            XYPlot xyplot = (XYPlot)jfreechart.getPlot();
            xyplot.setDomainPannable(true);
            xyplot.setRenderer(new YIntervalRenderer());
            return jfreechart;
    }

}
