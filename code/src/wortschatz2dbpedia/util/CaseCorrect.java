package wortschatz2dbpedia.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import wortschatz2dbpedia.match.Abstract;

public class CaseCorrect
{
	/** Takes a dbpedia title file sets the case of each title to that which is most used in the abstract
	 * (it calls Abstract.isUpperCase() for this).
	 */
	public static void setCaseFromAbstract(String fnTitlesIn,String fnTitlesOut,String fnAbstract) throws IOException
	{
		final int approxLines = 3144256;
		//if(new File(fnTitlesOut).exists()) throw new RuntimeException("Output file "+fnTitlesOut+" already exists.");
		BufferedReader in = new BufferedReader(new FileReader(fnTitlesIn));
		PrintWriter out = new PrintWriter(new File(fnTitlesOut));
		RandomAccessFile abstracts = new BufferedRandomAccessFile(fnAbstract,"r",10000);
		BinarySearchTextFile binSearch = new BinarySearchTextFile(abstracts);
		String line = null;
		int lines = 0;
		int noAbstract = 0;
		int upperCase = 0;
		
		while((line=in.readLine())!=null)
		{
			lines ++;
			if(lines%1000==0) {System.out.println(lines*100/approxLines+"% complete, "+lines+" lines...");}
			String[] tokens = line.split("\t");
			if(tokens.length!=2) continue;
			String url  = tokens[0];
			String title  = tokens[1];
			String theAbstract = binSearch.get(url,true);
			if(theAbstract==null) {noAbstract++;continue;}
			
			boolean isUpperCase = Abstract.isUpperCase(title, theAbstract);
			if(isUpperCase)
			{
				out.println(line);
				upperCase++;
			} else
			{
				out.println(url+'\t'+Character.toLowerCase(title.charAt(0))+title.substring(1));
			}
		}
		System.out.println(lines+" lines read");
		System.out.println(noAbstract+" lines without abstract");
		System.out.println(upperCase+" lower case titles");
		in.close();
		out.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		setCaseFromAbstract("input/dbpedia35/labels_en_decoded.csv","input/dbpedia35/labels_en_decoded_casecorrected.csv","input/dbpedia35/long_abstracts_en_decoded_sorted2.csv");
		//setCaseFromAbstract(args[0],args[1],args[2]);
	}
}