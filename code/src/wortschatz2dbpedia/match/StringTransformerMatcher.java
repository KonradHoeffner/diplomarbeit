package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;

public class StringTransformerMatcher extends StringMatcher {

	StringTransformer transformer;
	
	public StringTransformerMatcher(StringTransformer transformer)
	{
		this.transformer = transformer;
	}
		
	@Override
	// maybe rewrite later to use apache multimap but that adds additional dependencies and i'm not sure if that's worth it
	public MultiMap<String, String> match(Set<String> set1, Set<String> set2)
	{		
		// map words to their transformed counterparts (may be less words than original, with the new approach may also be more (if one word is mapped to several, e.g. "Star Wars" to "Star" and "Wars").
		MultiMap<String,String> set1Transformed = mapToTransformed(set1);
		MultiMap<String,String> set2Transformed = mapToTransformed(set2);
		// Reverse the two maps
		MultiMap<String,String> set1TransformedReversed = new MapOperations<String>().reverseMap(set1Transformed);
		MultiMap<String,String> set2TransformedReversed = new MapOperations<String>().reverseMap(set2Transformed);		

		// intersect the two sets of transformed words
		Set<String> intersection = new HashSet<String>(set1Transformed.values());
		intersection.retainAll(set2Transformed.values());
	
		// remove the empty string, if contained
		intersection.remove("");
		
		// create matching
		MultiMap<String, String> matching = new MultiHashMap<String, String>();
		for(String s:intersection)
		{
			for(String s1:set1TransformedReversed.get(s))
				for(String s2:set2TransformedReversed.get(s))
					matching.put(s1, s2);
					
		}
		return matching;
	}

	public MultiMap<String,String> mapToTransformed(Collection<String> words)//,StringTransformer stringTransformer
	{
		MultiMap<String,String> mapToTransformed = new MultiHashMap<String,String>();
		
		for(String s:words)
			mapToTransformed.putAll(s,transformer.transform(s));
		return mapToTransformed;
	}

}