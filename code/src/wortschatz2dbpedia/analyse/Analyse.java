package wortschatz2dbpedia.analyse;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.xmlgraphics.java2d.ps.EPSDocumentGraphics2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Liest eine ausgewertete Stichprobe ein und analysiert diese.
 * Unterteilt die Stichprobe mit Hilfe eines übergebenen Klassifizierers und bestimmt
 * die Häufigkeit und die Fehlerrate jeder Klasse. Die Ergebnisse werden auf folgende Art und Weise ausgegeben:
 * - Anzeige als Torten- (Vorkommen jeder Klasse ) und Balkendiagramm (Fehlerrate)
 * - Speichern der Diagramme im Format encapsulated postscript (eps)
 * - Speichern als Tabelle im Format comma separated value (csv) mit einem Tabulator als Trennzeichen
 *    
 *  */
public class Analyse
{
	static final String fileName = "analyse/mapping1/stichprobe_ausgewertet_urldecoded_without_disambiguations_and_redirects.csv";
	//static final String fileName = "analyse/mapping1/stichprobe_ausgewertet_urldecoded.csv";	

	//	private static Map<SampleEntry,String> mapSampleEntryToClass(SampleEntry[] sample, Classifier classifier)
	//	{
	//		Map<SampleEntry,String> sampleEntryToClass = new HashMap<SampleEntry,String>();
	//		for(SampleEntry entry:sample)
	//			sampleEntryToClass.put(entry,classifier.classify(entry.resource, entry.label));
	//		
	//		return sampleEntryToClass;
	//	}	

	//	private static Map<String,Integer> mapClassToGoodMatches(Map<SampleEntry,String> sampleEntryToClass)
	//	{
	//		Map<String,Integer> classToGoodMatches = new HashMap<String,Integer>();
	//		
	//	}	

	//	private static Map<String,Integer> countClassOccurrences(SampleEntry[] sample, Classifier classifier)
	//	{
	//		Map<String,Integer> occurrences = new HashMap<String,Integer>();
	//		for(SampleEntry entry:sample)
	//		{
	//			String classifiedAs = classifier.classify(entry.resource, entry.label);
	//			//if(occurrences.get(classifiedAs)!=null) occurrences.get(classifiedAs) 
	//			occurrences.put(classifiedAs,occurrences.get(classifiedAs)==null?1:occurrences.get(classifiedAs)+1);
	//		}
	//		return occurrences;
	//	}	
	//	
	//	private static Map<String,Integer[]> classOccurrenceAndGoodness(SampleEntry[] sample, Classifier classifier)
	//	{
	//		Map<String,Integer[]> statistics = new HashMap<String,Integer[]>();
	//		for(String className:classifier.getClasses())
	//			statistics.put(className, new Integer[2]);
	//		for(SampleEntry entry:sample)
	//		{
	//			String classifiedAs = classifier.classify(entry.resource, entry.label);
	//			//if(occurrences.get(classifiedAs)!=null) occurrences.get(classifiedAs) 
	//			occurrences.put(classifiedAs,occurrences.get(classifiedAs)==null?1:occurrences.get(classifiedAs)+1);
	//		}
	//		return occurrences;
	//	}		

	private static ClassStatistics[] classStatistics(SampleEntry[] sample, Classifier classifier)
	{
		String[] classes = classifier.getClasses();
		ClassStatistics[] classStatistics = new ClassStatistics[classes.length];		
		for(int i=0;i<classStatistics.length;i++) classStatistics[i] = new ClassStatistics(classes[i],0,0);
		for(SampleEntry entry:sample)
		{
			// das Wort "class" ist leider reserviert, bessere Vorschläge als "classifiedAs"?
			int classifiedAs = classifier.classify(entry);
			classStatistics[classifiedAs].occurrences++;
			//System.out.println(entry.correctness);
			if(entry.correctness) classStatistics[classifiedAs].goodMatches++;
		}
		// remove element zero
		classStatistics = Arrays.copyOfRange(classStatistics, 1, classStatistics.length);
		return classStatistics;
	}

