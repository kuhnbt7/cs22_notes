/**
 * Auto Generated Java Class.
 */

public class Test {
    
    
    public static void main(String[] args) { 
        IntNode newNode = new IntNode(2, new IntNode(3, new IntNode(4, new IntNode(9, new IntNode(10, null)))));
        IntNode n = new IntNode(3, null);
        IntNode.printOddsRecursive(newNode);
        IntNode.printOddsIterative(newNode);
    }
    

}

