package wortschatz2dbpedia.datasource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import wortschatz2dbpedia.util.BinarySearchTextFile;

public class DBpediaAbstractFileBinarySearchSource implements DBpediaAbstractDataSource
{
	String filename;
	RandomAccessFile file;

	public DBpediaAbstractFileBinarySearchSource(String filename) throws FileNotFoundException
	{
		this.filename=filename;
		file = new RandomAccessFile(filename,"r");
	}

	public void close() throws IOException
	{
		file.close();
	}

	@Override
	public String getAbstract(String article)
	{

		try
		{		
			String line = BinarySearchTextFile.get(filename, article, true);
			if(line==null) return "";//throw new RuntimeException("Error loading abstract for article "+article);
			return line.split("\t")[1];
		}
		catch(Exception e) {return "";}
	}
}