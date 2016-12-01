package wortschatz2dbpedia.match;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JProgressBar;

import org.apache.commons.collections15.MultiMap;
import org.apache.log4j.Logger;

import wortschatz2dbpedia.datasource.DBpediaRedirectSequentialFileSource;
import wortschatz2dbpedia.datasource.DBpediaTitlesSequentialFileSource;
import wortschatz2dbpedia.datasource.MatchingElement;
import wortschatz2dbpedia.util.DataSize;
import wortschatz2dbpedia.util.MultiHashMapUnique;

/**
 * Matching is the process of creating the mapping which this class has functions for.
 * If you want to access a mapping that already exists use the class wortschatz2dbpedia.match.Mapping.
 */
public class Matching {

	private final String wortschatzWordListFileName;
	private final String dbPediaTitlesFileName;
	private final String dbPediaDisambiguationsFileName;
	private final String dbPediaRedirectsFileName;
	private final String dbPediaAbstractsFileName;

	private final boolean doUseRedirects;
	private final boolean doSaveOutputSeperately;
	private final boolean doMatchWordsOnlyOnce;
	private final boolean doValidateWithAbstracts;
	private final boolean doFollowDisambiguations;

	private StringTransformerList transformerList;

	Set<String> words = null;
	Collection<String> disambiguations = null;

	private static final Logger log = Logger.getLogger(Matching.class); 
	//	public Matching(){};

	//	public Matching(String)
	//	{
	//		
	//	};

	public Matching(String wortschatzWordListFileName,
			String dbPediaTitlesFileName,
			String dbPediaDisambiguationsFileName,
			String dbPediaRedirectsFileName, String dbPediaAbstractsFileName,
			boolean doUseRedirects, boolean doSaveOutputSeperately,
			boolean doMatchWordsOnlyOnce, boolean doValidateWithAbstracts,
			boolean doFollowDisambiguations,
			StringTransformerList transformerList) throws IOException
			{
		this.wortschatzWordListFileName = wortschatzWordListFileName;
		this.dbPediaTitlesFileName = dbPediaTitlesFileName;
		this.dbPediaDisambiguationsFileName = dbPediaDisambiguationsFileName;
		this.dbPediaRedirectsFileName = dbPediaRedirectsFileName;
		this.dbPediaAbstractsFileName = dbPediaAbstractsFileName;
		this.doUseRedirects = doUseRedirects;
		this.doSaveOutputSeperately = doSaveOutputSeperately;
		this.doMatchWordsOnlyOnce = doMatchWordsOnlyOnce;
		this.doValidateWithAbstracts = doValidateWithAbstracts;
		this.doFollowDisambiguations = doFollowDisambiguations;
		this.transformerList = transformerList;
		//PropertyConfigurator.configure("log4j.properties");
		readInput();
			}

	//	public MultiMap<String, String> match()
	//	{
	//		MultiMap<String, String> wordsToResources = new MultiHashMap<String,String>();
	//		System.out.println("match() not implemented");
	//		return null;
	//	}

	public void readInput() throws IOException
	{
		/*if(words==null)*/ words = readWords(this.wortschatzWordListFileName);
		log.info("Wortschatz loaded: "+words.size()+ " words");
		if(doFollowDisambiguations)
		{
			disambiguations = readDisambiguations(this.dbPediaDisambiguationsFileName);
			log.info("Disambiguations loaded: "+disambiguations.size()+ " disambiguations");
		}
	}

	private static boolean isStandardDBPEdiaTitlesFormat(String line)
	{
		final String uriLabel = "http://www.w3.org/2000/01/rdf-schema#label";
		String[] tokens = line.split("\\t");
		if(!(tokens.length==4)) return false;
		String resource = tokens[0];
		if(resource.equals("")) return false;
		if(!tokens[1].equals(uriLabel)) return false;
		String label = tokens[2];
		if(label.equals("")) return false;
		if(!(tokens[4].equals("l"))) return false;
		return true;
	}

