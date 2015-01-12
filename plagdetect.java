package com.plagarism.servlet;

class plagdetect
{
	String[] atext,btext,acopy,bcopy,arawcopy,brawcopy;
	int acount=0,bcount=0,avgcount=0,totalcount=0;
	String araw="",braw="";
	double per;
	
	plagdetect()
	{

	}

	plagdetect(String[] a,String[] b)
	{
		atext=a;
		btext=b;
	}

	//removing and,or,not,the,etc.
	String[] words={"is","are","this","a","that","by","you","i","me","and","can","on","with","at","its","is","an","be","of","so","it","for","to","as","we","will","would","can","could","has","have","shall","should","he","in","she","do","does","these","there","their","too","here","had","those","using","used","use","the","Is","Are","This","A","That","You","I","Me","And","Can","On","With","At","Its","Is","An","Be","Of","So","It","For","To","As","We","Will","Would","Can","Could","Has","Have","Shall","Should","He","In","She","Do","Does","These","There","Their","Too","Here","Had","Those","Using","Used","Use","The","By"};
    void raw(){
    	for(int i=0;i<atext.length;i++)
		{
			int f=0;
			for(int j=0;j<words.length;j++)
			{
				if(atext[i].equalsIgnoreCase(words[j]))
				{
					f=1;
					break;
				}
			}
			if(f==0)
			{
			//removing ed and ing form
			String w=remove(atext[i]);
			araw=araw+w+" ";
			}
		}
		for(int i=0;i<btext.length;i++)
		{
			int f=0;
			for(int j=0;j<words.length;j++)
			{
				if(btext[i].equalsIgnoreCase(words[j]))
				{
					f=1;
					break;
				}
			}
			if(f==0)
			{
				//removing ed and ing form
				String w=remove(btext[i]);
				braw=braw+w+" ";
			}

		}
		araw=araw.trim();
		braw=braw.trim();
		acopy=araw.split(" ");
		arawcopy=araw.split(" ");
		bcopy=braw.split(" ");
		brawcopy=braw.split(" ");
	}

	String remove(String w)
	{
		if(w.endsWith("ed"))
			{
				char[] ar=w.toCharArray();
				int a=w.lastIndexOf("ed");
				ar[a]=' ';
				ar[a+1]=' ';
				w="";
				for(char c:ar)
				w=w+c;
				w=w.trim();
				//return w;
			}
		if(w.endsWith("ing"))
			{
				int a=w.lastIndexOf("ing");
				char[] ar=w.toCharArray();
				ar[a]=' ';
				ar[a+1]=' ';
				ar[a+2]=' ';
				w="";
				for(char c:ar)
					w=w+c;
				w=w.trim();
				//return w;
			}
		return w;
	}

	void display()
	{
		System.out.println("acopy from raw is :");
		for(int i=0;i<acopy.length;i++)
			{
				System.out.println(acopy[i]);
			}

		System.out.println("\nbcopy from raw is :");
		for(int i=0;i<bcopy.length;i++)
			{
				System.out.println(bcopy[i]);
			}
	}

	double count()
	{
		for(int i=0;i<acopy.length;i++)
		{
			for(int j=0;j<brawcopy.length;j++)
			{
				if(acopy[i].equalsIgnoreCase(brawcopy[j]))
				{
					brawcopy[j]="";
					acount++;
					break;
				}
			}

		}
		for(int i=0;i<bcopy.length;i++)
		{
			for(int j=0;j<arawcopy.length;j++)
			{
				if(bcopy[i].equalsIgnoreCase(arawcopy[j]))
				{
					arawcopy[j]="";
					bcount++;
					break;
				}			
			}
		}

		avgcount=(acount+bcount)/2;
		totalcount=(acopy.length+bcopy.length)/2;
		
		
		System.out.println("atext length="+ atext.length);
		System.out.println("btext length ="+ btext.length);
		System.out.println("acopy length="+ acopy.length);
		System.out.println("bcopy length ="+ bcopy.length);
		System.out.println("araw ="+ araw+"\n");
		System.out.println("\nbraw ="+ braw);

		System.out.println("acount ="+ acount);
		System.out.println("bcount ="+ bcount);
		System.out.println("avgcount ="+ avgcount);
		System.out.println("totalcount ="+ totalcount);
		
		per=avgcount*100/totalcount;
		System.out.println("percentagecount ="+ per);
		return per;
	}
}