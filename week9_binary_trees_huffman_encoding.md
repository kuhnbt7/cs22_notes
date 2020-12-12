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

### Using a binary tree for an algebraic expression

Ex ((a + (3*c)) - (d / 2))

!['alt text'](screenshots/week9_screenshot_1.PNG 'screenshot from class')

Make the leaf nodes constants or variables and interior nodes operators. Their children are their operands.

Inorder traversal will give the conventional algebraic notation - print '(' before recursive call on left subtree and ')' after recursive call on right subtree.

Preorder gives functional notation.

Postorder gives order in which computation must be carried out on a stack calculator.

### Fixed length character encodings

A character encoding maps each character to a number - computers usually use these.

Ex ASCII: 8 bits per character
Unicode: 16 bits per character

All encodings have the same length, and a character always has the same encoding.

But they tend to waste space. Ex - a newspaper article might only have 64 unique characters. You only need 6 bits to represent. You could save even more space by giving most common encodings 3-4 bits and less frequent ones 6 bits. 

So variable-length encodings allow us to compress a text file.

But how do you know how many bits the next character has?

One requirement - no character's encoding can be the prefix of another character's encoding.

One solution:

### Huffman encoding

Based on actual character frequencies in a given document.

Huffman uses a binary tree:
- To determine encoding of each character
- To decode an encoded file

The edges are 0 or 1 - left branches are 0, right branches 1.
Leaf nodes are where the characters live. Interior nodes don't have characters.

To get a character's encoding, follow the path from the root to its leaf node.

### To build a Huffman Tree

1. Read thru the text to determine text frequencies
2. Create a list of nodes containing (character, frequency) pairs for each character in the text, sorted by increasing frequency
3. Remove and merge the nodes with 2 lowest frequencies, forming a new node that is their parent. The parent gets the sum of the frequencies of its children
4. Add the parent to the listed of nodes, retaining sorted order
5. Repeat 3 and 4 until there is only one node in the list, which is the root

So this creates a set of links between the different characters:

!['alt text](screenshots/week9_screenshot_2.PNG 'alt text')

The most frequent characters end up with 2 bit encodings.

The shape of the tree depends on the character frequencies.

### Compressing a file using Huffman encoding

1. Read thru input and build huffman tree
2. Write a file header for the output file
    - Include character frequencies
    - Either include all characters or make list of (character, frequency) if you are going to use a subset
3. Traverse the tree to create a table contatining encoding of each character
4. Read thru file a second time and write the huffman code for each character to the output file

### Decompressing a file

1. Rebuild the tree from the frequency table
2. Read one bit at a time and traverse the tree
3. When you get to a leaf, return that character and return to the root

## Search Trees

_Search tree_: for every node k:
- all nodes in k's left subtree are < k
- all nodes in k's right subtree are >= k

With a search tree, an inorder traversal visits the nodes in order (of increasing key values).

So you can always figure out whether to go down a path based on whether the key is less than or greater than the value.




