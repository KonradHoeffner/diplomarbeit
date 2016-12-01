package wortschatz2dbpedia.match;

import java.util.Set;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;

public class SecondStringMatcher extends StringMatcher {
		
	@Override
	public MultiMap<String, String> match(Set<String> set1, Set<String> set2)
	{		
		MultiMap<String, String> matching = new MultiHashMap<String, String>();
		return matching;
	}

}