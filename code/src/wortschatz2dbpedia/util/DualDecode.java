package wortschatz2dbpedia.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

/**
 * Converts the first row with urldecode and the second one with java decoding
 *
 */
public class DualDecode {

	public static void main(String[] args) throws IOException
	{
		if(args.length!=2)
		{
			System.out.println("Java DualDecode. Usage: 'DualDecode sourcefile targetfile'");
			System.out.println("Converts the first row with urldecode and the second one with java decoding");
			//String a = "Visit Real\\'s \\u2013 at \\\"http://www.rgagnon.com\\\"";
			//String b = unquote(a);
			//System.out.println("Example: '"+a+"' -> '"+b+"'");
			return;
		}
		if(new File(args[1]).exists()) throw new IOException("Output file <"+args[1]+"> already exists.");
		BufferedReader in = new BufferedReader(new FileReader(args[0]));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
		String line;
		int lineNumber = 0;
		while((line=in.readLine())!=null)
		{
			lineNumber ++;
			String[] tokens = line.split("\t");
			if(tokens.length!=2)
				{
					System.out.println("Warning: skipping bad line nr. "+lineNumber+": "+line);
					continue;
				}

			String urldecoded = URLDecoder.decode(tokens[0]);
			String unquoted;

			try{unquoted = Unquote.unquote(tokens[1]);}			
			catch(Exception e)
			{
				tokens[1] = tokens[1].replaceAll("\\\\u", "u");
				try{unquoted= Unquote.unquote(tokens[1]);}

				catch(Exception f)
				{
					System.out.println("Warning: right part of line not decodable. skipping decoding for right part. line nr. "+lineNumber+": "+line);
					unquoted = tokens[1];
				}
			}
			unquoted = unquoted.replace('\n', ' ');
			urldecoded = urldecoded.replace('\n', ' ');

			out.println(urldecoded+"\t"+unquoted);			

		}
		out.close();
	}
}