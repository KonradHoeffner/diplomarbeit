package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;

public class MapOperations<T>
{

	public MultiMap<T,T> reverseMap(Map<T,T> map)
	{
		MultiMap<T,T> revertedMap = new MultiHashMap<T,T>();
		for(T key:map.keySet())
		{
			T value = map.get(key);
			revertedMap.put(value, key);
//			if(revertedMap.containsKey(value)) revertedMap.get(value).add(key);
//			else
//			{
//				HashSet<T> set = new HashSet<T>();
//				set.add(key);
//				revertedMap.put(value,set);
//			}
		}
		return revertedMap;
	}

	public MultiMap<T,T> reverseMap(MultiMap<T,T> map)
	{
		MultiMap<T,T> revertedMap = new MultiHashMap<T,T>();
		for(T key:map.keySet())
		{
			Collection<T> values = map.get(key);
			for(T value:values)
			revertedMap.put(value, key);
//			if(revertedMap.containsKey(value)) revertedMap.get(value).add(key);
//			else
//			{
//				HashSet<T> set = new HashSet<T>();
//				set.add(key);
//				revertedMap.put(value,set);
//			}
		}
		return revertedMap;
	}

}