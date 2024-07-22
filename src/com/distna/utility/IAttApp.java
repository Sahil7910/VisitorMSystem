
package com.distna.utility;

import org.jawin.*;

/**
 * Jawin generated code please do not edit
 *
 * Dispatch: IAttApp
 * Description: 
 * Help file:   
 *
 * @author JawinTypeBrowser
 */

public class IAttApp extends DispatchPtr {
	public static final GUID DIID = new GUID("{3823a63d-5891-3b4f-A460-DB0FB847075A}");
	public static final int IID_TOKEN;
	static {
		IID_TOKEN = IdentityManager.registerProxy(DIID, IAttApp.class);
	}

	/**
	 * The required public no arg constructor.
	 * <br><br>
	 * <b>Important:</b>Should never be used as this creates an uninitialized
	 * IAttApp (it is required by Jawin for some internal working though).
	 */
	public IAttApp() {
		super();
	}

	/**
	 * For creating a new COM-object with the given progid and with 
	 * the IAttApp interface.
	 * 
	 * @param progid the progid of the COM-object to create.
	 */
	public IAttApp(String progid) throws COMException {
		super(progid, DIID);
	}

	/**
	 * For creating a new COM-object with the given clsid and with 
	 * the IAttApp interface.
	 * 
	 * @param clsid the GUID of the COM-object to create.
	 */
	public IAttApp(GUID clsid) throws COMException {
		super(clsid, DIID);
	}

	/**
	 * For getting the IAttApp interface on an existing COM-object.
	 * This is an alternative to calling {@link #queryInterface(Class)}
	 * on comObject.
	 * 
	 * @param comObject the COM-object to get the IAttApp interface on.
	 */
	public IAttApp(COMPtr comObject) throws COMException {
		super(comObject);
	}

