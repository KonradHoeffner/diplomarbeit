package wortschatz2dbpedia.match;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jfree.util.Log;

/** Utility methods for whatever is done with the abstracts.*/

public class Abstract
{

	private static final Logger log = Logger.getLogger(Matching.class);

	public static boolean isAtStartOfSentence(String s,int pos)
	{
		if(pos==0) return true;
		if(pos>1&&s.charAt(pos-1)==' '&&s.charAt(pos-2)=='.') return true;
		return false;
	}


	public static boolean isUpperCase(String word,String theAbstract)
	{
		return isUpperCase(word,theAbstract,new int[2]);
	}

	/** is the word (first character of it) in the abstract little or big?
	 * if there is no occurrence in the abstract or if there is an equally big number of
	 * uppercase and lowercase occurrences, the default value (true) will be returned.
	 * the reason for this behaviour is that wikipedia is expected to contain more proper names and the like than not
	 * however, don't count on it as with further analysis the default value can be changed.
	 * also words at the beginning of a sentence (following a ". " will not be counted)
	 * as such this is only applicable to english and likewise languages like german, french... 
	 * (maybe it works elsewhere but who knows)
	 * @param numberOfmatches must be an array with 2 elements which are used to provide information about:
	 * numberOfMatches[0] - number of lower case matches
	 * numberOfMatches[1] - number of upper case matches
	 * this is useful if you want more information and to analyse why the function made the decision to declare a word as uppercase 
	 */
	public static boolean isUpperCase(String word,String theAbstract,int[] occurrences)
	{
		final boolean defaultValue = true;
		// if the first character does not have a differnt uppercase/lowercase variant (its not a letter) return the default value
		char first = word.charAt(0);
		if(Character.getType((int)first)!=Character.LOWERCASE_LETTER&&Character.getType((int)first)!=Character.UPPERCASE_LETTER) return defaultValue;

		String wordUpperCase = word.substring(0, 1).toUpperCase()+word.substring(1);
		String wordLowerCase = word.substring(0, 1).toLowerCase()+word.substring(1);

		// we want only real matches, not just if our word is contained in some other word, so we search for "\b\Qword\E\b" (\b - word boundaries)
		Pattern patternUpperCase = Pattern.compile("\\b"+Pattern.quote(wordUpperCase)+"\\b");
		Pattern patternLowerCase = Pattern.compile("\\b"+Pattern.quote(wordLowerCase)+"\\b");
		double d = Double.MAX_VALUE;
		Matcher matcherUpperCase = patternUpperCase.matcher(theAbstract); 
		Matcher matcherLowerCase = patternLowerCase.matcher(theAbstract);

		int bigOccurences = 0;
		int smallOccurences = 0;

		while(matcherUpperCase.find())
		{
			// Zählt nicht, da sowieso immer groß geschrieben am Satzanfang
			//if(!isAtStartOfSentence(theAbstract,matcherUpperCase.start()))
			bigOccurences++;
		}

		while(matcherLowerCase.find())
		{
			//if(!isAtStartOfSentence(theAbstract,matcherLowerCase.start())) // der fairness halber, falls die methode mal für eine andere sprache eingesetzt wird oder so, wobei der erste buchstabe nicht zwangsläufig groß geschrieben wird
			smallOccurences++;
		}

		occurrences[0] = smallOccurences;
		occurrences[1] = bigOccurences;
		if(bigOccurences>smallOccurences) return true;
		if(smallOccurences>bigOccurences) return false;
		return defaultValue;
	}



