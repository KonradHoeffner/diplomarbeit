package wortschatz2dbpedia.match;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class MatchingTest {

	final static boolean TEST_ALL_TITLES = true;
	final static int TITLES_LIMIT = 1500;
	
	final static String fnWordsPreview = "input//wortschatz//wordswitha.txt";
	final static String fnWordsAll = "input//wortschatz//wordswitha.txt";
	final static String fnWords = fnWordsPreview;

	final static String fnTitlesPreview = "input//dbpedia//articleswitha.txt";
	final static String fnTitlesAll = "input//dbpedia//articles_label_en.txt";
	final static String fnTitles = fnTitlesPreview;
//	final boolean previewFormat = false;

	@Before
	public void setUp() throws Exception {
	}

	//@Test
	public void testTitlesHaveRightForm() throws FileNotFoundException
	{	
		Scanner in = new Scanner(new File(fnTitles));

		final String uriLabel = "http://www.w3.org/2000/01/rdf-schema#label";
		int lineCount = 0;
		while(in.hasNext())
		{ 
			lineCount++;
			if(!TEST_ALL_TITLES&&lineCount>TITLES_LIMIT) break;		
			String line = in.nextLine();
			String[] tokens = line.split("\\t");
			//System.out.println(line);
			//System.out.println("t2:"+tokens[2]);
			assertTrue(tokens[1].equals(uriLabel));			
		}
		in.close();
		assertTrue(lineCount>=10);
	}

	//@Test
	public void testReadWords() throws IOException {
		Set<String> words = Matching.readWords(fnWords);
	}

	//@Test
	public void testMapLabelsToRessources() throws Exception {
		Map<String,String> labelsToRessources = Matching.readLabelsToRessources(fnTitles);
	}
	
	//@Test
	public void testMapWordsToRessources() throws Exception {
		Map<String,String> wordsToRessources = Matching.mapWordsToRessources(fnWords,fnTitles);
		System.out.println(wordsToRessources);
	}

	@Test
	public void testReadDisambiguations() throws Exception
	{
		String fileName = "input/test/disambiguation_random_1000.csv";
		Set<String> disambiguations = Matching.readDisambiguations(fileName);
		System.out.println(disambiguations);
	}
}

// not used anymore
// **************** testTitlesHaveRightForm PREVIEW FORMAT *************************************************
//String line = in.nextLine();
//String[] tokens = line.split("\\s");
//assertTrue(tokens[1].equals("<"+uriLabel+">"));			
//String label = line.substring(tokens[0].length()+tokens[1].length()+2*" ".length());
// has now about the following shape:
// "\"Fat Dom\" Gamiello"@en .

//			assertTrue(label.indexOf("\"")==0); // the label column always starts with the following character: "
//			assertTrue(label.lastIndexOf("\"")>0); // there is another " character inside
//			// it ends with this string:
//			assertTrue(label.substring(label.length()-6).equals("\"@en ."));
//			
//			label = label.substring(label.indexOf("\"")+1, label.lastIndexOf("\""));
//			for(String token:tokens) System.out.print(token+"|");
//			System.out.println();
// ****************** END PREVIEW FORMAT ****************************************