	public void match(String filename) throws Exception
	{
		match(filename,null,0);
	}
	//MultiMap<String,String>
	/**
	 * @param filename where to save the mapping
	 * @param progressBar if you want to inform a swing progressbar with the progress, set to zero if you dont want to use it
	 * @param lines the estimated number of dbpedia entries, used for calculating the progress (not used if progressBar = null) 
	 */
	public void match(String filename, JProgressBar progressBar, int lines) throws Exception
	{
		DBpediaTitlesSequentialFileSource inArticles = new DBpediaTitlesSequentialFileSource(dbPediaTitlesFileName);
		match(inArticles,this.words,this.transformerList,filename,progressBar,lines);
		//MultiMap<String,String> articlesToWords = 
			
		//MultiMap<String,String> ResourcesToWords = new MapOperations<String>().reverseMap(wordsToResources);

//		if(this.doUseRedirects)
//		{
//			log.info("Loading redirects");
//			Iterator<MatchingElement> inRedirects = new DBpediaRedirectSequentialFileSource(dbPediaRedirectsFileName);
//			MultiMap<String,String> articlesToWordsThroughRedirects = match(inRedirects,this.words,this.transformerListfilename);
//			articlesToWords.putAll(articlesToWordsThroughRedirects);
//			//			String fileName = "output/output_redirect.csv";
//			//			log.info("Saving followed redirects output in file "+fileName);
//			//			Matching.matchingToCSV(articlesToWordsThroughRedirects,disambiguations,fileName);
//
//			//			String[] redirect;
//			//			int numberOfRedirectsFollowed = 0;
//			//			while((redirect=inRedirects.next())!=null)
//			//				if(resourcesToWords.containsKey(redirect[0]))
//			//				{
//			//					numberOfRedirectsFollowed++;
//			//					Collection<String> words = resourcesToWords.get(redirect[0]);
//			//					resourcesToWords.remove(redirect[0]);
//			//					resourcesToWords.putAll(redirect[1], words);				
//			//				}
//			//			log.info("Redirects followed: "+numberOfRedirectsFollowed);
//		}
//		else
//		{
//			log.info("Redirects were not followed");
//		}
		//		String fileName = "output/output.csv";
		//		log.info("Saving output in file "+fileName);
		//		Matching.matchingToCSV(articlesToWords,disambiguations,fileName);
		//return articlesToWords;
	}
	
	// the reason one iterator and one set is used is because of RAM space. // MultiMap<String,String>
	public static void match(Iterator<MatchingElement> it,Set<String> words,StringTransformer transformer,String filename,JProgressBar progressBar,int lines) throws Exception
	{
		if(progressBar!=null)
			{
			progressBar.setMinimum(0);
			progressBar.setMaximum(lines);
			}
		final int SAVE_AFTER_THAT_MANY_LINES = 100000;
		log.info("Matching with "+transformer);
		log.debug("Invoking garbage collector...");
		Runtime.getRuntime().gc();
		log.debug("Maximum space: "+DataSize.maximumSpace());
		log.debug("Used space: "+DataSize.usedSpace());
		
		StringTransformerMatcher matcher = new StringTransformerMatcher(transformer);
		
		log.info("Transforming words...");
		MultiMap<String,String> wordsToTransformed = matcher.mapToTransformed(words); 
		log.debug("Used space: "+DataSize.usedSpace());
		log.info("Back transforming words...");
		MultiMap<String,String> transformedWordsReversed = new MapOperations<String>().reverseMap(wordsToTransformed);
		log.debug("Used space: "+DataSize.usedSpace());
		// Empty string is specifically created by some transformers to indicate removal
		transformedWordsReversed.remove("");

		Set<String> transformedWords = transformedWordsReversed.keySet();

		//Map<String,String> labelsToRessources = new HashMap<String,String>();

		//BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(dbPediaTitlesFileName)));

		//final String uriLabel = "http://www.w3.org/2000/01/rdf-schema#label";
		
		
		int titleCount=0;
		int foundCount = 0;
		MatchingElement matchingElement;

		log.info("Starting matching with a used space of "+DataSize.usedSpace());
		long spaceUsedBefore = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		MultiMap<String,String> articlesToWords = new MultiHashMapUnique<String,String>();
		while(it.hasNext())
		{
			matchingElement = it.next();
			//System.out.println(Runtime.getRuntime().freeMemory());
			//System.out.println(foundCount);
			for(String usedForMatching: transformer.transform(matchingElement.usedForMatching))
			{
				titleCount++;
				//String transformedLabel = transformer.transform(matchingElement[1]);
				//matchingElement.usedForMatching=;

				if(transformedWords.contains(usedForMatching))
					for(String word:transformedWordsReversed.get(usedForMatching))
						if(!word.equals(""))
						{
							foundCount++;
							articlesToWords.put(matchingElement.identifier,word);
							if(foundCount%10000==0)
								{
								long spaceUsedAfter = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
								log.debug("Mapping containing "+foundCount/1000+"k elements now with a size of "+DataSize.formatSize(spaceUsedAfter-spaceUsedBefore,3));
								if(progressBar!=null) progressBar.setValue(titleCount);
								System.out.println(titleCount);
								}
							// problem with this approach: duplicates may not be fully removed
							if(foundCount%SAVE_AFTER_THAT_MANY_LINES==0)
							{
								log.info("Saving part of the mapping in file "+filename);
								//if(new File(filename).exists()) throw new RuntimeException("File \""+filename+"\" already exists.");
								Matching.matchingToCSV(articlesToWords, filename,true);
								articlesToWords.clear();
							}

						}


			}
		}
		long spaceUsedAfter = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		log.debug("Finished matching with a used space of "+DataSize.usedSpace());
		log.debug("Estimated mapping size in RAM: "+DataSize.formatSize(spaceUsedAfter-spaceUsedBefore,3));
		//log.info(foundCount+" of "+lineCount+" matched ("+String.format("%.2f",foundCount*100.0/lineCount)+"%) ");
		log.info("DBPedia: "+titleCount+" titles searched");
		//log.debug("Removing duplicates...");
		log.info(foundCount+" matches found");
		
		log.info("DBPedia coverage: "+String.format("%.2f",foundCount*100.0/titleCount)+"%");
		log.info("Wortschatz coverage: "+String.format("%.2f",foundCount*100.0/words.size())+"%");

		log.info("Saving rest of the mapping in file "+filename);
		//if(new File(filename).exists()) throw new RuntimeException("File \""+filename+"\" already exists.");
		Matching.matchingToCSV(articlesToWords, filename,true);

		//return articlesToWords;
	}

