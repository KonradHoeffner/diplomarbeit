package wortschatz2dbpedia.datasource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import wortschatz2dbpedia.match.Matching;
import wortschatz2dbpedia.util.BufferedReaderIterable;

/**
 *** NORMAL FORMAT ***
	 the normal format comes downloaded from the dbpedia download page
	 http://wiki.dbpedia.org/Downloads -> titles -> csv
	 the left part is called "resource" and the right part "label"
	 the resource is urlEncoded
	 example lines: (tabulators \t between the entries)
	 Ichikawa%2C_Chiba	http://www.w3.org/2000/01/rdf-schema#label	Ichikawa	l
	 Kirk_O%27Bee	http://www.w3.org/2000/01/rdf-schema#label	Kirk O’Bee	l
	 i have no idea what the "l" stands for

 *** COMPACT FORMAT ***
	 however you can also provide a compact format
	 this eases the disk space and processing time
	 this format consists only of the resource (already url decoded) and the label
	 example lines:
	 Arboretum_de_Varennes-en-Argonne	Arboretum de Varennes-en-Argonne
	 Renée_Rienne	Renée Rienne
	 Ronald_Giles	Ronald Giles

 *** SIMPLE FORMAT *** *TODO*
 	this is the simplest one
 	it has only one column which is the resource, the label is calculated from it automatically
 	by doing the following transformations:
 	- replace "_" by " "
 *** REMARKS ***
 * article name and title are transformable into each other, at the moment only for english but later it should also be like this for other languages
 * obviously simple format only works if they are transformable
 */
public class DBpediaTitlesSequentialFileSource implements Iterator<MatchingElement>
{

	boolean hasCompactFormat;
	boolean hasSimpleFormat;
	Iterator<String> it;
	private final static Logger log = Logger.getLogger(Matching.class);
	private int lineCount;
	private static final String uriLabel = "http://www.w3.org/2000/01/rdf-schema#label";

	public DBpediaTitlesSequentialFileSource(String fileName) throws RuntimeException, IOException
	{
		BufferedReader test = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		hasSimpleFormat = lineHasSimpleFormat(test.readLine());
		test = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		hasCompactFormat = lineHasCompactFormat(test.readLine()); 
		// reset reader (mark() and reset() didn't work for me)
		it = new BufferedReaderIterable(new File(fileName)).iterator();
		lineCount = 0;
	}

	public static boolean lineHasCompactFormat(String line)
	{
		String[] tokens = line.split("\\t");
		log.info("DBPediaTitlesSequentialFileDataSource() detecting "+((tokens.length>=2)?"compact":" no compact")+" format");
		return (tokens.length>=2);
	}

	public static boolean lineHasSimpleFormat(String line)
	{
		String[] tokens = line.split("\\t");
		boolean hasSimpleFormat = tokens.length==1;
		log.info("DBPediaTitlesSequentialFileDataSource() detecting "+(hasSimpleFormat?"simple":"no simple")+" format");
		return hasSimpleFormat;
	}

	public static MatchingElement extractTitleFromSimpleFormat(String line) throws RuntimeException
	{
		if(line.split("\\t").length!=1) throw new RuntimeException
		("Not DBPedia title simple format (one word per line which is only the resource name, like George_Bush)");
		String article = line;
		String title = line.replaceAll("_", " ");
		return new MatchingElement(article,title);
	}

	public static MatchingElement extractTitleFromNormalFormat(String line) throws RuntimeException
	{
		// Tokens: 0 - ressource, 1 - property (rdfs:label), 2 - label 3 - "l" 
		String[] tokens = line.split("\\t");
		if(!tokens[1].equals(uriLabel)) throw new RuntimeException("Not dbPedia title normal format. Column 2 not equal to '"+uriLabel+"' The line is:\""+line+"\"");
		if(!tokens[3].equals("l")) throw new RuntimeException("Not dbPedia title normal format. Column 4 not equal to 'l'. The line is:\""+line+"\"");
		//String[] title = new String[2];
		String article;
		try{article = java.net.URLDecoder.decode(tokens[0],"ISO-8859-15");} catch (UnsupportedEncodingException e){throw new RuntimeException(e.getMessage());}
		String title = tokens[2];
		return new MatchingElement(article,title);
	}

	public static MatchingElement extractTitleFromCompactFormat(String line) throws RuntimeException
	{
		String[] tokens = line.split("\\t");
		if(tokens.length<2) throw new RuntimeException("Line does not have dbPedia title compact format. Tab separated CSV with at least 2 columns required. The line is:\""+line+"\"");
		return new MatchingElement(tokens[0], tokens[1]);	
	}

	/**
	 * @return the pair of strings containing {resource,label}
	 * @throws IOException 
	 * @throws RuntimeException problem with file input or unexpected format
	 */
	@Override
	public MatchingElement next()
	{
		String line = it.next();
		if(line==null) return null;
		if(line.equals("")) return next();
		if(hasSimpleFormat) return extractTitleFromSimpleFormat(line);
		if(line.split("\\t").length<2) return next();
		lineCount++;
		if(lineCount%500000==0) log.trace(lineCount/1000+"k DBpedia lines loaded");
		return hasCompactFormat?extractTitleFromCompactFormat(line):extractTitleFromNormalFormat(line);
	}

	@Override
	public boolean hasNext()
	{
		return it.hasNext();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("Remove() not possible.");
	}

}