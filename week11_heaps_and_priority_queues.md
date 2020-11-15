## Week 11: Heaps and priority queues

### Priority Queue

A collection in which each item has an associated number called a _priority_. We use a higher priority for items which are more important.

Example: a shared resource like a CPU

Key operations:
- insert (with position based on priority)
- remove

One way to implement priority queue is with a _heap_.

### Complete binary tree

A binary tree of height h is complete if:
- levels 0 thru h-1 are fully occupied
- there are no gaps to the left of a node in level h (gaps must be to the right)

### Representing a complete binary tree

Tree's nodes are stored in the array in the order given by a level-order traversal - top to bottom, left to right

Given a node in a[i]:
- its left child is in a[2i + 1]
- its right child is in a[2i +2]
- its parent is in a[(i-1) / 2] (using integer division)

### Heaps
A heap is a complete binary tree where every interior node is greater than or equal to its children.

The largest value is always at the root. The smallest value is always in one of the leaf nodes.

We are using _max-at-top_ heaps; there are also _min-at-top_ heaps which are the inverse.

### How to compare objects

We can't just use comparison operators (<, >, ==). The references would be compared, not the fields. We need to use a method to compare them.

The `Comparable` interface does this.

```
public interface Comparable<T>{
    public int compareTo(T other);
}
```

It is used when defining classes of objects that can be ordered.

Returns negative integer if item1 comes before item2, positive if item1 comes after item2, and 0 if they are equal (in the ordering).

As a result of the above properties, you can use the same operators:

item1 < item2 is equivalent to item1.compareTo(item2) < 0

### Heap implementation

We make heap generic, but the objects it accepts must implement Comparable:

```
public class Heap<T extends Comparable<T>> {
    private T[] contents;
    private int numItems;

    public Heap(int maxSize) {
        contents = T([])new Comparable[maxSize];  // we're    // casting here to the type T
        numItems=0;
    }
}
```

So to make a heap we have to use Integer, String, etc not int (primitives) because the type has to implement Comparable.

So going back to our objective, we want to get the item with the largest priority. This is easy - it's the root. But then we need to move the largest remaining item to the root, while maintaining a complete tree with each node >= children.

Algorithm:
1. Make a copy of largest item
2. Move last item to root (rightmost item in bottom level)
3. 'Sift down' new root item until it is >= children (or a leaf)
4. Return the largest item

Sifting down: compare the item at root to its largest child. If it's smaller, swap it with its largest child. Repeat until a leaf or it's not smaller than child.

```private void siftDown(int i) { // assume i=0
    T toSift = contents[i];
    int parent = i;
    int child = 2 * parent + 1;
    while (child < numItems){
        // if right child bigger, set child to be its index 
        if (child < numItems - 1 && contents[child].compareTo(contents[child + 1]) < 0) {
            child = child + 1;
        }
        if (toSift.compareTo(contents[child] >= 0){
            break;
        }
        // move child up and move down one level in tree
        contents[parent] = contents[child];
        parent = child;
        child = 2 * parent + 1;
    }
    contents[parent] = toSift;
}
```

Above we don't actually make individual swaps because it requires so much movement - we wait until we see where the item will go, then make the swap.

remove() calls siftDown:

```
public T remove(){
    // check for empty heap goes here
    T toRemove = contents[0];

    contents[0] = contents[numItems - 1];
    numItems --;
    siftDown(0);
    return toRemove;
}
```

### Inserting an item in a heap

Algorithm:
1. put the item in the next available slot (grow if needed)
2. 'sift up' the new item until it is <= its parent (or it becomes root item)

(we're skipping siftUp since it's simple)

```
public void insert (T item){
    if (numItems == contents.length){
        // code to grow array goes here
    }
    contents[numItems] = item;
    siftUp(numItems);
    numItems ++;
}
```

### Time complexity of a heap

A heap containing n items has height <= log<sub>2</sub>n. Why? Because the tree is balanced.

Thus, removal and insertion are both O(logn)
- for removal, go down at most log<sub>2</sub>n levels when sifting down; do a constant number of operations per level
- for insertion, go up at most log<sub>2</sub> levels when sifting up; constant number of operations per level

Thus, we can use heap for O(logn) time priority queue.

So back to our original problem - using a heap for a priority queue.

To implement this we:
- order items in heap according to their priorities
    - every item with have priority >= its children
    - highest priority item will be in root node
- get highest priority item by calling heap.remove()

We'll use a wrapper class for items to put in priority queue.

```
public class PQItem implements Comparable<PQItem> {
    // group an arbitrary object with its priority
    private Object data;
    private int priority;

    public int compareTo(PQItem other){
        // error checking goes here
        return (priority - other.priority;
    }
}
```

Example: PQItem item = new PQItem("Dave sullivan", 5)

compareTo returns negative int if item1 has lower priority than item2, positive int if item1 has higher priority than item2.

### Using a heap to sort an array

Recall selection sort: find smallest remaining element and swap it into place

Selection sort isn't efficient because we have to do scans of entire remaining array to find each next element.

**Heapsort** repeatedly finds the _largest_ remaining element and puts it into place.

Since it's a heap, it can find/remove largest remaining item in O(logn) time.

First, we have to convert a normal array into a heap.

Algorithm:
1. Start with parent of last element:
    contents[i], where i = ((n -1) - 1)/2 = (n - 2) / 2
2. sift down contents[i] and all elements to the left

So we're basically starting with the lowest thing that needs to be sifted and working our way up.

### Heapsort

Pseudocode:

