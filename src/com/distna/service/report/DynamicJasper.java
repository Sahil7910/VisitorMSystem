/*package com.distna.service.report;
import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.distna.domain.employee.AttendanceLogsBulkEntry;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;


public class DynamicJasper {
	private ReportDAO reportDAO; 
	public List<AttendanceLogsBulkEntry> bulkLogsEntry;
	
	
	public DynamicJasper(ReportDAO reportDAO)
	{
		this.reportDAO=reportDAO;
	}

	public JasperPrint buildReport(HttpServletRequest request, int workId,String dateFrom,String dateTo) throws ColumnBuilderException, JRException, ClassNotFoundException{
		Style headerStyle = getHeaderStyle();
		Style detailStyle = new Style();
		detailStyle.setBorder(Border.THIN);
		FastReportBuilder firstReport = getFirstReport(headerStyle, detailStyle);
		DynamicReport firstDynaRep = firstReport.build();
		bulkLogsEntry=reportDAO.getAttendanceLogsBulkEntryList(workId, dateFrom, dateTo);
		Object[] obj = new Object[bulkLogsEntry.size()];

		for(int i=0;i<bulkLogsEntry.size();i++)
		{
			AttendanceLogsBulkEntry attendanceLogsBulkEntry=new AttendanceLogsBulkEntry();
			attendanceLogsBulkEntry=(AttendanceLogsBulkEntry)bulkLogsEntry.get(i);
			obj[i]=attendanceLogsBulkEntry;
		}

		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(firstDynaRep, new ClassicLayoutManager(),new JRBeanArrayDataSource(obj));
		List pages = new ArrayList(jp.getPages());
		int i=1;
		for(int count=0;count<pages.size();count++){
			jp.addPage(i, (JRPrintPage)pages.get(count));
			i++;
		}
		return jp;
	}

	private static Style getHeaderStyle(){
		Style headerStyle = new Style();
		headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
		headerStyle.setBorderBottom(Border.PEN_2_POINT);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setBackgroundColor(Color.LIGHT_GRAY);
		headerStyle.setTextColor(Color.BLUE);
		headerStyle.setTransparency(Transparency.OPAQUE);
		return headerStyle;
	}

	private static AbstractColumn getColumn(String property, Class type,
			String title, int width, Style headerStyle, Style detailStyle)
					throws ColumnBuilderException 
	{
		AbstractColumn columnName = ColumnBuilder.getInstance()
				.setColumnProperty(property, type.getName()).setTitle(
						title).setWidth(Integer.valueOf(width))
						.setStyle(detailStyle).setHeaderStyle(headerStyle).build();
		return columnName;
	}

	private static FastReportBuilder getFirstReport(Style headerStyle, Style detailStyle) throws ColumnBuilderException, ClassNotFoundException{
		FastReportBuilder firstReport = new FastReportBuilder();

		AbstractColumn columnWorkId = getColumn("workID", Integer.class,
				"Employee No.", 30, headerStyle, detailStyle);
		AbstractColumn columnRecordDate = getColumn("recordDate", String.class,
				"Record Date", 30, headerStyle, detailStyle);

		AbstractColumn columnInTime = getColumn("inTime", String.class,
				"Check In Time", 30, headerStyle, detailStyle);
		AbstractColumn columnOutTime = getColumn("outTime", String.class,
				"Check Out Time", 30, headerStyle, detailStyle);
		firstReport.addColumn(columnWorkId);
		firstReport.addColumn(columnRecordDate);
		firstReport.addColumn(columnInTime);
		firstReport.addColumn(columnOutTime);
		firstReport.setTitle("Employee Wise Report");
		firstReport.setUseFullPageWidth(true); 

		return firstReport;
	}


}

*/