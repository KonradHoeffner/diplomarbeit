package wortschatz2dbpedia.gui;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;



public class ViewMappingPanel extends JPanel {

	JTable table;
	JScrollPane scrollPane;
	String[] columnNames = {"Article","Word","Disambiguation"};
	
	private final int DEFAULT_MAX_ROWS = 50;
	
	private String[][] extractData(MultiMap<String,String> mapping,int maxRows)
	{
		int rowsAdded = 0;
		LinkedList<String[]> rows = new LinkedList<String[]>();
		for(String article: mapping.keySet())
		{
			for(String word: mapping.get(article))
			{ 
				rows.add(new String[] {article,word,""});
				rowsAdded++;
				if(rowsAdded>=maxRows) break;
			}
			if(rowsAdded>=maxRows) break;
		}
		//System.out.println(Arrays.toString(rows.get(0)));
		String[][] rowsAsArray = new String[rows.size()][];
		//System.out.println(rows.size());
		for(int i=0;i<rows.size();i++) rowsAsArray[i] = rows.get(i);
		return rowsAsArray;
//		String[][] s = new String[10][];
//		for(int i=0;i<10;i++) s[i] = new String[] {"a","b","c"};
//		return s;
//		Object[][] data =
//		{
//			    {"", "",""},
//		};
//		return data;
	}
	
	public void setMapping(MultiMap<String,String> mapping,int maxRows)
	{	
		this.remove(scrollPane);
		table = new JTable(extractData(mapping,maxRows),columnNames);
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
		this.add(scrollPane);
	}

	public void setMapping(MultiMap<String,String> mapping) {setMapping(mapping,DEFAULT_MAX_ROWS);}
	
	public ViewMappingPanel()
	{
		super();
		String[][] data =
		{
			    {"", "",""},
		};

		table = new JTable(data,columnNames);
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		this.setLayout(new GridLayout(1,1));
		this.add(scrollPane);
		MultiMap<String,String> test = new MultiHashMap<String,String>();
	}
}
