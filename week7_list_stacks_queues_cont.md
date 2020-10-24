## Week 7 - List, Stacks and Queues, continued

### Stack ADT

- Stack is also a sequence, in which:
    - items can only be added/removed at the top
    - you can only access the item that is currently at the top

Operations:
- push: add an item to the top of the stack
- pop: remove item at top of stack
- peek: get the item at the top but don't remove it
- isEmpty
- isFull

First version of Stack interface:
```
public interface Stack(){
    boolean push(Object obj);
    Object pop();
    Object peek();
    boolean isEmpty();
    boolean isFull();
}
```
push() returns false if the stack is full, true otherwise.

Implementation:
```
public class ArrayStack implements Stack{
    private Object[] items;
    private int top; // index of top item

    public ArrayStack(int maxSize){
        items = new Object[maxSize];
        top = -1;
    }
}
```
Items are added from left to right - so push and pop don't require any shifting.

Recall that if we have an ArrayStack of type Object and we store a String in it, then if we pop the String and access it we have to type cast it in order to save it as a String and not an object.

We'd like to be able to limit a collection to one type (like String).

We can do this by using a _generic_ interface and class.

We include a type variable _T_ in the header. It's used as a placeholder for the actual type of the items.

```
public interface Stack <T>{
    boolean push(T item);
    T pop();
    T peek();
    boolean isEmpty();
    boolean isFull();
}
```

Then when we create an ArrayStack, we specify the type of items that we intend to store in the stack:  
`ArrayStack<String> s1 = new ArrayStack<String>(10);`

Implementation:

Java doesn't allow you to use a type variable to create an object or array. So we can't do: `items = new T[maxSize];`  
Instead we have to cast the array to the desired type:
```
public ArrayStack(int maxSize){
    items = (T[])new Object maxSize;
    top = -1;
}
```

Code for ArrayStack:

```
public class ArrayStack<T> implements Stack{
    private T[] items;
    private int top;

    public ArrayStack(int maxSize){
        if (maxSize == 0){
            throw new IllegalArgumentException("maxSize must be positive");
        }
        this.items = (T[])new Object maxSize;
        this.top = -1;
    }

    public boolean isEmpty(){
        return (this.top == -1);
    }

    public boolean isFull(){
        return (this.top == this.maxSize - 1)
    }

    public boolean push (T item){
        if (item == null){
            throw new IllegalArgumentException();
        } else if (this.isFull()){
            return false;
        } 
        this.top++;
        this.items[this.top] = item;
        return true;
    }

    public T pop(){
        if (this.isEmpty()){
            return null;
        }
        T removed = this.items[this.top];
        this.items[this.top] = null;
        this.top --;
        return removed;
    }

    public T peek(){
        if (this.isEmpty()){
            return null;
        }
        return this.items[this.top];
    }
```

Now implementing a stack with a linked list. You only need one field, a reference to the topmost item. The top item is the leftmost item in LLStack, vs rightmost item in ArrayStack. No need for a dummy head node, since we always insert/remove at the front of the list. Again, we don't have to preallocate memory for LLStack.

```
public class LLStack<T> implements Stack{

    private class Node{ // private inner class
        private T item;
        private Node next;

        private Node (T i, Node n){
            this.item = i;
            this.node = n;
        }
    }

    private Node top;
    
    public LLStack(){
        this.top = null;
    }
    public boolean isEmpty(){
        return (this.top == null);
    }

    public boolean isFull(){
        return false;
    }

    public boolean push (T item){
        Node newNode = new Node(item, this.top);
        top = newNode;
        return true;
    }

    public T pop(){
        if (this.isEmpty()){
            return null;
        }
        T removed = top.item;
        this.top = this.top.next;
        return removed;
    }

    public P peek(){
        if (this.isEmpty()){
            return null;
        }
        return this.top.item;
    }

}
```

Applications of Stacks:
- Converting a recursive algorithm to iterative one (use stack to empulate runtime stack)
- Making sure delimiters (params, brackets) are balanced
    - push open delimiters onto stack. When you reach close delimeter, pop item off stack and see if matches.
- Evaluating arithmetic expressions


### Queue ADT

Queue is a sequence in which:
- items are added at the rear and removed from the front (FIFO)
- you can only access the item that is at the front

Operations:
- insert: add item at the rear
- remove: remove item at front
- peek: get item at front but don't remove it
- isEmpty: test if queue is empty
- isFull: test if queue is full

Generic interface:

```
public interface Queue<T>{
    boolean insert(T item);
    T remove();
    T peek();
    boolean isEmpty();
    boolean isFull();
}
```

Implementation with Array:

```
public class ArrayQueue<T> implements Queue<T>{
    private T[] items;
    private int front;
    private int rear;
    private int numItems;

    public ArrayQueue(int maxSize){
        if (maxSize <= 0){
            throw new IllegalArgumentException("max size must be positive);
        }
        items = (T[]) new Object maxSize();
        this.front = 0;
        this.rear = -1;  // when we insert the first item    this is incremented to 0
        this.numItems = 0;
    }

    public boolean insert (T item){
        if (this.isFull()){
            return false;
        }
        this.rear = (this.rear + 1) % this.items.length;
        this.items[this.rear] = item;
        this.numItems++;
        return true;
    }

    public T remove(){
        if (this.isEmpty()){
            return null;
        }
        T removed = this.items[this.front];
        this.items[this.front] = null;
        this.front = (this.front + 1) / this.items.length;
        this.numItems --;
        return removed;
    }

    public T peek(){
         if (this.isEmpty()){
            return null;
        }
        return this.items[this.front];
    }

    public boolean isEmpty(){
        return (this.numItems == 0);
    }

    public boolean isFull(){
        return (this.numItems == this.items.length);
    }
}

```
Problem: what to do when we reach end of array? We don't want to have to shift everything.

When you add to rear and remove from front, the elements gradually shift to the right. Front becomes 1, 2, 3, etc and end becomes 8, 9, 10.

The solution is to maintain a circular queue. When we get to the end, we start over at the (now empty) beginning.

So we use the mod operator (%) when updating front or rear. It gives you the remainder of dividing by the length, so if front or rear is greater than length mod gives you the 'wraparound' index.

Another problem: when array is both full and empty, rear is one behind front

!['alt text'](screenshots/week7_screenshot_1.png 'screenshot from class')

This is why we maintain numItems - we can't easily distinguish empty from full.


With Linked List, we can efficiently remove an item at front and add an item to rear. LinkedList implementation:

```
public class LLQueue<T> implements Queue<T>{
    private class Node{
        private T item;
        private Node next;
        private Node(T i, Node n){
            this.item = i;
            this.next = n;
        }
    }
    private Node front;
    private Node rear;

    public LLQueue(){
        this.front = null;
        this.rear = null;
    }

    public boolean isEmpty(){
        return (this.front == null);
    }

    public boolean isFull(){
        return false;
    }

    public boolean insert(T item){
        Node newNode = new Node(item, null); // next will always be null
        if (this.isEmpty()){
            this.front = newNode;
            this.rear = newNode;
        } else {
            this.rear.next = newNode;
            this.rear = newNode;
        }
        return true;
    }

    public T remove(){
        if (this.isEmpty()){
            return null;
        }
        T removed = this.front.item;
        if (this.front == rear){ // one item
            this.front = null;
            this.rear = null;
        } else {
            this.front = this.front.next;
        }
        return removed;
    }

    public T peek(){
        if (this.isEmpty()){
            return null;
        }
        return this.front.item;
    }
}
```

Applications of queues:
- FIFO inventory control
- OS scheduling: processes, packets, etc.
- simulations of banks, airports, etc (lines)

### State-space search

**Searching** can be defined as finding a _sequence of actions_ that take you from an initial state to a goal state.

Example: n-queens:
- Initial state: an empty chessboard
- Actions: add or remove queen
- Goal state: n-queens placed, with no 2 queens on same row/col/diagonal

**State-space** all possible states that can be reached from initial state.

To formulate a search problem formulate 4 things:

1. Initial state
2. _Operators_ that take you from one state to another
3. _Goal test_ that determines whether you're in the goal state
4. _Costs_ associated with applying a given operator

Example: eight puzzle

Initial state: some configuration of tiles
Operators: move blank up, down, left, right  
Goal state: the eight numbers in order
Costs: cost of each action = 1 because they are all equal

Then:
If initial state is goal state, return it  
Otherwise, apply operators to generate all states that are one step from initial state (_successors_)

Different search strategies consider the successors in different orders, may use different data structures to stores the states.

When we generate a state, we create a _search node_ that contains:

- representation of state
- reference to node containing predecessor
- operator (action) that led from predecessor to this state
- number of steps from initial state to this one
- cost of getting from initial state to this one
- estimate of cost remaining to get to goal

The predecessor references connect the search nodes, creating a data structure known as a _tree_.

When we reach a goal, we trace up tree to find the solution.




