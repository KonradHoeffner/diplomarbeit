package wortschatz2dbpedia.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class Paste {

	public static void main(String[] args) throws IOException
	{
		if(args.length!=2)
		{
			System.out.println("Java Paste. Usage: 'paste sourcefile1 sourcefile2'");
			//String a = "Visit Real\\'s \\u2013 at \\\"http://www.rgagnon.com\\\"";
			//String b = unquote(a);
			//System.out.println("Example: '"+a+"' -> '"+b+"'");
			return;
		}

		BufferedReader in1 = new BufferedReader(new FileReader(args[0]));
		BufferedReader in2 = new BufferedReader(new FileReader(args[1]));
		//PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
		String line1;
		String line2;
		
		while(((line1=in1.readLine())!=null)|((line2=in2.readLine())!=null))
		{
			if(line1==null) throw new RuntimeException("File 2 has more lines than file 1: "+line1+" "+line2);
			if(line2==null) throw new RuntimeException("File 1 has more lines than file 2: "+line1+" "+line2);
			System.out.println(line1+"\t"+line2);
		}
	}

}