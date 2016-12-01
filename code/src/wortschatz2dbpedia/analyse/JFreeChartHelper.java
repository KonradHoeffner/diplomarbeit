package wortschatz2dbpedia.analyse;

import java.awt.Color;
import java.text.DecimalFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.ui.TextAnchor;

public class JFreeChartHelper
{
	public static void setLatexThesisPercentageBarChartStyle(JFreeChart chart)
	{
		// if we want to export the chart as eps we get the error "NYI: Gradient paint"
		// so we have to disable color gradients in the bar renderer
		// also we optimize the chart for printing as eps as we want it in the thesis in latex afterwards

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        NumberAxis percentAxis = new NumberAxis();
        percentAxis.setAutoRange(false);
        percentAxis.setRange(0,100);
        plot.setRangeAxis(percentAxis);
        
		chart.setBorderVisible(false);
		chart.setAntiAlias(false);
		chart.setTextAntiAlias(false);
		
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines and the gradient
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setBarPainter( new StandardBarPainter() );
        //show data on the bar
        renderer.setBaseItemLabelGenerator(new PercentageCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        //renderer.setMinimumBarLength(100);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
        //renderer.setDefaultBarPainter(new )
        // disable title, this will be done in latex in another manner (text under the image) (wie auch immer dass da heißt)
        chart.setTitle((String)null);
	}
	
	public static void setLatexThesisPieChartStyle(JFreeChart chart)
	{
		// if we want to export the chart as eps we get the error "NYI: Gradient paint"
		// so we have to disable color gradients in the bar renderer
		// also we optimize the chart for printing as eps as we want it in the thesis in latex afterwards
		
        // get a reference to the plot for further customisation...
        final PiePlot plot = (PiePlot)chart.getPlot();
        
        plot.setBackgroundPaint(Color.white);
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
        
		chart.setBorderVisible(false);
		chart.setAntiAlias(false);
		chart.setTextAntiAlias(false);
        // disable title, this will be done in latex in another manner (text under the image) (wie auch immer dass da heißt)
        chart.setTitle((String)null);
    	StandardPieSectionLabelGenerator slbl = new StandardPieSectionLabelGenerator(
		         "{0}\n{1}\n{2}",
		         new DecimalFormat("#,##0"),
		         new DecimalFormat("0%"));
		plot.setLabelGenerator(slbl);		//plot.setRadius(0.70);
		

	}
	
}
