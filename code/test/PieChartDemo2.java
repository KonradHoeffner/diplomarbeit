

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------
 * PieChartDemo2.java
 * ------------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: PieChartDemo2.java,v 1.1 2005/04/28 16:29:17 harrym_nu Exp $
 *
 * Changes
 * -------
 * 05-Feb-2003 : Version 1 (DG);
 * 10-Mar-2004 : Updated for rewritten pie chart classes (DG);
 *
 */

import java.awt.Color;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a pie chart using data from a
 * {@link DefaultPieDataset}.  This demo also shows an "exploded" section in the chart.
 *
 * @author David Gilbert
 */
public class PieChartDemo2 extends ApplicationFrame {

	/** Constant indicating no labels on the pie sections. */
	public static final int NO_LABELS = 0;

	/** Constant indicating name labels on the pie sections. */
	public static final int NAME_LABELS = 1;

	/** Constant indicating value labels on the pie sections. */
	public static final int VALUE_LABELS = 2;

	/** Constant indicating percent labels on the pie sections. */
	public static final int PERCENT_LABELS = 3;

	/** Constant indicating percent labels on the pie sections. */
	public static final int NAME_AND_VALUE_LABELS = 4;

	/** Constant indicating percent labels on the pie sections. */
	public static final int NAME_AND_PERCENT_LABELS = 5;

	/** Constant indicating percent labels on the pie sections. */
	public static final int VALUE_AND_PERCENT_LABELS = 6;
	/**
	 * Default constructor.
	 *
	 * @param title  the frame title.
	 */
	public PieChartDemo2(String title) {

		super(title);
		PieDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);

		// add the chart to a panel...
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

	}

	// ****************************************************************************
	// * JFREECHART DEVELOPER GUIDE                                               *
	// * The JFreeChart Developer Guide, written by David Gilbert, is available   *
	// * to purchase from Object Refinery Limited:                                *
	// *                                                                          *
	// * http://www.object-refinery.com/jfreechart/guide.html                     *
	// *                                                                          *
	// * Sales are used to provide funding for the JFreeChart project - please    * 
	// * support us so that we can continue developing free software.             *
	// ****************************************************************************

	/**
	 * Creates a sample dataset.
	 * 
	 * @return a sample dataset.
	 */
	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("One", new Double(43.2));
		dataset.setValue("Two", new Double(10.0));
		dataset.setValue("Three", new Double(27.5));
		dataset.setValue("Four", new Double(17.5));
		dataset.setValue("Five", new Double(11.0));
		dataset.setValue("Six", new Double(19.4));
		return dataset;
	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset  the dataset.
	 * 
	 * @return a chart.
	 */
	private JFreeChart createChart(PieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(
				"Pie Chart Demo 2",  // chart title
				dataset,             // dataset
				true,                // include legend
				true,
				false
		);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setNoDataMessage("No data available");
		plot.setExplodePercent(1, 0.30);
		// set the background color for the chart...
		chart.setBackgroundPaint(Color.yellow);
		plot.setSimpleLabels(true);
		StandardPieToolTipGenerator stip = new StandardPieToolTipGenerator(
		         "{0}={2}",
		         new DecimalFormat("#,##0"),
		         new DecimalFormat("0%nuu"));
		plot.setToolTipGenerator(stip);

		StandardPieSectionLabelGenerator slbl = new StandardPieSectionLabelGenerator(
		         "{0}={2}",
		         new DecimalFormat("#,##0"),
		         new DecimalFormat("0%naa"));
		plot.setLabelGenerator(slbl);		//plot.setRadius(0.70);
		
		plot.setExplodePercent(1, 1.00);


			return chart;
	}

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args  ignored.
	 */
	public static void main(String[] args) {

		PieChartDemo2 demo = new PieChartDemo2("Pie Chart Demo 2");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