```
heapSort(arr){
    // turn array into max-at-top heap
    heap = new Heap(arr);
    endUnsorted = arr.length - 1;
    while (endUnsorted > 0){
        // get largest remaining element and put it at
        // the end of the unsorted portion of array
        largestRemaining = heap.remove();
        arr[endSorted] = largestRemaining;
        endUnsorted --;
    }
}
```

(need to view slides to see this in action)

So, time complexity of heapsort:
- Time complexity of creating a heap from an array - O(nlogn). Because you sift down n / 2 elements, at most O(logn) steps per sift.
- Time complexity of sorting the array - O(nlogn). Because we remove n - 1 elements, which has at most O(logn) steps per removal.

So total time complexity - O(nlogn)

Comparison of sorting algorithms:

!['alt text'](screenshots/week11_screenshot_1.PNG 'screenshot from class')

Heapsort matches mergesort for best worst-case time complexity, but has better space complexity.

Insertion sort still best for almost sorted array. Heapsort is bad for this since it scrambles everything.

Quicksort still fastest in average case.

### State-space search revisited

We previously considered 3 algorithms for state-space search, all uninformed: breadth-first search, depth-first search, iterative-deepening search.

_Informed_ search algorithms attempt to consider more promising states first. They associate a _priority_ with each successor state, based on an estimate of its nearness to the goal state. When choosing next state to consider, choose the one with the highest priority.

So we use a priority queue to store yet-to-be-considered search nodes.

### Estimating the remaining cost

Priority of a state is based on remaining cost - cost of getting from state to closest goal state.

For most problems we can't determine exact remaining cost.

We estimate it using a _heuristic function_. Heuristic = rule of thumb.

h(x) = estimated remaining cost for state x

To find accepatble states, we need an _admissable_ heuristic - one that never overestimates remaining cost.

Back to the eight puzzle. We use manhattan distance as heuristic function. Manhattan distance is vertical + horizontal distance.

!['alt text'](screenshots/week11_screenshot_2.PNG 'snapshot 2 from class')

For each cell, we calculate manhattan distance from its goal state.

### Greedy search

Priority of state, p(x) = -1 * h(x)
- we multiply by negative 1 so states closer to the goal have higher priorities.

!['alt text'](screenshots/week11_screenshot_3.PNG 'screenshot 3 from class')

Greedy search tries highlighted successor first since is has higher priority.

Greedy search is:
- _incomplete_: it may not find a solution
- _not optimal_: the solution it finds might not have the lowest cost
    - it fails to consider the cost of getting _to_ the current state

### A* search

Priority of state, p(x) = -1 * (h(x) + g(x)) where g(x) is cost of getting from the initial state to x.

So this does take into account the cost of getting to the current state.

A* is complete and optimal provided that:
- h(x) is admissable
- g(x) increases or stays the same as the depth increases

Time and space complexity are still O(b<sup>d</sup>) where b is some constant and d is solution depth.

A* typically visits fewer states than other optimal algorithms. A comparison:

!['alt text'](screenshots/week11_screenshot_4.PNG 'screenshot from class 4')

### Implementing informed search

We'll add new subclasses of abstract Searcher class.

```
public class GreedySearcher extends Searcher {
    private Heap<PGItem> nodePQueue;

    public void addNode(SearchNode node) {
        nodePQueue.insert(
            new PQItem(node, -1 * node.getCostToGoal()));
    }
}
```

### Hash tables

Ideal case: searching = indexing

We'd have optimal efficiency if we could treat a key as an index into an array.

Example: data about sports team
- key = jersey number (0 - 99)
- class for individual player's record:

```
public class Player {
    private int jerseyNum;
    private String firstName;
}
```
Store player's records in an array: 
    Player[] teamRecords = new Player[100];

In this case, search and insertion are O(1).

In real world problems, things are not this simple.

1. Multiple data items can have same key
2. Key values might not work as array indices
3. Range of possible values might prevent us from giving each key value its own position in array.

To handle these problems, we use _hashing_:
- use a _hash function_ to convert keys into array indices
- use techniques to handle cases in which multiple keys are assigned the same hash value

The resulting data structure is a _hash table_.

The **hash function** defines a mapping from keys to integers.

A simple hash function for keys of lower-case letters: h(key) = ASCII value of first character - ASCII value of 'a'

Example:
- h("ant") = ASCII for 'a' - ASCII for 'a' = 0
- h("cat") = ASCII for 'c' - ASCII for 'a' = 2

h(key) is known as key's hash code.

A _collision_ occurs when items with different keys are assigned the same hash code.

### Dealing with collisions - separate chaining

- Each position in the hash table serves as a _bucket_ that can store multiple data items.

Two options:
1. each bucket is itself an array - but need to preallocate, and bucket can fill up
2. each bucket is a linked list - items with same hash code are 'chained' together

### Dealing with collisions - open addressing

- When position assigned by hash function is occupied, find another open position

The process of finding an open position is called _probing_. We need to use probing both when inserting and searching.

#### Linear probing

Probe sequence: keep incrementing index and wrap around as needed. If h(key) is full, try h(key +1), h(key +2), etc.

Advantage to linear probing: not going to miss an open cell

Disadvantage: we end up with clusters of occupied cells that lead to longer subsequent probes.

### Quadratic probing

Probe sequence: h(key), h(key) + 1<sup>2</sup>, h(key) + 2<sup>2</sup>, etc, wrapping around as necessary.

Advantage to quadratic probing: smaller clusters of occupied cells

Disadvantage: may fail to find existing open position



