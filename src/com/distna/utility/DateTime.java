package com.distna.utility;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class DateTime {

	static Calendar currentDate = null;
	static SimpleDateFormat formatter = null;

	/**
	 * Current Date by given format
	 * @param format
	 * @return
	 */
	public static String CurrentDate(String format)
	{
		currentDate = Calendar.getInstance();
		formatter= new SimpleDateFormat(format);
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}

	/**
	 * Add or subtract month/date/year from given date
	 * @param date
	 * @param task
	 * @param no
	 * @return
	 * @throws java.text.ParseException 
	 */
	public static String updatedDate(String date, String taskBy, int value) throws java.text.ParseException
	{
		String newDate = null;
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		Date today = null;
		try {
			today = df.parse(date);        
			Calendar ca1 = Calendar.getInstance();
			ca1.setTime(today);

			if(taskBy.equalsIgnoreCase("date"))
				ca1.add(Calendar.DATE, value); // Add 23 days in Dates in Calendar
			if(taskBy.equalsIgnoreCase("month"))
				ca1.add(Calendar.MONTH, value); // Add 2 Month in Date in Calendar
			if(taskBy.equalsIgnoreCase("year"))
				ca1.add(Calendar.YEAR, value); // Add 4 Year in Date in Calendar

			String nDate = Integer.toString(ca1.get(Calendar.DATE));
			String nMonth = Integer.toString(ca1.get(Calendar.MONTH));
			String nYear = Integer.toString(ca1.get(Calendar.YEAR));

			if(nDate.length()<2)
				nDate = "0"+nDate;
			if(nMonth.length()<2)
				nMonth = "0"+nMonth;

			String finalDate = nDate+""+nMonth+""+nYear;

			newDate = finalDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static String UniqueCardNumber(String id)
	{
		String uniqueKey = UUID.randomUUID().toString();
		try{
			String[] datasplit = uniqueKey.split("-");
			uniqueKey = "";
			for(int i=0;i<datasplit.length; i++)
			{
				uniqueKey += datasplit[i]; 
			}
			if(uniqueKey.length()>20){
				String ne = (String) uniqueKey.subSequence(0, (20-id.length()));
				uniqueKey = ne+""+id;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return uniqueKey;
	}

	public static String splitDate(String data)
	{
		String [] fdata = null;
		if(!data.equals(""))
			fdata = data.split("-");

		data = "";
		data = fdata[0]+""+fdata[1]+""+fdata[2];
		return data;
	}

	// Arrange Date e.g: 02082011 to 02-08-2011
	public static String ArrangeDate(String date)
	{
		String new_dt = date;
		if(date.length() == 8)
		{
			String[] ch = date.split("");
			new_dt = ch[1]+""+ch[2]+"-"+ch[3]+""+ch[4]+"-"+ch[5]+""+ch[6]+""+ch[7]+""+ch[8];
		}
		return new_dt;
	}

	
	public static String TodayDay()
	{
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String weekDays[]=new DateFormatSymbols().getWeekdays();
		return weekDays[dayOfWeek].toUpperCase();
		
//		String todday=null;
//         if(dayOfWeek==1)
//         {
//         	todday="SUNDAY";
//         }
//         if(dayOfWeek==2)
//         {
//         	todday="MONDAY";
//         }if(dayOfWeek==3)
//         {
//         	todday="TUESDAY";
//         }if(dayOfWeek==4)
//         {
//         	todday="WEDNESDAY";
//         }if(dayOfWeek==5)
//         {
//         	todday="THURSDAY";
//         }if(dayOfWeek==6)
//         {
//         	todday="FRIDAY";
//         }if(dayOfWeek==7)
//         {
//         	todday="SATURDAY";
//         }
//			return todday;
		
	}
	
	
	public static String TodaysDate()
	{
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}
	
	public String isLeapYear()
	{
		String isLeapyear="false";
		Calendar calendar = Calendar.getInstance();
	    int lastDig=Integer.parseInt(String.valueOf(calendar.get(Calendar.YEAR)).substring(2));
	    if(lastDig!=00)
	    {
	    	if(lastDig/4==0)
	    	{
	    		isLeapyear="true";
	    	}
	    }
	    else
	    {
	    	if(calendar.get(Calendar.YEAR)/400==0)
	    	{
	    		isLeapyear="true";
	    	}
	    }
		return isLeapyear;
		
	}
	

}