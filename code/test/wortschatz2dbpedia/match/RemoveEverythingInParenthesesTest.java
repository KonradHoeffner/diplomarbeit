package wortschatz2dbpedia.match;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

public class RemoveEverythingInParenthesesTest
{

	@Test
	public void testTransform()
	{
		StringTransformer transformer = new RemoveEverythingInParenthesesTransformer(); 
		assertTrue(transformer.transform("Nail").equals(Collections.singleton("Nail")));
		assertTrue(transformer.transform("Nail (Unit)").equals(Collections.singleton("Nail")));
	}

}
