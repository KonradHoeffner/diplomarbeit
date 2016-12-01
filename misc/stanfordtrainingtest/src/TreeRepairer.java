import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class TreeRepairer
{
	public static boolean checkTree(String tree)
	{
		int depth = 0;
		int count = 0;
		for(char c:tree.toCharArray())
		{
			count++;
			if(c=='(') depth++;
			if(c==')') depth--;
			//System.out.print(c);
			//if((count&0xfff)==0) System.out.println(count/1000+"k");
			if(depth<0) {/*System.out.println("error in character number "+count);*/return false;}
			//System.out.print(c);
		}
		if(depth==0) return true;//System.out.println("All ok");
		else return false;
		//System.out.println("Error in tree: end depth "+depth);
	}

	static final int DELETE_MODE = 0;
	static final int REPAIR_END_MODE = 1;
	static final int REPAIR_FULL_MODE = 2;	

	public static String repairTree(String tree, int mode)
	{
		StringBuffer t = new StringBuffer();
		int depth = 0;
		for(char c:tree.toCharArray())
		{ 
			if(c == '(') depth++;
			if(c == ')')
				if(depth>0) depth--;
				else
					if(mode==REPAIR_FULL_MODE) continue;
					else return null;
			t.append(c);
		}
		for(int i=0;i<depth;i++) t.append(')');
		return t.toString();		
	}

	//public static 

	//	private String readTextFile(String filename) throws FileNotFoundException
	//	{
	//		FileReader in = new FileReader(new File(filename));
	//		return filename;
	//	}

	/**
	 * @param treeTerminator regular expressions definitely indicating an end of a tree
	 * @throws IOException
	 */
	public static void repairForrest(String filenameIn,String filenameOut,String treeTerminator,int mode) throws IOException
	{
		if(mode!=DELETE_MODE) throw new RuntimeException("Mode not yet supported");
		String forrest = FileUtils.readFileToString(new File(filenameIn));
		String[] trees = forrest.split(treeTerminator);
		List<String> repairedTrees  = new LinkedList<String>();
		for(String tree: trees)
		{
			if(checkTree(tree)) repairedTrees.add(tree);
			else System.out.println("Wrong tree: <"+tree+">");
		}
		if(trees.length==repairedTrees.size())
			System.out.println("All "+trees.length+" trees are ok.");
		else
			System.out.println((trees.length-repairedTrees.size())+" of "+trees.length+" trees are invalid.");
		if(new File(filenameOut).exists()) throw new IOException("Output file "+filenameOut+" already exists.");
		//FileUtils.writeStringToFile(filenameOut,repairedTrees);
		PrintWriter out = new PrintWriter(new FileWriter(filenameOut));
		for(String tree:repairedTrees) out.println(tree);
		out.close();
	}

//	/**
//	 * @param treeTerminators regular expressions definitely indicating an end of a tree
//	 * @throws IOException
//	 */
//	public static void repairForrestOld(String filenameIn,String filenameOut,String[] treeTerminators,int mode) throws IOException
//	{
//		BufferedReader in = new BufferedReader(new FileReader(filenameIn));
//		char c;
//		int depth = 0;
//		int count = 0;
//		StringBuffer s = new StringBuffer();
//		while((c=(char) in.read())!=(char)-1)
//		{
//			s.append(c);
//			count++;
//			if(c=='(') depth++;
//			if(c==')') depth--;
//			if(depth==0) s = new StringBuffer();
//			if(s.toString().endsWith("(YF .+)")) System.out.println("Something is strange here");
//			//System.out.print(c);
//			//if((count&0xfff)==0) System.out.println(count/1000+"k");
//
//			if(depth<0) {System.out.println("error in character number "+count);return;}
//			//System.out.print(c);
//		}
//		if(depth==0) System.out.println("All ok");
//		else
//			System.out.println("Error in tree: end depth "+depth);
//
//		in.close();
//	}
	
	static void checkTreebankFormat(String tree)
	{
		checkTree(tree);
		System.out.println(Arrays.toString(tree.split("[ ()]")));
		for(char c:tree.toCharArray())
		{
			
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		final String inputFilename = "susanne/shorttrain.txt";
		final String outputFilename = "susanne/repaired_shorttrain.txt";
		final String treeTerminator = "\n";
		//repairForrest(inputFilename,outputFilename,treeTerminator,DELETE_MODE);
		String defectiveTree = " ( S ( Fa:t ( Rq:t (CSn When)  ) ( J:e (JJ necessary)  )  ) (YC +,) ( Nap:s (PPIS2 we)  ) ( Vdc (VMd should) (VV0v make)  ) ( Ni:O (PPH1 it) Ni:( J:j (JJ clear)  ) ( Fn:o (CST that) ( Np:s123 (NN2 countries) ( Fr ( Dq:s123 (DDQr which)  ) ( V (VV0v choose)  ) ( Ti:o ( s123 (YG -)  ) ( Vi (TO to) (VV0v derive)  ) ( Np:o (JJ marginal) (NN2 advantages)  ) ( P:q (II from) ( Nns (AT the) (JJ cold) (NN1n war)  )  ) ( Ti+ (CCr or) ( Vi (TO to) (VV0t exploit)  ) ( Ns:o (APPGh2 their) (NN1n potential) ( P (IF for) ( Tg ( Vg (VVGt disrupting)  ) ( Ns:o (AT the) (NN1n security) ( Po (IO of) ( Ns (AT the) (NN1c world)  )  )  )  )  )  )  )  )  )  ) ( 124 (YG -)  ) ( Vc (VMo will) ( LE=:G124 (LE21 not) (LE22 only)  ) (VV0v lose)  ) ( Ns:o (APPGi2 our) (NN1n sympathy)  ) ( Fn+ (CCB but) ( R:m (RR also)  ) ( V (VV0t risk)  ) ( Np:o ( G (APPGh2 their) (DAg own)  ) (NN2 prospects) ( P (IF for) ( Ns (JJ orderly) (NN1n development)  )  )  )  )  )  (YF +.))>";
		checkTreebankFormat(defectiveTree);
	}

}

