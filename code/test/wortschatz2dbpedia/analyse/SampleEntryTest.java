package wortschatz2dbpedia.analyse;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class SampleEntryTest
{

	@Test
	public void testSampleEntryString() throws Exception
	{
		SampleEntry sampleEntryFalse = new SampleEntry("\"Braniff\"	\"Braniff\"	0	\"fehleintrag in dbpedia (fehler bei bearbeiten der disambiguierung)\"");
		SampleEntry sampleEntryTrue = new SampleEntry("\"Braniff\"	\"Braniff\"	1	\"fehleintrag in dbpedia (fehler bei bearbeiten der disambiguierung)\"");
		SampleEntry braniffFalse = new SampleEntry("Braniff","Braniff",false,"");
		SampleEntry braniffTrue = new SampleEntry("Braniff","Braniff",true,"");
		assertTrue(sampleEntryFalse.equals(braniffFalse));
		assertTrue(sampleEntryTrue.equals(braniffTrue));
	}

	@Test
	public void testReadFromCSV() throws IOException
	{
		SampleEntry[] sample = SampleEntry.readFromCSV("input/test/stichprobe_ausgewertet.csv");
		assertTrue(sample.length==200);		
		SampleEntry braniff = new SampleEntry("Braniff","Braniff",false,"");
		assertTrue(sample[6].equals(braniff));
	}

}
