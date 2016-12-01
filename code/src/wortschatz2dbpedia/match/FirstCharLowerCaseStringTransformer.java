package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

public class FirstCharLowerCaseStringTransformer extends StringTransformer {

	public Collection<String> transform(String s)
	{
		if(s.length()<2) return Collections.singleton(s.toLowerCase());
		String transformed =  s.subSequence(0, 1).toString().toLowerCase()+s.substring(1);
		return Collections.singleton(transformed);
	}
	
	public String getDescription()
	{
		return "Converts the string to lowercase";
	}

}