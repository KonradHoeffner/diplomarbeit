package wortschatz2dbpedia.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// TODO: rewrite so that multiple values can be returned for each surface form
public class Mapping
{
	//static final String filename = "output/mapping/combined_mapping_sorted_conglomerated.csv";
	static final String filename = "output/mapping/redirects_disabiguations_identity_sorted_conglomerated.csv";

	final BinarySearchTextFile binSearch;
	//	public MultiMap<String,String> articleToSurfaceForm = new MultiHashMap<String,String>();
	//	public MultiMap<String,String> surfaceFormToArticle =  new MultiHashMap<String,String>();

	public Collection<String> getArticles(String surfaceForm)
	{
		try
		{
			String found = binSearch.get(surfaceForm, true);//surfaceForm
			//System.out.println(found);
			if(found==null) return Collections.emptyList();
			String[] tokens = found.split("\t");
			Set<String> values = new HashSet<String>();
			for(int i=1;i<tokens.length;i++)
				values.add(tokens[i]);
			return values;
		}
		catch (IOException e) {return null;}
	}


	public Mapping()
	{
		try
		{
			binSearch = new BinarySearchTextFile(this.filename);
		} catch (IOException e){throw new RuntimeException(e);}
		//try{load(filename);} catch (Exception e) {e.printStackTrace();}
	}

	public Mapping(String filename) throws IOException
	{
		binSearch = new BinarySearchTextFile(filename);
		//load(filename);
	}

	//	private void load(String filename) throws IOException
	//	{
	//		BufferedReader in = new BufferedReader(new FileReader(filename));
	//		String line;
	//		while((line=in.readLine())!=null)
	//		{
	//			String[] tokens = line.split("\t"); 
	//			String article = tokens[0];
	//			String surfaceForm = tokens[1];
	////			articleToSurfaceForm.put(article, surfaceForm);
	////			surfaceFormToArticle.put(surfaceForm, article);
	//		}
	//		in.close();		
	//	}
}