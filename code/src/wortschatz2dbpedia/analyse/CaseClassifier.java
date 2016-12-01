package wortschatz2dbpedia.analyse;

/**
 * Classifies whether both words are the same, only differing in case or neither
 *
 */
public class CaseClassifier implements Classifier
{
	//private final String[] classes = {"Redirect or disambiguation","Exactly equal","Only differing in first character","Only differing in case","Otherwise different"}; 	
	private final String[] classes = {"Redirect oder Disambiguierung oder in nicht nur in Groß- und Kleinschreibung verschieden","Genau gleich","Nur im ersten Buchstaben verschieden ","Andere Buchstaben verschieden"};
	
	@Override
	public int classify(SampleEntry entry)
	{
		String resource = entry.resource;
		String label = entry.label;
		String remark = entry.remark;
		if(new RedirectOrDisambiguationClassifier().classify(entry)!=3) return 0;
		//if(remark.contains("redirect")||remark.contains("fehldisambiguierung")) return 0;
		//System.out.println(resource+" "+label+" "+remark);
		if(resource.equals(label)) return 1;
		String resourceFirstChar = resource.subSequence(0, 1).toString().toLowerCase();
		String labelFirstChar = resource.subSequence(0, 1).toString().toLowerCase();
		
		if((resourceFirstChar+resource.substring(1)).equals((labelFirstChar+label.substring(1)))) return 2;
		if(resource.toLowerCase().equals(label.toLowerCase())) return 3;		
		return 0;
	}

	@Override public String[] getClasses() {return classes;}

}