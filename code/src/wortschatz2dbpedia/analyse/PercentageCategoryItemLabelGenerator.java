package wortschatz2dbpedia.analyse;

import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;

public class PercentageCategoryItemLabelGenerator implements
		CategoryItemLabelGenerator
{

	@Override
	public String generateColumnLabel(CategoryDataset dataSet, int column)
	{
		return null;// i have no idea for what this is
	}

	@Override
	public String generateLabel(CategoryDataset dataset, int row, int column)
	{
		StringBuffer value = new StringBuffer(dataset.getValue(row, column).toString());
		// remove trailing points and zeroes
		while(value.length()>1&&(value.charAt(value.length()-1)=='0'))
			value.deleteCharAt(value.length()-1);
		if(value.charAt(value.length()-1)=='.') value.deleteCharAt(value.length()-1);
		value.append("%");
		return value.toString();
	}

	@Override
	public String generateRowLabel(CategoryDataset dataset, int row)
	{
		return null;// i have no idea for what this is
	}

}
