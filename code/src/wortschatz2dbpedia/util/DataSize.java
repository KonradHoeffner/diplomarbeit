package wortschatz2dbpedia.util;

import java.text.NumberFormat;

public class DataSize
{
	public static String freeSpace()
	{
		return formatSize(Runtime.getRuntime().freeMemory(),3);
	}

	public static String maximumSpace()
	{
		return formatSize(Runtime.getRuntime().maxMemory(),3);
	}

	public static String usedSpace()
	{
		
		return formatSize(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory(),3);
	}
	
	public static int estimatedLineNumbers(String filename)
	{
		return 0;
		
	}

	public static String formatSize(long longSize, int decimalPos)
	  {
	     NumberFormat fmt = NumberFormat.getNumberInstance();
	     if (decimalPos >= 0)
	     {
	        fmt.setMaximumFractionDigits(decimalPos);
	     }
	     final double size = longSize;
	     double val = size / (1024 * 1024);
	     if (val > 1)
	     {
	        return fmt.format(val).concat(" MB");
	     }
	     val = size / 1024;
	     if (val > 10)
	     {
	        return fmt.format(val).concat(" KB");
	     }
	     return fmt.format(val).concat(" bytes");
	  }
	
}
