package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

/** Transforms every string smaller than a specified minimum size to the empty string*/
public class MaximumLengthTransformer extends StringTransformer {

	public int maximumLength;
	
	public MaximumLengthTransformer() {this(2);}
	
	public MaximumLengthTransformer(int maximumLength) {this.maximumLength=maximumLength;}

	public Collection<String> transform(String s)
	{
		if(s.length()>maximumLength) return Collections.singleton("");
		return Collections.singleton(s);
	}
	
	public String getDescription()
	{
		return "Deletes everything with a length >"+maximumLength;
	}

}