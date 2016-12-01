package wortschatz2dbpedia.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Transforms a sorted 2-row csv file into a multi row csv file in the following fashion:
 * Input:
 * key1	value1
 * key1 value2
 * key2	value3
 * Output:
 * key1	value1	value2
 * key2	value3
 */
public class Conglomerate
{ 
	public static void conglomerate(String filenameIn,String filenameOut) throws IOException
	{
		if(new File(filenameOut).exists()) throw new IOException("File "+filenameOut+" already exists.");
		BufferedReader in = new BufferedReader(new FileReader(filenameIn));
		PrintWriter out = new PrintWriter(new FileWriter(filenameOut));

		conglomerate(in,out);
		
		in.close();
		out.close();

	}
	
	public static void conglomerate(BufferedReader in,PrintWriter out) throws IOException
	{
		String line;
		String lastKey = "";
		Set<String> values = new LinkedHashSet<String>();//LinkedHashSet preserves the order
		String[] tokens = null;
		while((line=in.readLine())!=null)
		{
			tokens = line.split("\t");
			if(tokens.length<2) continue;
			if(!tokens[0].equals(lastKey)&&!values.isEmpty())
			{
				out.print(lastKey);
				for(String value:values) out.print("\t"+value);
				out.println();
				values.clear();
			}
			lastKey = tokens[0];
			values.add(tokens[1]);
		}
		out.print(tokens[0]);
		for(String value:values) out.print("\t"+value);
		out.println();	
	}
	
	public static void main(String[] args) throws IOException
	{
		conglomerate(
				"output/mapping/redirects_disabiguations_identity_sorted.csv",
				"output/mapping/redirects_disabiguations_identity_sorted_conglomerated.csv");
	}
}
