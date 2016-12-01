// for testing purposes (obviously does not make sense otherwise)
package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

public class RemoveEverythingInParenthesesTransformer extends StringTransformer {

	public Collection<String> transform(String s)
	{
		return Collections.singleton(s.replaceAll(" \\(\\w*\\)",""));
	}

	public String getDescription()
	{
		return "Transformes something like 'Nail (Unit)' into 'Nail (important: there must be a space before the opening bracket)'";
	}
		
}