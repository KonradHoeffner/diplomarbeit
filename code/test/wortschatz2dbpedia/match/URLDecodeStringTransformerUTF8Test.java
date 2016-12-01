package wortschatz2dbpedia.match;

import static org.junit.Assert.*;

import org.junit.Test;

public class URLDecodeStringTransformerUTF8Test
{

	@Test
	public void testTransform()
	{
		StringTransformer t = new URLDecodeStringTransformerUTF8();
		String[] encoded = {"CitiesAndTownsInPunjab%28Pakistan%29"};
		String[] decoded = {"CitiesAndTownsInPunjab(Pakistan)"};
		
		for(int i=0;i<encoded.length;i++)
		assertTrue(t.transform(encoded[i]).equals(decoded[i]));
	}

}
