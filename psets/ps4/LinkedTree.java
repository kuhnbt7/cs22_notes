/*
 * LinkedTree.java
 *
 * Computer Science E-22
 *
 * Modifications and additions by:
 *     name: Ben Kuhn
 *     username:
 */

import java.util.*;

/*
 * LinkedTree - a class that represents a binary tree containing data
 * items with integer keys.  If the nodes are inserted using the
 * insert method, the result will be a binary search tree.
 */
public class LinkedTree {
    // An inner class for the nodes in the tree
    private class Node {
        private int key;         // the key field
        private LLList data;     // list of data values for this key
        private Node left;       // reference to the left child/subtree
        private Node right;      // reference to the right child/subtree
        private Node parent;     // reference to the parent. NOT YET MAINTAINED!
        
        private Node(int key, Object data){
            this.key = key;
            this.data = new LLList();
            this.data.addItem(data, 0);
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    
    // the root of the tree as a whole
    private Node root;
    
    public LinkedTree() {
        root = null;
    }
    
    /*
     * Create an initially balanced binary search tree
     */
    public LinkedTree(int[] keys, Object[] dataItems){
        SortHelper.quickSort(keys, dataItems);  // Sort the keys and items
        root = balancedTree(keys, dataItems, 0, keys.length);
    }
    
    /*
     * recursive helper method for LinkedTree constructor. Recursively create a balanced search tree
     * from two arrays of keys and dataItems
     */
    private Node balancedTree(int[] keys, Object[] dataItems, int start, int end){
        if (start == end){
            return null;
        }

        int newRootIndex = (start + end) / 2;
        Node newRoot = new Node(keys[newRootIndex], dataItems[newRootIndex]);
        
        newRoot.left = balancedTree(keys, dataItems, start, newRootIndex);
        newRoot.right = balancedTree(keys, dataItems, newRootIndex + 1, end);
        
        if (newRoot.left != null){
            newRoot.left.parent = newRoot;
        }
        if (newRoot.right != null){
            newRoot.right.parent = newRoot;
        }
        return newRoot;
    }
    
    /*
     * Prints the keys of the tree in the order given by a preorder traversal.
     * Invokes the recursive preorderPrintTree method to do the work.
     */
    public void preorderPrint() {
        if (root != null) {
            preorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs a preorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void preorderPrintTree(Node root) {
        System.out.print(root.key + " ");
        if (root.left != null) {
            preorderPrintTree(root.left);
        }
        if (root.right != null) {
            preorderPrintTree(root.right);
        }
    }
    
    /*
     * Prints the keys of the tree in the order given by a postorder traversal.
     * Invokes the recursive postorderPrintTree method to do the work.
     */
    public void postorderPrint() {
        if (root != null) {
            postorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs a postorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void postorderPrintTree(Node root) {
        if (root.left != null) {
            postorderPrintTree(root.left);
        }
        if (root.right != null) {
            postorderPrintTree(root.right);
        }
        System.out.print(root.key + " ");
    }
    
    /*
     * Prints the keys of the tree in the order given by an inorder traversal.
     * Invokes the recursive inorderPrintTree method to do the work.
     */
    public void inorderPrint() {
        if (root != null) {
            inorderPrintTree(root);      
        }
        System.out.println();
    }
    
    /*
     * Recursively performs an inorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void inorderPrintTree(Node root) {
        if (root.left != null) {
            inorderPrintTree(root.left);
        }
        System.out.print(root.key + " ");
        if (root.right != null) {
            inorderPrintTree(root.right);
        }
    }
    
    /* 
     * Inner class for temporarily associating a node's depth
     * with the node, so that levelOrderPrint can print the levels
     * of the tree on separate lines.
     */
    private class NodePlusDepth {
        private Node node;
        private int depth;
        
        private NodePlusDepth(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
    
    /*
     * Prints the keys of the tree in the order given by a
     * level-order traversal.
     */
    public void levelOrderPrint() {
        LLQueue<NodePlusDepth> q = new LLQueue<NodePlusDepth>();
        
        // Insert the root into the queue if the root is not null.
        if (root != null) {
            q.insert(new NodePlusDepth(root, 0));
        }
        
        // We continue until the queue is empty.  At each step,
        // we remove an element from the queue, print its value,
        // and insert its children (if any) into the queue.
        // We also keep track of the current level, and add a newline
        // whenever we advance to a new level.
        int level = 0;
        while (!q.isEmpty()) {
            NodePlusDepth item = q.remove();
            
            if (item.depth > level) {
                System.out.println();
                level++;
            }
            System.out.print(item.node.key + " ");
            
            if (item.node.left != null) {
                q.insert(new NodePlusDepth(item.node.left, item.depth + 1));
            }
            if (item.node.right != null) {
                q.insert(new NodePlusDepth(item.node.right, item.depth + 1));
            }
        }
        System.out.println();
    }
    
    /*
     * Searches for the specified key in the tree.
     * If it finds it, it returns the list of data items associated with the key.
     * Invokes the recursive searchTree method to perform the actual search.
     */
    public LLList search(int key) {
        Node n = searchTree(root, key);
        if (n == null) {
            return null;
        } else {
            return n.data;
        }
    }
    
    /*
     * Recursively searches for the specified key in the tree/subtree
     * whose root is specified. Note that the parameter is *not*
     * necessarily the root of the entire tree.
     */
    private static Node searchTree(Node root, int key) {
        if (root == null) {
            return null;
        } else if (key == root.key) {
            return root;
        } else if (key < root.key) {
            return searchTree(root.left, key);
        } else {
            return searchTree(root.right, key);
        }
    }
    
    /*
     * Inserts the specified (key, data) pair in the tree so that the
     * tree remains a binary search tree.
     */
    public void insert(int key, Object data) {
        // Find the parent of the new node.
        Node parent = null;
        Node trav = root;
        while (trav != null) {
            if (trav.key == key) {
                trav.data.addItem(data, 0);
                return;
            }
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }
        
        // Insert the new node.
        Node newNode = new Node(key, data);
        if (parent == null) {    // the tree was empty
            root = newNode;
            root.parent = null;
        } else if (key < parent.key) {
            parent.left = newNode;
            newNode.parent = parent;
        } else {
            parent.right = newNode;
            newNode.parent = parent;
        }
    }
    
    /*
     * FOR TESTING: Processes the integer keys in the specified array from 
     * left to right, adding a node for each of them to the tree. 
     * The data associated with each key is a string based on the key.
     */
    public void insertKeys(int[] keys) {
        for (int i = 0; i < keys.length; i++) {
            insert(keys[i], "data for key " + keys[i]);
        }
    }
    
    /*
     * Deletes the node containing the (key, data) pair with the
     * specified key from the tree and return the associated data item.
     */
    public LLList delete(int key) {
        // Find the node to be deleted and its parent.
        Node parent = null;
        Node trav = root;
        while (trav != null && trav.key != key) {
            parent = trav;
            if (key < trav.key) {
                trav = trav.left;
            } else {
                trav = trav.right;
            }
        }
        
        // Delete the node (if any) and return the removed data item.
        if (trav == null) {   // no such key    
            return null;
        } else {
            LLList removedData = trav.data;
            deleteNode(trav, parent);
            return removedData;
        }
    }
    
    /*
     * Deletes the node specified by the parameter toDelete.  parent
     * specifies the parent of the node to be deleted. 
     */
    private void deleteNode(Node toDelete, Node parent) {
        if (toDelete.left != null && toDelete.right != null) {
            // Case 3: toDelete has two children.
            // Find a replacement for the item we're deleting -- as well as 
            // the replacement's parent.
            // We use the smallest item in toDelete's right subtree as
            // the replacement.
            Node replaceParent = toDelete;
            Node replace = toDelete.right;
            while (replace.left != null) {
                replaceParent = replace;
                replace = replace.left;
            }
            
            // Replace toDelete's key and data with those of the 
            // replacement item.
            toDelete.key = replace.key;
            toDelete.data = replace.data;
            
            // Recursively delete the replacement item's old node.
            // It has at most one child, so we don't have to
            // worry about infinite recursion.
            deleteNode(replace, replaceParent);
        } else {
            // Cases 1 and 2: toDelete has 0 or 1 child
            Node toDeleteChild;
            if (toDelete.left != null) {
                toDeleteChild = toDelete.left;
            } else {
                toDeleteChild = toDelete.right;  // null if it has no children
            }
            
            if (toDelete == root) {
                root = toDeleteChild;
            } else if (toDelete.key < parent.key) {
                parent.left = toDeleteChild;
                toDeleteChild.parent = parent;
            } else {
                parent.right = toDeleteChild;
                toDeleteChild.parent = parent;
            }
        }
    }
    
    /* Returns a preorder iterator for this tree. */
    public LinkedTreeIterator preorderIterator() {
        return new PreorderIterator();
    }
    
    /* 
     * inner class for a preorder iterator 
     * IMPORTANT: You will not be able to actually use objects from this class
     * to perform a preorder iteration until you have modified the LinkedTree
     * methods so that they maintain the parent fields in the Nodes.
     */
    private class PreorderIterator implements LinkedTreeIterator {
        private Node nextNode;
        
        private PreorderIterator() {
            // The traversal starts with the root node.
            nextNode = root;
        }
        
        public boolean hasNext() {
            return (nextNode != null);
        }
        
        public int next() {
            if (nextNode == null) {
                throw new NoSuchElementException();
            }
            
            // Store a copy of the key to be returned.
            int key = nextNode.key;
            
            // Advance nextNode.
            if (nextNode.left != null) {
                nextNode = nextNode.left;
            } else if (nextNode.right != null) {
                nextNode = nextNode.right;
            } else {
                // We've just visited a leaf node.
                // Go back up the tree until we find a node
                // with a right child that we haven't seen yet.
                Node parent = nextNode.parent;
                Node child = nextNode;
                while (parent != null &&
                       (parent.right == child || parent.right == null)) {
                    child = parent;
                    parent = parent.parent;
                }
                
                if (parent == null) {
                    nextNode = null;  // the traversal is complete
                } else {
                    nextNode = parent.right;
                }
            }
            
            return key;
        }
    }  

    /* Returns a preorder iterator for this tree. */
    public LinkedTreeIterator inorderIterator() {
        return new InorderIterator();
    }
    
    /*
     * inner class for an inorder iterator
     */
    private class InorderIterator implements LinkedTreeIterator{
        private Node nextNode;
        
        private InorderIterator(){
            // the traversal starts at the leftmost leaf node
            nextNode = root;
            while (nextNode.left != null){
                nextNode = nextNode.left;
            }
        }
        
        public boolean hasNext(){
            return (nextNode != null);
        }
        
        public int next(){  // return the key of nextNode and set nextNode equal to the next node
            if (nextNode == null){
                throw new NoSuchElementException();
            }
            
            int key = nextNode.key;
            
            
            // if nextNode has right branch, go to it then find a leaf on its left branch
            if (nextNode.right != null){
                nextNode = nextNode.right;
                while (nextNode.left != null){
                    nextNode = nextNode.left;
                }
            } else {  // otherwise, go back up tree and find first unvisited node
                Node parent = nextNode.parent;
                Node child = nextNode;
                while (parent != null && parent.right == child){
                    child = parent;
                    parent = child.parent;
                }
                nextNode = parent;
            
            if (parent == null){
                nextNode = null;
            }
            
            }
            return key;
            
        }
    }
    
    /*
     * "wrapper method" for the recursive depthInTree() method
     * from PS 4, Problem 2
     */
    public int depth(int key) {
        if (root == null) {    // root is the root of the entire tree
            return -1;
        }
        
        return depthInTree(key, root);
    }
    
    /*
     * original version of the recursive depthInTree() method  
     * from PS 4, Problem 2. You will write a more efficient version
     * of this method.
     */
    private static int depthInTree(int key, Node root) {
        if (key == root.key){   // key found
            return 0;
        }
        if (key < root.key) {  // only need to search left subtree
            if (root.left == null){  // not found
                return -1;
            }
            int depthInSubtree = depthInTree(key, root.left);
            if (depthInSubtree == -1){
                return -1;
            }
            return 1 + depthInSubtree;
        }
        // key is > root.key so only need to search right subtree
        if (root.right == null) {  // not found
            return -1;
        }
        int depthInSubtree = depthInTree(key, root.right);
        if (depthInSubtree == -1){
            return -1;
        }
        return 1 + depthInSubtree;    
    }
    
    /*
     * depthIter - uses iteration to determine the depth of the tree at which the key occurs,
     * or -1 if it does not occur
     */
    
    private int depthIter(int key){
        
        if (root == null) {
            return -1;
        }
        
        Node nextNode = root;
        int depth = 0;
        
        while (nextNode != null) {  // if nextNode is null we have reached the end of the branch
            if (nextNode.key == key) {
                return depth;
            }
            if (key < nextNode.key) {  // search left branch
                nextNode = nextNode.left;
            } else {    // search right branch
                nextNode = nextNode.right;
            }
            depth ++;
        }
        
        return -1;  // key was not found

    }
    
    /*
     * sumEvens - returns sum of even-valued keys in the LinkedTree
     */
    public int sumEvens(){
        return sumEvensInTree(root);
    }    
    
    /*
     * sumEvensInTree - returns sum of even-valued keys in the tree which is passed
     */
    private static int sumEvensInTree(Node start){
        if (start == null){
            return 0;
        }
        
        int leftSum = sumEvensInTree(start.left);   // get sum evens in left subtree
        int rightSum = sumEvensInTree(start.right);  // get sum evens in right subtree
        
        if (start.key % 2 == 0) {  // case where current node is even
            return start.key + leftSum + rightSum;
        }
        return leftSum + rightSum;  // case where current node is odd
        
    }
    
    /* 
     * deleteMax - deletes the node containing the largest value in the tree, returning the key's value
     * or -1 if it is not found
     */
    public int deleteMax(){
        if (root == null){
            return -1;
        }
        Node largest = root;
        Node largestParent = null;
        
        while (largest.right != null){  // when largest.right is null, the current node is the largest
            largestParent = largest;
            largest = largest.right;
        }
        
        int largestValue = largest.key; 
        
        if (largest.left != null) {  // if there is a value in left, replace the largest node with that node
            largestParent.right = largest.left;
            return largestValue;
        }
        
        largestParent.right = null;  // if no value in left, set largestParent.right to null (deleting largest) and return value
        return largestValue;
        
    }

    
    public static void main(String[] args) {
        System.out.println("--- Testing depth()/depthInTree() from Problem 2 ---");
        System.out.println();
        System.out.println("(0) Testing on tree from Problem 7, depth of 13");
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            
            int results = tree.depth(13);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(2);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 2);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("--- Testing depth()/depthInTree() from Problem 2 ---");
        System.out.println();
        System.out.println("(1) Testing on new tree, depth of 24");
        
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {10, 8, 5, 14, 23, 21, 35, 33, 24};
            tree.insertKeys(keys);
 
            int results = tree.depth(24);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(5);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 5);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();

        System.out.println("--- Testing depthIter() from Problem 7.1 ---");
        System.out.println();
        System.out.println("(2) Testing on tree from problem 7, depth of 13");
        
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
 
            int results = tree.depthIter(13);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(2);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 2);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();

        System.out.println("--- Testing depthIter from Problem 7.1 ---");
        System.out.println();
        System.out.println("(3) Testing on new tree, depth of 24");
        
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {10, 8, 5, 14, 23, 21, 35, 33, 24};
            tree.insertKeys(keys);
 
            int results = tree.depthIter(24);
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(5);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 5);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        
        System.out.println("--- Testing sumEvens() from Problem 7.2 ---");
        System.out.println();
        System.out.println("(4) Testing on tree from problem 7");
        
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
 
            int results = tree.sumEvens();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(224);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 224);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();

        System.out.println("--- Testing sumEvens() from Problem 7.2 ---");
        System.out.println();
        System.out.println("(5) Testing on new tree");
        
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {10, 8, 5, 14, 23, 21, 35, 33, 24};
            tree.insertKeys(keys);
 
            int results = tree.sumEvens();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(56);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 56);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
    
        System.out.println();
        
        System.out.println("--- Testing inorderIterator from Problem 8 ---");
        System.out.println();
        System.out.println("(6) Testing on new tree");
        
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
            LinkedTreeIterator iterator = tree.inorderIterator();
            int[] results = new int[keys.length];
            int[] expected = {13, 26, 30, 35, 37, 42, 47, 56, 70};

            System.out.println("Expected inorder traversal: ");
            for (int i = 0; i < expected.length; i++){
                System.out.print(expected[i] + " ");
            }
            System.out.println();
            System.out.println("Actual inorder traversal: ");
            int index = 0;
            while (iterator.hasNext()) {
                int next = iterator.next();
                System.out.print(next + " ");
                results[index] = next;
                index ++;
            }
            
            System.out.println();
            System.out.print("MATCHES EXPECTED RESULTS?:");
            System.out.println(Arrays.equals(results, expected));
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();
        
        System.out.println("--- Testing inorderIterator from Problem 8 ---");
        System.out.println();
        System.out.println("(7) Testing on new tree");
        
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {10, 8, 5, 14, 23, 21, 35, 33, 24};
            tree.insertKeys(keys);
            LinkedTreeIterator iterator = tree.inorderIterator();
            int[] results = new int[keys.length];
            int[] expected = {5, 8, 10, 14, 21, 23, 24, 33, 35};

            System.out.println("Expected inorder traversal: ");
            for (int i = 0; i < expected.length; i++){
                System.out.print(expected[i] + " ");
            }
            System.out.println();
            System.out.println("Actual inorder traversal: ");
            int index = 0;
            while (iterator.hasNext()) {
                int next = iterator.next();
                System.out.print(next + " ");
                results[index] = next;
                index ++;
            }
            
            System.out.println();
            System.out.print("MATCHES EXPECTED RESULTS?:");
            System.out.println(Arrays.equals(results, expected));

        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        System.out.println();
               
        System.out.println("--- Testing deleteMax() from Problem 7.3 ---");
        System.out.println();
        System.out.println("(8) Testing on tree from problem 7");
        
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
            tree.insertKeys(keys);
 
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(70);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 70);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();

        System.out.println("--- Testing deleteMax() from Problem 7.3 ---");
        System.out.println();
        System.out.println("(9) Testing on new tree");
        
        try {
            LinkedTree tree = new LinkedTree();
            int[] keys = {10, 8, 5, 14, 23, 21, 35, 33, 24};
            tree.insertKeys(keys);
 
            int results = tree.deleteMax();
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println(35);
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == 35);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();
        System.out.println("--- Testing balanced tree constructor from Problem 9 ---");
        System.out.println();
        System.out.println("(10) Testing on new tree");
        
        try {
            int[] keys = {10, 8, 5, 14, 23, 21, 35, 33, 24};
            String[] dataItems = {"d10", "d8", "d5", "d14", "d23", "d21", "d35", "d33", "d24"};
            LinkedTree tree = new LinkedTree(keys, dataItems);
 
            System.out.println("actual results:");
            tree.levelOrderPrint();
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }

        System.out.println();
        System.out.println("--- Testing balanced tree constructor from Problem 9 ---");
        System.out.println();
        System.out.println("(11) Testing on new tree");
        
        try{
         
            int[] keys = {10, 8, 4, 2, 15, 12, 7, 16};
            String[] dataItems = {"d10", "d8", "d4", "d2", "d15", "d12", "d7", "d16"};
            LinkedTree tree = new LinkedTree(keys, dataItems);
            System.out.println("actual results:");
            tree.levelOrderPrint();
            System.out.println();
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        

        
    }

        
 
}
