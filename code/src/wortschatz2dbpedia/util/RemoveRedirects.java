package wortschatz2dbpedia.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Removes all lines where the first row is a redirect from a file, whereas the redirects are taken
 * from the first row of a redirect file and the remaining lines are saved into a new label file.
 * The two input files have to be already decoded and the redirect file has to be sorted
 * ("LC_COLLATE=C sort --key=1 --field-separator=\t redirects_en.csv > redirects_en_sorted.csv").
 * The input files dont have to be conglomerated.
 *
 */
public class RemoveRedirects
{

	public static void removeRedirects(String filenameLabelsIn,String filenameLabelsOut, String filenameRedirects) throws IOException
	{
		if(new File(filenameLabelsOut).exists()) throw new IOException("File "+filenameLabelsOut+" already exists.");
		BufferedReader in = new BufferedReader(new FileReader(filenameLabelsIn));
		PrintWriter out = new PrintWriter(new FileWriter(filenameLabelsOut));
		BinarySearchTextFile redirects = new BinarySearchTextFile(filenameRedirects);

		String line;
		int numberOfLines = 0;
		int numberOfRedirects = 0;
		while((line=in.readLine())!=null)
		{
			numberOfLines++;
			String[] tokens = line.split("\t");
			String entity  = tokens[0];
			if(!redirects.contains(entity,true)) {out.println(line);numberOfRedirects++;}
			if(numberOfLines%10000==0) System.out.println(numberOfLines/1000+"k lines, "+numberOfRedirects/1000+"k redirects...");
			//System.out.println(entity);
		}
		
		in.close();
		out.close();

	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		removeRedirects(
				"input/dbpedia35/labels_en_decoded_sorted.csv",
				"input/dbpedia35/labels_en_decoded_sorted_withoutredirects.csv",
				"input/dbpedia35/redirects_en_decoded_sorted.csv");
	}

}
