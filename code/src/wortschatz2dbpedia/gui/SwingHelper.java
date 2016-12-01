package wortschatz2dbpedia.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class SwingHelper
{
	static void addComponent( final Container cont,final GridBagLayout gbl,final Component c,
			final int x, final int y,final int width, final int height,
			final double weightx, final double weighty)
	{addComponent(cont,gbl,c,x,y,width,height,weightx,weighty,GridBagConstraints.BOTH);}
	
	static void addComponent( final Container cont,
			final GridBagLayout gbl,
			final Component c,
			final int x, final int y,
			final int width, final int height,
			final double weightx, final double weighty, int fill )
	{
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = fill;//GridBagConstraints.BOTH;
		gbc.gridx = x; gbc.gridy = y;
		gbc.gridwidth = width; gbc.gridheight = height;
		gbc.weightx = weightx; gbc.weighty = weighty;
		gbl.setConstraints( c, gbc );
		cont.add( c );
	}	
}
