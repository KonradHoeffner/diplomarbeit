package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

public class TrimTransformer extends StringTransformer {

	public Collection<String> transform(String s)
	{
		String transformed =  s.trim();
		return Collections.singleton(transformed);
	}
	
	public String getDescription()
	{
		return "Removes leading and trailing spaces.";
	}

}