## Week 10: Search Trees, Heaps, Priority Queues

Back to binary search trees...

### Searching a binary search tree
We search a binary search tree with an integer because it's easy to compare integers in java (they're primitives).

We group duplicates together (multiple items with the same key). Therefore we store their values in a LLList, and if we search and find the key we return the LLList. We use a public wrapper method and a recursive search method.

```
(this is inside LinkedTree class)

public LLList search(int key){
    Node n = searchTree(root, key);
    if (n == null){
        return null;
    } else {
        return n.data;
    }
}

private static Node searchTree(Node root, int key){
    if (root == null){
        return null;
    } else if (key == root.key){
        return root;
    } else if (key < root.key){
        return searchTree(root.left, key);
    } else {
        return searchTree(root.right, key);
    }
}
```

### Inserting an item in a binary search tree

We insert a (key, value) pair. Example: a student record. The key is the student ID; the value is a string with the student's record.

So to insert an item (k, d) we begin by searching for k.

If we find k, we add d to the LLList. If we don't find k, we insert it in the tree. The last node we saw in the search becomes its parent, P. If k < P, it becomes left child, if k > P, it becomes right child.

If the tree is empty, make the new node the root.

Here's the iteration version of insert. We use 2 references, trav and parent. Parent stays one behind trav:

```
public void insert(int key, Object data){
    Node parent = null;
    Node trav = root;
    while (trav != null){
        if (trav.key == key){
            trav.data.addItem(data, 0);
            return;
        }
        parent = trav;  // update the parent
        if (key < trav.key){
            trav = trav.left;
        } else {
            trav = trav.right;
        }
    }
    Node newNode = new Node(key, data);
    if (root == null){
        root = newNode;
    } else if (key < parent.key){
        parent.left = newNode;
    } else {
        parent.right = newNode;
    }
}
```

### Deleting items from a binary search tree

There are 3 cases:

