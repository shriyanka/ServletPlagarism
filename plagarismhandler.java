//set temporarily claspath using :
//set classpath=%classpath%;C:\Java\tomcat\apache-tomcat-6.0.41\lib\servlet-api.jar;C:\Java\tomcat\apache-tomcat-6.0.41\lib\commons-fileupload-1.3.jar;C:\Java\tomcat\apache-tomcat-6.0.41\lib\commons-io-1.3-sources.jar;C:\Java\tomcat\apache-tomcat-6.0.41\lib\commons-io-1.3.jar
//set path=%path%;C:\Java\tomcat\apache-tomcat-6.0.41\lib\servlet-api.jar;C:\Java\tomcat\apache-tomcat-6.0.41\lib\commons-fileupload-1.3.jar;C:\Java\tomcat\apache-tomcat-6.0.41\lib\commons-io-1.3-sources.jar;


package com.plagarism.servlet;
import java.util.*;
import com.plagarism.servlet.plagindex;
import com.plagarism.servlet.plagdetect;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;
import org.apache.commons.io.output.DeferredFileOutputStream;

public class plagarismhandler extends HttpServlet
{
	private boolean isMultipart;
	private	String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file ;
	
	public void init(ServletConfig sc)
	{
		System.out.println("init()");
		ServletContext ctx=sc.getServletContext();
		filePath =ctx.getInitParameter("file-upload");
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException,NullPointerException,ServletException,NullPointerException
	{
		String file1="";//=filePath+req.getParameter("file");
		String file2=""; // (that will be read from directory of uploded files
		double max=0.0;
		String name="";
		
		//save the uploaded file in directory
		DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );

      try{ 
        // Parse the request to get file items.
        List fileItems = upload.parseRequest(req);
	
        // Process the uploaded file items
        Iterator i = fileItems.iterator();

      while ( i.hasNext () ) 
      {
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            String fileName = fi.getName();
            
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            // Write the file
            if( fileName.lastIndexOf("\\") >= 0 ){
               file = new File( filePath + 
               fileName.substring( fileName.lastIndexOf("\\"))) ;
               //file1=filePath + fileName.substring( fileName.lastIndexOf("\\"));
            }else{
               file = new File( filePath + 
               fileName.substring(fileName.lastIndexOf("\\")+1)) ;
               //file1=filePath + "\\" + fileName.substring(fileName.lastIndexOf("\\")+1);
            }
            fi.write( file ) ;
            file1=filePath +  fileName;
        }
      }
   
   		}
   		catch(Exception ex) 
   		{
   			System.out.println(ex);
   		}

		
		//file1= open in absolute path 

		plagindex ob=new plagindex();
		System.out.println("\n \n file1 is : "+file1);
		String ofile1=ob.openfile(file1);//original file for setting in textbox
		//break file1in chunks
		String[] atext=ob.filebreaker(ofile1);
		//open the directory
		//file2= open in absolute path file2
		File f=new File("C:\\Java\\tomcat\\apache-tomcat-6.0.41\\webapps\\palagrism\\fileuploades");
		String[] paths;
		paths=f.list();//lsiting of files and dir
		for(String p:paths)
		{
			String ofile2="";
			file2="C:\\Java\\tomcat\\apache-tomcat-6.0.41\\webapps\\palagrism\\fileuploades\\"+p;
			//opening the file2 in absoulute path
			if(!(file1.equalsIgnoreCase(file2))) // since file is already upoaded it will not compare with itself
				 ofile2=ob.openfile(file2);
		
			//break the file in chunks
			
			String[] btext=ob.filebreaker(ofile2);

			//parsing the atext,btext
			plagdetect obj=new plagdetect(atext,btext);
			obj.raw();
			double per=obj.count();
			if(max<=per) //for determining palagarism
			{
				max=per;
				name=file2;
			}
		}

		//now we have to ruturn per, ofile1 , ofile2
		res.setContentType("text/html");
		/*req.setAttribute("per",per);
		req.setAttribute("ofile1",ofile1);
		req.setAttribute("ofile2",ofile2);
		req.getRequestDispatcher("index.html").forward(req, res);*/


		
		PrintWriter out=res.getWriter();
		out.println("<h1 align='center' style='margin-top:30px;'>PROJECT : 2014</h1>");
		out.println("<hr style='margin-top:25px;color:blue;'>");
		out.println("<font-size=12 color=red ><p text-align='center'>file of uploaded student is : "+file1+"</p></font>");
		out.println("<font-size=12 color=red ><p text-align='center'>wait a second..<br>We are matching the uploaded file with all files of students saved in our Directory for checking the degree of Palagarism</p></font>");
		out.println("<font-size=12 color=red ><p text-align='center'>file of Directory with max similarity with uploded file is : "+name+"</p></font>");
		out.println("<font-size=12 color=red ><p text-align='center'> Degree of palagarism found between the two file is :"+max+" % </p></font>");
		out.println("<font-size=12 color=red ><p text-align='center'><br>ThankYou</p></font>");
	}

	public void destroy()
	{
		System.out.println("destroy()");
	}


}