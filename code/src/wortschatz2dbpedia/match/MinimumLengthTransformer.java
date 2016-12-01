package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

/** Transforms every string smaller than a specified minimum size to the empty string*/
public class MinimumLengthTransformer extends StringTransformer {

	public int minimumLength;
	
	public MinimumLengthTransformer() {this(3);}
	
	public MinimumLengthTransformer(int minimumLength) {this.minimumLength=minimumLength;}

	public Collection<String> transform(String s)
	{
		if(s.length()<minimumLength) return Collections.singleton("");
		return Collections.singleton(s);
	}
	
	public String getDescription()
	{
		return "Deletes everything with a length <"+minimumLength;
	}

}