import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.Test;

public class PrintWriterTest {

	@Test
	public void testIfANewPrintWriterAutomaticallyClearsAFile() throws FileNotFoundException
	{
		for(int i=0;i<2;i++)
		{
			PrintWriter out = new PrintWriter("output/test.txt");
			out.println("bla");
			out.close();
		}
		Scanner in = new Scanner(new File("output/test.txt"));
		in.next();
		assertFalse(in.hasNext());
		in.close();
		System.out.println("test");
	}
}
