package com.distna.utility;

import org.jawin.*;


/**
 * Jawin generated code please do not edit
 *
 * Dispatch: IDownloadUsers
 * Description: 
 * Help file:   
 *
 * @author JawinTypeBrowser
 */

public class IDownloadUsers extends DispatchPtr {
	public static final GUID DIID = new GUID("{3823a63d-5891-3b4f-A460-DB0FB847075A}");
	public static final int IID_TOKEN;
	static {
		IID_TOKEN = IdentityManager.registerProxy(DIID, IDownloadUsers.class);
	}

	/**
	 * The required public no arg constructor.
	 * <br><br>
	 * <b>Important:</b>Should never be used as this creates an uninitialized
	 * IDownloadUsers (it is required by Jawin for some internal working though).
	 */
	public IDownloadUsers() {
		super();
	}

	/**
	 * For creating a new COM-object with the given progid and with 
	 * the IDownloadUsers interface.
	 * 
	 * @param progid the progid of the COM-object to create.
	 */
	public IDownloadUsers(String progid) throws COMException {
		super(progid, DIID);
	}

	/**
	 * For creating a new COM-object with the given clsid and with 
	 * the IDownloadUsers interface.
	 * 
	 * @param clsid the GUID of the COM-object to create.
	 */
	public IDownloadUsers(GUID clsid) throws COMException {
		super(clsid, DIID);
	}

	/**
	 * For getting the IDownloadUsers interface on an existing COM-object.
	 * This is an alternative to calling {@link #queryInterface(Class)}
	 * on comObject.
	 * 
	 * @param comObject the COM-object to get the IDownloadUsers interface on.
	 */
	public IDownloadUsers(COMPtr comObject) throws COMException {
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
    
    * @param EnteredIP
    * @param url
    * @return int
    **/
    public int downloadToDB(String EnteredIP,String url) throws COMException
    {
      
		return ((Integer)invokeN("downloadToDB", new Object[] {EnteredIP, url})).intValue();
        
    }
}