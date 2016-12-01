package wortschatz2dbpedia.gui;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.commons.collections15.MultiMap;
import org.apache.log4j.Logger;
import org.ini4j.Ini;

import wortschatz2dbpedia.match.FirstCharLowerCaseStringTransformer;
import wortschatz2dbpedia.match.Matching;
import wortschatz2dbpedia.match.MaximumLengthTransformer;
import wortschatz2dbpedia.match.MinimumLengthTransformer;
import wortschatz2dbpedia.match.RemoveAccentsAndToLowerCaseStringTransformer;
import wortschatz2dbpedia.match.RemoveDisambiguationTransformer;
import wortschatz2dbpedia.match.RemoveEverythingInParenthesesTransformer;
import wortschatz2dbpedia.match.RemoveSpecialCharactersTransformer;
import wortschatz2dbpedia.match.SingleWordTransformer;
import wortschatz2dbpedia.match.StringTransformer;
import wortschatz2dbpedia.match.StringTransformerList;
import wortschatz2dbpedia.util.StopWatch;

public class MatchingFrame extends JFrame
{
	final static String TITLE = "wortschatz2dbpedia";
	final static String LAST_CONFIGURATION_FILENAME = "config/last.ini"; 
	private static final Logger log = Logger.getLogger(MatchingFrame.class); 

	// Progress Bar
	final JProgressBar progressBar = new JProgressBar();
	final int APPROX_LINES = 7332625;
	
	// Tabbed Pane
	final JTabbedPane tabbedPane = new JTabbedPane();

	List<ViewMappingPanel> viewMappingPanels = new LinkedList<ViewMappingPanel>();
	// file input
	final static String[] fileInputLabelTexts = 
	{"Wortschatz word list", "DBPedia titles","DBPedia disambiguations","DBPedia redirects","DBPedia abstracts"};

	JTextField fileInputWortschatzWordListTextField = new JTextField();
	JTextField fileInputDBPediaTitlesTextField = new JTextField();
	JTextField fileInputDBPediaDisambiguationsTextField = new JTextField();
	JTextField fileInputDBPediaRedirectsTextField = new JTextField();
	JTextField fileInputDBPediaAbstractsTextField = new JTextField();

	final JTextField fileInputTextFields[] =
	{
			fileInputWortschatzWordListTextField,
			fileInputDBPediaTitlesTextField,
			fileInputDBPediaDisambiguationsTextField,
			fileInputDBPediaRedirectsTextField,
			fileInputDBPediaAbstractsTextField
	};
	
	final JLabel[] fileInputLabels = new JLabel[fileInputLabelTexts.length];
	final JButton fileInputButtons[] = new JButton[fileInputLabelTexts.length];
	
	//new JTextField[fileInputLabelTexts.length];

	// transformers
	final JTabbedPane transformerTabbedPane = new JTabbedPane();
	final List<StringTransformer> allTransformers = new StringTransformerList(new MinimumLengthTransformer(3),new FirstCharLowerCaseStringTransformer(), new RemoveAccentsAndToLowerCaseStringTransformer(),new RemoveSpecialCharactersTransformer(),
			new RemoveEverythingInParenthesesTransformer(), 
			new RemoveDisambiguationTransformer(),new MaximumLengthTransformer(10),new SingleWordTransformer()).stringTransformers;

	final List<SelectTransformersPanel>  transformerTabs = new LinkedList<SelectTransformersPanel>();
	
	//private final Map<String,StringTransformer> transformerNameToTransformer = new HashMap<String,StringTransformer>();
	JButton transformerAddTransformerTabButton = new JButton("Add Transformer Tab");
	JButton transformerRemoveTransformerTabButton = new JButton("Remove Transformer Tab");
	JButton transformerRenameTransformerTabButton = new JButton("Rename Transformer Tab");

	// options
	final static String[] optionTexts =
	{"use and follow redirects","safe output seperately for each transformerlist","match words only once",
		"validate with abstracts","follow disambiguations","add new matches only for every one after the first mapping"};

	JCheckBox optionDoUseRedirectsCheckBox = new JCheckBox();
	JCheckBox optionDoSaveOutputSeparatelyCheckBox = new JCheckBox();
	JCheckBox optionDoMatchWordsOnlyOnceCheckBox = new JCheckBox();
	JCheckBox optionDoValidateWithAbstractsCheckBox = new JCheckBox();
	JCheckBox optionDoFollowDisambiguationsCheckBox = new JCheckBox();
	JCheckBox optionDoAddNewMatchesOnlyCheckBox = new JCheckBox();

	final JCheckBox[] optionCheckBoxes =
	{
			optionDoUseRedirectsCheckBox,
			optionDoSaveOutputSeparatelyCheckBox,
			optionDoMatchWordsOnlyOnceCheckBox,
			optionDoValidateWithAbstractsCheckBox,
			optionDoFollowDisambiguationsCheckBox,
			optionDoAddNewMatchesOnlyCheckBox
	};
	
