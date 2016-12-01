package wortschatz2dbpedia.util;

import java.io.IOException;

import org.junit.Test;

public class RandomLinesTest
{

	@Test
	public void testPrintRandomLines() throws IOException
	{
		RandomLines.printRandomLines("input/test/somefivewords.txt", 4);
	}

}