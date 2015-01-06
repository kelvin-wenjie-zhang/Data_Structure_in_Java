
import java.io.*;
import java.util.*;

public class FindPalindromes {

	public static void main(String[] args) {
		try
        {
            //check if there is a file name
            if(args.length == 0)
            {
                System.out.println("Please enter a file name at the end of the command line.");
                System.exit(0);
            }
            
            File file = new File(args[0]);
            
            //check whether the file exists
            if(! file.exists() )
            {
                System.out.println("The file does not exist.");
                System.exit(0);
            }
            
            Scanner input = new Scanner(file);
            while(input.hasNextLine())
            {
            	//read each line
                String token = input.nextLine();
                
                //create a stack
                Stack<Character> stack = new Stack<Character>();
                
                //remove the white space and punctuation
                String test = token.toLowerCase();
                String str = "";
                for(int i = 0; i < test.length(); i++){
                	if(test.charAt(i) >= 'a' && test.charAt(i) <= 'z')
                		str += test.charAt(i);
                }
                
                //we push all the characters to the stack from str
                for (int i = 0; i < str.length(); i++) {
                    stack.push(str.charAt(i));
                }

                //create another string that is a reverse of the stack
                String reverse = "";
                while (!stack.isEmpty()) {
                    reverse += stack.pop();
                }

                //print out the palindrome.
                if (str.equals(reverse))
                    System.out.println("\"" + token + "\"" + " is a palindrome.");
            }
            
            //close the file
            input.close();
            
        }catch (Exception e)
        {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            System.out.println("EXCEPTION CAUGHT: " + sw.toString() );
            System.exit( 0 );
        }

	}

}
