package wortschatz2dbpedia.datasource;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import wortschatz2dbpedia.util.BufferedReaderIterable;

public class DBpediaRedirectSequentialFileSource implements Iterator<MatchingElement>
{

	Iterator<String> it;
	private final static Logger log = Logger.getLogger(DBpediaRedirectSequentialFileSource.class);
	private int lineCount;

	public DBpediaRedirectSequentialFileSource(String fileName) throws RuntimeException, IOException
	{
		it = new BufferedReaderIterable(new File(fileName)).iterator();
		lineCount=0;
	}

	
	/**
	 * @return the pair of strings containing {resource,label}
	 * @throws IOException 
	 * @throws RuntimeException problem with file input or unexpected format
	 */
	@Override
	public MatchingElement next()
	{
		if(!hasNext()) return null;
		String line = it.next();
		//System.out.println("redirect line: "+line);
		final String[] tokens = line.split("\\t");
		//if(tokens.length!=2) throw new IOException("Wrong redirect format. Expected: <redirect_from><tabulator><redirect_to>\nLine was: "+line);
		// just ignore invalid lines
		if(tokens.length!=2) return next();
		lineCount++;
		if(lineCount%500000==0) log.trace(lineCount/1000+"k DBpedia redirects loaded");
		return new MatchingElement(tokens[1],DBpediaHelper.articleToTitle(tokens[0]));
	}


	@Override
	public boolean hasNext()
	{
		return it.hasNext();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}