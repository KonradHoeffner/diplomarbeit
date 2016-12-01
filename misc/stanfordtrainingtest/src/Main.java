import edu.stanford.nlp.parser.lexparser.*;

public class Main
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// String inputFile = "susanne/7line.txt";
		String inputFile = "susanne/shorttrain.txt";

		String[] parameters = { "-PCFG", "-train", inputFile, "-headFinder",
				"edu.stanford.nlp.trees.LeftHeadFinder" };

		LexicalizedParser.main(parameters);
	}

}