//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.commons.collections15.MultiMap;
//import org.apache.commons.collections15.multimap.MultiHashMap;
//import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
//
//import wortschatz2dbpedia.match.FirstCharLowerCaseStringTransformer;
//import wortschatz2dbpedia.match.MapOperations;
//import wortschatz2dbpedia.match.Matching;
//import wortschatz2dbpedia.match.MinimumLengthTransformer;
//import wortschatz2dbpedia.match.StringTransformerList;
//import wortschatz2dbpedia.match.StringTransformerMatcher;
////**********************************************************************************
//// DOES NOT WORK ANYMORE. USE wortschatz2dbpedia.gui.MatchingFrame instead.
////**********************************************************************************
//public class Main {
//
//	// ENGLISH
//	final static String fnWortliste = "input//wortschatz//en_words_all.txt";//words_en1m
//	final static String fnDBPediaAll = "input//dbpedia//articles_label_en.txt";
//	final static String fnDBPediaPreview = "input//dbpedia//articles_random_10000.txt";
//	final static String fnDBPedia = fnDBPediaAll;
//	final static String fnDisambiguations = "input//dbpedia//disambiguation//disambiguation_en.csv";//words_en1m
//	final static String fnRedirects = "";
//	final static boolean LOAD_DISAMBIGUATION_FILE = true;
//	// GERMAN
//	//	final static String fnWortliste = "input//wortschatz//words_de3m.txt";//words_en1m
//	//	final static String fnDBPediaAll = "input//dbpedia//articles_label_de.txt";
//	//	final static String fnDBPediaPreview = "does not exist";
//	//	final static String fnDBPedia = fnDBPediaAll;
//	//	final static String fnDisambiguations = "does not exist yet";//does not yet exist for german language
//	//	final static boolean LOAD_DISAMBIGUATION_FILE = false;
//
//	final static int MAX_LINES = 1000000;
//
//	public static final boolean REMOVE_ALREADY_MAPPED_WORDS_AFTER_EACH_TRANSFORMER = true;
//	public static final boolean SAVE_OUTPUT_FOR_EACH_TRANSFORMER_SEPARATELY = true;
//
//	private final static Logger log = Logger.getLogger(Matching.class); 
//
//	private static void match(Set<String> words,Collection<String> disambiguations,StringTransformerList[] transformers) throws Exception
//	{
//		int numberOfWords = words.size();
//		MultiMap<String,String> wordsToResources = new MultiHashMap<String,String>();
//
//		//		if(SAVE_OUTPUT_FOR_EACH_TRANSFORMER_SEPARATELY)
//		//			log.info("Saving output for each transformer separately, "+transformers.length+" transformers used");
//		//		else 
//		//			log.info("Saving output of all transformers in one file");
//
//		for(int transformerNr = 0;transformerNr<transformers.length;transformerNr++)
//		{
//			if(SAVE_OUTPUT_FOR_EACH_TRANSFORMER_SEPARATELY) wordsToResources.clear();
//			StringTransformerList transformer = transformers[transformerNr];
//
//			StringTransformerMatcher matcher = new StringTransformerMatcher(transformer);
//			// rework this, so that files with 7 mio words are also able to be searched (maybe work with multiple blocks?)
//			Map<String,String> wordsToTransformed = matcher.mapToTransformed(words); 
//			MultiMap<String,String> transformedWordsReversed = new MapOperations<String>().reverseMap(wordsToTransformed);
//			// Empty string is specifically created by some transformers to indicate removal
//			transformedWordsReversed.remove("");
//			Set<String> transformedWords = transformedWordsReversed.keySet();
//
//			//Map<String,String> labelsToRessources = new HashMap<String,String>();
//
//			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fnDBPedia)));
//
//			final String uriLabel = "http://www.w3.org/2000/01/rdf-schema#label";
//
//			int lineCount=0;
//			int foundCount = 0;
//			String line;
//
//			log.info("Matching with "+transformer);
//			while((line = in.readLine()) != null)
//				if(!line.equals(""))
//				{
//					//Runtime.getRuntime().freeMemory();
//					lineCount++;
//					if(lineCount%500000==0) log.trace(lineCount/1000+"k DBpedia lines loaded");
//					// Tokens: 0 - ressource, 1 - property (rdfs:label), 2 - label 
//					String[] tokens = line.split("\\t");
//					String resource = tokens[0];
//					if(!resource.equals(""))
//					{
//						if(!tokens[1].equals(uriLabel)) throw new Exception("Unexpected Format of input file. Column 2 not equal to '"+uriLabel+"'");
//						String label = tokens[2]; 		
//						//label = Matching.removeSpecialCharacters(label);
//
//						if(!label.equals(" "))
//						{
//							String transformedLabel = transformer.transform(label);
//
//							if(transformedWords.contains(transformedLabel))
//								for(String word:transformedWordsReversed.get(transformedLabel))
//									if(!word.equals(""))
//									{
//										foundCount++;
//										wordsToResources.put(word,resource);
//									}
//						}
//
//					}
//				}
//			in.close();
//			if(SAVE_OUTPUT_FOR_EACH_TRANSFORMER_SEPARATELY)
//			{
//				String fileNameCSV = "output/output"+(transformerNr+1)+".csv";				
//				log.info("Saving output in CSV file "+fileNameCSV);
//				Matching.matchingToCSV(wordsToResources,disambiguations,fileNameCSV);
//
//				String fileNameHTML = "output/output"+(transformerNr+1)+".html";				
//				log.info("Saving output in HTML file "+fileNameHTML);
//				//Matching.matchingToHTML(wordsToResources,fileNameHTML);
//			}
//			//Matching.matchingToHTML(wordsToResources,"output/output"+(transformerNr+1)+".html");
//
//			//log.info(foundCount+" of "+lineCount+" matched ("+String.format("%.2f",foundCount*100.0/lineCount)+"%) ");
//			log.info("DBPedia: "+lineCount+" titles searched");
//			log.info(foundCount+" matches found");
//			log.info("DBPedia coverage: "+String.format("%.2f",foundCount*100.0/lineCount)+"%");
//			log.info("Wortschatz coverage: "+String.format("%.2f",foundCount*100.0/numberOfWords)+"%");
//			if(REMOVE_ALREADY_MAPPED_WORDS_AFTER_EACH_TRANSFORMER)
//			{
//				words.removeAll(wordsToResources.keySet());
//				log.info("Removing already mapped words from Wortschatz word list. "+words.size()+" words left");
//				log.info(Matching.disambiguationsFound
//						+" disambiguations found with this transformer ("
//						+Matching.disambiguationsFound*100.0/foundCount+"% of those transformers matches).");
//
//			}		
//
//		}
//
//		if(!SAVE_OUTPUT_FOR_EACH_TRANSFORMER_SEPARATELY)
//		{
//			String fileName = "output/output.csv";
//			log.info("Saving output in file "+fileName);
//			Matching.matchingToCSV(wordsToResources,disambiguations,fileName);
//		}
//	}
//
//	public static void hack() throws Exception
//	{
//		log.debug("Starting hack method ");
//		Set<String> words = Matching.readWords(fnWortliste);
//		log.info("Wortschatz loaded: "+words.size()+ " words");
//		Collection<String> disambiguations; 
//		if(LOAD_DISAMBIGUATION_FILE)
//			disambiguations = Matching.readDisambiguations(fnDisambiguations);
//		else
//			disambiguations = Arrays.asList(new String[0]);
//
//		StringTransformerList[] transformers =
//		{
//								new StringTransformerList
//								(new MinimumLengthTransformer(3)),
//				
//								new StringTransformerList
//								(new MinimumLengthTransformer(3), new FirstCharLowerCaseStringTransformer())			
//
//				//				new StringTransformerList
//				//				(new RemoveSpecialCharactersTransformer(),new MinimumLengthTransformer(3), new LowerCaseStringTransformer(),new RemoveDisambiguationTransformer()),			
//
////				new StringTransformerList
////				(new RemoveAccentsAndToLowerCaseStringTransformer())//,new RemoveSpecialCharactersTransformer(),
////						//new MinimumLengthTransformer(3),new RemoveDisambiguationTransformer())			
//
//		};	
//		// Disambiguations have to be transformed too, because else they wouldnt match to the transformed resources
//		//disambiguations = new StringTransformerMatcher(transformers[0]).mapToTransformed(disambiguations).values();
//		Matching.disambiguationsFound=0;
//		match(words,disambiguations,transformers);
//		log.debug("Finished hack method");
//	}
//
//	public static void main(String[] args) throws Exception
//	{
//		long startTime = System.currentTimeMillis();
//		PropertyConfigurator.configure("log4j.properties");
//		log.trace("");
//		log.trace("*********************** start program ***********************");
//		hack();
//		long elapsedTime = System.currentTimeMillis()-startTime;
//		log.info("Time: "+elapsedTime/1000+"s");
//		log.trace("*********************** end program ***********************");
//	}
//
//}