	//	public void match() throws Exception
	//	{
	//
	//		int numberOfWords = words.size();
	//		MultiMap<String,String> wordsToResources = new MultiHashMap<String,String>();
	//
	//		//		if(SAVE_OUTPUT_FOR_EACH_TRANSFORMER_SEPARATELY)
	//		//			log.info("Saving output for each transformer separately, "+transformers.length+" transformers used");
	//		//		else 
	//		//			log.info("Saving output of all transformers in one file");
	//		StringTransformer[] transformers = {transformerList};
	//		for(int transformerNr = 0;transformerNr<transformers.length;transformerNr++)
	//		{
	//			if(doSaveOutputSeperately) throw new Exception("Saving output seperately currently not implemented");//wordsToResources.clear();
	//			StringTransformer transformer = transformers[transformerNr];
	//
	//			StringTransformerMatcher matcher = new StringTransformerMatcher(transformer);
	//
	//			Map<String,String> wordsToTransformed = matcher.mapToTransformed(words); 
	//			MultiMap<String,String> transformedWordsReversed = new MapOperations<String>().reverseMap(wordsToTransformed);
	//			// Empty string is specifically created by some transformers to indicate removal
	//			transformedWordsReversed.remove("");
	//			Set<String> transformedWords = transformedWordsReversed.keySet();
	//
	//			//Map<String,String> labelsToRessources = new HashMap<String,String>();
	//
	//			//BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(dbPediaTitlesFileName)));
	//			DBPediaTitlesSequentialFileSource in = new DBPediaTitlesSequentialFileSource(dbPediaTitlesFileName);
	//
	//			//final String uriLabel = "http://www.w3.org/2000/01/rdf-schema#label";
	//
	//			int titleCount=0;
	//			int foundCount = 0;
	//			String[] title;
	//
	//			log.info("Matching with "+transformer);
	//			while((title = in.next()) != null)
	//			{
	//				titleCount++;
	//				String transformedLabel = transformer.transform(title[1]);
	//
	//				if(transformedWords.contains(transformedLabel))
	//					for(String word:transformedWordsReversed.get(transformedLabel))
	//						if(!word.equals(""))
	//						{
	//							foundCount++;
	//							wordsToResources.put(word,title[0]);
	//						}
	//			}
	//
	//			if(doSaveOutputSeperately)
	//			{
	//				throw new Exception("doSaveOutputSeperately currently not supported");
	//				//				String fileNameCSV = "output/output"+(transformerNr+1)+".csv";				
	//				//				log.info("Saving output in CSV file "+fileNameCSV);
	//				//				Matching.matchingToCSV(wordsToResources,disambiguations,fileNameCSV);
	//				//
	//				//				String fileNameHTML = "output/output"+(transformerNr+1)+".html";				
	//				//				log.info("Saving output in HTML file "+fileNameHTML);
	//				//				Matching.matchingToHTML(wordsToResources,fileNameHTML);
	//			}
	//			//WortschatzToDBPedia.matchingToHTML(wordsToResources,"output/output"+(transformerNr+1)+".html");
	//
	//			//log.info(foundCount+" of "+lineCount+" matched ("+String.format("%.2f",foundCount*100.0/lineCount)+"%) ");
	//			log.info("DBPedia: "+titleCount+" titles searched");
	//			log.info(foundCount+" matches found");
	//			log.info("DBPedia coverage: "+String.format("%.2f",foundCount*100.0/titleCount)+"%");
	//			log.info("Wortschatz coverage: "+String.format("%.2f",foundCount*100.0/numberOfWords)+"%");
	//			if(doMatchWordsOnlyOnce)
	//			{
	//				throw new Exception("doMatchOnlyOnce currently not supported");
	////				words.removeAll(wordsToResources.keySet());
	////				log.info("Removing already mapped words from Wortschatz word list. "+words.size()+" words left");
	////				log.info(Matching.disambiguationsFound
	////						+" disambiguations found with this transformer ("
	////						+Matching.disambiguationsFound*100.0/foundCount+"% of those transformers matches).");
	//
	//			}		
	//
	//		}
	//
	//		if(!doSaveOutputSeperately)
	//		{
	//			String fileName = "output/output.csv";
	//			log.info("Saving output in file "+fileName);
	//			Matching.matchingToCSV(wordsToResources,disambiguations,fileName);
	//		}
	//	}

