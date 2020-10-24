## State-Space Search (cont)

There are different ways to decide how to test the sequences of steps in state-space search.

The top node is called the _root_, and holds the initial state.

The predecessor references are _edges_.

The _depth_ of a Node N is the number of edges on path from Node to the root.

To represent a search strategy:

We use a _searcher_ object. It maintains a data structure with the nodes we have yet to consider.

Different search strategies have different searcher objects. 

A searcher may have a depth limit.

Every searcher must be able to do:

- add a single node  (or list of nodes) to collection of yet-to-be-considered nodes
- indicate whether there are nodes still to be considered
- return the next node to be considered
- determine if a node is at or beyond depth limit

`Searcher` is an abstract superclass which defines objects and methods used by all search algorithms. It includes one or more abstract methods which are defined in subclasses. It cannot be instantiated.

Implementation:

```
public abstract class Searcher{
    private int depthLimit;
    public abstract void addNode(SearchNode Node);
    public abstract void addNodes(List nodes);
    public abstract boolean hasMoreNodes();
    public abstract SearchNode nextNode();
    public void setDepthLimit(int limit){
        this.depthLimit = limit;
    }
    public boolean depthLimitReached(SearchNode node){
        ...
    }
}
```
Search strategies will extend this class and implement abstract methods.

We use abstract class instead of interface because we can define instance variables whereas interface cannot.

When we find the solution, we will accept any Searcher object, which because of polymorphism will allow us to pass in different search strategies.

The interpreter uses _dynamic binding_ to figure out which version of the method should be called.

When generating successors, we don't include states that we've already seen (on the same path). It's not really practical to look on different paths though.

### Breadth-first search

When choosing a node from those yet-to-be-considered, always choose the shallowest option.

So consider eveything at depth 0, then everythign at depth 1, etc.

You get this behavior if you use a queue.

```
public class BreadthFirstSearcher extends Searcher{
    private Queue<SearchNode> nodeQueue;

    public void addNode(SearchNode node){
        nodeQueue.insert(node);
    }
    public SearchNode nextNode(){
        return nodeQueue.remove();
    }
    ...
}
```

At each node for level 1 of BFS, the algorithm considers that node and then adds all its successors to the end of the queue (to be evaluated after all the level 1 node are evaluated). As the level 1 nodes are taken out of the queue, they remain 'reachable' because their successor nodes retain references to them. This means they also stay in memory. If you reach a depth limit the first nodes might lose their references eventually.

Features of BFS:

- It is _complete_: if there's a solution, it will find it
- For problems like the 8 puzzle where all moves have equal weight, it will find the optimal (minimal-cost) solution
    - might not be optimal if different operators have different costs
- Time and space complexity:
    - assume each node has b successors
    - finding solution at depth d:
        - nodes considered (and stored) = 1 + b + b<sup>2</sup>... + b<sup>d</sup> = O(b<sup>d</sup>) - not good

### Depth-first search

When choosing nodes to consider, always choose the deepest one

So you always go down a given path until stuck, then backtrack

This uses a stack - snce we can only add to the top, it takes the successors first:

```
public class DepthFirstSearcher extends Searcher{
    private Stack<SearchNode> nodeStack;
    
    public void addNode(SearchNode node){
        this.nodeStack.push(node);
    }
    public SearchNode nextNode(){
        this.nodeStack.pop();
    }
}
```
_When you reach depth limit for a given path, all nodes on that path are removed from the stack and don't retain any references_. Therefore the memory used for that path can be reclaimed by the garbage collector. This makes memory usage better than BFS.

Much better space complexity:
- let m be max depth of a search node
- DFS only stores a single path thru the tree along with siblings at given depth
- space complexity = O(b*m)  where b is max number of successors
- Time complexity: if there are many solutions, BFS can often find one quickly. Worst case is still O(b<sup>m</sup>)

- It can also get stuck going down the wrong path - so it is _neither complete nor optimal_ (because you might put a depth limit that is too short).

### Iterative deepening search

- Eliminates need to choose depth limit
- Basic idea:
```
d = 0;
while (true){
    // perform DFS with depth limit of d
    d++;
}

So it tries DFS with multiple depth limits.

This combines best of BFS and DFS. It is complete and optimal when BFS is.

BFS, DFS, and IDS are examples of _uninformed_ search algorithms. They consider states without regard to how close those states are to reaching a goal. It's better to use _informed_ search algorithms.

