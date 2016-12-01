package wortschatz2dbpedia.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import wortschatz2dbpedia.match.SingletonTransformer;
import wortschatz2dbpedia.match.StringTransformer;
import wortschatz2dbpedia.match.URLDecodeStringTransformerUTF8;

/**
 * Applies a string transformer to every line of a specified text file and saves the result to another specified text file 
 *
 */
public class StringTransformFile {

	
	public static void transformOnlyOneColumn(String inputFileName,String outputFileName,SingletonTransformer transformer,int column) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputFileName))));
		String line;
		String[] split;
		StringBuffer outLine;
		int lineNr = 0;
		while((line=in.readLine()) != null)
		{
			lineNr++;
			if(lineNr%100000==0) System.out.println(lineNr+" Lines processed...");
			outLine = new StringBuffer();
			split = line.split("\t");
			//System.out.println(Arrays.toString(split));
			for(int i=0;i<split.length;i++)
			{
				if(i!=column) outLine.append(split[i]);
				else outLine.append(transformer.transform(split[i]));
				//if(!split[i].equals(transformer.transform(split[i]))) System.out.println(split[i]+"->"+transformer.transform(split[i]));
				if(i<split.length-1) outLine.append("\t");
			}
			out.println(outLine);
		}
		in.close();
		out.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		final String inputFileName;
		String outputFileName;

//		inputFileName = "input/dbpedia/abstract/longabstract_en_urldecoded.csv";
//		outputFileName = "input/dbpedia/abstract/longabstract_en_decoded.csv";
		inputFileName = "nlp2rdf/resources/hierarchy/yago/yagoclasses_links.nt.expanded.count";
		outputFileName = "nlp2rdf/resources/hierarchy/yago/yagoclasses_links.nt.expanded_decoded.count";
		
		//		String inputFileName = "analyse/mapping1/stichprobe_ausgewertet.csv";
//		String outputFileName = "analyse/mapping1/stichprobe_ausgewertet_urldecoded.csv";

		//inputFileName  = "input/dbpedia/abstract/somelines.csv";
		//outputFileName = "input/dbpedia/abstract/somelines_output.csv";

//		String inputFileName = null;
//		String outputFileName = null;
//		String inputFileName	= "input/dbpedia/redirect/redirect_en_compact.csv";
//		String outputFileName	= "input/dbpedia/redirect/redirect_en_compact_urldecoded.csv";

//		String inputFileName	= "input/dbpedia/articles_label_en_compact_firstline.csv";
//		String outputFileName	= "input/dbpedia/articles_label_en_compact_urldecoded.csv";
		
		if(new File(outputFileName).exists()) throw new RuntimeException("File already exists. Program aborted.");
			
				SingletonTransformer transformer = new SingletonTransformer(new URLDecodeStringTransformerUTF8());
		
		transformOnlyOneColumn(inputFileName, outputFileName, transformer, 1);
//		if(args.length!=2)
//		{
//			//System.out.println("Illegal number of parameters. Call RandomLines inputfile outputfile numberoflines");
//			System.out.println("Illegal number of parameters. Call RandomLines inputfile numberoflines");
//			return;
//		}
		//selectRandomLines(args[0],args[1],Integer.valueOf(args[2]));
		//for(String s:selectRandomLines(readFile(args[0]),Integer.valueOf(args[1]))) System.out.println(s);

		//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
//		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputFileName))));
//		String line;
//		while((line=in.readLine()) != null)
//		{
//			out.println(transformer.transform(line));
//		}
//		in.close();
//		out.close();
	}
	
//	private static String[] readFile(String fileName) throws IOException
//	{
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
//		String line;
//		Vector<String> lines = new Vector<String>();
//		while((line=in.readLine()) != null)
//			lines.add(line);
//		return lines.toArray(new String[0]);		 
//	}
//
////	public static String[] selectRandomLines(String filename,int numberOfLines) throws FileNotFoundException
////	{
////		LineNumberReader in = new LineNumberReader(new InputStreamReader(new FileInputStream(new File(filename))));
////		in.setLineNumber(numberOfLines);
////		// File does not have more than numberOfLines lines
////		if(in.readLine()==null)
////		String[] lines = new String[numberOfLines];
////		
////	}
//
//	public static List<String> selectRandomLines(String[] lines,int numberOfLines)
//	{
//		if(numberOfLines>= lines.length) return Arrays.asList(lines);
//
//		Random r = new Random();
//
//		HashSet<Integer> selectedLineNumbers = new HashSet<Integer>();
//		Vector<String> selectedLines = new Vector<String>();
//
//		int lineNumber;
//		for(int i=0;i<numberOfLines;i++)
//		{
//			do
//			{
//				lineNumber = r.nextInt(lines.length);
//			}			
//			while(selectedLineNumbers.contains(lineNumber));
//			selectedLines.add(lines[lineNumber]);
//			selectedLineNumbers.add(lineNumber);
//		}
//		return selectedLines;
//	}
	
//	public void saveRandomLines(String fnIn,String fnOut,int numberOfLines) throws IOException
//	{
//		String[] lines = readFile(fnIn);
//		
//	}
}
