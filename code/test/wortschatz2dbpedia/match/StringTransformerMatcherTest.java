package wortschatz2dbpedia.match;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;
import org.junit.Before;
import org.junit.Test;

public class StringTransformerMatcherTest {

	final static String fnWordsPreview = "input//wortschatz//wordswitha.txt";
	final static String fnWordsAll = "input//wortschatz//wordswitha.txt";
	final static String fnWords = fnWordsPreview;
	final static String fnDBPedia = "input//dbpedia//articleswitha_first10k.txt";
	
	@Before
	public void setUp() throws Exception {
	}

	
	@Test
	public void testMatch() {
		Set<String> set1 = new HashSet<String>(Arrays.asList("aa","AA","nn","x"));
		Set<String> set2 = new HashSet<String>(Arrays.asList("Aa","NN","aA"));
		MultiMap<String,String> matching = 
			new StringTransformerMatcher(new LowerCaseStringTransformer()).match(set1, set2);
		MultiMap<String,String> expectedMatching = new MultiHashMap<String,String>();
		
		expectedMatching.put("AA", "aA");
		expectedMatching.put("AA", "Aa");
		expectedMatching.put("nn", "NN");
		expectedMatching.put("aa", "aA");
		expectedMatching.put("aa", "Aa");
		System.out.println(matching);
		System.out.println(expectedMatching);

		assertTrue(matching.equals(expectedMatching));
	}

	@Test
	public void testShrinkPercentages() throws Exception
	{
		StringTransformerMatcher matcher =  new StringTransformerMatcher(new StringTransformerList(new LowerCaseStringTransformer()));
		
		Set<String> words = Matching.readWords(fnWords);
		testShrinkPercentage(words,matcher);
		
		Set<String> labels = Matching.readLabelsToRessources(fnDBPedia).keySet();
		testShrinkPercentage(labels,matcher);
		
	}
	
	
	public void testShrinkPercentage(Set<String> s,StringTransformerMatcher matcher)
	{
		
		MultiMap<String,String> map = matcher.mapToTransformed(s);
		System.out.println("Reduced with "+matcher.transformer.getClass().toString()+" from "+s.size()+
				" to "+new HashSet<String>(map.values()).size()+" ("+new HashSet<String>(map.values()).size()*100/s.size()+"%)");
		//System.out.println("This Transformer does the following: "+matcher.transformer.getDescription());
		
	}

}
