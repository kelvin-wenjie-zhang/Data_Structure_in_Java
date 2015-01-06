

import java.util.Scanner;

public class ExpressionTree {

	private static class TreeNode
	{
		// to determine if it is a leaf node
		private final boolean  leaf;
		// to store the operator
	    private final char operator;
	    // to store the value if this is a leaf node
	    private Integer operand;
	    // left subtree and right subtree of the node
	    private TreeNode left, right;

	    // constructor
	    private TreeNode ( boolean leaf, char operator, int operand)
	    {
	       this.leaf = leaf;
	       this.operator = operator;
	       this.operand = operand;
	       this.left = null; 
	       this.right = null;
	    }
	      
	      // we override it so that we can print out the expression tree
	      // for leaf nodes, show the value; for internal, show the operator.
	      public String toString()    
	      {  return leaf ? Integer.toString(operand) : Character.toString(operator) ;  }
	   }
	
	   public static boolean isInteger(String s) {
		    try { 
		        Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    }
		    // only got here if we didn't return false
		    return true;
	   }
	   public static int toInteger(String s){
		   int x;
		   try { 
		         x = Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		    	// this -1 would not occur because we always
		    	// check it before we use it as an integer.
		    	return -1; 
		    }
		return x;
	   }
	
	   public static void printTree(TreeNode node){
		   if(node != null){
			   System.out.print("(");
			   printTree(node.left);
			   System.out.print(node);
			   printTree(node.right);
			   System.out.print(")");
		   }
	   }
	   
	public static void main(String[] args) {
		System.out.println("Please enter a white-space delimited postfix expression:");
		Scanner kbd = new Scanner(System.in);
		MyStack<TreeNode> stack = new MyStack<TreeNode>();
		String s = kbd.nextLine();
		String[] tokens = s.split(" ");
		for(int i = 0; i < tokens.length; i++) {
			String tmp = tokens[i];
			if(isInteger(tmp)){
				stack.push(new TreeNode(true,'\0',toInteger(tmp)));
			}
			if(!isInteger(tmp)){
				TreeNode node1 = stack.pop();
				TreeNode node2 = stack.pop();
				TreeNode node3 = new TreeNode(false,tmp.charAt(0),0);
				node3.right = node1;
				node3.left = node2;
				stack.push(node3);
			} 
		}
		printTree(stack.pop());
        System.out.println();
	}

}
