
import java.io.*;
import java.util.*;

public class problem_1 {
	public final static int[] COINS = {25,10,5};
	public static void main(String[] args) throws IOException{
		int money = getIntegerInput();
		if(money < 0)
			System.out.println("Please enter a valid positive integer.");
		else if( money % 5 !=0 ){
			System.out.println( "Sorry, " + money + " can't be changed.");
		}
		else if(money >= 0){
			int[] counts = new int[COINS.length];
			System.out.println("Change for "+money+ " = ");
			printAll(0,COINS,money,counts);
		}

	}
	public static void printAll(int ind, int[] denom,int N,int[] vals){
	    if(N==0){
	    	for(int i = 0; i < denom.length; i++){
	    		for(int j = 0; j < vals[i]; j++)
	    			System.out.print(denom[i] + " ");
	    	}
	        System.out.println();
	        return;
	    }
	    if(ind == (denom.length))
	    	return;             
	    int currdenom = denom[ind];
	    for(int i=(N/currdenom);i >= 0;i--){
	        vals[ind] = i;
	        printAll(ind+1,denom,N-i*currdenom,vals);
	    }
}
	
	public static String getInput() throws IOException {
		// retrieve input from the command line.
		System.out.print("Please enter a positive number:");
		Scanner input = new Scanner(System.in);
		return input.nextLine();
	}
	
	public static int getIntegerInput() throws IOException {
		String line = getInput();
		int result = -1;
		try {
			result = Integer.parseInt(line);
		} catch (NumberFormatException nfExc) {
			result = -1;
		}
		return result;
	} 
	
}
