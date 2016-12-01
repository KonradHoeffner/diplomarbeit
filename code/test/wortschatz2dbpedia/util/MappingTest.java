package wortschatz2dbpedia.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class MappingTest
{
	@Test
	public void testMapping()
	{
		Mapping mapping = new Mapping();
		assertTrue(mapping.getArticles("Elephant").contains("Elephant_(film)"));
	}
}
