package wortschatz2dbpedia.match;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class StringTransformerList extends StringTransformer
{
	public List<StringTransformer> stringTransformers;

	public int size() {return stringTransformers.size();}
	
	public StringTransformerList(StringTransformer ... transformers)
	{
		stringTransformers = new LinkedList<StringTransformer>(Arrays.asList(transformers));
	}

	public StringTransformerList clone()
	{
		StringTransformerList clone = new StringTransformerList();
		clone.stringTransformers.addAll(this.stringTransformers);
		return clone;
	}
	
	public Collection<String> transform(String s)
	{
		Collection<String> transformed = Collections.singleton(s);
		for(StringTransformer transformer : stringTransformers)
		{
			transformed = transformer.transform(transformed);
		}

		return transformed;
	}

	//public void add(StringTransformer transformer) {stringTransformers.add(transformer);}
	
	@Override
	public String toString()
	{
		if(stringTransformers.isEmpty()) return "does nothing";
		StringBuffer description = new StringBuffer("[");
		for(StringTransformer transformer: stringTransformers) description.append(transformer.getDescription()+", ");
		description.delete(description.length()-2,description.length());
		description.append("]");
		return description.toString();
			
	}
	
	@Override
	public String getDescription()
	{
		return "Applies several transformers one after the other.";
	}
	
}