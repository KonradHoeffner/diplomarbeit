package wortschatz2dbpedia.match;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.MultiMap;

public abstract class StringMatcher {

	abstract MultiMap<String,String> match(Set<String> a,Set<String> b);
}