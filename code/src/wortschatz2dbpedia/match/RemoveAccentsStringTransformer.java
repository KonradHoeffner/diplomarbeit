package wortschatz2dbpedia.match;

import java.util.Collection;
import java.util.Collections;

/** Remove french accents ê,é,è,á,à,ô
 */
public class RemoveAccentsStringTransformer extends StringTransformer {

	public Collection<String> transform(String s)
	{
		// lowercase
		s = s.replace('Ý', 'Y');
		s = s.replaceAll("[ÙÚÛÜ]", "U");
		s = s.replaceAll("[ÒÓÔÕÖ]", "O");
		s = s.replaceAll("[ÌÍÎÏ]", "I]");
		s = s.replaceAll("[ÈÉÊË]", "E");
		s = s.replace('Ç', 'C');
		s = s.replaceAll("[ÀÁÂÃÄÅÆ]", "A");
		// uppercase
		s = s.replace('ý', 'y');
		s = s.replaceAll("[ùúûü]", "u");
		s = s.replaceAll("[òóôõö]", "o");
		s = s.replaceAll("[ìíîï]", "i]");
		s = s.replaceAll("[èéêë]", "e");
		s = s.replace('ç', 'c');
		s = s.replaceAll("[àáâãäåæ]", "a");

		return Collections.singleton(s);
	}

	public String getDescription()
	{
		return "Removes accents and converts to lowercase";
	}

}