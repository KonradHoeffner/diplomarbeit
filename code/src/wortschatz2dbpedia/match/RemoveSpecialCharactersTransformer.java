package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

public class RemoveSpecialCharactersTransformer extends StringTransformer {
	
	public Collection<String> transform(String s)
	{
			s = s.replaceAll("\\\\u\\w\\w\\w\\w", "");
			s = s.replaceAll("[^\\w ]", "");
			return Collections.singleton(s);
	}
	
	public String getDescription()
	{
		return "Removes all characters, that do not fit to \\w";
	}

}