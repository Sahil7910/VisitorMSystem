
package com.distna.utility;

import org.jawin.*;


/**
 * Jawin generated code please do not edit
 *
 * Dispatch: IDatFileRead
 * Description: 
 * Help file:   
 *
 * @author JawinTypeBrowser
 */

public class IDatFileRead extends DispatchPtr {
	public static final GUID DIID = new GUID("{064e04da-2820-4964-8BFD-4D6117FA2362}");
	public static final int IID_TOKEN;
	static {
		IID_TOKEN = IdentityManager.registerProxy(DIID, IDatFileRead.class);
	}

	/**
	 * The required public no arg constructor.
	 * <br><br>
	 * <b>Important:</b>Should never be used as this creates an uninitialized
	 * IDatFileRead (it is required by Jawin for some internal working though).
	 */
	public IDatFileRead() {
		super();
	}

	/**
	 * For creating a new COM-object with the given progid and with 
	 * the IDatFileRead interface.
	 * 
	 * @param progid the progid of the COM-object to create.
	 */
	public IDatFileRead(String progid) throws COMException {
		super(progid, DIID);
	}

	/**
	 * For creating a new COM-object with the given clsid and with 
	 * the IDatFileRead interface.
	 * 
	 * @param clsid the GUID of the COM-object to create.
	 */
	public IDatFileRead(GUID clsid) throws COMException {
		super(clsid, DIID);
	}

	/**
	 * For getting the IDatFileRead interface on an existing COM-object.
	 * This is an alternative to calling {@link #queryInterface(Class)}
	 * on comObject.
	 * 
	 * @param comObject the COM-object to get the IDatFileRead interface on.
	 */
	public IDatFileRead(COMPtr comObject) throws COMException {
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
    
    * @param UserLogFileName
    * @param url
    * @return String
    **/
    public String ReadUserLogFromDatFile(String UserLogFileName,String url) throws COMException
    {
      
		return (String)invokeN("ReadUserLogFromDatFile", new Object[] {UserLogFileName, url});
        
    }
    /**
    *
    
    * @param UserLogFileName
    * @param url
    * @return String
    **/
    public String ReadAttLogFromDatFile(String UserLogFileName,String url) throws COMException
    {
      
		return (String)invokeN("ReadAttLogFromDatFile", new Object[] {UserLogFileName, url});
        
    }
}