	/** Takes a dbpedia title file and sets the case of each title to that which is most used in the abstract
	 * (it calls Abstract.isUpperCase() for this).
	 * Methodically it opens both files at the same time (both are supposed to be decoded and sorted) and then 
	 * sets the title of each article according to its case in the abstract.
	 * The Algorithm should be in O(n).
	 * The abstract file has the format "article \t abstract"
	 * The title file has the format "article \t title"
	 * It works like this:</br>
	 * It reads both first lines of both the abstract file and the titles file and reads the article name
	 * Now there are tree cases:</br>
	 * 1) both names are equal</br>
	 * 2) the name from the title file < the name from the abstract file</br>
	 * 3) the name from the title file > the name from the abstract file (should not really happen often since it would mean the title file does not contain an entry for it)</br> 
	 * The course of action for the three cases:</br>
	 * 1) determine the case and replace the title with it, then save the modified title line to the new title file, then read two new lines</br> 
	 * 2) read a new line from the title file and compare again</br>
	 * 3) read a new line from the abstract file and compare again</br>
	 * repeat until one of the two input files is at its end</br>
	 */
	public static void setCaseFromAbstract(String fnTitleIn,String fnTitleOut,String fnAbstract) throws IOException
	{
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Log.info("Set case from abstract file "+fnAbstract+". Using input title file "+fnTitleIn+", saving result in file "+fnTitleOut+"...");
		BufferedReader inTitle = new BufferedReader(new FileReader(fnTitleIn));
		BufferedReader inAbstract = new BufferedReader(new FileReader(fnAbstract));
		if(new File(fnTitleOut).exists()) throw new RuntimeException("Output file "+fnTitleOut+" already exists."); 
		PrintWriter out = new PrintWriter(new File(fnTitleOut));
		//RandomAccessFile abstracts = new RandomAccessFile("","r");

		String lineTitle = inTitle.readLine();
		String lineAbstract = inAbstract.readLine();
		int count = 0;
		int foundCount=0;
		int replacementCount=0;

		while(lineTitle!=null&&lineAbstract!=null)
		{
			count++;
			String[] titleTokens	= lineTitle.split("\t");
			if(titleTokens.length!=2) // skip invalid line
			{
				lineTitle = inTitle.readLine(); 
				continue;
			}
			String[] abstractTokens = lineAbstract.split("\t");
			if(abstractTokens.length!=2) // skip invalid line
			{
				lineAbstract = inAbstract.readLine(); 
				continue;
			}

			String articleFromTitleFile	= titleTokens[0];
			String articleFromAbstractFile	= abstractTokens[0];
			String title	= titleTokens[1];

			String theAbstract = abstractTokens[1];

			int compareResult = articleFromTitleFile.compareToIgnoreCase(articleFromAbstractFile);
			if(compareResult<0) compareResult = -1;
			if(compareResult>0) compareResult = 1;

			switch(compareResult)
			{
			case 0: // success, replace if needed and read new lines in both files
			{
				foundCount++;
				String newLineTitle = lineTitle;

				if(!isUpperCase(articleFromTitleFile.replace('_',' '), theAbstract))
				{
					replacementCount++;
					newLineTitle = 
						articleFromTitleFile+"\t"
						+title.substring(0, 1).toLowerCase()
						+title.substring(1);
					//					out.println(newLineTitle);
				}
				newLineTitle+="\t"+theAbstract;
				out.println(newLineTitle);
				lineTitle = inTitle.readLine();
				lineAbstract = inAbstract.readLine();				
				break;
			}
			case -1: // print and skip title line 
			{
				//out.println(lineTitle);
				lineTitle = inTitle.readLine();
				break;
			}
			case 1: // skip abstract line
			{
				lineAbstract = inAbstract.readLine();
				break;
			}
			}
			if(foundCount%100000==0) log.info(foundCount/1000+"k entries processed.");
		}
		log.info("Iterations: "+count);
		log.info("Found: "	+foundCount);
		log.info("Replaced: "	+replacementCount);
		inTitle.close();
		inAbstract.close();
		out.close();
		log.info("... finished in "+stopWatch);
	}

