package wortschatz2dbpedia.util;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.collections15.multimap.MultiHashMap;

public class MultiHashMapUnique<A,B> extends MultiHashMap<A,B>
{
	private static final long serialVersionUID = -4337720501132307560L;

	@Override
	// coll may be null
	protected Collection<B> createCollection(Collection<? extends B> coll)
	{
		if(coll==null) return new HashSet<B>();
		return new HashSet<B>(coll);
	}
	
}