	public int getIIDToken() {
		return IID_TOKEN;
	}
	
	
    /**
    *
    
    * @return void
    **/
    /*public void QueryInterface(Object[] riid,void[] 
        [] ppvObj) throws COMException
    {
      
		invokeN("QueryInterface", new Object[] {riid, ppvObj});
        
    }*/
    /**
    *
    
    * @return int
    **/
    /*public int AddRef() throws COMException
    {
      
		return ((Integer)invokeN("AddRef", new Object[] {})).intValue();
        
    }*/
    /**
    *
    
    * @return int
    **/
    /*public int Release() throws COMException
    {
      
		return ((Integer)invokeN("Release", new Object[] {})).intValue();
        
    }*/
    /**
    *
    
    * @return void
    **/
    /*public void GetTypeInfoCount(int[] pctinfo) throws COMException
    {
      
		invokeN("GetTypeInfoCount", new Object[] {pctinfo});
        
    }*/
    /**
    *
    
    * @param itinfo
    * @param lcid
    * @return void
    **/
    /*public void GetTypeInfo(int itinfo,int lcid,void[] 
        [] pptinfo) throws COMException
    {
      
		invokeN("GetTypeInfo", new Object[] {new Integer(itinfo), new Integer(lcid), pptinfo});
        
    }*/
    /**
    *
    
    * @param cNames
    * @param lcid
    * @return void
    **/
    /*public void GetIDsOfNames(Object[] riid,int[] 
        [] rgszNames,int cNames,int lcid,int[] rgdispid) throws COMException
    {
      
		invokeN("GetIDsOfNames", new Object[] {riid, rgszNames, new Integer(cNames), new Integer(lcid), rgdispid});
        
    }*/
    /**
    *
    
    * @param dispidMember
    * @param lcid
    * @param wFlags
    * @return void
    **/
    /*public void Invoke(int dispidMember,Object[] riid,int lcid,short wFlags,Object[] pdispparams,Variant[] pvarResult,Object[] pexcepinfo,int[] puArgErr) throws COMException
    {
      
		invokeN("Invoke", new Object[] {new Integer(dispidMember), riid, new Integer(lcid), new Short(wFlags), pdispparams, pvarResult, pexcepinfo, puArgErr});
        
    }*/
	   /**
    *
    
    * @return String
    **/
    public String message() throws COMException
    {
      
		return (String)invoke("message");
        
    }
    /**
    *
    
    * @param 
    * @return void
    **/
    public void InitMethod() throws COMException
    {
      
		 invokeN("InitMethod", new Object[] {});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String TestMethod(String EnteredIP) throws COMException
    {
      
		return (String)invokeN("TestMethod", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String DisConnect(String EnteredIP) throws COMException
    {
      
		return (String)invokeN("DisConnect", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String GetSerial(String EnteredIP) throws COMException
    {
     //System.out.println("Serial No v="+(String)invokeN("GetSerial", new Object[] {EnteredIP}));
		return (String)invokeN("GetSerial", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String SetSystemTime(String EnteredIP) throws COMException
    {
      
		return (String)invokeN("SetSystemTime", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String ChangeIP(String EnteredIP) throws COMException
    {
      
		return (String)invokeN("ChangeIP", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param ConnIP
    * @param NewIP
    * @return String
    **/
    public String SetNewIP(String ConnIP,String NewIP) throws COMException
    {
      
		return (String)invokeN("SetNewIP", new Object[] {ConnIP, NewIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String ClearAdminPrivilege(String EnteredIP) throws COMException
    {
      
		return (String)invokeN("ClearAdminPrivilege", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String ClearLogs(String EnteredIP) throws COMException
    {
      
		return (String)invokeN("ClearLogs", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String RestartDevice(String EnteredIP) throws COMException
    {
      
		return (String)invokeN("RestartDevice", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String ViewStatus(String EnteredIP) throws COMException
    {
      //System.out.println("Status="+(String)invokeN("ViewStatus", new Object[] {EnteredIP}));
		return (String)invokeN("ViewStatus", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String GetDeviceStatus(String EnteredIP) throws COMException
    {
      
		return (String)invokeN("GetDeviceStatus", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @param Url
    * @return int
    **/
    public int downloadLogs(String EnteredIP,String Url) throws COMException
    {
      
		return ((Integer)invokeN("downloadLogs", new Object[] {EnteredIP, Url})).intValue();
        
    }
    /**
    *
    
    * @param EnteredIP
    * @param Url
    * @return int
    **/
    public int downloadLogsVer9(String EnteredIP,String Url) throws COMException
    {
      
		return ((Integer)invokeN("downloadLogsVer9", new Object[] {EnteredIP, Url})).intValue();
        
    }
    /**
    *
    
    * @param EnteredIP
    * @return String
    **/
    public String deleteUser(String EnteredIP) throws COMException
    {
      
		return (String)invokeN("deleteUser", new Object[] {EnteredIP});
        
    }
    /**
    *
    
    * @param dwEnrollNumber
    * @param EnteredIP
    * @param fpIndex
    * @return String
    **/
    public String BtnDeleteUser(String dwEnrollNumber,String EnteredIP,String fpIndex) throws COMException
    {
      
		return (String)invokeN("BtnDeleteUser", new Object[] {dwEnrollNumber, EnteredIP, fpIndex});
        
    }
    /**
    *
    
    * @param EnteredIP
    * @param Url
    * @return int
    **/
    public int downloadUserTmpVer9(String EnteredIP,String Url) throws COMException
    {
      
		return ((Integer)invokeN("downloadUserTmpVer9", new Object[] {EnteredIP, Url})).intValue();
        
    }
    /**
    *
    
    * @param EnteredIP
    * @param Url
    * @return int
    **/
    public int DownloadUserTmp(String EnteredIP,String Url) throws COMException
    {
      
		return ((Integer)invokeN("DownloadUserTmp", new Object[] {EnteredIP, Url})).intValue();
        
    }
}
