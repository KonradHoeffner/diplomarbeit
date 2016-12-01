package wortschatz2dbpedia.datasource;

public class DBpediaHelper
{
	public static String articleToTitle(String article)
	{
		return article.replaceAll("_", " ");
	}
}
