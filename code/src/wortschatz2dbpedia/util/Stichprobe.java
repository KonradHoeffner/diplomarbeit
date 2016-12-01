package wortschatz2dbpedia.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Stichprobe {

	final static private String INPUTFILE = "analyse/mapping1/stichprobe.txt";

	final static private int NUMBER_OF_CATEGORIES = 3;

	final static private String[] CATEGORY_NAMES = 
	{
		"Case sensitive matching",
		"Case insensitive matching",
		"Other matching"
	};

	final static private String[] CATEGORY_OUTPUT_FILENAMES =
	{
		"analyse/mapping1/stichprobe_case_sensitive.txt",
		"analyse/mapping1/stichprobe_case_insensitive.txt",
		"analyse/mapping1/stichprobe_other.txt"
	};

	public static void main(String[] args) throws FileNotFoundException
	{
		analysiereStichprobe(INPUTFILE);
	}

	private static void analysiereStichprobe(String stichprobeDateiname) throws FileNotFoundException
	{
		List<String[]> matching = new Vector<String[]>();

		Scanner in = new Scanner(new File(stichprobeDateiname));
		while (in.hasNext())
			matching.add(in.nextLine().replaceAll("dbpedia:|wortschatz:|_%28disambiguation%29", "").split("   "));
		in.close();

		int numberOfPairs = matching.size();

		int[] categoryCounts = new int[NUMBER_OF_CATEGORIES];
		PrintWriter[] categoryOutput = new PrintWriter[NUMBER_OF_CATEGORIES];

		for(int i=0;i<NUMBER_OF_CATEGORIES;i++)
			categoryOutput[i] = new PrintWriter(CATEGORY_OUTPUT_FILENAMES[i]);


		for (String[] pair : matching)
		{
			int category;

			category = 0;
//			if		(pair[0].equals(pair[1]))								category = 0; 
//			else if (pair[0].toLowerCase().equals(pair[1].toLowerCase()))	category = 1;
//			else															category = 2;	

			categoryCounts[category]++;

			categoryOutput[category].println(pair[0] + "	" + pair[1]);

		}

		for(PrintWriter out : categoryOutput) out.close();

		System.out.println(numberOfPairs + " matchings in der Stichprobe.");

		for(int category = 0; category < NUMBER_OF_CATEGORIES; category++)
			System.out.println(CATEGORY_NAMES[category]+": "+ categoryCounts[category]
			                                                            + " ("+categoryCounts[category]*100.0/numberOfPairs+"%)");
	}

}
