package wortschatz2dbpedia.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import wortschatz2dbpedia.match.StringTransformer;
import wortschatz2dbpedia.match.StringTransformerList;

public class SelectTransformersPanel extends JPanel
{
	private final DefaultListModel transformerSelectedModel = new DefaultListModel(); 
	final JList transformerSelectedJList = new JList(transformerSelectedModel);
	private final DefaultListModel transformerDeselectedModel = new DefaultListModel();
	final JList transformerDeselectedJList = new JList(transformerDeselectedModel);

	final JButton transformerAddButton = new JButton("Add transformer");
	final JButton transformerInfoButton = new JButton("Info about transformer");

	final JButton transformerRemoveButton = new JButton("Remove transformer");
	final StringTransformerList transformerListSelected = new StringTransformerList();
	final StringTransformerList transformerListDeselected = new StringTransformerList();

	//final Collection<StringTransformer> transformers = new ArrayList;
	
	public SelectTransformersPanel(Collection<StringTransformer> transformers)
	{
		transformerListDeselected.stringTransformers.addAll(transformers);
		ActionListener listener = new SelectTransformersPanelActionListener(this);
		
		GridBagLayout transformerLayout = new GridBagLayout();
		this.setLayout(transformerLayout);

		SwingHelper.addComponent(this, transformerLayout, new JLabel("Transformers selected"),		0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NONE);
		SwingHelper.addComponent(this, transformerLayout, new JLabel("Transformers deselected"),	1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NONE);

		final JScrollPane transformerSelectedScrollPane = new JScrollPane(transformerSelectedJList);
		final JScrollPane transformerDeselectedScrollPane = new JScrollPane(transformerDeselectedJList);
		//this.setSize(500, 3000);
//		this.add(transformerSelectedScrollPane);
//		this.add(transformerDeselectedScrollPane);
//		this.add(transformerRemoveButton);
//		this.add(transformerAddButton);
		//                   																		x  y  w  h   wx  wy	  fill
		SwingHelper.addComponent(this, transformerLayout, transformerSelectedScrollPane,			0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.BOTH);
		SwingHelper.addComponent(this, transformerLayout, transformerDeselectedScrollPane,			1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.BOTH);
		SwingHelper.addComponent(this, transformerLayout, transformerRemoveButton,					0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NONE);
		SwingHelper.addComponent(this, transformerLayout, transformerAddButton,						1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NONE);
		SwingHelper.addComponent(this, transformerLayout, transformerInfoButton,					2, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NONE);
		transformerRemoveButton.addActionListener(listener);
		transformerAddButton.addActionListener(listener);
		transformerInfoButton.addActionListener(listener);
		
		updateJLists();
	}

	public StringTransformerList getSelectedTransformers()
	{
		return this.transformerListSelected.clone();
	}
	
	void updateJLists()
	{
		transformerSelectedModel.clear();
		transformerDeselectedModel.clear();
		for(final StringTransformer selectedTransformer: transformerListSelected.stringTransformers)
			transformerSelectedModel.addElement(selectedTransformer.getClass().toString().split(" ")[1].split("\\.")[2]);
		for(final StringTransformer deselectedTransformer: transformerListDeselected.stringTransformers)
			transformerDeselectedModel.addElement(deselectedTransformer.getClass().toString().split(" ")[1].split("\\.")[2]);
	}

	void addTransformer(final StringTransformer transformer)
	{
		transformerListSelected.stringTransformers.add(transformer);
		transformerListDeselected.stringTransformers.remove(transformer);
		updateJLists();
	}

	void removeTransformer(final StringTransformer transformer)
	{
		transformerListSelected.stringTransformers.remove(transformer);
		transformerListDeselected.stringTransformers.add(transformer);
		updateJLists();
	}

	//	void addTransformer(int nr)
	//	{
	//		StringTransformer transformer = transformerListDeselected.stringTransformers.get(nr);
	//		transformerListSelected.stringTransformers.add(transformer);
	//		transformerListDeselected.stringTransformers.remove(transformer);
	//		updateTransformerJLists();		
	//	}
	//
	//	void removeTransformer(int nr)
	//	{
	//		StringTransformer transformer = transformerListSelected.stringTransformers.get(nr);
	//		transformerListDeselected.stringTransformers.add(transformer);
	//		transformerListSelected.stringTransformers.remove(transformer);
	//		updateTransformerJLists();		
	//	}


}
