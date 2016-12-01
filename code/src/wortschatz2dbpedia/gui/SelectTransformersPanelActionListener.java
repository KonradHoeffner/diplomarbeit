package wortschatz2dbpedia.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import wortschatz2dbpedia.match.StringTransformer;

public class SelectTransformersPanelActionListener implements ActionListener
{
	private SelectTransformersPanel transformerPanel;

	private SelectTransformersPanelActionListener(){};
	public SelectTransformersPanelActionListener(SelectTransformersPanel transformerPanel) {this.transformerPanel=transformerPanel;}

	private void infoAboutTransformer()
	{
		for(int index: transformerPanel.transformerDeselectedJList.getSelectedIndices())
			JOptionPane.showMessageDialog
			(
					transformerPanel, 
					transformerPanel.transformerListDeselected.stringTransformers.get(index).getDescription()
			);
		for(int index: transformerPanel.transformerSelectedJList.getSelectedIndices())
			JOptionPane.showMessageDialog
			(
					transformerPanel, 
					transformerPanel.transformerListDeselected.stringTransformers.get(index).getDescription()
			);
	}
	
	private void addTransformer()
	{
		List<StringTransformer> transformers = new LinkedList<StringTransformer>();
		// do it in two steps because otherwise with multiple indices the indices would become wrong
		for(int index: transformerPanel.transformerDeselectedJList.getSelectedIndices())
			transformers.add(transformerPanel.transformerListDeselected.stringTransformers.get(index));
		for(StringTransformer transformer:transformers)
			transformerPanel.addTransformer(transformer);
	}

	private void removeTransformer()
	{
		List<StringTransformer> transformers = new LinkedList<StringTransformer>();
		// do it in two steps because otherwise with multiple indices the indices would become wrong
		for(int index: transformerPanel.transformerSelectedJList.getSelectedIndices())
			transformers.add(transformerPanel.transformerListSelected.stringTransformers.get(index));
		for(StringTransformer transformer:transformers)
			transformerPanel.removeTransformer(transformer);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource()==transformerPanel.transformerInfoButton) infoAboutTransformer();
		if(event.getSource()==transformerPanel.transformerAddButton) addTransformer();
		if(event.getSource()==transformerPanel.transformerRemoveButton) removeTransformer();
	}


}
