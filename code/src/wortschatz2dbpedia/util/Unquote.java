package wortschatz2dbpedia.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class Unquote {

	public static void main(String[] args) throws IOException
	{
		if(args.length!=2)
		{
			System.out.println("Java Descaper. Usage: 'unquote sourcefile targetfile'");
			String a = "Visit Real\\'s \\u2013 at \\\"http://www.rgagnon.com\\\"";
			String b = unquote(a);
			System.out.println("Example: '"+a+"' -> '"+b+"'");
			return;
		}

		BufferedReader in = new BufferedReader(new FileReader(args[0]));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
		String line;
		int errors = 0;
		int lines = 0;
		while((line=in.readLine())!=null)
		{
			// remove linebreaks
			if(line.contains("\\n")) System.out.println("Linebreak removed from line: "+line.substring(0, Math.min(40,line.length()))+"...");
			line = line.replaceAll("\\\\n", "");
			//line = line.replaceAll("\\n", "");
			lines ++;
			if((lines%100000)==0){System.out.println(lines+" lines processed...");}
			String unquoted;
			try{unquoted= unquote(line);
			
			out.println(unquoted);}
			catch(Exception e)
			{
				errors ++;
				String lineModified = line.replaceAll("\\\\u", "u");
				try{unquoted= unquote(lineModified);
				out.println(unquoted);}
				catch(Exception f)
				{
					out.println(line);
					System.out.println("Warning: error processing the line <"+line+">."+f.getMessage());
				}
			}
		}
			System.out.println("Finished with "+errors+" errors.");
			in.close();
			out.close();
			/*
      output :
      Visit Real\'s at http://www.rgagnon.com
      Visit Real's at http://www.rgagnon.com
			 */
		}

		public static String unquote(String a) {
			Properties prop = new Properties();
			try {
				prop.load(new ByteArrayInputStream(("x=" + a).getBytes()));
			}
			catch (IOException ignore) {}
			return prop.getProperty("x");
		}
	}