	public static int disambiguationsFound=0;

	public static Set<String> readDisambiguations(String fileName) throws IOException
	{
		Set<String> words = new HashSet<String>();		
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		String line;
		while((line = in.readLine()) != null)
			//words.add(removeSpecialCharacters(line));
			words.add(line.split("\t")[0]);
		in.close();	
		return words;
	}

	public static void matchingToCSV(MultiMap<String, String> articleToWords, String fileName,boolean append) throws IOException
	{
		matchingToCSV(articleToWords,Arrays.asList(new String[0]),fileName,append);
	}

	/** Outputformat is "word[tab]article*/
	public static void matchingToCSV(MultiMap<String, String> articleToWords,Collection<String> disambiguations, String filename,boolean append) throws IOException
	{
		PrintWriter outCSV = new PrintWriter(new FileWriter(filename,append));
		for(String article:articleToWords.keySet())
			for(String word:articleToWords.get(article))	
				outCSV.println(formatCSV(word,article,disambiguations));

		outCSV.close();
	}

	public static void matchingToRDFN3(MultiMap<String, String> articleToWords, String fileName) throws FileNotFoundException
	{
		PrintWriter outRDFN3 = new PrintWriter(fileName);	

		for(String article:articleToWords.keySet())
			for(String word:articleToWords.get(article))
			{
				outRDFN3.println(article+"\thttp://nlp2rdf.org/exactLink\t"+word);
			}
		outRDFN3.close();
	}

	// umdrehen zu resourcesToWords
	//	public static void matchingToHTML(MultiMap<String, String> wordsToResources, String filename) throws FileNotFoundException
	//	{
	//		PrintStream outHTML = new PrintStream(filename);
	//
	//		for(String word:wordsToResources.keySet())
	//			for(String resource:wordsToResources.get(word))	
	//				outHTML.println(formatHTML(word,resource));
	//
	//		outHTML.close();
	//	}

	public static String formatCSV(String article, String word)
	{
		return article+"	"+word;
	}

	public static String formatCSV(String article,String word,Collection<String> disambiguations)
	{
		if(article.contains("_(disambiguation)")||
				(disambiguations!=null&&disambiguations.contains(article.replaceAll("_(disambiguation)","").trim())))			
		{disambiguationsFound++; return formatCSV(article,word)+"	d";}
		else
			return formatCSV(article,word);
	}

	public static String formatHTML(String article, String word)
	{
		return "<a href='http://dbpedia.org/resource/"+article+"' target=_blank> " +
		"dbpedia:"+article+"</a>&nbsp;&nbsp;<a href='http://corpora.uni-leipzig.de/rdf/resource/eng/"+word+"' " +
		"target=_blank> wortschatz:"+word+"</a><br>";
	}

