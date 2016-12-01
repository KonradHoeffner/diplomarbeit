package wortschatz2dbpedia.datasource;

public class MatchingElement
{
	public String identifier;
	public String usedForMatching; 

	public MatchingElement(String identifier, String usedForMatching)
	{
		this.identifier = identifier;
		this.usedForMatching = usedForMatching;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatchingElement other = (MatchingElement) obj;
		if (identifier == null)
		{
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (usedForMatching == null)
		{
			if (other.usedForMatching != null)
				return false;
		} else if (!usedForMatching.equals(other.usedForMatching))
			return false;
		return true;
	}
		
}