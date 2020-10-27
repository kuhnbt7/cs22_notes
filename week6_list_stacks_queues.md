## Week 6 - List stacks and queues

### Traversing a linked list (cont)

To traverse a linked list, we use a helper variable `trav`. Sometimes one helper variable is not enough, for instance if we are inserting a new character in a sorted linked list of characters. 

In this case, we use `trav` to find the right position in the list, but when it has, it has overshot the position where we want to insert the character. So we use a second helper variable `trail` which stays one behind `trav`.

So at each iteration, we make `trail = trav` and `trav = trav.next`. Then when we find our insertion point we have `trail` pointing to the place we want to insert the new character.

## Sequences
### Sequence: an ordered collection of items

We can implement a sequence using an array or linked list

With an array:

- you can access any element in constant time
- no extra memory is needed for links between items

But:

- you have to preallocate the size of array
- insertion and deletion can require shifting items to the left or right

With a linked list:

- Locations in memory are random - not next to each other
- You can grow to an arbitrary length
- Insertion/deletion doesn't require shifting

But:

- There's no random access
- You need extra memory for links between elements

## List ADT

### List: sequence where you can access, insert, and remove items at any position in the sequence

Operations supported:

- getItem(i)
- addItem(item, i)
- removeItem(i)
- length()
- isFull()

### Implementing list with an array:

```
public class ArrayList implements List{
    private Object[] items;
    private int length;

    public ArrayList(int maxSize){
        this.items = new Object[maxSize];
        this.length = 0;
    }

    public int length(){
        return this.length;
    }

    public boolean isFull(){
        return (this.length == this.items.length);
    }

    public boolean addItem(Object item, int i){
        if (item == null || i < 0 || i > this.length){
            throw new IllegalArgumentException();
        }
        else if (this.isFull){
            return false;
        }
        // shift items in list
        for (int j = this.length - 1; j >= i; j--){
            this.items[j + 1] = this.items[j];
        }
        this.items[i] = item;
        this.length ++;
        return true;

    }

    public Object removeItem(int i){
        if (i < 0 || i >= this.length){
            return new IndexOutOfBoundException();
        }
        Object removed = this.items[i];

        // shift items after i to the left

        for (int j = i; j < this.length - 1; j++){
            this.items[j] = this.items[j + 1]
        }
        this.items[this.length -1] = null;

        this.length --;
        return removed;
    }

    public Object getItem(int i){
        if (i < 0 || i > this.length){
            throw new IndexOutOfBoundsException();
        }
        return items[i];
    }

    public String toString(){
        String str = "{";

        if (this.length > 0){
            for (int i = 0; i < this.length; i++){
                str = str + this.items[i] + ", ";
            }
            str = str + this.items[this.length -1];
        }
        str = str + "}";

        return str;
    }
    
}
```

### Implementing list with linked list
Note: we have a wrapper class here that 'contains' the linkedlist

We also use a <i>dummy head node</i> at the start of the linked list. It doesn't count towards the length. It allows us to handle special cases in adding and removing nodes.

Making node an inner class allows LLList to access private fields while restricting their access from outside LLList.
```
public class LLList implements List{
    private Node head;
    private int length;

    private class Node{

        private Node(Object i, Node n){
            item = i;
            next = n;
        }
    private Object item;
    private Node next;

    public LLList(){
        this.head = new Node(null, null);
        this.length = 0;
    }
 
    public boolean isFull(){
        return false;
    }

    private Node getNode(int i){
        // return the node at index i
        Node trav = this.head;
        int travIndex = -1;
        while (travIndex < i){
            travIndex ++;
            trav = trav.next;
        }
    }

    // addItem makes use of the dummy node
    public boolean addItem(Object item, int i){
        if (item == null || i < 0 || i > this.length){
            throw new IllegalArgumentException();
        }

        Node newNode = new Node(item, null);
        Node prevNode = this.getNode(i - 1);
        newNode.next = prevNode.next;
        prevNode.next = newNode;
        this.length ++;
        return true;
    }

    public Object removeItem(int i){
        if (i < 0 || i > this.length){
            throw new IndexOutOfBoundsException();
        }
        Node prevNode = getNode(i -1);
        Object removed = prevNode.next.item;;
        prevNode.next = prevNode.next.next;
        length --;
        return removed;
    }

    public String toString(){
         String str = "{";
        
        Node trav = head.next;    // skip over the dummy head node
        while (trav != null) {
            str = str + trav.item;
            if (trav.next != null) {
                str = str + ", ";
            }
            trav = trav.next;
        }
        
        str = str + "}";
        return str;
    }
    }
}
```

### List Efficiency

**ArrayList**
- getItem(): O(1)
- addItem():
    - best: O(1) - at the end of list no shifting
    - worst: O(n) - at the beginning have to move all items
    - average: O(n) - have to shift half the items
- removeItem():
    - best: O(1) - removing last item
    - worst: O(n) - shifting all items
    - average: O(n)
- Space efficiency: O(m) where m is maximum anticipated length


**LLList**
- getItem():
    - best: O(1)
    - worst: O(n) 
    - average: O(n) (halfway thru the list is still linear)
- addItem():
    - best: O(1) to add to front of list
    - worst: O(n) to add to end
    - average: O(n) to add to middle
    - You <i>could</i> maintain a reference to the end of the list which would make adding to the end O(1)
- removeItem():
    - best: O(1): removing first item
    - worst: O(n): removing last
    - average: O(n)

### Iterator

**Motivation:** let's say we want to count the number of occurrences of an item in a linked list. For an ArrayList that's fine, we just iterate through the items and check to see if each item is the item we're interested in. For a LLList, though, each time we do getItem(i), the program has to iterate through all elements prior to i in order to retrieve the item at i.

**Solution:** provide an iterator. An iterator efficiently iterates through a list. An iterator has a `hasNext()` method which tells you if there is an additional item, and `next()` which gives the next item and advances the iterator.

The iterator interface looks like this:

```
public interface ListIterator{
    boolean hasNext();
    object next();
}
```

We implement the LLListIterator inside the LLList class (like Node). It looks like this:

```
public class LLList{
    private Node head;
    private int length;

    private class LLListIterator implements ListIterator{
        private Node nextNode; // points to node with next item
        public LLListIterator(){
            this.nextNode = this.head.next;
        }
    }

        public ListIterator iterator(){
            return new LLListIterator();
        }

        public boolean hasNext(){
            return (this.nextNode != null);
        }

        public Object next(){
            // throw exception is nextNode is null

            Object item = this.nextNode.item;
            this.nextNode = this.nextNode.next;
            return item;
        }
}
```


