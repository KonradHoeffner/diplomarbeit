package wortschatz2dbpedia.match;

import org.junit.Test;

import com.wcohen.ss.Jaro;
import com.wcohen.ss.JaroWinkler;
import com.wcohen.ss.api.StringDistance;

public class SecondStringTest {

	@Test
	public void distanceTest()
	{		
		
		// http://secondstring.sourceforge.net/doc/iiweb03.pdf paper über die verschiedenen methoden
		// dort steht auch: tokenbasierte verfahren 10x so schnell wie jaro varianten, welche 10x so schnell wie 
		// monge elkan ist
		// Achtung: obwohl die Funktion "distanz" heißt, ist es eigentlich eine Ähnlichkeitsfunktion
		// (hohe Werte - hohe Ähnlichkeit, niedrige Werte - niedrige Ähnlichkeit)
		// Jaro distance,
		
		System.out.println("Jaro distance");
		StringDistance dist = new JaroWinkler();
		String[][] testStringPairs = 
		{
				{"a","b"},
				{"aa","bb"},
				{"ARTICLE","article"},
				{"Article","article"},
				{"articlE","article"},				
				{"article","article"},
				{"déjà","deja"}, // übereinstimmung von deja zu déjà sollte größer sein als zu duju 
				{"duju","deja"},				
				{"article","article (disambiguation)"},
				{"<article>","article"},
				{"%23%23article%23%23","article"},
				{"article","tree"}
		};
		for(String[] pair:testStringPairs)
			System.out.println(pair[0]+" "+pair[1]+": "+dist.score(pair[0], pair[1]));

	}
}
