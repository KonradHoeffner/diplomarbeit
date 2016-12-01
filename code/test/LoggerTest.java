import org.apache.log4j.Logger;
import org.junit.Test;

public class LoggerTest
{
	private static final Logger log = Logger.getLogger(LoggerTest.class);
	
	@Test
	public void testLogger()
	{
		log.info("blablablublu");
	}
	
//	public static void main(String[] args)
//	{
//		log.info("Main speaking here.");
//	}
}