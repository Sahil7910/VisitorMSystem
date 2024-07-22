package com.distna.utility;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class Imageload  {


	public String uploadImage(MultipartFile file,HttpServletRequest req,String folderName,String fileName)  throws IOException
	{	
		/*System.out.println("image uploaded");*/
		// To save image in folder 	
		String path = null;
		String extention=null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		if (file.getSize() > 0) {
			//String extension =file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			inputStream = file.getInputStream();
			extention=file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
			
			String fileFolder=req.getSession().getServletContext().getRealPath(folderName);
			File folderFile=new File(fileFolder);
			if(!folderFile.exists())
			{
				folderFile.mkdirs();
			}
			path = req.getSession().getServletContext().getRealPath(folderName)+"\\"+fileName;
			outputStream = new FileOutputStream(path);
			/*System.out.println(fileName);*/
			int readBytes = 0;
			byte[] buffer = new byte[8192];
			while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
				outputStream.write(buffer, 0, readBytes);
			}
			outputStream.close();
			inputStream.close();
		}
		return path;
	}
	
}