	public static void analyseAbstract(String filename) throws IOException
	{/*
last result: (dbpedia 33 data set, replace "_" with " " in the article name to get estimated title )
... finished in 0:09:53.626
Result: 2754997 upper case (98.72913426460154%), 35463 lower case (1.2708657353984647%) of 2790460 lines.
-> sehr niedrig , weiter untersuchen

das ersetzen war falsch, jetzt nochmal richtig:
Result: 2670002 upper case (95.68322068762856%), 120458 lower case (4.316779312371437%) of 2790460 lines.

setCaseFromAbstract yields with the same data: (aber hier direkt die titel benutzt)
Iterations: 4722784
Found: 1261833
Replaced: 59092
... finished in 0:06:44.913

davor (nur mit den artikelnamen):
Iterations: 4722784
Found: 1261833
Replaced: 16181

jetzt mit artikelname, dort aber "_" ersetzt durch " ":
Iterations: 4722784
Found: 1261833
Replaced: 59086

deepAnalyseAbstract(): first version (tries to match everything) aber am anfang der zeile wird nicht gezählt

---------------------- normal----------------------
-----------multi word-----------
Lines with occurrences  using regEx .*\s.* in the abstract: 557247 of 2365412 lines (23.558137017990948%). Lines with no occurrences: 1808165
-----------multi word mit klammer-----------
Lines with occurrences  using regEx .* \(.* in the abstract: 232 of 297198 lines (0.07806243649015135%). Lines with no occurrences: 296966

-----------single word-----------
Lines with occurrences  using regEx [^\s]* in the abstract: 180336 of 423426 lines (42.58973232630967%). Lines with no occurrences: 243090
-----------all words-----------
Lines with occurrences in the abstract: 737583 of 2788838 lines (26.44768179435306%). Lines with no occurrences: 2051255

43% ist zwar schon ganz gut aber immer noch etwas wenig. wie sieht es denn aus, wenn man nur zum vergleich mal 
am anfang eines satzes mitzählt?
-> aufruf von isAtStartOfSentence() auskommentieren in isUpperCase() 
-> kommt komischerweise genau das gleich raus. warum? -> ah, es muss ein neues caseinfofile erzeugt werden
---------------------- auch am start des satzes mitzählen----------------------
-----------multi word-----------
... finished in 0:00:24.082
Lines with occurrences  using regEx .*\s.* in the abstract: 1424492 of 2365412 lines (60.22172881510705%). Lines with no occurrences: 940920
-----------single word-----------
... finished in 0:00:18.177
Lines with occurrences  using regEx [^\s]* in the abstract: 405641 of 423426 lines (95.79973832499658%). Lines with no occurrences: 17785
----------- all words-----------
... finished in 0:00:14.749
Lines with occurrences in the abstract: 1830133 of 2788838 lines (65.62349623750107%). Lines with no occurrences: 958705

Was schließen wir daraus?
- alles in klammern kann bedenkenlos weg, das kommt fast nie vor 
- größte ausbeute: nur das erste wort nehmen, das gibt schonmal 42%
- alternative: schrittweise, erst das ganze und dann immer wörter wegnehmen, das ist langsamer aber müsste sich nicht viel nehmen 
von der genauigkeit also braucht man das nicht. ist schon sehr unwahrscheinlich das mehrere großschreibungen derselben sache vorkommen.
(interessante spielerei bei zu viel zeit allerdings: verwende beide methoden und vergleiche dann am ende die differenz)
Frage:
- wie erhöhen wir jetzt die 42%? wenn es nicht vorkommt kann man ja nicht viel machen. die 95% die man 
hat wenn man auch das anfang nimmt sind zwar super aber es nützt einem ja nix...
was gibt es noch für merkmale an denen man die großschreibung ansehen kann?
- mehrwörter sind ja meist eigennamen, also müsste eine korrelation sein zwischen "ist mehrwort" und "ist groß"
-> überprüfen am wortschatz
- ächz... was noch?
- "singlewörter" bleiben übrig, vielleihcht mal auf der kompletten seite gucken. oder gibts nen kompletten dump downzuloaden?
kann ich mir nicht vorstellen, dass in dem ganzen artikel so ein wort nicht mal mitten im satz vorkommt...
- man könnte die ganze seite durchsuchen, wenn es da dumps zu gibt -> geht nicht
		 */
		log.info("Analyse abstract "+filename+"...");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line;
		int upperCase = 0;
		int lines = 0;
		while((line=in.readLine())!=null)
		{
			lines++;
			if(lines%100000==0) log.info(lines/1000+"k entries processed.");
			String[] tokens = line.split("\t");
			if(tokens.length<2) continue;
			String article = tokens[0];
			String estimatedTitle = article.replace('_',' ');
			String theAbstract = tokens[1];
			if(isUpperCase(estimatedTitle,theAbstract)) upperCase++;			
		}
		in.close();
		log.info("... finished in "+stopWatch);
		log.info("Result: "+upperCase+" upper case ("		+ (double)upperCase*100/lines+
				"%), "+(lines-upperCase)+" lower case ("	+ (double)(lines-upperCase)*100/lines +
				"%) of "+lines+" lines.");
	}