	// word list created with "cut -f2 words.txt > somefilename.txt"
	public static Set<String> readWords(String fnWords) throws IOException
	{
		Set<String> words = new HashSet<String>();		
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fnWords)));
		String line;
		while((line = in.readLine()) != null)
			//words.add(removeSpecialCharacters(line));
			words.add(line);
		in.close();	
		return words;
	}



	// evtl. aufsplitten in einen teil der die datei liest und einen, der die map generiert -> dnan kann der name mapLabelsToRessources bleiben
	// oder ich benenne es um... (create labelsToRessourcesMap z.B.)
	public static Map<String,String> readLabelsToRessources(String fnTitles) throws Exception
	{	
		//Map<String,String> ressourcesToLabels = new HashMap<String,String>();
		Map<String,String> labelsToRessources = new HashMap<String,String>();

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fnTitles)));
		final String uriLabel = "http://www.w3.org/2000/01/rdf-schema#label";

		int count=0;
		String line;
		while((line = in.readLine()) != null)
		{
			count++;
			if(count%20000==0) System.out.println(count);
			// Tokens: 0 - ressource, 1 - property (rdfs:label), 2 - label 
			String[] tokens = line.split("\\t");
			String ressource = tokens[0];
			if(!tokens[1].equals(uriLabel)) throw new Exception("Unexpected Format of input file. Column 2 not equal to '"+uriLabel+"'");
			String label = tokens[2]; 		
			//label = removeSpecialCharacters(label);
			//System.out.println(label+"->"+ressource);
			labelsToRessources.put(label,ressource);
			//			}
			//words.add(in.nextLine());
		}
		in.close();
		//System.out.println(words);
		return labelsToRessources;
	}	

	public static Map<String,String> readLabelsToRessources(String fnTitles,int firstLine,int lastLine) throws Exception
	{	
		//Map<String,String> ressourcesToLabels = new HashMap<String,String>();
		Map<String,String> labelsToRessources = new HashMap<String,String>();

		LineNumberReader in = new LineNumberReader(new InputStreamReader(new FileInputStream(new File(fnTitles))));

		final String uriLabel = "http://www.w3.org/2000/01/rdf-schema#label";

		in.setLineNumber(firstLine);
		for(int lineNumber=firstLine;lineNumber<=lastLine;lineNumber++)
		{
			//if(count%20000==0) System.out.println(count);
			String line = in.readLine();
			// Tokens: 0 - ressource, 1 - property (rdfs:label), 2 - label 
			String[] tokens = line.split("\\t");
			String ressource = tokens[0];
			if(!tokens[1].equals(uriLabel)) throw new Exception("Unexpected Format of input file. Column 2 not equal to '"+uriLabel+"'");
			String label = tokens[2]; 		
			//label = removeSpecialCharacters(label);
			//System.out.println(label+"->"+ressource);
			labelsToRessources.put(label,ressource);
			//			}
			//words.add(in.nextLine());
		}
		in.close();
		//System.out.println(words);
		return labelsToRessources;
	}	

	//	Map<String,String> wordsToLabels = new HashMap<String,String>();

	//		for(String word:words)
	//		{
	//			
	//		}

	static String extractLabel(String s)
	{
		return s.substring(s.indexOf("\"")+1, s.lastIndexOf("\""));
	}	

	static Map<String,String> mapWordsToRessources(String fnWords,String fnTitles) throws Exception
	{
		Set<String> words = Matching.readWords(fnWords);
		Map<String,String> labelsToRessources = Matching.readLabelsToRessources(fnTitles);
		Map<String,String> wordsToRessources = new HashMap<String,String>();

		// simplify labels and words to ease comparison		
		labelsToRessources = StringMethods.simplify(labelsToRessources);
		words = StringMethods.simplify(words);

		Set<String> labels = labelsToRessources.keySet();

		//System.out.println(words);
		//System.out.println(labels);
		int foundExactMatches = 0;
		for(String word: words)
		{
			// search for a label which is similar to this word
			//String label;
			//for(String label:labels)


			if(labels.contains(word))
			{//System.out.println("matching found");
				wordsToRessources.put(word, java.net.URLDecoder.decode(labelsToRessources.get(word)));
				foundExactMatches++;
			}	//String label = labelsToRessources.keySet();
		}
		System.out.println(foundExactMatches+" matches from "+words.size()+"words ("+foundExactMatches*100/words.size()+"%).");
		return wordsToRessources;
	}

}

// old code
// ****************** PREVIEW FORMAT *****************************************
// Tokens: 0 - ressource, 1 - property (rdfs:label), rest: label 

//			String[] tokens = line.split("\\s");
//String ressource = tokens[0];
// ****************** EXTRACT LABEL *****************************************
//			String property = tokens[1];
//			if(property.equals(uriLabel))
//			{
//String label;
//
//String rest = line.substring(tokens[0].length()+tokens[1].length()+2*" ".length());
// has now about the following shape:
// "\"Fat Dom\" Gamiello"@en .
//label = extractLabel(rest);
// ****************** EXTRACT LABEL *****************************************			
// ****************** END PREVIEW FORMAT *****************************************
