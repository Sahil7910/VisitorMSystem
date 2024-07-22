package com.distna.service.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.distna.domain.calendar.ShiftDefinition;
import com.distna.domain.calendar.ShiftMaster;
import com.distna.domain.employee.AttendanceLogsBulkEntry;
import com.distna.domain.employee.AttendanceLogsOutdoorEntry;


public class AttendanceLogsBulkEntryDAOImpl implements
		AttendanceLogsBulkEntryDAO {
	
	private HibernateTemplate hibernateTemplate;
	private SessionFactory sessionFactory;
	public void setSessionDataFactory(SessionFactory sessionFactory) 
	{
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		this.sessionFactory=sessionFactory;
	}
	
	@Override
	public void saveAttendanceLogsBulkEntrys(AttendanceLogsBulkEntry attendanceLogsBulkEntry) {
		DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy HH:mm:ss");
		
		try {
			List<AttendanceLogsBulkEntry> currentDateAttendenceRecord=hibernateTemplate.find("from AttendanceLogsBulkEntry where workID="+attendanceLogsBulkEntry.getWorkID()+" AND recordDate='"+attendanceLogsBulkEntry.getRecordDate()+"'");
			int currentShiftId= attendanceLogsBulkEntry.getShift();
			if(currentDateAttendenceRecord.size()==0)
			{
				String startDateAndTime=attendanceLogsBulkEntry.getStartDateAndTime();
				String endDateAndTime=attendanceLogsBulkEntry.getEndDateAndTime();
				//DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy HH:mm:ss");
				//DateFormat dateFormat24=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				Date startDateAndTimeDate;
				Date endDateAndTimeDate;
				Date currentTime;
				startDateAndTimeDate = dateFormat.parse(startDateAndTime);
				endDateAndTimeDate = dateFormat.parse(endDateAndTime);
				currentTime = dateFormat.parse(attendanceLogsBulkEntry.getRecordDate()+" "+attendanceLogsBulkEntry.getTimeAsPerShftTimings());
				/*startDateAndTimeDate.setHours(startDateAndTimeDate.getHours()-2);	
				endDateAndTimeDate.setHours(endDateAndTimeDate.getHours()+3);*/
				
				
				List<ShiftDefinition> shiftDefinitionList= hibernateTemplate.find("from ShiftDefinition where shiftid="+currentShiftId);
				if(shiftDefinitionList.size()!=0)
				{
					ShiftDefinition currentShiftDefinitionObj=shiftDefinitionList.get(0);
					
					if(!currentShiftDefinitionObj.getPunchBeginBefore().equals("0"))
					{
					
					String punchBeginBefore=currentShiftDefinitionObj.getPunchBeginBefore();
					String []punchBeginBeforeArray=punchBeginBefore.split(":");
					startDateAndTimeDate.setHours(startDateAndTimeDate.getHours()-Integer.parseInt(punchBeginBeforeArray[0]));
					startDateAndTimeDate.setMinutes(startDateAndTimeDate.getMinutes()-Integer.parseInt(punchBeginBeforeArray[1]));
					startDateAndTimeDate.setSeconds(startDateAndTimeDate.getSeconds()-Integer.parseInt(punchBeginBeforeArray[2]));
					}
				
					if(!currentShiftDefinitionObj.getPunchEndAfter().equals("0"))
					{
					String punchEndAfter=currentShiftDefinitionObj.getPunchEndAfter();
					String []punchEndAfterArray=punchEndAfter.split(":");
					endDateAndTimeDate.setHours(endDateAndTimeDate.getHours()+Integer.parseInt(punchEndAfterArray[0]));
					endDateAndTimeDate.setMinutes(endDateAndTimeDate.getMinutes()+Integer.parseInt(punchEndAfterArray[1]));
					endDateAndTimeDate.setSeconds(endDateAndTimeDate.getSeconds()+Integer.parseInt(punchEndAfterArray[2]));
					}
				}
					if(!(currentTime.getTime()>=startDateAndTimeDate.getTime()&&currentTime.getTime()<=endDateAndTimeDate.getTime()))
						{
									String previousDate="0";
									int workId=attendanceLogsBulkEntry.getWorkID();
									SimpleDateFormat format=new SimpleDateFormat("d-M-yyyy");
									   int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
									   try {
											Date currentDateConverted=format.parse(attendanceLogsBulkEntry.getRecordDate());
											previousDate=format.format(currentDateConverted.getTime()-MILLIS_IN_DAY);
										} catch (ParseException e) {
										 	// TODO Auto-generated catch block
											e.printStackTrace();
										}
									   
									 List<AttendanceLogsBulkEntry> previousDateAttendenceActualRecord=new ArrayList<AttendanceLogsBulkEntry>();
										List<AttendanceLogsBulkEntry> previousDateAttendenceRecord=hibernateTemplate.find("from AttendanceLogsBulkEntry where workID="+workId);
										for(AttendanceLogsBulkEntry attendanceLogsBulkEntry2:previousDateAttendenceRecord)
										{
											
												Date recordDate=format.parse(attendanceLogsBulkEntry2.getRecordDate());
												Date prevDate=format.parse(previousDate);
												if(recordDate.equals(prevDate))
												{
													previousDateAttendenceActualRecord.add(attendanceLogsBulkEntry2);
												}
											
										}
												//+" AND recordDate='"+previousDate+"'");
					     			if(previousDateAttendenceActualRecord.size()!=0)
					     			{
					     				String previousStartTime=previousDateAttendenceActualRecord.get(0).getStartDateAndTime();
					     				String previousEndTime=previousDateAttendenceActualRecord.get(0).getEndDateAndTime();
					     				String priviousTimings=previousDateAttendenceActualRecord.get(0).getTimeAsPerShftTimings();
					     				attendanceLogsBulkEntry.setId(previousDateAttendenceActualRecord.get(0).getId());
					    				
					     				String timeAsPerShftTimings=priviousTimings+"~"+attendanceLogsBulkEntry.getTimeAsPerShftTimings();
					    				
					    				attendanceLogsBulkEntry.setTimeAsPerShftTimings(timeAsPerShftTimings);
					    				attendanceLogsBulkEntry.setStartDateAndTime(previousStartTime);
						     			attendanceLogsBulkEntry.setEndDateAndTime(previousEndTime);
						     			attendanceLogsBulkEntry.setRecordDate(previousDateAttendenceActualRecord.get(0).getRecordDate());
						     			if(previousDateAttendenceActualRecord.get(0).isExceptionFlag())
						     			{
						     				attendanceLogsBulkEntry.setExceptionFlag(false);
						     			}
						     			else
						     			{
						     				attendanceLogsBulkEntry.setExceptionFlag(true);
						     			}
						     			hibernateTemplate.saveOrUpdate(attendanceLogsBulkEntry);
					     			}
 					     			else
					     			{
					     					 Calendar startCal = Calendar.getInstance();
						     				  Calendar endCal = Calendar.getInstance();
						     				  Calendar currentStartCal = Calendar.getInstance();
						     				  Calendar currentendCal = Calendar.getInstance();

						     				  startCal.clear();
						     				  endCal.clear();
						     				  currentStartCal.clear();
						     				  currentendCal.clear();
						     				  
						     				  
						     				  
						     				 startCal.setTime( startDateAndTimeDate );
						     				 endCal.setTime( endDateAndTimeDate );
						     				 currentStartCal.setTime( currentTime );
						     				 currentendCal.setTime( currentTime );
						     				  
						     				 startCal.set( Calendar.YEAR, 2011 );
						     				 endCal.set( Calendar.YEAR, 2011 );
						     				 startCal.set( Calendar.MONTH, 1 );
						     				 endCal.set( Calendar.MONTH, 1 );
						     				 startCal.set( Calendar.DAY_OF_YEAR, 1 );
						     				 
						     				currentStartCal.set( Calendar.YEAR, 2011 );
						     				currentendCal.set( Calendar.YEAR, 2011 );
						     				currentStartCal.set( Calendar.MONTH, 1 );
						     				currentendCal.set( Calendar.MONTH, 1 );
						     				currentStartCal.set( Calendar.DAY_OF_YEAR, 1 );

						     				  
						     				
						     				  
						     			/*	 startCal.setTime( startDateAndTimeDate );
						     				 endCal.setTime( endDateAndTimeDate );
						     				 currentStartCal.setTime( currentTime );
						     				 currentendCal.setTime( currentTime );
						     				  
						     				 startCal.set( Calendar.YEAR, 2011 );
						     				 endCal.set( Calendar.YEAR, 2011 );
						     				 startCal.set( Calendar.MONTH, 1 );
						     				 endCal.set( Calendar.MONTH, 1 );
						     				 startCal.set( Calendar.DAY_OF_YEAR, 1 );
						     				 
						     				currentStartCal.set( Calendar.YEAR, 2011 );
						     				currentendCal.set( Calendar.YEAR, 2011 );
						     				currentStartCal.set( Calendar.MONTH, 1 );
						     				currentendCal.set( Calendar.MONTH, 1 );
						     				currentStartCal.set( Calendar.DAY_OF_YEAR, 1);*/
						     				
						     				if(attendanceLogsBulkEntry.getOverNight()==1)
							     			{
						     					/*if(Integer.parseInt(attendanceLogsBulkEntry.getTimeAsPerShftTimings().split(":")[0])<12)
						    					{
						    						currentTime.setDate(currentTime.getDate()+1);
						    					}*/
						     					
						     					String currentOverNight=format.format(currentTime);
						     					String endDateAndTimeDateOverNight=format.format(endDateAndTimeDate);
						     					if(currentOverNight.equals(endDateAndTimeDateOverNight))
						     					{
						     						currentStartCal.set( Calendar.DAY_OF_YEAR, 2);
						     					}
						     					else
						     					{
						     						currentStartCal.set( Calendar.DAY_OF_YEAR, 1);
						     					}
						     					endCal.set( Calendar.DAY_OF_YEAR, 2 );
						     				    currentendCal.set( Calendar.DAY_OF_YEAR, 2 );
							     			}
						     				else
						     				{
						     					endCal.set( Calendar.DAY_OF_YEAR, 1 );
						     					currentendCal.set( Calendar.DAY_OF_YEAR, 1 );
						     				}
						     				/*currentStartCal.add(Calendar.HOUR, -2);*/
						     				
						     				if(shiftDefinitionList.size()!=0)
						    				{
						    					ShiftDefinition currentShiftDefinitionObj=shiftDefinitionList.get(0);
						    					
						    					if(!currentShiftDefinitionObj.getPunchBeginBefore().equals("0"))
						    					{
						    					
						    					String punchBeginBefore=currentShiftDefinitionObj.getPunchBeginBefore();
						    					String []punchBeginBeforeArray=punchBeginBefore.split(":");
						    					currentStartCal.add(Calendar.HOUR,-Integer.parseInt(punchBeginBeforeArray[0]));
						    					currentStartCal.add(Calendar.MINUTE,-Integer.parseInt(punchBeginBeforeArray[1]));
						    					currentStartCal.add(Calendar.SECOND,-Integer.parseInt(punchBeginBeforeArray[2]));
						    					}
						    					if(!currentShiftDefinitionObj.getPunchEndAfter().equals("0"))
						    					{
						    					String punchEndAfter=currentShiftDefinitionObj.getPunchEndAfter();
						    					String []punchEndAfterArray=punchEndAfter.split(":");
						    					currentendCal.add(Calendar.HOUR, Integer.parseInt(punchEndAfterArray[0]));
						    					currentendCal.add(Calendar.MINUTE, Integer.parseInt(punchEndAfterArray[1]));
						    					currentendCal.add(Calendar.SECOND, Integer.parseInt(punchEndAfterArray[2]));
						    					}
						    				}	
						     				
						     				if(attendanceLogsBulkEntry.getWorkID()==2||attendanceLogsBulkEntry.getWorkID()==3)
						    				{
						     					boolean test=currentStartCal.after(startCal);
						     				 if((currentStartCal.after(startCal)&&currentendCal.before(endCal))||currentStartCal.getTime().after(startCal.getTime())&&currentendCal.getTime().before(endCal.getTime()))
						     				 	{
						     					 hibernateTemplate.saveOrUpdate(attendanceLogsBulkEntry);
						     				 	}
						    				}
					     			}
						}
					else
						{
								hibernateTemplate.saveOrUpdate(attendanceLogsBulkEntry);
							
						}
				/*hibernateTemplate.saveOrUpdate(attendanceLogsBulkEntry);	*/		
			}
			else
			{
					String previousTimings=currentDateAttendenceRecord.get(0).getTimeAsPerShftTimings();
					String[] previousTimingsArray=previousTimings.split("~");
					boolean previousTimingsFlag=false;
					for(String previousTimingsObj:previousTimingsArray)
					{
						if(previousTimingsObj.equals(attendanceLogsBulkEntry.getTimeAsPerShftTimings()))
						{
							previousTimingsFlag=true;
						}
					}
					if(!previousTimingsFlag)
					{
						String startDateAndTime=attendanceLogsBulkEntry.getStartDateAndTime();
						String endDateAndTime=attendanceLogsBulkEntry.getEndDateAndTime();
						
						//String startDateAlreadyPresent=currentDateAttendenceRecord.get(0).getStartDateAndTime();
						//String endDateAlreadyPresent=currentDateAttendenceRecord.get(0).getEndDateAndTime();
						//String startDateAndTime=attendanceLogsBulkEntry.getStartDateAndTime();
						//String endDateAndTime=attendanceLogsBulkEntry.getEndDateAndTime();
						
						DateFormat dateFormat1=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						Date startDateAndTimeDateFormat;
						Date endDateAndTimeDateFormat;
						Date currentTime;
						startDateAndTimeDateFormat = dateFormat1.parse(startDateAndTime);
						endDateAndTimeDateFormat = dateFormat1.parse(endDateAndTime);
						currentTime = dateFormat1.parse(attendanceLogsBulkEntry.getRecordDate()+" "+attendanceLogsBulkEntry.getTimeAsPerShftTimings());
						/*startDateAndTimeDateFormat.setHours(startDateAndTimeDateFormat.getHours()-2);
						endDateAndTimeDateFormat.setHours(endDateAndTimeDateFormat.getHours()+3);*/
						
						List<ShiftDefinition> shiftDefinitionList= hibernateTemplate.find("from ShiftDefinition where shiftid="+currentShiftId);
						if(shiftDefinitionList.size()!=0)
						{
							ShiftDefinition currentShiftDefinitionObj=shiftDefinitionList.get(0);
							
							if(!currentShiftDefinitionObj.getPunchBeginBefore().equals("0"))
							{
							
							String punchBeginBefore=currentShiftDefinitionObj.getPunchBeginBefore();
							String []punchBeginBeforeArray=punchBeginBefore.split(":");
							startDateAndTimeDateFormat.setHours(startDateAndTimeDateFormat.getHours()-Integer.parseInt(punchBeginBeforeArray[0]));
							startDateAndTimeDateFormat.setMinutes(startDateAndTimeDateFormat.getMinutes()-Integer.parseInt(punchBeginBeforeArray[1]));
							startDateAndTimeDateFormat.setSeconds(startDateAndTimeDateFormat.getSeconds()-Integer.parseInt(punchBeginBeforeArray[2]));
							}
							
							if(!currentShiftDefinitionObj.getPunchEndAfter().equals("0"))
							{
							String punchEndAfter=currentShiftDefinitionObj.getPunchEndAfter();
							String []punchEndAfterArray=punchEndAfter.split(":");
							endDateAndTimeDateFormat.setHours(endDateAndTimeDateFormat.getHours()+Integer.parseInt(punchEndAfterArray[0]));
							endDateAndTimeDateFormat.setMinutes(endDateAndTimeDateFormat.getMinutes()+Integer.parseInt(punchEndAfterArray[1]));
							endDateAndTimeDateFormat.setSeconds(endDateAndTimeDateFormat.getSeconds()+Integer.parseInt(punchEndAfterArray[2]));
							}
						}
						if((currentTime.getTime()>=startDateAndTimeDateFormat.getTime()&&currentTime.getTime()<=endDateAndTimeDateFormat.getTime()))
						{
							attendanceLogsBulkEntry.setId(currentDateAttendenceRecord.get(0).getId());
							String timeAsPerShftTimings=previousTimings+"~"+attendanceLogsBulkEntry.getTimeAsPerShftTimings();
							attendanceLogsBulkEntry.setTimeAsPerShftTimings(timeAsPerShftTimings);
						
							if(attendanceLogsBulkEntry.getInTime().equals("0"))
							{
								attendanceLogsBulkEntry.setInTime(currentDateAttendenceRecord.get(0).getInTime());
							}
							if(attendanceLogsBulkEntry.getOutTime().equals("0"))
							{
								attendanceLogsBulkEntry.setOutTime(currentDateAttendenceRecord.get(0).getOutTime());
							}
							if(attendanceLogsBulkEntry.getBreakIn().equals("0"))
							{
								attendanceLogsBulkEntry.setBreakIn(currentDateAttendenceRecord.get(0).getBreakIn());
							}
							if(attendanceLogsBulkEntry.getBreakOut().equals("0"))
							{
								attendanceLogsBulkEntry.setBreakOut(currentDateAttendenceRecord.get(0).getBreakOut());
							}
							
							
							if(attendanceLogsBulkEntry.getBreakIn2().equals("0"))
							{
								attendanceLogsBulkEntry.setBreakIn2(currentDateAttendenceRecord.get(0).getBreakIn2());
							}
							
							if(attendanceLogsBulkEntry.getBreakOut2().equals("0"))
							{
								attendanceLogsBulkEntry.setBreakOut2(currentDateAttendenceRecord.get(0).getBreakOut2());
							}
							
							
							
							if(currentDateAttendenceRecord.get(0).isExceptionFlag())
			     			{
			     				attendanceLogsBulkEntry.setExceptionFlag(false);
			     			}
			     			else
			     			{
			     				attendanceLogsBulkEntry.setExceptionFlag(true);
			     			}
							hibernateTemplate.saveOrUpdate(attendanceLogsBulkEntry);
						}
					}
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

	@Override
	public List<AttendanceLogsBulkEntry> getAttendanceLogsBulkEntrys(int employeeNo) {
//		String query="from Employee where STR_TO_DATE(dateOfBirth,'%d-%m') >= STR_TO_DATE('"+currentDateString+"','%d-%m') and STR_TO_DATE(dateOfBirth,'%m') = STR_TO_DATE('"+currentDateString+"','%m') order by STR_TO_DATE(dateOfBirth,'%d-%m-%Y')";
			Calendar currCalendar=Calendar.getInstance();
			currCalendar.set(Calendar.DATE,1);
			Date currentDate=currCalendar.getTime();
			DateFormat dateFormat=new SimpleDateFormat("d-M-yyyy");
			Calendar calendar=Calendar.getInstance();
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DATE, 1);
			//"from attendance_logs_bulk_entry where workID=4 and STR_TO_DATE(Recorddate,'%d-%m-%Y') >= STR_TO_DATE('4-2013','%m-%Y') and STR_TO_DATE(Recorddate,'%d-%m-%Y') <= STR_TO_DATE('5-2013','%m-%Y');
			//String nextMonthsDate=dateFormat.format();
			
			List<AttendanceLogsBulkEntry> attendanceLogsBulkEntriesList=hibernateTemplate.find("from AttendanceLogsBulkEntry where workID="+employeeNo
					+" AND STR_TO_DATE(recordDate,'%d-%m-%Y') >= STR_TO_DATE('"+dateFormat.format(currentDate)
					+"','%d-%m-%Y') and STR_TO_DATE(recordDate,'%d-%m-%Y') <= STR_TO_DATE('"+dateFormat.format(calendar.getTime())
					+"','%d-%m-%Y') order by STR_TO_DATE(recordDate,'%d-%m-%Y')");
		
			/*	
			Session session = sessionFactory.openSession();
			String query="SELECT e.employeeId,e.firstName,e.lastName,e.dateOfBirth from Employee e where STR_TO_DATE(e.dateOfBirth,'%d-%m') >= STR_TO_DATE('"+currentDateString+"','%d-%m')";
			List<Employee> listOfEmployee= session.createQuery(query).list();
			session.close();*/
			/*System.out.println("from AttendanceLogsBulkEntry where workID="+employeeNo+" AND STR_TO_DATE(recordDate,'%d-%m-%Y') >= STR_TO_DATE('"+dateFormat.format(currentDate)+"','%d-%m-%Y') and STR_TO_DATE(recordDate,'%d-%m-%Y') <= STR_TO_DATE('"+dateFormat.format(calendar.getTime())+"','%d-%m-%Y') order by STR_TO_DATE(recordDate,'%d-%m-%Y')");
			System.out.println(dateFormat.format(currentDate));
			System.out.println(dateFormat.format(calendar.getTime()));*/
			return attendanceLogsBulkEntriesList;
	}
	

	@Override
	public void saveAttendanceLogsBulkEntrysReports() {
		
		Session session = sessionFactory.openSession();
		Connection connection= session.connection();
		String query="INSERT INTO attendance_logs_bulk_entry_report SELECT * FROM attendance_logs_bulk_entry;";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();
	
		
	}

	@Override
	public void saveAttendanceLogsBulkEntrysDefault(
			AttendanceLogsBulkEntry attendanceLogsBulkEntry) {
		try {
			List<AttendanceLogsBulkEntry> currentDateAttendenceRecord=hibernateTemplate.find("from AttendanceLogsBulkEntry where workID="+attendanceLogsBulkEntry.getWorkID()+" AND recordDate='"+attendanceLogsBulkEntry.getRecordDate()+"'");
			if(currentDateAttendenceRecord.size()==0)
			{
				List<ShiftMaster> shiftMasterList =hibernateTemplate.find("from ShiftMaster where shiftname='DefaultShift' and shiftcode='DisDS'");
				if(shiftMasterList.size()!=0)
				{						
					attendanceLogsBulkEntry.setShift(shiftMasterList.get(0).getShiftid());
				}
				 hibernateTemplate.saveOrUpdate(attendanceLogsBulkEntry);
			}
			else
			{
				String previousTimings=currentDateAttendenceRecord.get(0).getTimeAsPerShftTimings();
				String[] previousTimingsArray=previousTimings.split("~");
				boolean previousTimingsFlag=false;
				for(String previousTimingsObj:previousTimingsArray)
				{
					if(previousTimingsObj.equals(attendanceLogsBulkEntry.getTimeAsPerShftTimings()))
					{
						previousTimingsFlag=true;
					}
				}
				if(!previousTimingsFlag)
				{
					attendanceLogsBulkEntry.setId(currentDateAttendenceRecord.get(0).getId());
					String timeAsPerShftTimings=previousTimings+"~"+attendanceLogsBulkEntry.getTimeAsPerShftTimings();
					attendanceLogsBulkEntry.setTimeAsPerShftTimings(timeAsPerShftTimings);
					if(attendanceLogsBulkEntry.getInTime().equals("0"))
					{
						attendanceLogsBulkEntry.setInTime(currentDateAttendenceRecord.get(0).getInTime());
					}
					if(attendanceLogsBulkEntry.getOutTime().equals("0"))
					{
						attendanceLogsBulkEntry.setOutTime(currentDateAttendenceRecord.get(0).getOutTime());
					}
					if(attendanceLogsBulkEntry.getBreakIn().equals("0"))
					{
						attendanceLogsBulkEntry.setBreakIn(currentDateAttendenceRecord.get(0).getBreakIn());
					}
					if(attendanceLogsBulkEntry.getBreakOut().equals("0"))
					{
						attendanceLogsBulkEntry.setBreakOut(currentDateAttendenceRecord.get(0).getBreakOut());
					}
					if(currentDateAttendenceRecord.get(0).isExceptionFlag())
	     			{
	     				attendanceLogsBulkEntry.setExceptionFlag(false);
	     			}
	     			else
	     			{
	     				attendanceLogsBulkEntry.setExceptionFlag(true);
	     			}
					List<ShiftMaster> shiftMasterList =hibernateTemplate.find("from ShiftMaster where shiftname='DefaultShift' and shiftcode='DisDS'");
					if(shiftMasterList.size()!=0)
					{						
						attendanceLogsBulkEntry.setShift(shiftMasterList.get(0).getShiftid());
					}
					hibernateTemplate.saveOrUpdate(attendanceLogsBulkEntry);
				}
			}
			}
	 catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	
		@Override
	public boolean isRecordAvailable(String recorddate, int employeeId) {
		List<AttendanceLogsBulkEntry> attendanceLogsBulkEntries=hibernateTemplate.find("from AttendanceLogsBulkEntry where recordDate='"+recorddate+"' and workID="+employeeId);
		if(attendanceLogsBulkEntries.size()==0)
		{
			return true;
		}
		return false;
	}

	@Override
	public void saveAttendanceBulkEntryForOutdoor(
			AttendanceLogsBulkEntry attendanceLogsBulkEntry) {
		hibernateTemplate.save(attendanceLogsBulkEntry);
	}

}
