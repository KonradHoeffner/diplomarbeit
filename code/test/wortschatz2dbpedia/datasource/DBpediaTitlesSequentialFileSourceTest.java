package wortschatz2dbpedia.datasource;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class DBpediaTitlesSequentialFileSourceTest
{
	String compactLine = "A&A	A&A";
	String normalLine = "A%26A	http://www.w3.org/2000/01/rdf-schema#label	A&A	l";

	@Test
	public void testExtractTitleFromNormalFormat() throws IOException
	{
		MatchingElement title = new MatchingElement("A&A","A&A");
		MatchingElement extractedTitle = DBpediaTitlesSequentialFileSource.extractTitleFromNormalFormat(normalLine);
		assertTrue(title.equals(extractedTitle));
	}

	@Test
	public void testExtractTitleFromCompactFormat() throws IOException
	{
		MatchingElement title = new MatchingElement("A&A","A&A");
		MatchingElement extractedTitle = DBpediaTitlesSequentialFileSource.extractTitleFromCompactFormat(compactLine);
		assertTrue(title.equals(extractedTitle));
	}

}