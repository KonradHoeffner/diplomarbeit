package wortschatz2dbpedia.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CreateBigWordFile {

	final static int NUMBER_OF_LINES = 8*1000*1000; 
	
	public static void main(String[] args) throws FileNotFoundException
	{
		PrintWriter out = new PrintWriter("input/wortschatz/bigfile.txt");
		
		for(int i=0;i<NUMBER_OF_LINES;i++)
			out.println("word"+i);
		out.close();
		
	}
}
