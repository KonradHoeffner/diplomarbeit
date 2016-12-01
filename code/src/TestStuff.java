import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringEscapeUtils;


public class TestStuff
{

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		String s = "The letter A";
		System.out.println(s.replaceAll("\\b\\QA\\E\\b", "found"));
		//System.out.println(StringEscapeUtils.escapeJava("Müller's \"Kuh\""));
		//System.out.println(StringEscapeUtils.unescapeJava(StringEscapeUtils.escapeJava("Müller's \"Kuh\"")));
//		String s = "%21%21%21_%28album%29";
//		s="100%";
//		System.out.println(s);
//		System.out.println(java.net.URLDecoder.decode(s));
//		java.net.URLDecoder.decode(s,"UTF-8");
		//System.out.println("finishing touches on the R\u00E0d a Trest album due to financial diffi");
		
	}

}