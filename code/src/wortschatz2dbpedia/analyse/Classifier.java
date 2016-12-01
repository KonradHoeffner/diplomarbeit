package wortschatz2dbpedia.analyse;

/**
 * Classifies a matching by returning the name of the matchings class.
 */
public interface Classifier
{
	public String[] getClasses();
	public int classify(SampleEntry entry); // class 0 will be excluded from further analysis  
}
