// for testing purposes (obviously does not make sense otherwise)
package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

/**
 * If you want only the first returned value of the result set of a transformer. 
 * Convenience class as most transformers only have one result anyways.
 */
public class SingletonTransformer {

	private final StringTransformer transformer; 
	
	public SingletonTransformer(StringTransformer transformer)
	{
		this.transformer = transformer;
	}
	
	public String transform(String s)
	{
		return transformer.transform(s).toArray(new String[0])[0];
	}
		
}