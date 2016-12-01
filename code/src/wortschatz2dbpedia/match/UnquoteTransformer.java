package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringEscapeUtils;

public class UnquoteTransformer extends StringTransformer
{

	@Override
	public Collection<String> transform(String s)
	{
		return Collections.singleton(StringEscapeUtils.unescapeJava(s));
	}

	@Override
	public String getDescription()
	{
		return "Unquotes strings, e.g. \"M\\u0815ller\" -> \"Müller\"";
	}


}
