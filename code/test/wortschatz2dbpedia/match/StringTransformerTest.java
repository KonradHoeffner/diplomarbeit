package wortschatz2dbpedia.match;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import wortschatz2dbpedia.match.LowerCaseStringTransformer;
import wortschatz2dbpedia.match.StringMatcher;
import wortschatz2dbpedia.match.StringTransformer;
import wortschatz2dbpedia.match.StringTransformerMatcher;
import wortschatz2dbpedia.match.URLDecodeStringTransformer;

public class StringTransformerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test 
	public void urlDecoderTransformerTest()
	{
		String[] urlDecoded = {"Mööp!!","\"A string\"","étépétété"};
		String[] urlEncoded = {"M%F6%F6p!!","%22A+string%22","%E9t%E9p%E9t%E9t%E9"};
		
		String[] result = new String[3];
		
		SingletonTransformer transformer = new SingletonTransformer(new URLDecodeStringTransformer());
		for(int i=0;i<urlEncoded.length;i++)
			result[i] = transformer.transform(urlEncoded[i]);
		System.out.println(Arrays.toString(result));
		System.out.println(Arrays.toString(urlDecoded));
		assertTrue(Arrays.deepEquals(result, urlDecoded));
			//System.out.println(transformer.transform(mixedCaseStrings[i]));
	}
	
	@Test 
	public void lowerCaseStringTransformerTest()
	{
		String[] mixedCase = {"bAlAbA","AAAARGH","nnn"};
		String[] lowerCase = {"balaba","aaaargh","nnn"};
		
		String[] result = new String[3];
		
		SingletonTransformer transformer = new SingletonTransformer(new LowerCaseStringTransformer());
		for(int i=0;i<mixedCase.length;i++)
			result[i] = transformer.transform(mixedCase[i]);
		assertTrue(Arrays.deepEquals(result, lowerCase));
			//System.out.println(transformer.transform(mixedCaseStrings[i]));
	}
	
	@Test 
	public void stringTransformerMatcherTest()
	{
		StringMatcher matcher = new StringTransformerMatcher(new LowerCaseStringTransformer());
		Set<String> words1 = new HashSet<String>(Arrays.asList("NNN","AARGH","bla","bli"));
		Set<String> words2 = new HashSet<String>(Arrays.asList("aargh","nNn","a","b"));
		System.out.println(matcher.match(words1, words2));
	}
	
}