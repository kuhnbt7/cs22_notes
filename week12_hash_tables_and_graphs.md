## Week 12 - Hash function and Graphs

Back to quadratic probing: it can be easy to end up with a situation like this:

!['alt text'](screenshots/week12_screenshot_1.PNG 'screenshot from class')

because there are no perfect squares with last length digit 2, 3, 7, or 8.

To solve for this we use **double hashing**

### Double hashing

Use 2 hash functions:
- h1 computes the hash code
- h2 computes the increment for probing

probe sequence: h1, h1 + h2, h1 + 2h2, ...

Combines good features of linear and quadratic:

- reduces clustering
- will find open position if there is one, if table size is prime number

### Removing items under open addressing

Since to search for items we use the same sequence of probes as inserting an item, it's hard to conclude that an item is **not** in the table if we're allowing items to be removed. Because when we get to an empty cell, how do we know it has always been empty? Maybe it was full so the item we're looking for is farther in the table.

To fix this we distinguish between:
- _removed positions_ that previously held an item
- _empty positions_ that never held an item

During searching we don't stop when we get to a removed position. We can insert items though.

### Inteface for Hash Tables

```
public interface HashTable{
    boolean insert(Object key, Object value);
    Queue<Object> search(Object key);
    Queue<Object> remove(Object key);
}
```

`insert` takes a key-value pair and returns:
- true if the pair can be added
- false if it can't (_overflow_)

`search` and `remove` take a key, and return a queue with all the values associated with that key.

### Implementation with open addressing

```
public class OpenHashTable implements HashTable{
    priivate class Entry{
        private Object key;
        private LLQueue<Object> values;
    }

    private Entry[] table;
    private int probeType;
}
```

We use a private inner class for entries in the hashtable, and a LLQueue for the values associated with a given key.

### Empty vs Null
When we remove a key and values, we:
- leave the `Entry` object in the table
- set its key and object values to null

Now we know there used to be something there.

### Probing using Double Hashing

```
private int probe(Object key){
    int i = h1(key);  // first hash function
    int h2 = h2(key);  // second hash function

    // probe until get an empty position or match
    while (table[i] != null && !key.equals(table[i].key)){
        i = (i + h2) % table.length;
    }
    return i;
}
```

# Avoiding an infinite loop

The while loop could lead to an infinite loop - if the key isn't in the table, and there are no empty positions we can reach during probing.

So we can stop probing after checking n positions because it will be repeating after that.

### Search and removal

```
public LLQueue<Object> search(Object key){
    // throw exception if key == null
    int i = probe(key);
    if (i == -1 || table[i] == null) {
        return null;
    } else {
        return table[i].values;
    }
}

public LLQueue<Object> remove(Object key){
    // throw an exception if key == null
    int i = probe(key);
    if (i == -1 || table[i]==null){
        return null;
    }

    LLQueue<Object> removedVals = table[i].values;
    table[i].key = null;
    table[i].values = null;
    return removedVals;
}
```

### Insertion

- We begin by probing for the key. Several potential cases:

1. The key is already in the table
    - add the value to the values in the key entry
2. The key is not in the table. Three subcases:
    a. encountered one or more removed positions during probing
        - put the key in the first removed position seen during probing. We can't stop when we find this because maybe the key exists later on.
    b. no removed position, get to an empty position
        - put the key, value pair there
    c. no removed or empty position
        - this is overflow, return false

### Dealing with overflow

Overflow is when you can't find a position for an item

- When does it occur?

- linear probing: when all positions are occupied
- quadratic probing: when all positions are occupied, or the probe sequence can't reach unoccupied slots
- double hashing:
    - if the table size is prime, same as linear
    - if not, same as quadratic

To avoid overflow and make searches faster, we grow the hash table if % of occupied positions gets too big.

But, when growing the table we have to rehash everything, because items might have ended up in different positions if the table were larger.

### Implementing the hash function

Characteristics of a good hash function:
1. efficient to compute
2. Uses the entire key
    - changing any digit should change the hash code
3. Distributes keys more or less uniformly across the hash table
4. Must be a function (key must always get same hash code)

In java every object has a hashCode() method.

Version inherited from object uses memory location.

### Hash function for strings: version 1

h<sub>a</sub> = sum of characters' ASCII values

So all permutations of a set of characters get the same code.

E.g., h<sub>a</sub> 'eat' = h<sub>a</sub>'tea'

- Range of possible hash codes is limited
    Example: h<sub>a</sub>'a    ' (a and 4 spaces) equals 225. h<sub>a</sub>'zzzzz' equals 610.

### Hash function for strings: version 2

Compute a weighted sum of ASCII values:
<code>h<sub>b</sub> = a<sub>0</sub>b<sup>n-1</sup> + ... + a<sub>n-2</sub>b + a<sub>n-1</sub></code>

where a<sub>i</sub> = ASCII value of ith character
b = a constant
n = number of characters

Multiplying by powers of b allows the positions of the characters to affect the hash code.

We may get arithmetic overflow, and thus code may be negative. We adjust when this happens.

