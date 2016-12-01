package wortschatz2dbpedia.analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import wortschatz2dbpedia.match.SingletonTransformer;
import wortschatz2dbpedia.match.URLDecodeStringTransformer;

public class SampleEntry
{
	public final String resource;
	public final String label;
	public final boolean correctness;
	public final String remark;

	public SampleEntry(String resource, String label, boolean correctness,String remark)
	{
		this.resource = resource;
		this.label = label;
		this.correctness = correctness;
		this.remark = remark;
	}

	private String stripQuotation(String s)
	{
		if(s.matches("\".*\"")) s=s.substring(1, s.length()-1);
		return s;
	}
	
	public SampleEntry(String line) throws Exception
	{
		String fields[] = line.split("\t");
		if(fields.length<3||!fields[2].matches("0|1")) throw new Exception("Line of random sample .csv table has the wrong format. Expected <resource><tab><label><tab><0 or 1><tab><rest>");
		// Remove double quotes around resource and lable if existing, open office creates those when saving csv
		for(int i=0;i<2;i++) fields[i]=stripQuotation(fields[i]);
			
		resource	=	fields[0];	
		label		=	fields[1];
		correctness	=	fields[2].equals("1")?true:false;
		if(fields.length>=4) remark = stripQuotation(fields[3]); else remark="";
	}

	public static SampleEntry[] readFromCSV(String fileName) throws IOException
	{
		final int MAX_LINES_IGNORED=2;
		int linesIgnored = 0;
		List<SampleEntry> entries = new LinkedList<SampleEntry>();  
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		String line;
		while((line = in.readLine()) != null)
		{
			line = new SingletonTransformer(new URLDecodeStringTransformer()).transform(line);
			SampleEntry entry;
			try
			{
				entry = new SampleEntry(line);
				entries.add(entry);
			}
			catch(Exception e) {
				linesIgnored++;
				if(linesIgnored>MAX_LINES_IGNORED) throw new IOException("Too many ignored lines (more than "+MAX_LINES_IGNORED+")");
					/*System.out.println("Reading sample - ignoring line: "+line);*/
					}
		}
		return entries.toArray(new SampleEntry[0]);
		
	}

	@Override
	public String toString()
	{
		return "("+resource+","+label+","+(correctness?"1":"0")+","+remark+")";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (correctness ? 1231 : 1237);
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result
		+ ((resource == null) ? 0 : resource.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SampleEntry other = (SampleEntry) obj;
		if (correctness != other.correctness)
			return false;
		if (label == null)
		{
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (resource == null)
		{
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		return true;
	}	

}
