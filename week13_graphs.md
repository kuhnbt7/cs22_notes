## Week 13 - Graphs

Going back to our example of a highway graph, where weights between cities represent the cost of getting to that end city from a start city.

- Each `Vertex` object holds info about the vertex including an adjacency list of `Edge` objects
- A `Graph` object has a single field called `vertices` which is a reference to a linked list of `vertex` objects

A _spanning tree_ is a subset of a connected graph that contains all vertices and a subset of edges that connect the tree (removing cycles)

A _minimum spanning tree_ has the lowest cost among all possible spanning trees.

If all edges have unique costs, there is only one MST. If some have duplicate costs, there may be more than one.

Applications:
- shortest highway system for a set of cities
- smallest length of cable to connect a network

**Claim**: if you divide vertices into 2 disjoint subsets A and B, the lowest cost vertex connecting A and B must be part of the MST.

Proof:
1. Assume we create a MST (T) that doesn't include that vertex (v<sub>a</sub>, v<sub>b</sub>)
2. T must include a path from v<sub>a</sub> to v<sub>b</sub>, so it must include one of the other edges that span A and B, and each vertex on that edge must connect to v<sub>a</sub> and v<sub>b</sub>
3. Then adding v<sub>a</sub> and v<sub>b</sub> introduces a cycle
4. Removing the other edge makes the spanning tree have a lower total cost

Picture:
!['alt text'](screenshots/week13_screenshot_1.PNG 'screenshot from class')

### Prim's MST algorithm

Begin with any of the following subsets:
- A = one of the vertices
- B = all of the other vertices

Repeatedly:
- select the lowest cost edge (v<sub>a</sub>, v<sub>b</sub>) connecting a vertex in A to a vertex in B
- add (v<sub>a</sub>, v<sub>b</sub>) to the spanning tree
- move vertex v<sub>b</sub> from set B to set A
- continue until all vertices are in set A

Note we are not necessarily minimizing path between two edges - we are minimizing the total edge cost.

### Implementing Prim's algorithm

Use `v.done` to indicate membership in set A or B

We repeatedly scan the set of vertices to find the next edge to add.

This is O(EV) where E is number of edges and V is number of vertices.

We can do better by storing vertices in B in a heap-based priority queue.

The priority of a vertex is:
`-1 * cost of lowest-cost edge connecting x to a vertex in set A`

But, you need to update priorities over time because vertices are moving frm A to B.

We don't go into that here. But the efficiency is O(E * log V)

### Shortest-path problem

Applications:
- google maps
- routing internet traffic

For an _unweighted graph_, can do this:
- start a breadth-first traversal from origin, v
- stop traversal when you reach other vertex, w
- path from v to w in resulting (possibly partial) spanning tree is shortest

This is because with a breadth-first search you basically visit everything one node away, then 2 nodes, etc. and it is unweighted

### Dijkstra's algorithm

- Finds shortest path from a vertex to all other vertices

Basic idea:
- maintain estimates of shortest path from origin to every vertex (along with costs)
- refine these estimates as we traverse

We say a vertex w is _finalized_ if we have found the shortest path from v to w.

We do the following:
- find the unfinalized vertex w with lowest cost estimate (initially this is origin)
- mark w finalized
- examine each unfinalized neighbor x of w to see if there is a shorter path to x that passes through w
- if there is, update shortest-path estimate for x

### Implementation of Dijkstra

Similar to implementation of Prim

Use a heap-based priority queue to store unfinalized vertices.
- cost = -1 * cost of current shortes-path estimate
- need to update vertex's priority when update its shortes-path estimate
- time complexity = O(ElogV)

### Topological sort

Used to order vertices in directed acyclic graph (DAG)

Topological order: ordering of vertices such that if there is directed edge from A to B, a comes before b

Application: ordering courses according to prereqs. Directed edge from a to b indicates a is a prereq of b

A _successor_ of a vertex v in a directed graph is a vertex w such that (v, w) is an edge in the graph.

Basic idea: find vertices with no successors and work backwards

One approach:
    S = a stack to hold vertices as they are visited
    while there are still unvisited vertices:
        find a vertex v with no unvisited successors
        mark v visited
        S.push(v)
    return S

Popping the vertices off the stack gives one possible topological ordering.

### Traveling salesperson problem

Salesperson needs to travel to a number of cities to visit clients, and wants to do so as efficiently as possible.

A _tour_ is a path that:
    - begins at starting vertex
    - passes through every other vertex once and only once
    - returns to starting vertex

Brute force: perform exhaustive search of all possible tours. Can show this as a tree:

!['alt text'](screenshots/week13_screenshot_2.png 'screenshot from class')

For n cities, there are (n - 1)! leaf nodes - half are redundant.

Informed search: focus on most promising paths; use a function that estimates how good a path is.

Better than brute forces, but still exponential space and time.

Review of algorithm complexity:
!['alt text'](screenshots/week13_screenshot_3.png 'screenshot from class')

Algorithms above dotted line are called _polynomial time_.

Below the line algorithms are called _exponential time_.

Exponential time is much worse.

Any problem that can be solved with polynomial time is considered an 'easy problem'.

Problems with no polynomial time solutions are considered 'hard' or 'intractable' and can only be solved for small values of n.

So we resort to finding approximate functions: _heuristic_ techniques.

## Take home lessons

OO programming allows us to capture abstractions in programs we write.

Key concepts: encapsulation, inheritance, polymorphism.

Abstract data types allows us to organize and manipulate collections of data.

Efficiency matters when dealing with large collections of data.

Java built in collections:

```
java.util.ArrayList<T>
java.util.LinkedList<T> (also implements Queue)
java.util.Stack<T>
java.util.TreeMap<K, V> (balanced search tree)
java.util.HashMap<K, V> (hash table)
java.util.PriorityQueue<T> (a heap)
```






