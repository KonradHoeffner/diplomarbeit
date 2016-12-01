package wortschatz2dbpedia.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.junit.Test;

public class BinarySearchTextFileTest
{

	@Test
	public void testGetAbstracts() throws IOException
	{		
		String filename = "input/dbpedia/abstract/longabstract_en_decoded.csv";

			RandomAccessFile in = new RandomAccessFile(filename, "r");
			BinarySearchTextFile binSearch = new BinarySearchTextFile(in);
			String[] searchWordsContained = {"Nail_(unit)"};
			//String[] searchWordsNotContained = {"Obrohom","Goroffe","Mosos"};
			
			StopWatch watch = new StopWatch();
			watch.start();
			
				for(String searchWord : searchWordsContained)
				{
					assertTrue(binSearch.contains(searchWord,true));
				}
//			for(String searchWord : searchWordsNotContained)
//			{
//				assertFalse(BinarySearchTextFile.contains(in,searchWord,isCSV[i]));				
//			}
			
			System.out.println(watch);
	}

//	//@Test
//	public void testGetMultiple() throws IOException
//	{		
//		String[] filenames = {"input/test/sortedwordsmultiple.csv"};
//		boolean[] isCSV = {false,true};
//
//		for(int i=0;i<filenames.length;i++)
//		{
//			String filename = filenames[i];
//
//			RandomAccessFile in = new RandomAccessFile(filename, "r");
//			String[] searchWords = {"Abraham","Elephant","Olophont"};
//			String[][] findings= {{"blablabla"},{"blablabla","blublublu","blöblöblö"},{}};
//			
//			StopWatch watch = new StopWatch();
//			watch.start();
//			final int repetitions = 1000;
//			
//			for(int j=0;j<repetitions/searchWords.length;j++)
//				for(int k=0;k<searchWords.length;k++)
//				{
//					String searchWord = searchWords[k];
//					assertTrue(Arrays.equals(BinarySearchTextFile.getMultiple(in, searchWords[k]),findings[k]));
//					
//				}
//			System.out.println(repetitions+" repetitions");
//			System.out.println(watch);
//		}
//	}

	
	@Test
	public void testGet() throws IOException
	{		
		String[] filenames = {"input/test/sortedwords.txt","input/test/sortedwords.csv"};
		boolean[] isCSV = {false,true};

		for(int i=0;i<filenames.length;i++)
		{
			String filename = filenames[i];

			RandomAccessFile in = new RandomAccessFile(filename, "r");
			BinarySearchTextFile binSearch = new BinarySearchTextFile(in);
			String[] searchWordsContained = {"Abraham","Giraffe","Moses","Elephant"};
			String[] searchWordsNotContained = {"Obrohom","Goroffe","Mosos"};
			
			StopWatch watch = new StopWatch();
			watch.start();
			final int repetitions = 1000;
			
			for(int j=0;j<repetitions/searchWordsContained.length;j++)
				for(String searchWord : searchWordsContained)
				{
					assertTrue(binSearch.contains(searchWord,isCSV[i]));
					
				}
			for(String searchWord : searchWordsNotContained)
			{
				assertFalse(binSearch.contains(searchWord,isCSV[i]));				
			}
			
			System.out.println(repetitions+" repetitions");
			System.out.println(watch);
		}
	}

}
