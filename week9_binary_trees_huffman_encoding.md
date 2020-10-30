## Binary Trees and Huffman Encoding

### Data Dictionary
A collection of data with two operations:
- search for an item (and possibly delete it)
- insert a new item

We could implement with a sorted list - time efficiency O(n)

data structure | searching for item | inserting item
---------------|--------------------|---------------
sorted list implemented with array | o(logn) with binary search | O(n)
list implemented with linked list | O(n) traversing the list (binary search is nlog(n)) | O(n) - O(1) to insert, but O(n) to find the location

A tree will get us to better than O(n) efficiency

A _tree_ consists of:
- a set of nodes
- a set of edges, each of which connects a pair of nodes

Each node may have one or more _data items_. Each data item can contain one or more fields.

_Key field_ is the field used when searching for a data item.

Data items with the same key are referred to as duplicates.

The _root_ is the node at the top of the tree.

A node N connected to nodes directly below it is the _parent_.

Each node is the child of at most one parent.

Nodes with the same parent are _siblings_.

 A node's _ancestors_ are its parent, parent's parents, etc.

 A node's _descendants_ are its children, its children's children, etc.

 A _leaf node_ is a node without children.

 A _interior node_ is a node with one or more children.

 Each node is a root of a smaller tree! We call them subtrees.

 There is exactly one _path_ from each node to the root.

 _Depth_ is number of edges from node to root. Nodes with the same depth form a _level_.

 The height of a tree is the maximum depth of its nodes. So height is _number of edges_, not number of nodes (this is a bit counterintuitive)   

 ### Binary Trees

 In a binary tree, nodes have at most 2 children. We distinguish between them by calling them left or right.

 Recursive definition: A binary tree is either:
 - empty
 - a node, that has:
    - one or more pieces of data, and
    - a _left subtree_, which is a binary tree
    - a _right subtree_, which is a binary tree

### Representing a binary tree using linked nodes

```
public class LinkedTree{
    private class Node{
        private int key;
        private LLList data;
        private Node left;
        private Node right;
    }

    private Node root;
}
```
The root variable points to the root of the tree as a whole.

_Traversing_ a binary tree involves visiting all the nodes in the tree.
_Visiting_ means processing its data in some way. So it's possible to pass thru a node without visiting it.

### Preorder traversal

1. Visit the root, N
2. Recursively perform a preorder traversal of N's left subtree
3. Recursively perform a preorder traversal of N's right subtree

This is a static method of the LinkedTree class:

```
...
private static void preorderPrintTree(Node root){ // root not necessarily the root of entire tree
    System.out.print(root.key + " ");
    if (root.left != null){
        preorderPrintTree(root.left);
    }
    if (root.right != null){
        preorderPrintTree(root.right);
    }
}
```

It's easier to traverse trees with recursion because with iteration it's hard to keep track of references to parents.

### Postorder traversal

1. Recursively perform a traversal of N's left subtree
2. Recursively perform a traversal of N's right subtree
3. visit the root N

So the root is visited last.

```
...
public static void postOrderPrintTree(Node root){
    if (root.left != null){
        postOrderPrintTree(root.left);
    }
    if (root.right != null){
        postOrderPrintTree(root.right);
    }
    System.out.print(root.key + " ");
}
```

### Inorder Traversal

1. Recursively perform an inorder traversal of N's left subtree
2. visit the root N
3. Recursively perform an inorder traversal of N's right subtree

```
private static void inorderPrintTree{
    if (root.left != null){
        inorderPrintTree(root.left);
    }
    System.out.print(root.key + " ");
    if (root.right != null){
        inorderPrintTree(root.right);
    }
}
```

### Level order traversal

Visit the nodes one level at a time, from top to bottom and left to right.

This is like breadth-first search and can be done using a queue (no in-class implementation).

(stopped at 1:00:26)




