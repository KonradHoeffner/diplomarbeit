package wortschatz2dbpedia.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import wortschatz2dbpedia.analyse.Classifier;
import wortschatz2dbpedia.analyse.SampleEntry;
import wortschatz2dbpedia.analyse.SpecialCharactersCaseSensitiveExceptFirstCharClassifier;
import wortschatz2dbpedia.match.SingletonTransformer;
import wortschatz2dbpedia.match.URLDecodeStringTransformerUTF8;

public class ClassifyFile
{
	private static final boolean resourceFirst = false;

	public static void classifyFile(String inputFileName,String outputFileName,Classifier classifier,int classNumber) throws IOException
	{
		System.out.println("Using Classifier "+classifier+" with class "+classNumber+"("+classifier.getClasses()[classNumber]+")");
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputFileName))));
		String line;
		String[] split;
		String resource;
		String word;
		int foundCount = 0;
		while((line=in.readLine()) != null)
		{
			//outLine = new StringBuffer();
			split = line.split("\t");
			//System.out.println(Arrays.toString(split));
			if(resourceFirst)
			{
				resource = split[0];
				word = split[1];
			} else
			{
				word = split[0];
				resource = split[1];
			}
			resource = new SingletonTransformer(new URLDecodeStringTransformerUTF8()).transform(resource);
			word = new SingletonTransformer(new URLDecodeStringTransformerUTF8()).transform(word);
			SampleEntry entry = new SampleEntry(resource,word,false,"");
			
			if(classifier.classify(entry)==classNumber)
				{
				out.println(resource+"\t"+word);
				foundCount++;
				}
		}
		System.out.println(foundCount+" entries found for this class.");
		in.close();
		out.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		Classifier classifier = new SpecialCharactersCaseSensitiveExceptFirstCharClassifier();
		//int classNumber = 1;
		String inputPath = "analyse/mapping1/";
		String outputPath = "analyse/mapping1/stichprobe1/";
		String inputFileNameRelative = "output1.csv"; 
		String inputFileName = inputPath+inputFileNameRelative;
		
		for(int classNumber=1;classNumber<classifier.getClasses().length;classNumber++)
		{
			String outputFileName = outputPath+classifier.getClasses()[classNumber]+"-"+classNumber+"."+inputFileNameRelative;
			if(new File(outputFileName).exists()) throw new IOException("Outputfile already exists "+outputFileName);
			classifyFile(inputFileName,outputFileName,classifier,classNumber);
		}
		
	}

}
