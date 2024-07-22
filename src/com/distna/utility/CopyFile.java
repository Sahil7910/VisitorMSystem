package com.distna.utility;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class CopyFile {
	  public void readFile() {

		    BufferedReader br = null;
		    
		    try {
		      
		      br = new BufferedReader(new FileReader("F:\\Tanaji.txt"));
		      String line = null;
		      
		      while ((line = br.readLine()) != null) {
		        
		        String[] values = line.split(",");
		        
		        //Do necessary work with the values, here we just print them out
		        for (String str : values) {
		          System.out.println(str);
		        }
		        System.out.println();
		      }
		    }
		    catch (FileNotFoundException ex) {
		      ex.printStackTrace();
		    }
		    catch (IOException ex) {
		      ex.printStackTrace();
		    }
		    finally {
		      try {
		        if (br != null)
		          br.close();
		      }
		      catch (IOException ex) {
		        ex.printStackTrace();
		      }
		    }
		  }

		  public static void main(String[] args) {
			 /* CopyFile test = new CopyFile();
		    test.readFile();*/
		    
		BufferedReader br = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bw = null;

		try {

			// br = new BufferedReader(new
			// FileReader("D:\\Anup's Shared Folder\\Tanaji.txt"));
			// br = new BufferedReader(new
			// FileReader("D:\\Anup's Shared Folder\\Mndootemails.txt"));
			br = new BufferedReader(new FileReader(
					"D:\\Anup's Shared Folder\\SangolaNagariEmails.txt"));
			bufferedReader = new BufferedReader(new FileReader(
					"D:\\Anup's Shared Folder\\aaaa.txt"));
			File file = new File("D:\\Anup's Shared Folder\\bbbb.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			String line = null;

			while ((line = br.readLine()) != null) {

				String[] values = line.split(",");

				// Do necessary work with the values, here we just print them
				// out
				for (String str : values) {
					String writeLine = null;
					boolean flag = false;
					int count = 0;
					while ((writeLine = bufferedReader.readLine()) != null) {
						if (writeLine.contains(str)) {
							if (count == 0) {
								flag = true;
								System.out.println(str + " exists in "
										+ writeLine);
								// if file doesnt exists, then create it
								count++;
							} else {
								bw.write(writeLine
										+ "-----------------------------------");
								System.out.println("---------------");
								bw.newLine();
							}
						}
					}
					if (!flag) {
						System.out.println("write-----" + line);
						bw.write(line);
						bw.newLine();

					}
				}
				System.out.println();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
				if (bufferedReader != null)
					bufferedReader.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}

	}
		  
		  
			/* String query="SELECT email from EmailId order by email";
			List<String> listOfEmployee= session.createQuery(query).list();
		  	BufferedWriter bw=null;
		  	try {
		  		File file = new File("D:\\Anup's Shared Folder\\bbbb.txt");
			      if (!file.exists()) {
						
							file.createNewFile();
						
					}
			      FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
					bw = new BufferedWriter(fw);
	 for(String stringObj:listOfEmployee)
	 {
		 
		System.out.println("write-----"+stringObj);
		bw.write(stringObj);
		bw.newLine();
		 
	 }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  finally{
  	 if (bw != null)
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  	
  }*/
		
		
		
		
	   /* BufferedReader br=null;
	    BufferedReader bufferedReader=null;
	    BufferedWriter bw=null;
	    
	    try {
		      
	    //	      br = new BufferedReader(new FileReader("D:\\Anup's Shared Folder\\Tanaji.txt"));
	   //   br = new BufferedReader(new FileReader("D:\\Anup's Shared Folder\\Mndootemails.txt"));
		      br = new BufferedReader(new FileReader("D:\\Anup's Shared Folder\\qqqq.txt"));
		
		      bufferedReader = new BufferedReader(new FileReader("D:\\Anup's Shared Folder\\Final All Emails.txt"));
		      File file = new File("D:\\Anup's Shared Folder\\bbbb.txt");
		      if (!file.exists()) {
					file.createNewFile();
				}
	 
				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
				bw = new BufferedWriter(fw);
		      String line = null;
		      
		      while ((line = br.readLine()) != null) {
		        
		        String[] values = line.split(",");
		        
		        //Do necessary work with the values, here we just print them out
		        for (String str : values) {
		        	
		        	if(str.contains("<")&&str.contains(">"))
		        	{
		        		int index=str.indexOf("<");
		        		int indexLast=str.indexOf(">");
		        		String newFinalString=str.substring(index+1,indexLast);
		        		str=newFinalString;
		        	}
		        	
		        	 String writeLine = null;
		        	 boolean flag=false;
		        	 int count=0;
		        	 String query="SELECT id from EmailId where email like '%"+str.trim()+"%'";
						List<Integer> listOfEmployee= session.createQuery(query).list();
						PreparedStatement preparedStatement=null;
						if(listOfEmployee.size()==0)
						{
							try {
								preparedStatement = connection.prepareStatement("INSERT INTO emailid (emailId) VALUES ('"+str.trim()+",')");
								preparedStatement.executeUpdate();
						        System.out.println(str.trim());
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
		        	 
		        	
		        	 
		        	 
		        	 
		        	 while ((writeLine = bufferedReader.readLine()) != null) {
		        		 if(writeLine.contains(str))
		        		 {
		        			 if(count==0)
		        			 {
		        			 flag=true;
		        			 System.out.println(str + " exists in " + writeLine);
		        				// if file doesnt exists, then create it	
		        			 count++;
		        			 }
		        			 else
		        			 {
		        				 bw.write(writeLine+"-----------------------------------");
		        				 System.out.println("---------------");
					        	bw.newLine();
		        			 }
		        		 }
		        	 }
		        	if(!flag)
		        	{
		        		System.out.println("write-----"+line);
		        		bw.write(line);
		        		bw.newLine();
      				
		        	}
		        	 
		      
		        	 
		        }
		      }
		    }
		    catch (FileNotFoundException ex) {
		      ex.printStackTrace();
		    }
		    catch (IOException ex) {
		      ex.printStackTrace();
		    }
		    finally {
		      try {
		    	  session.close();
		        if (br != null)
		          br.close();
		        if (bw != null)
		        	bw.close();
		        if (bufferedReader != null)
		        	bufferedReader.close();
		       
		      }
		      catch (IOException ex) {
		        ex.printStackTrace();
		      }
	    
	    
		    }*/
		  
		  

}



