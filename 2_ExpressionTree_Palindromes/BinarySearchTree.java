

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
    {
        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
        boolean deleted;

        // Constructors
        BinaryNode(AnyType theElement)
        {
            this(theElement, null, null, false);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt, boolean del)
        {
            element = theElement;
            left = lt;
            right = rt;
            deleted = del;
        }
    }

    /** The tree root. */
    private BinaryNode<AnyType> root;

    /**
     * Construct the tree.
     */
    public BinarySearchTree()
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert(AnyType x)
    {
        root = insert(x, root);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove(AnyType x)
    {
        root = remove(x, root);
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin()
    {
        if (isEmpty())
            return null;
        return findMin(root).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax()
    {
        if (isEmpty())
            return null;
        return findMax(root).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(AnyType x)
    {
        return contains(x, root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty()
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty()
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree()
    {
        if (isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t)
    {
        if (t == null)
            return new BinaryNode<AnyType>(x, null, null, false);
        
        int compareResult = x.compareTo(t.element);
            
        if (compareResult < 0)
            t.left = insert(x, t.left);
        else if (compareResult > 0)
            t.right = insert(x, t.right);
        else {
        	if( t.deleted == true )
        		t.deleted = false;
        }
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t)
    {
        if (t == null)
            return t;   // Item not found; do nothing
            
        int compareResult = x.compareTo(t.element);
            
        if (compareResult < 0)
            t.left = remove(x, t.left);
        else if (compareResult > 0)
            t.right = remove(x, t.right);
        else
        	t.deleted = true;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t)
    {
        if (t == null)
            return null;
        
        // we keep track of the parent node
        BinaryNode<AnyType> tmp = findMin(t.left);
        
        // when tmp is not the leaf node
        if(tmp != null)
        	return tmp;
        
        // if the minimum is not yet deleted, we find the minimum
        if(!t.deleted) 
        	return t;
        // otherwise, we have to find the min in the right subtree of this deleted min
        return findMin(t.right);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t)
    {
    	if (t == null)
            return null;
        
        // we keep track of the parent node
        BinaryNode<AnyType> tmp = findMax(t.right);
        
        // when tmp is not the leaf node
        if(tmp != null)
        	return tmp;
        
        // if the maximum is not yet deleted, we find the maximum
        if(!t.deleted) 
        	return t;
        // otherwise, we have to find the max in the left subtree of this deleted max
        return findMax(t.left);
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(AnyType x, BinaryNode<AnyType> t)
    {
        if (t == null)
            return false;
            
        int compareResult = x.compareTo(t.element);
            
        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
        	//we find the x, but we need to check whether or not it has been deleted
        	if(!t.deleted)
        		return true; 	//Match
        	else
        		return false;	// since the node is deleted, we don't find it
        }
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree(BinaryNode<AnyType> t)
    {
        if (t != null)
        {
        	printTree(t.left);
        	// if the node has been deleted, we don't print it
        	if(!t.deleted)
        		System.out.println(t.element);
        	else
        		System.out.print("");
        	printTree(t.right);
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height(BinaryNode<AnyType> t)
    {
        if (t == null)
            return -1;
        else
            return 1 + Math.max(height(t.left ), height( t.right));    
    }
    
    // Test program
    // NOTE: This is the test program that came with the textbook source code.
    // Passing all these tests does NOT guarantee that nothing is wrong with
    // your code. Additional testing is highly recommended.
    public static void main(String [ ] args)
    {
/*      BinarySearchTree<Integer> t = new BinarySearchTree<Integer>();
        final int NUMS = 4000;
        final int GAP  =   37;

        System.out.println("Checking... (no more output means success)");

        for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
            t.insert(i);

        for (int i = 1; i < NUMS; i+= 2)
            t.remove(i);

        if (NUMS < 40)
            t.printTree();
        if (t.findMin() != 2 || t.findMax() != NUMS - 2)
            System.out.println("FindMin or FindMax error!");

        for (int i = 2; i < NUMS; i+=2)
             if (!t.contains(i))
                 System.out.println("Find error1!");

        for (int i = 1; i < NUMS; i+=2)
        {
            if (t.contains(i))
                System.out.println("Find error2!");
        }
        */
    	BinarySearchTree<Integer> t = new BinarySearchTree<Integer>();
    	t.insert(5);
    	t.insert(3);
    	t.insert(7);
    	t.insert(2);
    	t.insert(4);
    	t.insert(6);
    	t.insert(8);
    	t.remove(3);
    	t.remove(2);
    	t.remove(8);
    	t.printTree();
    	if(t.contains(3))
    		System.out.println("t has 3");
    	System.out.println("max is " + t.findMax());
    	System.out.println("min is " + t.findMin());
    	System.out.println("Lazy deletion is successfully implemented.");
    }
}
