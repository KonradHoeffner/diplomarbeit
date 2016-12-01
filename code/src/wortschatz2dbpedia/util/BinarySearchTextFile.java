package wortschatz2dbpedia.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class BinarySearchTextFile
{
	final RandomAccessFile in;
	Map<Long,String> cache = new HashMap<Long,String>();
	final int MAXIMUM_CACHE_SIZE = 100000;

	public BinarySearchTextFile(RandomAccessFile in)
	{
		this.in = in;
	}

	public BinarySearchTextFile(String filename) throws IOException
	{
		//this(new RandomAccessFile(filename, "r"));
		this(new BufferedRandomAccessFile(filename, "r", 3000));
	}

	/**
	 * @param isCSV if set to false, the whole line will be matched, if set to true only the first column
	 * @return a string which matches / whose first column matches (if isCSV is set to true)
	 * the given word (update: not ignoring case anymore).
	 */
	public String get(String word,boolean isCSV) throws IOException
	{
		in.seek(0);
		long lowerBound = 0;
		long upperBound = in.length()-2;
		do
		{
			String line = null;
			String key;
			long pos = (lowerBound+upperBound)>>1; //start in the middle 
			
			if((key=cache.get(pos))!=null)
			{
				int compareResult = word.compareTo(key);
				if(compareResult<0) upperBound = pos-1; else
				if(compareResult>0) lowerBound = pos+1; else // here pos+1 because we didnt read the line
				if(compareResult==0)
				{
					in.seek(pos);
					if(pos>0) in.readLine();
					do
					{
						line = in.readLine();
					}
					while(isCSV&&line.split("\t").length<2);
					return line;
					//if(line==null) {return null;}
				}	
			}
			else
			{
				in.seek(pos);
				//char c;
				if(pos>0) in.readLine();
				String[] tokens = null;				
				if(isCSV)
					do
					{
						line = in.readLine();
						if(line==null) {return null;}
						tokens = line.split("\t");
					} while(tokens.length<2);
				else
				{
					line = in.readLine();
					if(line==null) {return null;}
				};
				key = isCSV?tokens[0]:line;
				if(cache.size()<MAXIMUM_CACHE_SIZE) cache.put(pos, key);
				int compareResult = word.compareTo(key); 
				if(compareResult<0) upperBound = pos-1; else
				if(compareResult==0) return line.toString(); else
				//attention here. because \n is needed to recognize the next word, we go one step back here
				//the file pointer already points on the next to-read byte			
				if(compareResult>0) lowerBound = in.getFilePointer()-1;
			}
			if(lowerBound>upperBound) return null;

		} while(true);
		//return found;
	}

	//	/** Like get but works also with multiple lines for one key 
	//	 */
	//	public static String[] getMultiple(RandomAccessFile in,String word) throws IOException
	//	{
	//		boolean isCSV = true;
	//		in.seek(0);
	//		long lowerBound = 0;
	//		long upperBound = in.length()-2;
	//		do
	//		{
	//			long pos = (lowerBound+upperBound)>>1; //start in the middle 
	//
	//			in.seek(pos);
	//
	//			if(pos>0) in.readLine();
	//			long posStartOfLine = in.getFilePointer();
	//			String line = in.readLine();
	//
	//			String key = isCSV?line.toString().split("\t")[0]:line.toString();
	//
	//			int compareResult = word.compareTo(key);
	//			if(compareResult==0)
	//			{
	//				String previousKey = null;
	//				long previousPosStartOfLine = posStartOfLine; 
	//				// go back () to the first line with this key
	//				//for this: read the previous line
	//				//for this: find the start of the previous line
	//				do
	//				{
	//					if(posStartOfLine>0)
	//					{ 
	//						pos = posStartOfLine -1;
	//						char c;
	//						do {in.seek(pos);c=(char)in.readByte();pos--;} while(c!='\n');
	//						// now we've just read the end of the line before the previous line so we just read the line...
	//						previousPosStartOfLine = pos+1;
	//						String previousLine = in.readLine();
	//						previousKey = isCSV?line.toString().split("\t")[0]:line.toString();
	//					}
	//				} while(previousKey.equals(key));
	//				
	//				return null;//line.toString();
	//			}
	//			if(compareResult<0) upperBound = pos-1;
	//			//attention here. because \n is needed to recognize the next word, we go one step back here
	//			//the file pointer already points on the next to-read byte			
	//			if(compareResult>0) lowerBound = in.getFilePointer()-1;
	//			if(lowerBound>upperBound) return null;
	//
	//		} while(true);
	//		//return found;
	//	}


	// ******************************* Convenience Methods *******************************************

	public static String get(String filename,String word,boolean isCSV) throws IOException
	{
		RandomAccessFile in = new RandomAccessFile(filename, "r"); // open in read only mode
		String result  = new BinarySearchTextFile(in).get(word,isCSV); 
		in.close();
		return result;
	}

	public static boolean contains(String filename,String word,boolean isCSV) throws IOException
	{
		return get(filename,word,isCSV)!=null;
	}

	public boolean contains(String word,boolean isCSV) throws IOException
	{
		return get(word,isCSV)!=null;
	}


	public boolean contains(String word) throws IOException
	{
		return contains(word,false);
	}

	public static boolean contains(String filename, String word) throws IOException
	{
		return contains(filename,word,false);
	}


	//	public static void main(String[] args) throws IOException
	//	{
	//		String fileName = "input/test/sortedwords.txt";
	//		//String fileName = "input/wortschatz/de_words_all_sorted.txt"; // 37 MB text file
	//		RandomAccessFile in = new RandomAccessFile(fileName, "r");
	//		String[] searchWords = {"Abraham","Giraffe","Moses"};
	//		StopWatch watch = new StopWatch();
	//		watch.start();
	//		final int repetitions = 1000;
	//		for(int i=0;i<repetitions/searchWords.length;i++)
	//			for(String searchWord : searchWords)
	//			{
	//				boolean isContained = contains(in,searchWord);
	//			}
	//		System.out.println(repetitions+" repetitions");
	//		System.out.println(watch);
	//		//System.out.println("Looking for "+searchWord+": "+contains(fileName,searchWord));
	//	}



}
