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
    
    public LinkedTree(int[] keys, Object[] dataItems){
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
            } else {
                nextNode = child;
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
            return 1 + depthInTree(key, root.left);
        }
        // key is >= root.key so only need to search right subtree
        if (root.right == null) {  // not found
            return -1;
        }
        return 1 + depthInTree(key, root.right);    
    }
    
    /*
     * depthIter - uses iteration to determine the depth of the tree at which the key occurs,
     * or -1 if it does not occur
     */
    
    private int depthIter(int key){
        
        if (this.root == null){
            return -1;
        }
        
        Node nextNode = this.root;
        int depth = 0;
        
        while (nextNode != null) {  // if null we have reached the end of the branch
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
     * sumEvensInTree - returns sum of even-valued keys in the tree or subtree which is passed
     */
    private static int sumEvensInTree(Node root){
        if (root == null){
            return 0;
        }
        
        int leftSum = sumEvensInTree(root.left);   // get sum evens in left subtree
        int rightSum = sumEvensInTree(root.right);  // get sum evens in right subtree
        
        if (root.key % 2 == 0){
            return root.key + leftSum + rightSum;
        }
        return leftSum + rightSum;
        
    }
    
    /* 
     * deleteMax - deletes the node containing the largest value in the tree, returning the key's value
     */
    private int deleteMax(){
        if (this.root == null){
            return -1;
        }
        Node largest = root;
        Node largestParent = null;
        
        while (largest.right != null){  // when largest.right is null, the current node is the largest
            largestParent = largest;
            largest = largest.right;
        }
        
        int largestValue = largest.key;
        
        if (largest.left != null) {  // if there is a value in left, replace the current node with that node
            largestParent.right = largest.left;
            return largestValue;
        }
        
        largestParent.right = null;  // if no value in left, set largestParent.right to null (deleting largest) and return value
        return largestValue;
        
    }
    
    /*
     * sumEvens - returns sum of even-valued keys in the LinkedTree
     */
    public int sumEvens(){
        return sumEvensInTree(this.root);
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
        
        System.out.println();    // include a blank line between tests
        
        System.out.println("--- Testing depth()/depthInTree() from Problem 2 ---");
        System.out.println();
        //System.out.println("(1) Testing on 
        
        LinkedTree tree = new LinkedTree();
        System.out.println("empty tree: " + tree.depthIter(13));
        
        int[] keys = {37, 26, 42, 13, 35, 56, 30, 47, 70};
        tree.insertKeys(keys);
        System.out.println("depth of 13: " + tree.depthIter(13));
        System.out.println("depth of 37: " + tree.depthIter(37));
        System.out.println("depth of 47: " + tree.depthIter(47));
        System.out.println("depth of 50: " + tree.depthIter(50));
        
        LinkedTree newTree = new LinkedTree();
        System.out.println("empty tree: " + newTree.sumEvens());
        
        int[] newKeys = {4, 1, 3, 6, 5, 2};
        newTree.insertKeys(newKeys);
        System.out.println("tree with keys from 1 to 6: " + newTree.sumEvens());
        
        LinkedTree tree3 = new LinkedTree();
        System.out.println("empty tree: " + tree3.deleteMax());
        System.out.println();
        
        int[] keys3 = {37, 26, 42, 13, 35, 56, 30, 47, 70};
        tree3.insertKeys(keys3);
        tree3.levelOrderPrint();
        System.out.println();
        
//        System.out.println("first deletion: " + tree3.deleteMax());
//        tree3.levelOrderPrint();
//        System.out.println();
//        
//        System.out.println("second deletion: " + tree3.deleteMax());
//        tree3.levelOrderPrint();
        
        LinkedTreeIterator iterator = tree3.inorderIterator();
        //while (iterator.hasNext()){
          //  System.out.println(iterator.next());
        //}

        /*
         * Add at least two unit tests for each method from Problem 7.
         * Test a variety of different cases. 
         * Follow the same format that we have used above.
         * 
         * IMPORTANT: Any tests for your inorder iterator from Problem 8
         * should go BEFORE your tests of the deleteMax method.
         */
        
    }
}