1. Node x has no children
    - set its parent to null (that's it)

2. x has one child
    - take x's parent's reference to x and make it refer to x's child

3. x has 2 children
    - leave x's node where it is. replace its key and value with those from another node. The replacement must retain the search tree inequalities.
        - so you could choose the largest item in the left subtree, or the smallest item in the right subtree.
        - we're going to do option 2 - the smallest item from the right subtree.
    - once we find the smallest item in the right subtree, copy it into x, and delete the smallest item. That node will never have 2 children - it will have no children or one right child.

```
public LLList delete(int key){
    // Find the node and its parent
    Node parent = null;
    Node trav = root;
    while (trav != null && trav.key != key){
        parent = trav;
        if (key < trav.key){
            trav = trav.left;
        } else {
            trav = trav.right;
        }
    }

    // Delete the node (if any) and return the removed items
    if (trav == null){
        return null;
    } else {
        LLList removedData = trav.data;
        deleteNode(trav, parent);
        return removedParent;
    }
}

private void deleteNode(Node toDelete, Node parent){
    if (toDelete.left != null && toDelete.right != null){
        // Find a replacement and its parent, because it has
        // two children
        Node replaceParent = toDelete;
        // Get smallest item in right subtree
        Node replace = toDelete.right;
        while (replace.left != null){
            replaceParent = replace;
            replace = replace.left;
        }
        // Replace toDelete's key and data with that of the
        // replacement item
        toDelete.key = replace.key;
        toDelete.data = replace.data;

        // Recursively delete the replacement item's old node.
        // It has at most one child, so we don't have to   
        // worry about infinite recursion
        deleteNode(replace, replaceParent);
    } else {
        Node toDeleteChild;
        if (toDelete.left != null) {
            toDeleteChild = toDelete.left;
        } else {
            toDeleteChild = toDelete.right;
        }
        // Note: in case 1, toDeleteChild will have 
        // a value of null

        if (toDelete == root) {
            root = toDeleteChild;
        } else if (toDelete.key < parent.key) {
            parent.left = toDeleteChild;
        } else {
            parent.right = toDeleteChild;
        }
    } 
}
```

Reminder that there is exactly one path from each node to root. And the depth of a node is the number of edges on the path from it to the root. Nodes of the same depth form a level. The height of a tree is the max depth of its nodes.

### Efficiency of Binary Tree search

- Efficiency of any traversal algorithm: O(n). Because you process all nodes and perform O(1) operations on each one.

- Search, insert, and delete all have the same efficiency. Because both insert and delete are basically a serach followed by O(1) operations.

Time complexity of searching:
- best case: O(1) - the root is the key (**not** when the tree has one node, because we are thinking about efficiency as the tree grows)
- worst case: O(h), where h is the height of the tree
- average case: O(h)

But what is this in terms of n? It depends.

First we need to talk about balance.

A tree is **balanced** if for each of its nodes, the node's subtrees have the same height or heights that differ by one.

So for a balanced tree with n nodes, height = O(logn).

- Each time that you follow an edge down the longest path, you cut the problem size in half.

So for a balanced search tree, worst case time complexity is O(logn)

But if the search tree is not balanced, worst case time complexity is O(n).

This worst case is why we usually don't use binary search trees to implement dictionaries.

Some trees take measures to remain balanced.

### 2-3 Tree

A 2-3 tree is a balanced tree in which:
- all nodes have equal-height subtrees (perfect balance - not differing by 1)

To achieve this there are 2 types of nodes:
- 2 node: contains one data item and 0 or 2 children
- 3 node: contains two data items and 0 or 3 children

2 nodes have the same L-R rules as normal nodes.

3 nodes have 2 data items so: items less than the left item go left, items greater than item 1 but less than item 2 go center, and items greater than the right item go right.

### Search in 2-3 Trees

It's pretty straightforward:

- If k == one of the root node's keys, you're done
- else if k < root node's first key:
    - search the left subtree
- else if the root is a 3 node and k < second key
    - search the middle subtree
- else
    - search the right subtree

### Insertion in 2-3 trees

To insert an item with key k:

- search for k, but don't stop until you hit a leaf node
- let L be the leaf node at the end of the search
- if L is a 2 node:
    - add k to L, making it a 3 node
- else if L is a 3 node:
    - split L into two 2 nodes containing the items with the smallest and largest of: k, L's first key, L's 2nd key
    - the middle item is 'sent up' and inserted into L's parent

!['alt text'](screenshots/week10_screenshot_1.PNG 'screenshot from class')

We don't group duplicates together in 2-3 trees.

What if there's no room in the parent when you try to insert - eg the parent is already a 3 node?

You keep splitting - send the middle element of the parent up to its parent. If you get to the root and it is also already a 3 node, you slit and the middle element become the new root.

!['alt text'](screenshots/week10_screenshot_2.png 'screenshot from class')

### Efficiency of 2-3 trees

A 2-3 tree with n items has height h <= log<sub>2</sub>n.

So time complexity of search and insertion are O(logn). Search visits at most h+1 nodes, and insertion visits at most 2h+1 nodes. Deletion is also O(logn).

### External storage

Options we've talked about so far don't work well if you want to store data externally on disk.

Data is transferred to/from disk in units called _blocks_, typically 4 to 8 kb in size.

Accessing disk is much slower than memory - 10 milliseconds vs 10 nanoseconds.

B-trees are designed to minimize the number of times you have to go to disk.

### B-trees

A B-tree of order m is a tree in which each node has:

- at most 2m entries (and for internal nodes, 2m + 1 children)
- at least m entries (and for internal nodes, m + 1 children)
- exception: the root node can have as few as 1 entry

A 2-3 tree is essentially a B-tree of order 1.

To minimize the number of disk accesses, we make m as large as possible. Each disk read brings in new items. The tree ends up being shorter and thus searching for an item requires fewer disk reads.

A large value of m doesn't make sense for an in-memory tree because it leads to many key comparisons per node. These comparisons are less expensive than accessing the disk so they make sense for on-disk trees.

### Searching/Insertion for B-tree

Same as searching a 2-3 tree, you go to the appropriate child based on the value of the key you're searching for.

Insertion also similar. Search for key until you reach a leaf node. If the mode has fewer than 2m items, add the item to the leaf node. Otherwise split the node, dividing up the 2m + 1 items. The smallest m remain in the original node. The largest m go in a new node. The middle entry gets sent up and inserted into the parent. Like 2-3 trees, if you make it up to the root, the root is split and one item goes up. Whe an internal node is split, its references to its childrena re split as well.

### Time analysis of B-trees

All internal nodes have at least m children. Thus a B-tree with n items has height <= log<sub>m</sub>n, and search and insertion are both O(log<sub>m</sub>n). Deletion is also logarithmic.

### In Summary

Binary search trees can be O(logn) but they can degenerate to O(n) if they are out of balance. 2-3 trees and B-trees are balanced serach trees that guarantee O(logn) performance.

B-trees offer improved on-disk performance because they reduce the number of disk accesses.



