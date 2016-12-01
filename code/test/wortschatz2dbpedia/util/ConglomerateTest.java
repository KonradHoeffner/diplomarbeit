package wortschatz2dbpedia.util;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;

public class ConglomerateTest
{

	@Test
	public void testConglomerate() throws IOException
	{
		String input =
		"Konrad	Höffner\n"+
		"Konrad	aus_der_Konservendose\n"+
		"Konrad	Möffner\n"+
		"Konrad	Röffner\n"+
		"Albrecht	Höffner\n"+
		"Balu	Blöffner\n";
		String expectedOutput=
			"Konrad	Höffner	aus_der_Konservendose	Möffner	Röffner\n"+
			"Albrecht	Höffner\n"+
			"Balu	Blöffner\n";

		StringWriter out = new StringWriter();
		Conglomerate.conglomerate(new BufferedReader(new StringReader(input)),new PrintWriter(out));
		String output = out.getBuffer().toString();
		assertTrue("Output not as expected. Output:\n"+output+"\nExpected:\n"+expectedOutput+"\n",output.equals(expectedOutput));
	}

}
