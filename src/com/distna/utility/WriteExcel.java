package com.distna.utility;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import com.distna.service.visitor.VisitorBatchPojo;



import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Font;
import jxl.format.Format;
import jxl.format.Orientation;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel {

  private WritableCellFormat timesBoldUnderline;
  private WritableCellFormat times;
  private String inputFile;
  
public void setOutputFile(String inputFile) {
  this.inputFile = inputFile;
  }

  public void write(VisitorBatchPojo visitorBatchPojo) throws IOException, WriteException, PrinterException {
    File file = new File(inputFile);
    WorkbookSettings wbSettings = new WorkbookSettings();

    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    workbook.createSheet("Report", 0);
    WritableSheet excelSheet = workbook.getSheet(0);
    createLabel(excelSheet);
    createContent(excelSheet,visitorBatchPojo);

    workbook.write();
    workbook.close();
    System.out.println("file path"+file.getAbsolutePath());
    TicketPrintPage ticketPrintPage=new TicketPrintPage(file);
	ticketPrintPage.printTicketFile(file,1);
	System.out.println("print Complete");
  }

  
  //Daywise.......setOutputFileDaywise
  
  
  private void createLabel(WritableSheet sheet)
      throws WriteException {
    // Lets create a times font
    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
    // Define the cell format
    times = new WritableCellFormat(times10pt);
    // Lets automatically wrap the cells
    times.setWrap(true);

    // Create create a bold font with unterlines
    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
        UnderlineStyle.SINGLE);
    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
    // Lets automatically wrap the cells
    timesBoldUnderline.setWrap(true);

    CellView cv = new CellView();
    cv.setFormat(times);
    cv.setFormat(timesBoldUnderline);
    cv.setAutosize(true);
    cv.setSize(20);
    

    // Write a few headers
    //addCaption(sheet, 0, 0, "Name");
    //addCaption(sheet, 1, 0, "");
    

  }

  private void createContent(WritableSheet sheet,VisitorBatchPojo visitorBatchPojo) throws WriteException,
      RowsExceededException {
	  addLabel(sheet, 1, 0, visitorBatchPojo.getBatchId());
	  addLabel(sheet, 1, 1, visitorBatchPojo.getCmpyName());
	  addLabel(sheet, 1, 2, visitorBatchPojo.getDate());
	  addLabel(sheet, 1, 3, visitorBatchPojo.getIntime());
	  addLabel(sheet, 1, 4, visitorBatchPojo.getVisiting());
	  addLabel(sheet, 1, 5, visitorBatchPojo.getPurpose());
	  addLabel(sheet, 1, 6, visitorBatchPojo.getVisitorId()+"");
	  
  }


  private void addCaption(WritableSheet sheet, int column, int row, String s)
      throws RowsExceededException, WriteException {
    Label label;
    label = new Label(column, row, s, timesBoldUnderline);
    sheet.addCell(label);
  }

  private void addNumber(WritableSheet sheet, int column, int row,
      Integer integer) throws WriteException, RowsExceededException {
    Number number;
    number = new Number(column, row, integer, times);
    sheet.addCell(number);
  }

  private void addLabel(WritableSheet sheet, int column, int row, String s)
      throws WriteException, RowsExceededException {
    Label label;
    label = new Label(column, row, s, times);
    sheet.addCell(label);
  }

  /*public void writeExcelMain(HttpSession session) throws WriteException, IOException, PrinterException {
    WriteExcel test = new WriteExcel();
    String sessionUrl=session.getServletContext().getRealPath("/");
    //System.out.println(sessionUrl);
    test.setOutputFile(sessionUrl+"/bill.xls");
    test.write(session);
    System.out.println("Please check the result file under E:/write/bill.xls ");
  }*/
  
  
} 