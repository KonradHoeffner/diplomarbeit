package wortschatz2dbpedia.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import wortschatz2dbpedia.match.RemoveEverythingInParenthesesTransformer;

/**
 * Transforms a decoded 2-row csv redirect or disambiguation file in a mapping.
 * It does this by removing everything in parentheses in the first row and also by replacing
 * underscores by spaces in the first row.
 */
public class CreateRedirectOrDisambiguationMapping
{ 
	public static void createMapping(String filenameIn,String filenameOut) throws IOException
	{
		if(new File(filenameOut).exists()) throw new IOException("File "+filenameOut+" already exists.");
		BufferedReader in = new BufferedReader(new FileReader(filenameIn));
		PrintWriter out = new PrintWriter(new FileWriter(filenameOut));

		createMapping(in,out);
		
		in.close();
		out.close();

	}
	
	public static void createMapping(BufferedReader in,PrintWriter out) throws IOException
	{
		String line;
		while((line=in.readLine())!=null)
		{
			String[] tokens = line.split("\t");
			if(tokens.length<2) continue;
			String transformed = transformed = tokens[0].replace('_',' ');
			transformed = new RemoveEverythingInParenthesesTransformer().transform(transformed).iterator().next();
						
			out.println(transformed+'\t'+tokens[1]);
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		createMapping(
				"input/dbpedia35/disambiguations_en_decoded.csv",
				"output/mapping/disambiguations_mapping.csv");
	}
}
