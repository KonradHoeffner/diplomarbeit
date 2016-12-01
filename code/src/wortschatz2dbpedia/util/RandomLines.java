package wortschatz2dbpedia.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomLines {

	public static void main(String[] args) throws IOException
	{
		System.out.println("** Random Lines - prints a specified amount of lines randomly chosen out of a file but in the right order **");
		if(args.length!=2)
		{
			System.out.println("Illegal number of parameters. Call Randomlines inputfile numberoflines");
			return;
		}
		printRandomLines(args[0],Integer.valueOf(args[1]));
		//for(String s:selectRandomLines(readFile(args[0]),Integer.valueOf(args[1]))) System.out.println(s);
	}

	public static void printRandomLines(String filename, int numberOfLinesSelected) throws IOException
	{
		LineNumberReader in = new LineNumberReader(new FileReader(filename));
		// Zeilen zählen
		String line;
		int numberOfLinesInFile = 0;
		while((line=in.readLine())!=null) numberOfLinesInFile++;
		// Reader zurücksetzen
		in.close();
		in = new LineNumberReader(new FileReader(filename));
		// falls <numberOfLinesSelected> >= <numberOfLinesInFile> alles ausgeben
		if(numberOfLinesSelected>=numberOfLinesInFile)
		{
			while((line=in.readLine())!=null) System.out.println(line);
		} else
		{
			// <numberOfLinesSelected> Zufallszahlen von 0 bis <numberOfLinesInFile-1> bestimmen
			Random r = new Random();
			Set<Integer> randomNumbers = new HashSet<Integer>(); 
			while(randomNumbers.size()<numberOfLinesSelected)
			{
				randomNumbers.add(r.nextInt(numberOfLinesInFile));
			}
			// Sortieren
			List<Integer> randomNumbersList  = new LinkedList<Integer>(randomNumbers);
			Collections.sort(randomNumbersList);
			// Ausgeben
			for(Integer lineNumber:randomNumbers)
			{
				while(in.getLineNumber()<=lineNumber) line=in.readLine();
				System.out.println(line);
			}
		}
		in.close();
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

	//	public static String[] selectRandomLines(String filename,int numberOfLines) throws FileNotFoundException
	//	{
	//		LineNumberReader in = new LineNumberReader(new InputStreamReader(new FileInputStream(new File(filename))));
	//		in.setLineNumber(numberOfLines);
	//		// File does not have more than numberOfLines lines
	//		if(in.readLine()==null)
	//		String[] lines = new String[numberOfLines];
	//		
	//	}

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
