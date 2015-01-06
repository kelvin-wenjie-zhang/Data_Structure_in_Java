

/**
 * This hash table uses the quadratic probing to deal with the collision, thus it may have
 * a significant part that is overlapped with the QuadraticProbingHashTable from the textbook.
 * Notice that the myhash() function is different from the textbook.
 * @author Kelvin Zhang, uni: wz2261
 *
 */

public class myHashtable<AnyType> {
	
	private static final int DEFAULT_TABLE_SIZE = 101;
	private HashEntry<AnyType> [ ] array; // The array of elements
    private int occupied;                 // The number of occupied cells
    private int theSize;                  // Current size
	
	public myHashtable() {
		this( DEFAULT_TABLE_SIZE );
	}
	
	public myHashtable( int size )
    {
        allocateArray( size );
        doClear( );
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing.
     * @param x the item to insert.
     */
    public boolean insert( AnyType x )
    {
            // Insert x as active
        int currentPos = findPos( x );
        if( isActive( currentPos ) )
            return false;

        array[ currentPos ] = new HashEntry<AnyType>( x, true );
        theSize++;
        
            // check if we need to rehash
        if( ++occupied > array.length / 2 )
            rehash( );
        
        return true;
    }

    private void rehash( )
    {
        HashEntry<AnyType> [ ] oldArray = array;

            // Create a new double-sized, empty table
        allocateArray( 2 * oldArray.length );
        occupied = 0;
        theSize = 0;

            // Copy table over
        for( HashEntry<AnyType> entry : oldArray )
            if( entry != null && entry.isActive )
                insert( entry.element );
    }

    /**
     * Method that performs quadratic probing resolution.
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos( AnyType x )
    {
        int offset = 1;
        int currentPos = myhash( x );
        
        while( array[ currentPos ] != null &&
                !array[ currentPos ].element.equals( x ) )
        {
            currentPos += offset;  // Compute ith probe
            offset += 2;
            if( currentPos >= array.length )
                currentPos -= array.length;
        }
        
        return currentPos;
    }

    /**
     * Remove from the hash table.
     * @param x the item to remove.
     * @return true if item removed
     */
    public boolean remove( AnyType x )
    {
        int currentPos = findPos( x );
        if( isActive( currentPos ) )
        {
            array[ currentPos ].isActive = false;
            theSize--;
            return true;
        }
        else
            return false;
    }
    
    /**
     * Get current size.
     * @return the size.
     */
    public int size( )
    {
        return theSize;
    }
    
    /**
     * Get length of internal table.
     * @return the size.
     */
    public int capacity( )
    {
        return array.length;
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return the matching item.
     */
    public boolean contains( AnyType x )
    {
        int currentPos = findPos( x );
        return isActive( currentPos );
    }

    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    private boolean isActive( int currentPos )
    {
        return array[ currentPos ] != null && array[ currentPos ].isActive;
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( )
    {
        doClear( );
    }

    private void doClear( )
    {
        occupied = 0;
        for( int i = 0; i < array.length; i++ )
            array[ i ] = null;
    }
    
    // this is my own hash function that is different from the textbook
    /**
     * A hash routine for objects with hashcode
     * 
     * @param x the obeject with hashcode to hash
     * @return the hash value
     */
    private int myhash( AnyType x )
    {
    	String str = x.toString();
    	int hashVal = x.hashCode();
    	
    	for(int i = 0; i < str.length(); i++)
    		hashVal = 57 * hashVal + str.charAt(i);
        
        hashVal %= array.length;
        if( hashVal < 0 )
            hashVal += array.length;

        return hashVal;
    }
    
    private static class HashEntry<AnyType>
    {
        public AnyType  element;   // the element
        public boolean isActive;  // false if marked deleted

        public HashEntry( AnyType e )
        {
            this( e, true );
        }

        public HashEntry( AnyType e, boolean i )
        {
            element  = e;
            isActive = i;
        }
	
    }
    
    private void allocateArray( int arraySize )
    {
        array = new HashEntry[ nextPrime( arraySize ) ];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
    
    // a small test for the hash table
    public static void main(String[] args){
    	myHashtable<String> ht = new myHashtable<String>();
    	ht.insert("hello");
    	ht.insert("world");
    	
    	if(ht.contains("hello") && ht.contains("world"))
    		System.out.println("Hello world, my hash table.");
    	
    }
    
    
}
