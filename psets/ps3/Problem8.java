/*
 * Problem8.java
 */
   
public class Problem8 {
    
    /**
     * isPal - takes a string an returns true if it is a palindrome, false if it is not
     */
    public static boolean isPal(String str){
        if (str == null){
            throw new IllegalArgumentException();
        }
        if (str.length() == 1){  // string of length 1 is palindrome
            return true;
        }
        
        ArrayStack<Character> stack = new ArrayStack<>(str.length());
        ArrayQueue<Character> queue = new ArrayQueue<>(str.length());
        for (int i = 0; i < str.length(); i++){
            if (Character.isLetter(str.charAt(i))){
                stack.push(Character.toLowerCase(str.charAt(i)));
                queue.insert(Character.toLowerCase(str.charAt(i)));
            }
        }
        
        // the stack will yield letters in reverse order; the queue will yield letters in original order
        while (!(stack.isEmpty())){
            char nextStackLetter = stack.pop();
            char nextQueueLetter = queue.remove();
            if (nextStackLetter != nextQueueLetter){
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println("--- Testing method isPal ---");
        System.out.println();

        System.out.println("(0) Testing on \"A man, a plan, a canal, Panama!\"");
        try {
            boolean results = isPal("A man, a plan, a canal, Panama!");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
        System.out.println();    // include a blank line between tests
        

        System.out.println("(1) Testing on \"anaconda\"");
        try {
            boolean results = isPal("anaconda");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("false");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == false);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        

        System.out.println("(0) Testing on \"Rotator\"");
        try {
            boolean results = isPal("Rotator");
            System.out.println("actual results:");
            System.out.println(results);
            System.out.println("expected results:");
            System.out.println("true");
            System.out.print("MATCHES EXPECTED RESULTS?: ");
            System.out.println(results == true);
        } catch (Exception e) {
            System.out.println("INCORRECTLY THREW AN EXCEPTION: " + e);
        }
        
    }
}