	private static void classStatisticsToCSV(ClassStatistics[] classStatistics,String fileName) throws FileNotFoundException
	{
		PrintStream outCSV = new PrintStream(fileName);
		for(ClassStatistics statistics: classStatistics)
			outCSV.println(	statistics.className+"\t"+statistics.occurrences+"\t"
					+statistics.goodMatches+"\t"+statistics.goodMatches*1.0/statistics.occurrences+"\t");
		outCSV.close();
	}
	
	private static void detailedClassStatisticsToCSV(SampleEntry[] sample, Classifier classifier,String fileName) throws FileNotFoundException
	{
		PrintStream outCSV = new PrintStream(fileName);
		List<List<SampleEntry>> classes = new LinkedList<List<SampleEntry>>();//[];
		// initialize
		for(int i=0;i<classifier.getClasses().length;i++)
			classes.add(new LinkedList<SampleEntry>());
		// order
		for(int i=0;i<sample.length;i++)
			classes.get(classifier.classify(sample[i])).add(sample[i]);
		// write
		for(int i=0;i<classifier.getClasses().length;i++)
		{
			outCSV.println("The following pairs belong to class "+i+": "+classifier.getClasses()[i]+" (order: resource word)");
			for(SampleEntry entry:classes.get(i))
				outCSV.println(entry.resource+"\t"+entry.label);
		}		
		outCSV.close();
	}
	

	
//	private static void chartToEPS(JFreeChart chart,String fileName,int width,int height) throws IOException
//	{
//		FileOutputStream finalImage = new FileOutputStream(new File(fileName));
//		EpsGraphics2D g = new EpsGraphics2D(chart.getTitle().toString(), finalImage, 0, 0, width, height);
//		chart.draw(g, new Rectangle(width,height));
//		g.flush();
//		g.close();
//		finalImage.close();
//	}

	private static void chartToEPS(JFreeChart chart,String fileName,int width,int height) throws IOException
	{
		FileOutputStream finalImage = new FileOutputStream(new File(fileName));
		EPSDocumentGraphics2D g = new EPSDocumentGraphics2D(false);
		g.setGraphicContext(new org.apache.xmlgraphics.java2d.GraphicContext());

		//Set up the document size
		g.setupDocument(finalImage, width, height); //400pt x 200pt
		//out is the OutputStream to write the EPS to
		chart.draw(g, new Rectangle(width,height));
		          		          
		g.finish(); //Wrap up and finalize the EPS file

		finalImage.close();
	}


	private static void classStatisticsToLatex(ClassStatistics[] classStatistics,String fileName) throws FileNotFoundException
	{
		PrintStream outLatex = new PrintStream(fileName);
		//\begin{center}
		//\begin{tabular}{ll}
		//Fall	&Vorkommen absolut	&Vorkommen relativ	&korrekt
		//case irgendwas & anzahl	&	anteil	& genauigkeit\\
		//...
		//\end{tabular}
		//\end{center}
		outLatex.println("\\begin{center}");
		outLatex.println("\begin{tabular}{lr}");

		for(ClassStatistics statistics: classStatistics)
			outLatex.println(
					statistics.className+"\t&"
					+statistics.occurrences+"\t&"
					+statistics.goodMatches+"\t"+statistics.goodMatches*1.0/statistics.occurrences+"\t");
		outLatex.println("\\end{tabular}");
		outLatex.println("\\end{center}");
		outLatex.close();
	}
	private static void createAndShowGUI(JFreeChart chart,int width, int height) {createAndShowGUI(chart,width,height,0);}
	
