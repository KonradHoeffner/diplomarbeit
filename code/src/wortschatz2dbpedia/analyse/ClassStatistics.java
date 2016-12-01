package wortschatz2dbpedia.analyse;

/** Data structure to group matching classes statistics
 * There is assumed to be at most one "ClassStatistics" object for any given name in any collection 
 * and instances with the same className are assumed equal.
 */
public class ClassStatistics
{
	public String className;
	public int occurrences;
	public int goodMatches;
	
	public ClassStatistics(String className, int occurrences, int goodMatches)
	{
		this.className = className;
		this.occurrences = occurrences;
		this.goodMatches = goodMatches;
	}
	
	@Override
	public String toString()
	{
		return "("+className+","+goodMatches+" good Matches,"+occurrences+" occurrences,"+goodMatches*100.0/occurrences+"%)";
	}

	public int goodMatchesPercentage()
	{
		if(occurrences==0) return 0;
		return goodMatches*100/occurrences;
	}
	
//	@Override
//	public int hashCode()
//	{
//		final int prime = 31;
//		int result = 1;
//		result = prime * result
//				+ ((className == null) ? 0 : className.hashCode());
//		return result;
//	}
//	
//	@Override
//	public boolean equals(Object obj)
//	{
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		ClassStatistics other = (ClassStatistics) obj;
//		if (className == null)
//		{
//			if (other.className != null)
//				return false;
//		} else if (!className.equals(other.className))
//			return false;
//		return true;
//	}

}