	//new JCheckBox[optionTexts.length];
	// action buttons
	final static String[] actionButtonTexts = {"Check input","Match",/*"Save Mapping,"*/"Analyse","Load Configuration","Save Configuration"};
	JButton[] actionButtons = new JButton[actionButtonTexts.length]; 

	void saveState(final File file) throws IOException
	{
		final Ini ini = new Ini();
		for(int i=0;i<fileInputTextFields.length;i++)
			ini.add("file input",fileInputLabelTexts[i],fileInputTextFields[i].getText());
		for(int i=0;i<optionCheckBoxes.length;i++)
			ini.add("options",optionTexts[i],optionCheckBoxes[i].isSelected());

		ini.store(file);
	}

	void loadState(final File file) throws IOException
	{
		final Ini ini = new Ini(file);
		for(int i=0;i<fileInputTextFields.length;i++)
			//System.out.println(i+ini.get("file input",fileInputLabelTexts[i],String.class));
			fileInputTextFields[i].setText(ini.get("file input",fileInputLabelTexts[i],String.class));		
		for(int i=0;i<optionCheckBoxes.length;i++)
			optionCheckBoxes[i].setSelected(ini.get("options",optionTexts[i],boolean.class));
		//this.resize(this.getWidth()+1, this.getHeight()+1);
		//this.resize(this.getWidth()-1, this.getHeight()-1);
	}

	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		createAndShowGUI();
		final String[]	fileNames			= (String[]) in.readObject();
		for(int i=0;i<=fileNames.length;i++) fileInputTextFields[i].setText(fileNames[i]);

