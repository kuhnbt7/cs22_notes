/**
 * Auto Generated Java Class.
 */
public class StringRecursion {
    
    // print letters in str separated by commas, on one line
    public static void printLetters(String str){
        if (str == null || str.equals("")){
            return;
        }
        if (str.length() == 1){
            System.out.println(str);
            return;
        }

        System.out.print(str.charAt(0) + ", ");
        printLetters(str.substring(1));
    }
    
    // return a String with all instances of oldChar replaced by newChar
    public static String replace(String str, char oldChar, char newChar){
        if (str == null){
            return null;
        } else if (str.equals("")){
            return "";
        }
        if (str.charAt(0) == oldChar){
            return newChar + replace(str.substring(1), oldChar, newChar);
        } else {
            return str.charAt(0) + replace(str.substring(1), oldChar, newChar);
        }
            
    }
    
    // return the first index where ch occurrs in str, or -1 if it does not occur
    public static int indexOf(char ch, String str){
        if(str == null || str.equals("")){
            return -1;
        }
        if (str.charAt(0) == ch){
            return 0;
        }
        int substrSearch = indexOf(ch, str.substring(1));
        if(substrSearch == -1){
            return -1;
        }                       
        return 1 + substrSearch;
    }
    
    public static void main(String[] args) { 
        printLetters("I like to recurse!");
        System.out.println();
        System.out.println(replace("base case", 'e', 'y'));
        System.out.println(indexOf('y', "Rabbit"));
        System.out.println(indexOf('b', "Rabbit"));

    }
    
    
}
