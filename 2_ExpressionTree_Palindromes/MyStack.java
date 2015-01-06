
import java.util.ArrayList;

public class MyStack<AnyType> {

	private ArrayList<AnyType> mylist = new ArrayList<AnyType>();
	
	//check for its emptiness
	public boolean isEmpty(){
		return mylist.isEmpty();
	}
	
	//return the size of the stack
	public int getSize(){
		return mylist.size();
	}
	
	//check the peek of the stack
	public AnyType peek() {
	    return mylist.get(getSize() - 1);
	  }
	
	//pop the stack
	public AnyType pop(){
		AnyType x = mylist.get(getSize() - 1);
	    mylist.remove(getSize() - 1);
	    return x;
	}
	
	//push the stack
	public void push(AnyType x) {
	    mylist.add(x);
	  }
	
	// use this for the system.out.print so that we can print the stack
	public String toString() {
	    return "stack: " + mylist.toString();
	  }
	
	// test whether or not the MyStack class works
	public static void main(String[] args) {
		
		MyStack<String> test = new MyStack<String>();
		test.push("This MyStack class works perfectly.");
		test.push("This MyStack class is NOT working.");
		test.push("This MyStack class is dead.");
		test.pop();
		test.pop();
		System.out.print(test);
	}

}
