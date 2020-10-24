/**
 * Auto Generated Java Class.
 */

public class IntNode {
    private int val;
    private IntNode next;
    
    public IntNode(int val, IntNode next){
        this.val = val;
        this.next = next;
    }
    
    public static void printOddsRecursive(IntNode node){
        if (node == null){
            return;
        }
        if (node.val % 2 != 0){
            System.out.println(node.val);
        }
        printOddsRecursive(node.next);
            
    }
    
    public static void printOddsIterative(IntNode node){
        while (node != null){
            if (node.val % 2 != 0){
                System.out.println(node.val);
            }
            node = node.next;
        }
    }
}
