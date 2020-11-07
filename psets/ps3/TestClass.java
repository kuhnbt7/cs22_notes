public class TestClass{
    public static void main(String[] args){
        
        Integer[] items = {1, 2, 4, 3, 2, 5, 2, 1, 3, 2, 7, 2, 9, 0};
        ArrayList arr = new ArrayList(items);
        System.out.println("arr before removing 2:" + arr);
        System.out.println("2 is removed: " + arr.removeAll(2));
        System.out.println("arr after removing 2:" + arr);
        
        String[] letters = {"a", "b", "c", "a", "c", "d", "e", "a"};
        ArrayList list1 = new ArrayList(letters);
        System.out.println("list1 before removing a");
        list1.removeAll("a");
        System.out.println("list1 after removing a" + list1);
        
        LLList arr2 = new LLList(items);
        System.out.println("LLList arr2 before removing 2: " + arr2);
        System.out.println("2 is removed:" + arr2.removeAll(2));
        System.out.println("LLList arr2 after removing 2: " + arr2);
        arr2.removeAll(12);
        System.out.println("LLList arr2 after trying to remove 12: " + arr2);
        
        Integer[] items1 = {1};
        arr2 = new LLList(items1);
        arr2.removeAll(1);
        System.out.println("Arr2 with one element which was removed: " + arr2);
        

    }
}
    