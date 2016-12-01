import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.junit.Test;

public class Ini4JTest
{
	@Test
	public void readTest() throws InvalidFileFormatException, IOException
	{
		Ini ini = new Ini(new File("input/test/firstini.ini"));
		int age = ini.get("happy","age",int.class);
		String bla = ini.get("bla","bla",String.class);
		assertTrue(bla.equals("Nice one!"));
		assertTrue(age==99);
	}
	
	@Test
	public void writeTest() throws InvalidFileFormatException, IOException
	{
		File file = new File("input/test/random.ini");
		Ini ini = new Ini();
		int random = new Random().nextInt();
		ini.add("random", "number",random);
		ini.store(file);
		ini = new Ini(file);
		assertTrue(ini.get("random", "number",int.class)==random);
		//assertTrue(age==99);
	}

}