		final boolean[]	checkBoxValues		= (boolean[]) in.readObject();
		for(int i=0;i<=checkBoxValues.length;i++) optionCheckBoxes[i].setEnabled(checkBoxValues[i]);
	}

	void match() throws Exception
	{
		StopWatch stopWatchWholeMatching = new StopWatch();
		stopWatchWholeMatching.start();
		//List<MultiMap<String,String>> mappings = new LinkedList<MultiMap<String,String>>();
		String dateString = new Date().toString();
		for(SelectTransformersPanel transformerTab : this.transformerTabs)
		{
			int index = this.transformerTabs.indexOf(transformerTab);
			String tabTitle = this.transformerTabbedPane.getTitleAt(index);
			log.info("Matching "+tabTitle+"...");
			Matching matching = new Matching(
					this.fileInputWortschatzWordListTextField.getText(),
					this.fileInputDBPediaTitlesTextField.getText(),
					this.fileInputDBPediaDisambiguationsTextField.getText(),
					this.fileInputDBPediaRedirectsTextField.getText(),
					this.fileInputDBPediaAbstractsTextField.getText(),
					this.optionDoUseRedirectsCheckBox.isSelected(),
					this.optionDoSaveOutputSeparatelyCheckBox.isSelected(),
					this.optionDoMatchWordsOnlyOnceCheckBox.isSelected(),
					this.optionDoValidateWithAbstractsCheckBox.isSelected(),
					this.optionDoFollowDisambiguationsCheckBox.isSelected(),
					transformerTab.getSelectedTransformers()
			);
			StopWatch stopWatchSingleMatching = new StopWatch();
			stopWatchSingleMatching.start();
			//MultiMap<String,String> mapping = matching.match(); 
			new  File("output/mapping/"+dateString).mkdir();
			String filename = "output/mapping/"+dateString+"/"+tabTitle;
			matching.match(filename,progressBar,APPROX_LINES);
			
			stopWatchSingleMatching.stop();
			log.info("... finished in "+stopWatchSingleMatching);
			//mappings.add(mapping);
		}

//		// remove all entrys from mapping i+x, who are also contained in mapping i
//		if(this.optionDoAddNewMatchesOnlyCheckBox.isEnabled()&&mappings.size()>1)
//		{
//			StopWatch stopWatchRemoveDuplicates = new StopWatch();
//			stopWatchRemoveDuplicates.start();
//			log.info("Removing duplicate matches from subsequent mappings...");
//			// unfortunately, multimap does not support a removeAll option. the current implementation may be slow for big mappings.
//			int i;
//			for(i=0;i<mappings.size()-1;i++)
//				for(int j=i+1;j<mappings.size();j++)
//					for(String key: mappings.get(i).keySet())
//						for(String value: mappings.get(i).get(key))
//					mappings.get(j).remove(key, value);
//			stopWatchRemoveDuplicates.stop();
//			log.info("... finished in "+stopWatchRemoveDuplicates);
//		}
		
		clearMappingPanels();
		
//		//save and show the mappings
//		log.info("Save and show the mappings");
//		for(int i=0;i<transformerTabs.size();i++)
//		{
//			String tabTitle = this.transformerTabbedPane.getTitleAt(i);
//			MultiMap<String,String> mapping = mappings.get(i);
//			
//			addViewMappingPanel(tabTitle, mapping);
//			
//			String filename = "output/mapping/"+tabTitle+".csv";
//			if(new File(filename).exists()&&!tabTitle.equals("New Mapping")) throw new RuntimeException("File \""+filename+"\" already exists.");
//			Matching.matchingToCSV(mapping, filename);
//		}
//		//for(MultiMap<String,String> mapping:mappings)
//		//JOptionPane.showMessageDialog(matchingFrame, "match() not implemented");
//		stopWatchWholeMatching.stop();
//		log.info("The whole matching took "+stopWatchWholeMatching);
	}

	//		public void saveInputValuesToFile(String fileName) throws IOException
	//		{
	//			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
	//			out.writeObject(fileInputTextFields);
	//			out.writeObject(optionCheckBoxes);
	//			out.close();
	//		}
	//	
	//		public void loadInputValuesFromFile(String fileName) throws IOException, ClassNotFoundException
	//		{
	//			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
	//			fileInputTextFields = (JTextField[]) in.readObject();
	//			optionCheckBoxes = (JCheckBox[]) in.readObject();
	//			in.close();
	//		}	

	public MatchingFrame() throws IOException, ClassNotFoundException
	{
		createAndShowGUI();
	}

	public JPanel createViewMappingPanel()
	{
		return null;
	}

	private void clearMappingPanels()
	{
		for(ViewMappingPanel viewMappingPanel: viewMappingPanels)
		{
			tabbedPane.remove(viewMappingPanel);
		}
		viewMappingPanels.clear();
	}
	
	private void addViewMappingPanel(String title, MultiMap<String,String> mapping)
	{
		ViewMappingPanel viewMappingPanel = new ViewMappingPanel();
		tabbedPane.add(title,viewMappingPanel);
		viewMappingPanel.setMapping(mapping);
		viewMappingPanels.add(viewMappingPanel);
	}
	
	public void createAndShowGUI()
	{
		this.setTitle(TITLE);

		final ActionListener listener = new MatchingActionListener(this);

		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 
		  
		//Container pane = this.getContentPane();
		// *************** JTabbedPane ********************
		this.getContentPane().add(tabbedPane);
		// ***************MAIN PANEL **************************
		final JPanel mainPanel = new JPanel();

		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
		tabbedPane.add("Main",mainPanel);
		//this.getContentPane().add(panel);
		// ***************VIEW MAPPING PANEL **************************
		
		//mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
//		viewMappingPanel = new ViewMappingPanel();
//		tabbedPane.add("View Mapping",viewMappingPanel);

		// *************** FILE INPUT *********************
		// ** panel **
		final JPanel fileInputPanel = new JPanel();
		GridBagLayout fileInputLayout = new GridBagLayout();
		fileInputPanel.setLayout(fileInputLayout);
		mainPanel.add(fileInputPanel);
		//this.getContentPane().add(fileInputPanel);
		// ** labels **
		for(int i=0;i<fileInputLabelTexts.length;i++) fileInputLabels[i] = new JLabel(fileInputLabelTexts[i]);
		// ** text fields **

		// ** buttons **
		// Add to fileInputPanel
		for(int i=0;i<fileInputLabelTexts.length;i++)
		{
			//fileInputTextFields[i] = new JTextField();
			fileInputButtons[i] = new JButton("Browse");		
			fileInputButtons[i].addActionListener(listener);

			//fileInputPanel.add(fileInputLabels[i]);
			//fileInputPanel.add(fileInputTextFields[i]);
			//fileInputPanel.add(fileInputButtons[i]);
			//                   													x  y  w  h   wx  wy	  fill
			SwingHelper.addComponent( fileInputPanel, fileInputLayout, fileInputLabels[i],		0, i, 1, 1, 0.0, 0.0, GridBagConstraints.NONE);
			SwingHelper.addComponent( fileInputPanel, fileInputLayout, fileInputTextFields[i],	1, i, 1, 1, 1.0, 1.0, GridBagConstraints.HORIZONTAL);
			SwingHelper.addComponent( fileInputPanel, fileInputLayout, fileInputButtons[i],		2, i, 1, 1, 0.0, 0.0, GridBagConstraints.NONE);

		}
		// *************** TRANSFORMERS *******************
		JPanel transformerPanel = new JPanel();
		transformerPanel.setLayout(new BoxLayout(transformerPanel,BoxLayout.PAGE_AXIS));
		mainPanel.add(transformerPanel);
		
		transformerPanel.add(transformerTabbedPane);

		SelectTransformersPanel transformerTab = new SelectTransformersPanel(allTransformers);
		transformerTabs.add(transformerTab);

		transformerTabbedPane.add("New Mapping",transformerTab);
		transformerPanel.add(transformerAddTransformerTabButton);
		transformerPanel.add(transformerRemoveTransformerTabButton);
		transformerPanel.add(transformerRenameTransformerTabButton);
		transformerAddTransformerTabButton.addActionListener(listener);
		transformerRemoveTransformerTabButton.addActionListener(listener);
		transformerRenameTransformerTabButton.addActionListener(listener);
		//JPanel transformerTab = new JPanel();
//		//transformerTabbedPane.setN
//		
//		transformerTabbedPane.add("Mapping 1",transformerTab);
//
//		//                   																		x  y  w  h   wx  wy	  fill
//		transformerAddButton.addActionListener(listener);
//		transformerRemoveButton.addActionListener(listener);
//		transformerListDeselected = new StringTransformerList(new MinimumLengthTransformer(3),new FirstCharLowerCaseStringTransformer(), new RemoveAccentsAndToLowerCaseStringTransformer(),new RemoveSpecialCharactersTransformer(),
//				new RemoveDisambiguationTransformer(),new MaximumLengthTransformer(2));

		// *************** OPTIONS ************************
		// ** panel **
		final JPanel optionPanel = new JPanel();
		final GridBagLayout optionsLayout = new GridBagLayout();
		optionPanel.setLayout(optionsLayout);
		mainPanel.add(optionPanel);

		final JLabel[] optionLabels			= new JLabel[optionTexts.length];		

		for(int i=0;i<optionTexts.length;i++)
		{
			optionLabels[i] = new JLabel(optionTexts[i]);
			// if not already loaded
			if(optionCheckBoxes[i]==null) 
				optionCheckBoxes[i] = new JCheckBox();
			//                   											x y w h wx       wy
			SwingHelper.addComponent( optionPanel, optionsLayout, optionLabels[i],		0, i, 1, 1, 0.0, 0.0 );
			SwingHelper.addComponent( optionPanel, optionsLayout, optionCheckBoxes[i],	1, i, 1, 1, 0.0, 0.0 );

			//optionPanel.add(optionLabels[i]);
			//optionPanel.add(optionCheckBoxes[i]);
		}
		// *************** BUTTONS ************************
		final JPanel actionButtonPanel = new JPanel();
		//final GridBagLayout actionButtonLayout = new GridBagLayout();
		final FlowLayout actionButtonLayout = new FlowLayout();
		actionButtonPanel.setLayout(actionButtonLayout);
		mainPanel.add(actionButtonPanel);

		for(int i=0;i<actionButtonTexts.length;i++)
		{
			actionButtons[i]=new JButton(actionButtonTexts[i]);
			actionButtons[i].addActionListener(listener);
			actionButtonPanel.add(actionButtons[i]);

		}
		//buttonCreateMapping.setSize(200,100);
		//buttonSaveMapping.setSize(200,100);

		// *************** PROGRESS BAR ************************
		progressBar.setStringPainted(true);
		mainPanel.add(progressBar);

		// Save values
		//saveInputValuesToFile(INPUT_VALUE_CACHE);

		// Save state on close
		this.addWindowListener(
				new WindowListener() {

					@Override public void windowClosing(final WindowEvent arg0)
					{
						try
						{
							saveState(new File(LAST_CONFIGURATION_FILENAME));
						} catch (final IOException e)
						{
							e.printStackTrace();
						}
					}
					@Override public void windowClosed(final WindowEvent arg0){}
					@Override public void windowDeactivated(final WindowEvent arg0){}
					@Override public void windowDeiconified(final WindowEvent arg0){}
					@Override public void windowIconified(final WindowEvent arg0){}
					@Override public void windowOpened(final WindowEvent arg0){}
					@Override public void windowActivated(final WindowEvent e){}
				});
		this.setSize( 1200, 1000 );
		this.setVisible( true );
	}

	public static void main( final String[] args ) throws IOException, ClassNotFoundException 
	{
		//PropertyConfigurator.configure("log4j.properties");
		final MatchingFrame matching = new MatchingFrame();
		matching.setLocation(0, 50);
		if(new File(LAST_CONFIGURATION_FILENAME).exists())
			matching.loadState(new File(LAST_CONFIGURATION_FILENAME));

		//createAndShowGUI();
	}

	public void addTransformerTab()
	{
		SelectTransformersPanel tab = new SelectTransformersPanel(allTransformers);
		this.transformerTabs.add(tab);
		this.transformerTabbedPane.add("New Mapping",tab);
	}

	public void removeTransformerTab(SelectTransformersPanel tab)
	{
		this.transformerTabs.remove(tab);
		this.transformerTabbedPane.remove(tab);
	}

}