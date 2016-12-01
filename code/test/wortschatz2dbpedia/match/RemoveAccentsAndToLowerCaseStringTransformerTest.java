package wortschatz2dbpedia.match;

import static org.junit.Assert.*;

import org.junit.Test;

import wortschatz2dbpedia.match.RemoveAccentsAndToLowerCaseStringTransformer;


public class RemoveAccentsAndToLowerCaseStringTransformerTest {

	@Test
	public void transformTest()
	{
		assertTrue(new RemoveAccentsAndToLowerCaseStringTransformer().transform("Déjà").equals("deja"));
	}
}
