package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

public class LowerCaseStringTransformer extends StringTransformer {

	public Collection<String> transform(String s)
	{
		String transformed =  s.toLowerCase();
		return Collections.singleton(transformed);
	}
	
	public String getDescription()
	{
		return "Converts the string to lowercase";
	}

}