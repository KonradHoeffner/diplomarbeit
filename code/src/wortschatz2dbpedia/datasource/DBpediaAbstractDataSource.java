package wortschatz2dbpedia.datasource;

import java.io.IOException;

public interface DBpediaAbstractDataSource
{
	public String getAbstract(String article);
}