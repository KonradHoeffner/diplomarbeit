package wortschatz2dbpedia.match;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class AbstractTest
{

//	@Test
//	public void testIsAtStartOfSentence()
//	{
//		String s = "I'm hungry. I'm hungry too.";
//		Integer startPositions[] = {0,12};
//		for(int pos=0;pos<s.length();pos++)
//			assertTrue(Abstract.isAtStartOfSentence(s, pos)==Arrays.asList(startPositions).contains(pos));
//	}

	@Test
	public void testIsUpperCase()
	{
		String[][][] testCases = {
				{{"A","a"},			{"The letter A is the first letter in the Latin alphabet. Its name in English is spelled ae; the plural is aes, though this is rare."}},
				{{"pilot","Pilot"},	{"Pilot is a different job. Pilot blablabla. A pilot likes danger."}},
				{{"BAM"},{"BAM is our now product. Buy BAM now because BAM makes bam in your tram."}}
		};
		boolean[] expectedUpperCase = {true,false,true};

		for(int i=0;i<testCases.length;i++)
		{
			String[][] testCase = testCases[i];
			
			for(String word:testCase[0])
				for(String sentence:testCase[1])
				{
					//System.out.println("testing \""+word+"\" with \""+sentence+"\", expected uppercase: "+expectedUpperCase[i]);
					assertTrue(Abstract.isUpperCase(word, sentence)==expectedUpperCase[i]);					
				}
		}
	}
	
	@Test
	public void testSetCaseFromAbstract()
	{
		
	}

}