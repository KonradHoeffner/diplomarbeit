package wortschatz2dbpedia.match;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/** E.g. "Star Wars" -> {"Star","Wars"}*/
public class SingleWordTransformer extends StringTransformer {

	public Collection<String> transform(String s)
	{
		return Arrays.asList(s.split(" "));
	}

	public String getDescription()
	{
		return "Splits a multiword into its parts, e.g. \"Star Wars\" -> {\"Star\",\"Wars\"}";
	}
		
}