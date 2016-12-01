package wortschatz2dbpedia.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.collections15.MultiMap;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import wortschatz2dbpedia.match.Matching;

public class MatchingActionListener implements ActionListener
{
	MatchingFrame matchingFrame;

	private static final Logger log = Logger.getLogger(MatchingActionListener.class); 

	public MatchingActionListener(MatchingFrame matchingFrame) 
	{
		this.matchingFrame=matchingFrame;
		//PropertyConfigurator.configure("log4j.properties");
	}

	private void fileInputButtonPressed(int nr)
	{
		JFileChooser chooser;
		if(!matchingFrame.fileInputTextFields[nr].getText().equals(""))
		{
			int index = matchingFrame.fileInputTextFields[nr].getText().lastIndexOf(System.getProperty("file.separator"));
			if(index!=-1)
				chooser = new JFileChooser(matchingFrame.fileInputTextFields[nr].getText().substring(0,index));
			else chooser = new JFileChooser("input");
		}
		else chooser = new JFileChooser("input");
		
		int returnVal = chooser.showOpenDialog(matchingFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			//						System.out.println("You chose to open this file: " +
			//								chooser.getSelectedFile().getName());
			matchingFrame.fileInputTextFields[nr].setText(chooser.getSelectedFile().getPath());		
		}
	}
	private void actionButtonPressed(JButton button) throws Exception
	{
		String text = button.getText();

		if(text.equals("Check input"))			checkInput();
		if(text.equals("Match"))				matchingFrame.match();
		if(text.equals("Save Mapping"))			saveMapping();
		if(text.equals("Analyse"))				analyse();
		if(text.equals("Load Configuration"))	loadConfiguration();
		if(text.equals("Save Configuration"))	saveConfiguration();
	}

	private void checkInput()
	{
		//Matching matching = new Matching();
		JOptionPane.showMessageDialog(matchingFrame, "checkInput() not implemented");
	}

	private void saveMapping()
	{
		JOptionPane.showMessageDialog(matchingFrame, "saveMapping() not implemented");
	}


	private void analyse()
	{
		JOptionPane.showMessageDialog(matchingFrame, "analyse() not implemented");
	}

	private void loadConfiguration()
	{
		JFileChooser chooser = new JFileChooser("config");
		int returnVal = chooser.showOpenDialog(matchingFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION)
			try
		{
				matchingFrame.loadState(chooser.getSelectedFile());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void saveConfiguration()
	{
		JFileChooser chooser = new JFileChooser("config");
		int returnVal = chooser.showOpenDialog(matchingFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION)
			try
		{
				matchingFrame.saveState(chooser.getSelectedFile());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void addTransformerTab()
	{
		matchingFrame.addTransformerTab();
		//transformerPanel.transformerTabbedPane.add("Matching "+(transformerPanel.transformerTabbedPane.getComponentCount()+1),new JButton("test"));
	}

	private void removeTransformerTab()
	{
		matchingFrame.removeTransformerTab((SelectTransformersPanel)matchingFrame.transformerTabbedPane.getSelectedComponent());
		//transformerPanel.transformerTabbedPane.remove(transformerPanel.transformerTabbedPane.getSelectedComponent());
	}

	private void renameTransformerTab()
	{
		int index = matchingFrame.transformerTabbedPane.getSelectedIndex();
		String newName = (String)JOptionPane.showInputDialog(
				matchingFrame,
				"New name for the tab (and the matching which will be generated from it):",
				"Rename tab",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
		"");
		matchingFrame.transformerTabbedPane.setTitleAt(index,newName);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{			

		for(int i=0;i<MatchingFrame.fileInputLabelTexts.length;i++)
			if(event.getSource()==matchingFrame.fileInputButtons[i]) fileInputButtonPressed(i);

		for(int i=0;i<MatchingFrame.actionButtonTexts.length;i++)
			if(event.getSource()==matchingFrame.actionButtons[i])
				try
		{
					actionButtonPressed((JButton)event.getSource());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if(event.getSource()==matchingFrame.transformerAddTransformerTabButton) addTransformerTab();
		if(event.getSource()==matchingFrame.transformerRemoveTransformerTabButton) removeTransformerTab();
		if(event.getSource()==matchingFrame.transformerRenameTransformerTabButton) renameTransformerTab();
	}
}