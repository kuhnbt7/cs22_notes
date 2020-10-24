/* 
 * Bag.java
 * 
 * Computer Science E-22, Harvard University
 *
 * Modified by: Ben Kuhn, kuhnbt@gmail.com
 */

/**
 * An interface for the Bag ADT.
 */
public interface Bag {
    /** 
     * adds the specified item to the Bag.  Returns true on success
     * and false if there is no more room in the Bag.
     */
    boolean add(Object item);
    
    /** 
     * removes one occurrence of the specified item (if any) from the
     * Bag.  Returns true on success and false if the specified item
     * (i.e., an object equal to item) is not in the Bag.
     */
    boolean remove(Object item);
    
    /**
     * returns true if the specified item is in the Bag, and false
     * otherwise.
     */
    boolean contains(Object item);
    
    /**
     * returns true if the calling object contain all of the items in
     * otherBag, and false otherwise.  Also returns false if otherBag 
     * is null or empty. 
     */
    boolean containsAll(Bag otherBag);
    
    /**
     * returns the number of items in the Bag.
     */
    int numItems();
    
    /**
     * grab - returns a reference to a randomly chosen in the Bag.
     */
    Object grab();
    
    /**
     * toArray - return an array containing the current contents of the bag
     */
    Object[] toArray();
    
    /**
     * capacity - returns the maximum number of items the Bag is able to hold.
     */
    int capacity();
    
    /**
     * isFull - returns true if the Bag is full, false otherwise
     */
    boolean isFull();
    
    /**
     *  increaseCapacity - increases the capacity of the Bag by the specified amount
     */
    void increaseCapacity(int amount);
    
    /**
     * removeItems - removes all instances of items in Bag other from the Bag.
     * Returns true if one or more items is removed, false otherwise
     */
    boolean removeItems(Bag other);
    
    /**
     * unionWith - returns a Bag with one occurrence of any item found in the Bag
     * or the Bag other
     */
    Bag unionWith(Bag other);
    
} 
