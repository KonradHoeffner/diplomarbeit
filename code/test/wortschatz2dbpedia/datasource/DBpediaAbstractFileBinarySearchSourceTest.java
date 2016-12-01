package wortschatz2dbpedia.datasource;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class DBpediaAbstractFileBinarySearchSourceTest
{

	
	@Test
	public void testGetAbstract() throws IOException
	{
		DBpediaAbstractDataSource in =  new DBpediaAbstractFileBinarySearchSource("input/dbpedia/abstract/longabstract_en_decoded.csv");
		String beginOfAbstract = "Elephants are large land mammals of the order Proboscidea and the family Elephantidae.";
		assertTrue(in.getAbstract("Elephant").startsWith(beginOfAbstract));
		//System.out.println(in.getAbstract("Elephant"));
		System.out.println(in.getAbstract("Starship_Enterprise"));
		System.out.println(in.getAbstract("Outer_space"));
		System.out.println(in.getAbstract("Pig"));
		System.out.println(in.getAbstract("Elephant"));
	}

}
