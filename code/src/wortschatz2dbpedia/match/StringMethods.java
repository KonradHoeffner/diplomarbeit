package wortschatz2dbpedia.match;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StringMethods {

	static String simplify(String s)
	{
		return s.toLowerCase();
	}
	
	static Set<String> simplify(Set<String> strings)
	{
		Set<String> simplified = new HashSet<String>();
		for(String s:strings)
		{
			simplified.add(simplify(s));
		}
		return simplified;
	}

	static Map<String,String> simplify(Map<String,String> stringMap)
	{
		Map<String,String> simplified = new HashMap<String,String>();
		Set<String> keys = stringMap.keySet();

		for(String s:keys)
		{
			simplified.put(simplify(s),stringMap.get(s));
		}
		return simplified;
	}
	
}
