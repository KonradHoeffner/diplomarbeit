package wortschatz2dbpedia.analyse;

/**
 * Classifies whether both words are the same, only differing in case or neither
 *
 */
public class GeneralClassifier implements Classifier
{
	//private final String[] classes = {"Redirect or disambiguation","Exactly equal","Only differing in first character","Only differing in case","Otherwise different"}; 	
	private final String[] classes = {"","Exakt gleich","Groß- und Kleinschreibung","Weitere Unterschiede","Redirect","Disambiguierung"};
	
	@Override
	public int classify(SampleEntry entry)
	{
		String resource = entry.resource;
		String label = entry.label;

		if(new RedirectOrDisambiguationClassifier().classify(entry)==1) return 4;
		if(new RedirectOrDisambiguationClassifier().classify(entry)==2) return 5;

		if(resource.equals(label)) return 1;
		if(resource.toLowerCase().equals(label.toLowerCase())) return 2;		
		return 3;
	}

	@Override public String[] getClasses() {return classes;}

}