Java uses this function with b=31 in the hashCode() method of String class.

### Hash table efficiency

- In best case, search and insertion are O(1)

- In worst case, search and insertion are linear.
    - open addressing: O(m) where m = size of hash table
    - separate chaining: O(n) where n = number of keys

With good choices of hash function and table size, comlpexity is better than O(logn) and approaches O(1).

_load factor_ = # of keys in table / size of table

To prevent performance degradataion:
- open addressing: keep load factor < 1/2
- separate chaining: keep load factor < 1

Bigger tables use more memory but have better performance.

### Limitations of hash table

- It can be hard to come up with good hash function
- items in hash table aren't ordered by keys. So we can't easily:
    - print contents in sorted order
    - perform a range search
    - perform a rank search (get largest, second largest, etc)
    We can do all these things with a search tree


## Graphs

What is a graph?

It consists of:
- a set of _vertices_ (aka _nodes_)
- a set of _edges_ (aka _arcs_) each of which connects a pair of vertices

Example: a highway graph. Vertices represent cities, edges represent highways.

This is a _weighted_ graph, with a cost associated with each edge.

!['alt text'](screenshots/week12_screenshot_2.PNG 'screenshot from class')

### Relationships between vertices

Two vertices are _adjacent_ if they are connected by a single edge.

_Neighbors_: collection of all vertices adjancent to a vertex.

_Path_: sequence of edges that connect 2 vertices

A graph is _connected_ if there is a path between any 2 vertices

A graph is _complete_ if there is an edge between every pair of vertices.

A _directed_ graph has a direction associated with each edge, depicted using an arrow. Edges are often represented using an order pair of form (start vertex, end vertex).

### Cycles in a graph

A _cycle_ is a path that:
- leaves a vertex using one edge
- returns to that vertex using a **different** edge

An _acyclic_ graph has no cycles.

### Trees vs graphs

A tree is a special type of graph
- connected, undirected, and acyclic
- we usually single out one vertex as a root, but graph theory doesn't require this

### Spanning trees

A spanning tree is a subset of a connected graph that contains:
- all of the vertices
- a subset of the edges that form a tree

### Representing a graph: option 1

- Use an _adjacency matrix_: a 2 dimensional array in which element [r][c] = the cost of going from vertex r to vertex c

Example:

!['alt text'](screenshots/week12_screenshot_3.PNG 'screenshot from class')

Use a special value to indicate there's no edge from r to c. Shown as shaded cell above. Can't use zero, because there might be zero cost.

This representation wastes space if graph is sparse (few edges per vertex); is memory-efficient if the graph is dense.

### Representing a graph: option 2

- Use one _adjacency list_ for each vertex. This is a linked list wth all info on the edges coming from that vertex.

Example:
!['alt text'](screenshots/week12_screenshot_4.PNG 'screenshot from class')

Ths uses less memory if graph is sparse.

Our representation uses option 2, and a linked list to represent the array on the left.

```
public class Graph {
    private class Vertex {
        private String id;
        private Edge edges;
        private Vertex next;
        private boolean encountered;
        private boolean done;
        private Vertex parent;
        private double cost;
        ...
    }
    private class Edge {
        private Vertex start;
        private Vertex end;
        private double cost;
        private Edge next;
    }
    private Vertex vertices; 
    ...
}
```

### Traversing a graph

Involves starting at some vertex and visiting all vertices that can be reached from that vertex.

If a graph is connected, all vertices will be visited.

We'll consider 2 types of traversals:

- depth first: proceed as far as possible along a path before backing up
- breadth first: visit a vertex, visit its neighbors, visit unvisited vertices 2 edges away, 3 edges, etc.

Web crawler is example of graph traversal.

### Depth first traversal

- Visit a vertex, then make recursive calls on all its unvisited neighbors

```
private static void dfTrav(Vertex v, Vertex parent) {
    System.out.println(v.id);
    v.done = true;
    v.parent = parent;

    Edge e = v.edges;
    while (e != null){
        Vertex w = e.end;
        if (!w.done){
            dfTrav(w, v);
        e = e.next;
        }
    }
}
```

The edges obtained by following the parent references for a spanning tree with the origin of the traversal as its root.

### Checking for cycles

To check for cycles on an undirected graph:

- perform a depth-first traversal, marking vertices as visited
- when considering neighbors of a visited vertex, if we discover one marked as visited, there must be a cycle

If no cycles are found, it's acyclic.

This does not work for directed graphs.

### Breadth first traversal

- Use a queue to store vertices we've seen but not yet visited

```
private static void bfTrav(Vertex origin) {
    origin.encountered = true;
    origin.parent = null;
    Queue<Vertex> q = new LLQueue<Vertex>();
    q.insert(origin);

    while(!q.isEmpty()) {
        Vertex v = q.remove();
        System.out.println(v.id);
        // add v's unencountered neighbors to the queue
        Edge e = v.edges;
        while (e != null){
            Vertex w = e.end;
            if (!w.encountered){
                w.encountered = true;
                w.parent = v;
                q.insert(w);
            }
            e = e.next;
        }
    }
}
```




