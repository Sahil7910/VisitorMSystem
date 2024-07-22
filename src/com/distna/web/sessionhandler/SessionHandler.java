package com.distna.web.sessionhandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.distna.domain.company.CanteenItems;
import com.distna.domain.company.CanteenTimings;
import com.distna.service.company.CanteenItemsDAO;
import com.distna.service.company.CanteenTimingsDAO;

@Component
public class SessionHandler extends HandlerInterceptorAdapter{
	
	
	private CanteenItemsDAO canteenItemsDAO;
	private CanteenTimingsDAO canteenTimingsDAO;
	
	public SessionHandler()
	{
		super();
	}
	
	/*public SessionHandler(CanteenItemsDAO canteenItemsDAO, CanteenTimingsDAO canteenTimingsDAO )
	{
		this.canteenItemsDAO=canteenItemsDAO;
		this.canteenTimingsDAO=canteenTimingsDAO;
	}
	*/
	
	public void setCanteenItemsDAO(CanteenItemsDAO canteenItemsDAO) {
		this.canteenItemsDAO = canteenItemsDAO;
	}

	public CanteenItemsDAO getCanteenItemsDAO() {
		return canteenItemsDAO;
	}

	public CanteenTimingsDAO getCanteenTimingsDAO() {
		return canteenTimingsDAO;
	}

	public void setCanteenTimingsDAO(CanteenTimingsDAO canteenTimingsDAO) {
		this.canteenTimingsDAO = canteenTimingsDAO;
	}

	@Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
		//int xxxx= request.getSession().getMaxInactiveInterval();
		HttpSession session=request.getSession(false);
		
		long lastAccessedTime=0;
		DateFormat currentDateFormat=new SimpleDateFormat("HH:mm:ss");
		String cuttentTimeString =currentDateFormat.format(new Date().getTime());
		Date currentCanteenTime=currentDateFormat.parse(cuttentTimeString);
		
		List<CanteenItems> canteenItemsList=canteenItemsDAO.getCanteenItemsList();
		List<CanteenTimings> canteenTimingsList=canteenTimingsDAO.getCanteenTimingsList();
		
		for (CanteenTimings canteenTimings : canteenTimingsList) {
			if(currentCanteenTime.before(currentDateFormat.parse(canteenTimings.getEndTime())) && currentCanteenTime.after(currentDateFormat.parse(canteenTimings.getStartTime())) )
			{
				for (CanteenItems canteenItems : canteenItemsList) {
					if(canteenItems.getItemsId()==canteenTimings.getDefaultItem())
					{
						session.setAttribute("canTimingName","It's "+canteenTimings.getTimingName()+" time.");
						session.setAttribute("canteenItemName", "The default item is: "+canteenItems.getItemName()+"." );
						break;
					}
				}
			}
			else
			{
				session.setAttribute("defaultCanteenItem", "");
				session.setAttribute("canteenTimingName", "");
			}
		}
		
		
		
		
		
		Properties properties=new Properties();
		String path=request.getSession().getServletContext().getRealPath("")+"/time.properties";
		File file=new File(path);
		if(!file.exists())
		{
			file.createNewFile();
		}
		FileInputStream fileInputStream=new FileInputStream(path);
		properties.load(fileInputStream);
		if(properties.containsKey(request.getSession().getId()))
		{			
			lastAccessedTime=Long.parseLong(properties.getProperty(request.getSession().getId()));
			long currentTime=Calendar.getInstance().getTimeInMillis();
			long timeDifference=currentTime-lastAccessedTime;
			long diffInMin=timeDifference/(60*1000);
			if(diffInMin>120)
			{
				properties.remove(request.getSession().getId());
				/*properties.setProperty(request.getSession().getId(), String.valueOf(request.getSession().getLastAccessedTime()));
				properties.store(new FileOutputStream(path), null);*/
				properties.store(new FileOutputStream(path), null);
				/*response.sendRedirect("loginpage.htm");*/
				response.sendRedirect("loginWhenSessionExpires.htm");
			}
			else
			{
				properties.load(fileInputStream);
				properties.setProperty(request.getSession().getId(), String.valueOf(request.getSession().getLastAccessedTime()));
				properties.store(new FileOutputStream(path), null);
			}
		}
		else
		{
			properties.load(fileInputStream);
			properties.setProperty(request.getSession().getId(), String.valueOf(request.getSession().getLastAccessedTime()));
			properties.store(new FileOutputStream(path), null);
		}
	    return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		
	}

}
