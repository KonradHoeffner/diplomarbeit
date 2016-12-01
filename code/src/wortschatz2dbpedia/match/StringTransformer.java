package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.HashSet;

public abstract class StringTransformer
{	
	abstract Collection<String> transform(String s);
	
	public abstract String getDescription();
	
	Collection<String> transform(Collection<String> c)
	{
		HashSet<String> transformed = new HashSet<String>();
		for(String s:c)
			transformed.addAll(this.transform(s));
		return transformed;
	}
}