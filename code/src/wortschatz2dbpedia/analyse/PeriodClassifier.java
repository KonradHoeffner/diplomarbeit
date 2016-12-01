package wortschatz2dbpedia.analyse;

public class PeriodClassifier implements Classifier
{
	private final String[] classes =
	{"","different","equal","equal except for periods"}; 	

	@Override
	public int classify(SampleEntry e)
	{
		if(new RedirectOrDisambiguationClassifier().classify(e)!=4) return 0;
		String label= e.label.toLowerCase();
		String resource = e.resource.toLowerCase();
		
		if(resource.equals(label)) return 2;
		if  (resource.replaceAll("\\.", "").
				equals(label.replaceAll("\\.", ""))) return 3;
		return 1;
	}

	@Override public String[] getClasses() {return classes;}
}
