// for testing purposes (obviously does not make sense otherwise)
package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

public class EmptyStringTransformer extends StringTransformer {

	public Collection<String> transform(String s)
	{
		String transformed =  "";
		return Collections.singleton(transformed);
	}

	public String getDescription()
	{
		return "Deletes everything";
	}
		
}