	private static void createAndShowGUI(JFreeChart chart,int width, int height, int position) {
		//Create and set up the window.
		final int borderX = 20;
		final int borderY = 50;
		JFrame frame;
		frame = new JFrame("HelloWorldSwing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		BufferedImage image = chart.createBufferedImage(800, 600);
//
		Rectangle r = new Rectangle(width,height);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = image.createGraphics();
		chart.draw(graphics, r);

		JLabel imageLabel = new JLabel();
		imageLabel.setIcon(new ImageIcon(image));
		frame.getContentPane().add(imageLabel);
		
		//Display the window.
		frame.pack();
		frame.setVisible(true);
		frame.setSize(width+borderX, height+borderY);
		frame.setLocation(position%2*width,(position/2)*height+50);
	}

	private static JFreeChart createPieChart(String title,ClassStatistics[] classStatistics)
	{
		DefaultPieDataset pieDataSet = new DefaultPieDataset();
		for(ClassStatistics statistics : classStatistics)
			pieDataSet.setValue(statistics.className, statistics.occurrences);
		JFreeChart chart = ChartFactory.createPieChart(title, pieDataSet, false,false,false);
		JFreeChartHelper.setLatexThesisPieChartStyle(chart);
		return chart;
	}
	
//	private static void createAndShowPieChart(String title,ClassStatistics[] classStatistics)
//	{
//		createAndShowGUI(chart);
//	}

	private static JFreeChart createBarChart(String title,ClassStatistics[] classStatistics)
	{
		DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();
		for(ClassStatistics statistics : classStatistics)
			barDataSet.setValue(statistics.goodMatchesPercentage(),statistics.className,"");
		//DefaultBarDataset barDataSet = new DefaultPieDataset();
		//		for(ClassStatistics statistics : classStatistics)
		//			pieDataSet.setValue(statistics.className, statistics.occurrences);
		JFreeChart chart = ChartFactory.createBarChart
		(title, "", "percentage of good matches", barDataSet, PlotOrientation.VERTICAL, true, true, true);
		JFreeChartHelper.setLatexThesisPercentageBarChartStyle(chart);
		 //BarChartProperties b;
		//ChartFactory.createVerticalBarChart3D();
		//JFreeChart chart = ChartFactory.createVerticalBarChart(title, barDataSet, true,true,true);

		return chart;
	}

	public static void main(String[] args) throws IOException
	{
		final int epsWidth = 590;
		final int epsHeight = 300;
		//BarRenderer.setDefaultBarPainter(new StandardBarPainter());
		//BarRenderer.setDefaultBarPainter(new StandardBarPainter());

		SampleEntry[] sample = SampleEntry.readFromCSV(fileName);
		//System.out.println(Arrays.toString(sample));
		//Map<String,Integer> caseOccurrences = countClassOccurrences(sample,new CaseClassifier());

		Classifier[] classifiers = {new SpecialCharactersClassifier()};//new CaseClassifier(),new SpecialCharactersClassifier()};
		for(Classifier classifier: classifiers)
		{
			ClassStatistics[] classStatistics = classStatistics(sample,classifier);
			JFreeChart pieChart = createPieChart(classifier.getClass().toString().split(" ")[1],classStatistics);
			JFreeChart barChart = createBarChart(classifier.getClass().toString().split(" ")[1],classStatistics);
			createAndShowGUI(pieChart,epsWidth,epsHeight,0);
			createAndShowGUI(barChart,epsWidth,epsHeight,1);
			//System.out.println(Arrays.toString(classStatistics));
			classStatisticsToCSV(classStatistics,"analyse/mapping1/stichprobe1/"+classifier.getClass().toString().split(" ")[1]+".csv");
			detailedClassStatisticsToCSV(sample,classifier,"analyse/mapping1/stichprobe1/"+classifier.getClass().toString().split(" ")[1]+"_detailed.csv");
			chartToEPS(pieChart,"analyse/mapping1/stichprobe1/"+classifier.getClass().toString().split(" ")[1]+".piechart.eps",epsWidth,epsHeight);
			chartToEPS(barChart,"analyse/mapping1/stichprobe1/"+classifier.getClass().toString().split(" ")[1]+".barchart.eps",epsWidth,epsHeight);			
			//System.out.println(classifier.getClass().toString());

		}

	}
}