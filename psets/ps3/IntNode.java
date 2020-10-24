public class IntNode {
    private int val;
    private IntNode next;
    
    public IntNode(int val, IntNode next){
        this.val = val;
        this.next = next;
    }
    
    public static void printOddsRecursive(IntNode start){
        if (start == null){
            return;
        }
        
        if (start.val % 2 == 1){
            System.out.println(start.val);
        }
        printOddsRecursive(start.next);
    }
        
        
    
    public static void printOddsIterative(IntNode start){
        
        IntNode trav = start;
        while (trav != null){
            if (trav.val % 2 == 1){
                System.out.println(trav.val);
            }
            trav = trav.next;
        }
        
    }
    
    public static void main (String [] args){
    IntNode testIntNode = new IntNode(2, new IntNode(3, new IntNode(4, new IntNode(5, null))));
    IntNode.printOddsRecursive(testIntNode);
    IntNode.printOddsIterative(testIntNode);
    }
}