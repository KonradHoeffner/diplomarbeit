package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

/** Remove french accents ê,é,è,á,à,ô
 */
public class RemoveAccentsAndToLowerCaseStringTransformer extends StringTransformer {

	public Collection<String> transform(String s)
	{
		s = s.toUpperCase();
		s = s.replace('Ý', 'Y');
		s = s.replaceAll("[ÙÚÛÜ]", "U");
		s = s.replaceAll("[ÒÓÔÕÖ]", "O");
		s = s.replaceAll("[ÌÍÎÏ]", "I]");
		s = s.replaceAll("[ÈÉÊË]", "E");
		s = s.replace('Ç', 'C');
		s = s.replaceAll("[ÀÁÂÃÄÅÆ]", "A");
		//s = s.replaceAll("[]", "S");
		
		s = s.toLowerCase();
		return Collections.singleton(s);
	}

	public String getDescription()
	{
		return "Removes accents and converts to lowercase";
	}

}