	/** Um die folgenden Analyseschritte zu der Groß und Kleinschreibung zu beschleunigen, wird
	 * eine Datei mit Zeilen folgenden Formats erzeugt:
	 * article	smallOccurrences	bigOccurrences	 
	 * Da die Ursprungsdatei sehr groß ist, dürfte das einiges an Geschwindigkeit bringen.
	 * @throws IOException 
	 */
	public static void createCaseInfoFile(String abstractFilename, String infoOutputFilename) throws IOException
	{
		if(new File(infoOutputFilename).exists()) throw new RuntimeException("File already exists: "+infoOutputFilename);
		StopWatch stopWatch = new StopWatch(); stopWatch.start();
		log.info("Creating case info file "+infoOutputFilename+" out of abstract file "+abstractFilename+"...");
		BufferedReader in = new BufferedReader(new FileReader(abstractFilename));
		PrintWriter out = new PrintWriter(infoOutputFilename);
		String line;
		int lines = 0;
		while((line=in.readLine())!=null)
		{
			lines++;
			if(lines%100000==0) log.info(lines/1000+"k entries processed.");
			String[] tokens = line.split("\t");
			if(tokens.length<2) continue;
			int[] occurrences = new int[2];
			String word = tokens[0].replace("_", " ");
			String theAbstract = tokens[1];
			isUpperCase(word, theAbstract, occurrences);
			out.println(word+'\t'+occurrences[0]+'\t'+occurrences[1]);
		}		
		in.close();
		out.close();
		log.info("... finished in "+stopWatch); 
	}

	/**
	Convenience method if you want to analyse all lines.
	 */
	public static void deepAnalyseAbstract(String caseInfoFilename,String smallOccurrencesFilename,String bigOccurrencesFilename, String differenceOccurrencesFilename) throws IOException
	{
		deepAnalyseAbstract(caseInfoFilename, smallOccurrencesFilename, bigOccurrencesFilename, differenceOccurrencesFilename,null);
	}

