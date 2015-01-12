package com.plagarism.servlet;
import java.io.*;
class plagindex
{
	//String file1,file2;
	
	String openfile(String file)
	{
		StringBuffer text=new StringBuffer();
		if(file!=null)
		{
			try
			{
				FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String read;  
  				while((read=br.readLine())!=null)
  					{
  						text.append(read).append("\n");
  					}
   			}
  			catch(Exception exnt)
  			{
  				exnt.printStackTrace();
 			}
		}
		else
			text=text.append("Failed to open file : ").append(file);
	return text.toString();
	}

	String[] filebreaker(String file)
	{
		String[] text;
		text = (file.toString().trim()).split("[\\s,{,},\n,[/**/],;,:,\\,,.,?,!,\',\"]");
		return text;
	}

}