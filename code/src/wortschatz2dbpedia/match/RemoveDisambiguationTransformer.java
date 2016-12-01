package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

public class RemoveDisambiguationTransformer extends StringTransformer {

	public Collection<String> transform(String s)
	{
		//return s.replaceAll("(disambiguation)","").replaceAll("(Disambiguation)","").replaceAll("(DISAMBIGUATION)","").trim();
		return Collections.singleton(s.replaceAll("([dD]isambiguation)","").trim());
	}
	
	public String getDescription()
	{
		return "Removes '(disambiguation)'";
	}

}