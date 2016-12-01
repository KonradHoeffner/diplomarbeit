package wortschatz2dbpedia.wordsensedisambiguation;

/**
Idee für wordsensedisambiguation:
Eingabe: satz und wort, dessen sinn erfasst werden soll
Algorihmus:
benutze wortschatz2dbpedia um die in frage kommenden dbpedia artikel rauszukriegen

möglichkeit 1:
vergleiche die im satz vorkommenden wörter mit denen im dbpedia artikel (alternativ nur der abstract).
wähle den, bei dem die meisten wörter vorkommen (bzw. bei dem der score am größten ist mit einer gewichtung
die z.B. von der wortfrequenz abhängt)
möglichkeit 2:
- wähle nur die signifikanten kookurrenzen und sehe nach, wie oft die im artikel vorkommen 
vorteil möglichkeit 1:
- mehr wörter zur auswahl, 
vorteil möglichkeit 2:
- nur relevante wörter werden genutzt, bessere gewichtung der wörter nach signifikanz und so möglich

Vorteil: 
Nachteil: geht nur für wortsinne, zu denen es dbpedia artikel gibt
Mögliche Erweiterung um diesen Nachteil zu umgehen:
nehme den bis jetzt besten algo und benutze den hier nur für die fälle, bei denen der algo hier
sicher ist ein ergebnis zu finden. das macht man, indem man guckt, ob das wort im satz ein eigenname oder ein 
substantiv ist. (mit dem stanford parser, das wäre garnicht so schwer)
 *
 */
public class Main
{

	public String disambiguate(String sentence, String word)
	{
		// look the word up at wortschatz and get the dbpedia links
		// is there only one link?
		// choose this one
		// possibility one: 
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("Nepali: नेपाली");
	}

}
