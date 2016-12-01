package wortschatz2dbpedia.analyse;

/**
 * Classifies whether both words are the same, only differing in case or neither
 *
 */
public class RedirectOrDisambiguationClassifier implements Classifier
{
	private final String[] classes = {"","Redirect","Disambiguation","Rest"}; 	

	@Override
	public int classify(SampleEntry entry)
	{	
		if(entry.remark.contains("redirect")) return 1;
		if(entry.remark.contains("fehldisambiguierung")) return 2;
		return 3;
	}

	@Override public String[] getClasses() {return classes;}

}