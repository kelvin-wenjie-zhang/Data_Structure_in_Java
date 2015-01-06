

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Hashtable;

/**
 * This is a spell checker that requires three files: a dictionary file, a commonly misspelling file,
 * and a test file needed to be spell-checked.
 * 
 * Please remember the misspelling file must be in the following format:
 * In each line, the first word is the wrong word, the second word is the right word, and they must be
 * separated by a single white space.
 * 
 * We use two different hash table to keep track the dictionary and the misspelling file.
 * @author Kelvin Wenjie Zhang, UNI: wz2261
 *
 */

public class SpellChecker {
	
	//this spell checker uses the quadratic probing hash table since it can automatically rehash
	myHashtable<String> dictionary;
	Hashtable<String,String> misspelling;
	
	public SpellChecker(File dictionaryFile, File misspellingFile, File testFile) {
		//create the hash table to store the provided dictionary
		dictionary = new myHashtable<String>();
		
		try{
			//this two variables are used for reading the file into our hash tables
			String toRead, token;
			
			//read the dictionary file
			BufferedReader readDict = new BufferedReader(new FileReader(dictionaryFile));
			
			while((toRead = readDict.readLine())!=null){
				StringTokenizer st = new StringTokenizer(toRead);
				while(st.hasMoreTokens()){
					token = st.nextToken();
					dictionary.insert(token);
				}
			}
			readDict.close();
			
			//read the commonly misspelled words
			misspelling = new Hashtable<String,String>();
			BufferedReader readMis = new BufferedReader(new FileReader(misspellingFile));
			String wrong, right;
			while((toRead = readMis.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(toRead);
				
				wrong = st.nextToken();
				right = st.nextToken();
				
				//check if both of the wrong word and the right word exist
				if(wrong == null || wrong == " " || right == null || right ==" "){
					System.out.println("In the misspelling file, please make sure the first word is the wrong word, and the second word is the right word.");
					System.exit(0);
				}
				
				//we add these matching into the misspelling hash table
				misspelling.put(wrong, right);
				
			}
			readMis.close();
			
			//then we read the test file
			BufferedReader readTest = new BufferedReader(new FileReader(testFile));
			//initialize the line number so that we can know which line it is
			int line = 0;
			while ((toRead = readTest.readLine()) != null) {
				
				line++;
				
                StringTokenizer st = new StringTokenizer(toRead);
                
                while (st.hasMoreTokens()) {
                    String inputWord = st.nextToken();
                    
                    //to check if we can find it in the dictionary
                    boolean findout = findWordDict(inputWord);
                    //if we find it, then we don't need to do anything, go to next token
                    if(findout == true)
                    	continue;
                    //if not, we check if we can find it in the misspelling file
                    String result = null;
                    if(findout == false)
                    	result = findWordMis(inputWord);
                    //then we find the correct word for the misspelling
                    if(result != null){
                    	System.out.println("line " + line + ", " + inputWord + ": " + result);
                    }
                    
                    //otherwise, we assume this is a misspelling word
                    else{
                    	//we create the array list to hold the correct words
                    	ArrayList<String> correct = new ArrayList<String>();
                    	spellCheckWord(inputWord, correct);
                    	if(correct.size() == 0)
                    		System.out.println("line " + line + ", " + inputWord + ": " + "Sorry, no correct words are found.");
                    	else
                    		System.out.println("line " + line + ", " + inputWord + ": " + correct);
                    }
                    
                }
                
            }
            readTest.close();
			
			
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			System.out.println("EXCEPTION CAUGHT: " + sw.toString() );
			System.exit(0);
		}

		
	}
	/**
	 * To check if we can find the word in the dictionary
	 * @param wordToFind that we want to find in the dictionary
	 * @return true if we find it. otherwise, not.
	 */
	public boolean findWordDict(String wordToFind){
		String word = wordToFind.toLowerCase();
		if(dictionary.contains(word))
			return true;
		else
			return false;
	}
	
	/**
	 * To check if we can find the word in the misspelling file
	 * @param wordToFind that we want to find in the misspelling file
	 * @return the correct word if we find it. otherwise, we return null.
	 */
	public String findWordMis(String wordToFind){
		String lookup;
		String word = wordToFind.toLowerCase();
		if ((lookup = (String)misspelling.get(word)) != null)
            return lookup;
		return null;
	}
	
	/**
	 * For the misspelling words, we alter them by adding a character, or
	 * removing a character, or swapping two adjacent characters
	 * If none of these works, then we print out "no correct words are found".
	 * @param wordToCheck the word that we want to check
	 * @param correct the list of correct words that we want to print out
	 */
	public void spellCheckWord(String wordToCheck, ArrayList<String> correct) {
		
		String word = wordToCheck.toLowerCase();
		
		//alter by addition
		for(int i = 0; i <= word.length(); i++){
			char myChar = 'a';
			for(int j = 1; j <= 26; j++){
				String test = word.substring(0,i) + myChar + word.substring(i, word.length());
				myChar++;
				if(dictionary.contains(test))
					correct.add(test);
			}
		}
		
		//alter by swapping
		for(int i = 0; i < word.length() -1 ; i++){
			char[] tmp = word.toCharArray();
			char tmpchar = tmp[i];
			tmp[i] = tmp[i+1];
			tmp[i+1] = tmpchar;
			String test = "";
			for(char c: tmp)
				test += c;
			if(dictionary.contains(test))
				correct.add(test);
		}
		
		//alter by deletion
		for(int i = 0; i < word.length(); i++){
			String test = word.substring(0,i) + word.substring(i+1,word.length());
			if(dictionary.contains(test))
				correct.add(test);
		}
		
	}
	
	
	public static void main(String[] args) {
		try {
			//check the three argument in the command line
			if(args.length < 3 ) {
				System.out.println("Please provide 3 files names in the command line: dictionary file, misspelling file, test file");
				System.exit(0);
			}
			
			//check the existence of the 3 files
			File file1 = new File(args[0]);
			File file2 = new File(args[1]);
			File file3 = new File(args[2]);
			
			if( !file1.exists() || !file2.exists() || !file3.exists() )
            {
                System.out.println("Please make sure all the files exist in the current directory.");
                System.exit(0);
            }
			
			//execute the constructor
			new SpellChecker(file1, file2, file3);
			
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			System.out.println("EXCEPTION CAUGHT: " + sw.toString() );
			System.exit(0);
		}
	}
}