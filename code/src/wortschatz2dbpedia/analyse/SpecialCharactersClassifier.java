package wortschatz2dbpedia.analyse;

import wortschatz2dbpedia.match.RemoveAccentsAndToLowerCaseStringTransformer;
import wortschatz2dbpedia.match.URLDecodeStringTransformer;

/**
 * Classifies whether both words are the same, only differing in case or neither
 *
 */
public class SpecialCharactersClassifier implements Classifier
{
	private final String[] classes =
	{"redirect or wrong disambiguation or no special characters","Akzente und Umlaute","Striche","Punkte","Mehrere davon","Anderes"}; 	

	@Override
	public int classify(SampleEntry entry)
	{
		String resource = entry.resource;
		String label = entry.label;
		//String remark = entry.remark;
		
		resource	= resource.toLowerCase();
		label		= label.toLowerCase();
		if(new RedirectOrDisambiguationClassifier().classify(entry)!=3) return 0;
		//if(remark.contains("redirect")||remark.contains("fehldisambiguierung")) return 4;
		
		if(resource.equals(label)) return 0;

		if(new RemoveAccentsAndToLowerCaseStringTransformer().transform(resource).equals
				(new RemoveAccentsAndToLowerCaseStringTransformer().transform(label)))
			return 1;
		
		//System.out.println(resource+", "+label);
		if(resource.replaceAll("-", "").equals(label.replaceAll("-", ""))) return 2;
		if(resource.replaceAll("\\.", "").equals(label.replaceAll("\\.", ""))) return 3;		
		
		if(new RemoveAccentsAndToLowerCaseStringTransformer().transform(resource.replaceAll("\\.|-", ""))
				.equals(new RemoveAccentsAndToLowerCaseStringTransformer().transform(label.replaceAll("\\.|-", "")))) return 4;
		//System.out.println(resource+" "+label);
		return 5;
	}

	@Override public String[] getClasses() {return classes;}

}