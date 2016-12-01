package wortschatz2dbpedia.match;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;

public class URLDecodeStringTransformerUTF8 extends StringTransformer {

		public Collection<String> transform(String s)
		{
			//System.out.println(java.nio.charset.Charset.availableCharsets());

			String transformed=null;
			try {
				transformed = java.net.URLDecoder.decode(s,"UTF-8"); // "ISO-8859-15" - western europe, bei den redirects geht anscheinend "UTF-8"
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//catch(Exception e) {System.out.println("Error decoding string: "+s);}
			return Collections.singleton(transformed);
		}

		@Override
		public String getDescription() {
			return "Decodes url encodings";
		}

}