	/**differences will only be measured if not both values are 0
	 * @param regEx if not null, lines will only be counted if they match regEx
	 */
	@SuppressWarnings("unchecked") // generic arrays
	public static void deepAnalyseAbstract(String caseInfoFilename,String smallOccurrencesFilename,String bigOccurrencesFilename, String differenceOccurrencesFilename,String regEx) throws IOException
	{
		StopWatch stopWatch = new StopWatch(); stopWatch.start();
		log.info("Deep analysing abstract using regEx "+regEx+", reading info file "+caseInfoFilename+", writing small occurrences file "+smallOccurrencesFilename+", big occurrences file"+bigOccurrencesFilename+" and occurrence difference file "+differenceOccurrencesFilename+"...");

		BufferedReader in = new BufferedReader(new FileReader(caseInfoFilename));

		PrintWriter outSmallOccurrences = new PrintWriter(smallOccurrencesFilename);
		PrintWriter outBigOccurrences = new PrintWriter(bigOccurrencesFilename);
		PrintWriter outDifferenceOccurrences = new PrintWriter(differenceOccurrencesFilename);
		PrintWriter[] writers = {outSmallOccurrences,outBigOccurrences,outDifferenceOccurrences};

		// maps "n occurrences in a line" to "how many lines have this property"
		Map<Integer, Integer> smallOccurrences = new HashMap<Integer,Integer>();
		Map<Integer,Integer> bigOccurrences = new HashMap<Integer,Integer>();
		Map<Integer,Integer> differenceOccurrences = new HashMap<Integer,Integer>();
		Map[] maps = {smallOccurrences,bigOccurrences,differenceOccurrences};
		String line;
		int lines = 0;
		int noOccurrences = 0;
		while((line=in.readLine())!=null)
		{
			String[] tokens = line.split("\t");
			if(tokens.length!=3) throw new RuntimeException("Wrong format. 3 tab separated columns expected");
			String article = tokens[0];
			if(regEx!=null)
			{
				if(!article.matches(regEx)) continue;
			}
			lines++;
			if(lines%100000==0) log.info(lines/1000+"k entries processed.");

			Integer smallOccurrencesInThisLine = Integer.valueOf(tokens[1]); 
			Integer bigOccurrencesInThisLine = Integer.valueOf(tokens[2]);
			Integer differenceOccurrencesInThisLine = smallOccurrencesInThisLine-bigOccurrencesInThisLine;

			// lines with no occurrences at all get counted and dont get included in the difference statistic
			if(smallOccurrencesInThisLine==0&&bigOccurrencesInThisLine==0)
			{
				noOccurrences++;
				differenceOccurrencesInThisLine = null;
			}

			Integer[] occurrencesInThisLine = {smallOccurrencesInThisLine,bigOccurrencesInThisLine,differenceOccurrencesInThisLine};
			for(int i=0;i<maps.length;i++)
			{
				if(occurrencesInThisLine[i]==null) continue;
				Map<Integer,Integer> map = maps[i];
				Integer linesWithThisNumberOfOccurrences = smallOccurrences.get(occurrencesInThisLine[i]);
				if(linesWithThisNumberOfOccurrences==null) linesWithThisNumberOfOccurrences=0;
				linesWithThisNumberOfOccurrences++;
				map.put(occurrencesInThisLine[i], linesWithThisNumberOfOccurrences);
			}
			//smallOccurrences.put(, value);

		}
		// save the maps
		for(int i=0;i<writers.length;i++)
		{
			PrintWriter writer = writers[i];
			Map<Integer,Integer> map = maps[i];
			List<Integer> keys = new LinkedList<Integer>(map.keySet());
			Collections.sort(keys);
			for(int key:keys)
				writer.println(key+"\t"+map.get(key));
		}
		in.close();
		for(PrintWriter writer: writers) writer.close();
		int occurrences = lines - noOccurrences;
		log.info("... finished in "+stopWatch);
		log.info("Lines with occurrences "+
				((regEx==null)?"":(" using regEx "+regEx+" "))+
				"in the abstract: "+occurrences+" of "+lines+" lines ("+
				(double)occurrences*100/lines+"%). Lines with no occurrences: "+noOccurrences);
	}

	public static void main(String[] args) throws IOException
	{
		PropertyConfigurator.configure("log4j.properties");
		String inTitleFilename  = "input/dbpedia/titles_en_decoded.csv";
		String outTitleFilename = "input/dbpedia/replaceunderscores_matchwitharticle_titles_en_casecorrected.csv";
		String abstractFilename = "input/dbpedia/abstract/longabstract_en_decoded.csv";
		String caseInfoFilename = "analyse/caseinfo.csv";
		String caseInfoCountAtStartOfSentenceTooFilename = "analyse/caseinfo_count_at_start_of_sentence_too.csv";
		String smallOccurrencesFilename = "analyse/smalloccurrences.csv";
		String bigOccurrencesFilename = "analyse/bigoccurrences.csv";
		String differenceOccurrencesFilename = "analyse/differenceoccurrences.csv";

		//analyseAbstract(abstractFilename);
		//setCaseFromAbstract(inTitleFilename,outTitleFilename,abstractFilename);
		//createCaseInfoFile(abstractFilename, caseInfoCountAtStartOfSentenceTooFilename);
		// single word, multi word
		String[] regExes = {".*\\s.*",".* \\(.*","[^\\s]*",null};//{".*[_ ].*","[]"};
		for(String regEx : regExes)
			deepAnalyseAbstract(caseInfoFilename, smallOccurrencesFilename+regEx, bigOccurrencesFilename+regEx, differenceOccurrencesFilename+regEx,regEx);
		//deepAnalyseAbstract(caseInfoFilename, smallOccurrencesFilename, bigOccurrencesFilename, differenceOccurrencesFilename);
	}
}