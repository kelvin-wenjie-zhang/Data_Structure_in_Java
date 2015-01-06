
import java.io.*;
import java.util.*;

public class Huffman {

	//the file content that is used to construct the huffman tree
	private String text;
	//unique characters
	private ArrayList<Character> unique;
	private int[] frequency;
	private Node root; //represent for the huffman tree that we will build
	
	//constructor
    public Huffman(){
    	this("");
    }
    public Huffman(String input){
    	unique = new ArrayList<Character>();
    	text = new String(input);
    	run();
    }
    
    private void run(){
    	
    	//first, we find out all the unique characters
    	char[] arr = text.toCharArray();
    	for (int i = 0; i < arr.length; i++){
    		if(!unique.contains(arr[i]))
    			unique.add(arr[i]);
    	}
    	//then, we compute the frequency of each character
    	frequency = new int[unique.size()];
    	for (int i = 0; i < arr.length; i++){
    		frequency[unique.indexOf(arr[i])]++;
    	}
    	
    	//then, we use the frequency table to build the huffman tree.
    	Node root = buildTree();
    	
    	// build code table
        String[] codes = new String[unique.size()];
        buildCode(codes, root, "");
        
        // then we print out the Huffman code for each character
        System.out.println("********************************************");
        System.out.println("Huffman Codes: ");
        for(int i = 0; i < unique.size(); i++){
        	if(unique.get(i).equals('\n'))
        		System.out.print("NL" + ": " + codes[i]);
        	else if(unique.get(i).equals(' '))
        		System.out.print("SP" + ": " + codes[i]);
        	else
        		System.out.print(unique.get(i) + ": " + codes[i]);
        	System.out.println();
        }
        System.out.println("********************************************");
        System.out.println("The Huffman tree may not look beautiful.");
        // then we continue to print out the Huffman tree in the console window
        printTree(root,1);
        System.out.println();
        
        // then we prompt the user to enter a code of 0's and 1's
        System.out.println("Please enter a code of 0's and 1's: ");
        Scanner kbd = new Scanner(System.in);
        char[] s = kbd.next().toCharArray();
        //String result = decode(s);
        System.out.println("Sorry, I couldn't debug it for this part. Please check the decode() method.");
        
        System.out.println("********************************************");
        // finally we prompt the user for a series of characters
        System.out.println("Please enter a a series of following characters: ");
        for(int i = 0; i < unique.size(); i++){
        	if(Character.toString(unique.get(i)).equals(" "))
        			System.out.print("SP ");
        	else if(Character.toString(unique.get(i)).equals("\n"))
        			System.out.print("NL ");
        	else
        		System.out.print(unique.get(i)+" ");
        }
        
        System.out.println();
        Scanner scan = new Scanner(System.in);
        char[] str = scan.next().toCharArray();
        for(char k: str)
        	System.out.print(codes[unique.indexOf(k)]);
        System.out.println();
        System.out.println("********************************************");
        System.out.println("Thank you.");
    }
    
    /**
     * Built the huffman tree given the frequencies
     * @param freq is the given frequencies
     * @return the root node
     */
    private Node buildTree(){
    	// initialize priority queue with singleton trees
        BinaryHeap<Node> myheap = new BinaryHeap<Node>();
        for (char i = 0; i < unique.size(); i++){
        	myheap.insert(new Node(unique.get(i),frequency[i],null,null));
        }
        
        //merge two smaller trees
        //Notice: I add a public method called "size" in the BinaryHeap class
        while (myheap.size() > 1) {
            Node left  = myheap.deleteMin();
            Node right = myheap.deleteMin();
            //create a new parent node to link these two child nodes
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            myheap.insert(parent);
        }
        
        return myheap.deleteMin();
    }
    
    /**
     * Internal method to print out the tree
     * @param root is the root node of the huffman tree
     * @param current is the current number of nodes in order to print T1, T2,...,Tn
     */
    private void printTree(Node root, int current){
    	if(root == null)
    		return;
    	else if(root.isLeaf()){
    		for(int i = current; i > 0 ; i--)
    			System.out.print("\t");
    		if(Character.toString(root.ch).equals(" "))
    			System.out.println("SP");
    		else if(Character.toString(root.ch).equals("\n"))
    			System.out.println("NL");
    		else
    			System.out.println(root.ch);
    	}
    	else{
    		System.out.print("\t");
    		System.out.println("T"+ current);
    		System.out.println("\t");
    		
    		System.out.print("\t");
    		System.out.print("/");
    		System.out.print("\t");
    		System.out.println("\\");
    	}
    	printTree(root.left,current+1);
    	printTree(root.right,current+2);
    }
    
    /**
     * Internal method to build the huffman code for each character
     * @param codes is the array stored each huffman code with respect to
     * the position in the unique arraylist
     * @param x is the node that we currently visit
     * @param s is the huffman code we need to store
     */
    private void buildCode(String[] codes, Node x, String s){
    	if (!x.isLeaf()) {
            buildCode(codes, x.left,  s + '0');
            buildCode(codes, x.right, s + '1');
        }
        else
            codes[unique.indexOf(x.ch)] = s;
    }
    
    /**
     * Internal method to decode a sequence of huffman codes 
     * @param in is the input which should contain only 0's and 1's
     * @return the decoded string
     */
    private String decode(char[] in){
    	String output = "";
    	int[] input = new int[in.length];
    	for(int i = 0; i < in.length; i++){
    		try{
    		input[i] = Integer.parseInt(Character.toString(in[i]));
    		}catch(Exception e){
    			System.out.println("NumberFormatException Caught. ");
    			System.exit(0);
    		}
    	}
    	
    	traverse(root,input,0,output);
    	
    	if(output != null)
    		return output;
    	else
    		return "This code has an error.";
    }
    
    /**
     * Internal method to traverse the huffman tree
     * @param x is the current node we are visiting
     * @param input is the array containing 0's and 1's
     * @param current is the current index of input
     * @param output is the decoded string
     */
    private void traverse(Node x, int[] input, int current, String output){
    	if(x.isLeaf())
    		output += Character.toString(x.ch);
    	else if(input[current] == 0)
    		traverse(x.left,input,++current,output);
    	else if(input[current] == 1)
    		traverse(x.right,input,++current,output);
    }
    
    /**
     * The Node class that we used for the huffman nodes.
     * @author Wenjie Zhang, UNI: wz2261
     */
    private static class Node implements Comparable<Node> {
        
    	private final char ch; //store the character
        private final int freq; // the frequency of the character
        private final Node left, right;

        //general constructor
        Node(char ch){
        	this(ch,1,null,null);
        }
        //constructor
        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // to determine if this is a leaf node
        private boolean isLeaf() {
            assert (left == null && right == null) || (left != null && right != null);
            return (left == null && right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
	
    
	public static void main(String[] args) {
		try {
			//check the argument file in the command line
			if(args.length == 0 ) {
				System.out.println("Please provide one text file name in the command line.");
				System.exit(0);
			}
			
			//read the whole file as a string
			BufferedReader reader = new BufferedReader(new FileReader(args[0]));
			String input = "";
			while(reader.ready()){
				input = input + reader.readLine() + "\n";
			}
			reader.close();
			
			Huffman myHuffman = new Huffman(input);
			
			
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			System.out.println("EXCEPTION CAUGHT: " + sw.toString() );
			System.exit(0);
		}
		

	}

}
