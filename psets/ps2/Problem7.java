
public class Problem7{
    
    public static void main(String [] args){
        
        int [] test1 = {12, 4};
        System.out.println("First test");
        pairSums(16, test1);
        
        int[] test2 = {3, 7, 8, 1, 3};
        System.out.println("Second test");
        pairSums(10, test2);
        
        int [] test3 = {10, 4, 7, 7,  8, 5, 15};
        System.out.println("Third test");
        pairSums(12, test3);
        
        int [] test4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 5, 6, 7, 5, 4, 5};
        System.out.println("Fourth test");
        pairSums(14, test4);
        
        System.out.println("pairSumsImproved first test:");
        pairSumsImproved(16, test1);
        System.out.println("pairSumsImproved second test:");
        pairSumsImproved(12, test3);
        System.out.println("pairSumsImproved third test:");
        pairSumsImproved(14, test4);
    }
    
    /*
     * for each array index i, pairSums makes (arr.length - i - 1) comparisons.
     * c(n) is therefore equal to n * (n - 1) / 2 (same as selection sort) and time complexity is O(n^2)
     */
    public static void pairSums(int k, int[] arr){
        if (arr == null || arr.length == 0 || arr.length == 1){
            return;
        }
        for (int i = 0; i < arr.length; i++){
            for (int j = i + 1; j < arr.length; j++){
                if (arr[i] + arr[j] == k){
                    System.out.println(arr[i] + " + " + arr[j] + " = " + k);
                }
            }
        }
    }
    
    /*
     * pairSumsImproved first sorts the array using quicksort which is O(nlogn) in the average case. Once the array is 
     * sorted, the time complexity of the remainder of the algorithm is O(n). So for the entire algorithm, the nlogn
     * term predominates and the algorithm is O(nlogn)
     */
    
    public static void pairSumsImproved(int k, int arr[]){
        if (arr.length == 0 || arr.length == 1){
            return;
        }
        Sort.quickSort(arr);
        
        int startIndex = 0;
        int endIndex = arr.length - 1;
        
        int sum;
        while (startIndex < endIndex){
            sum = arr[startIndex] + arr[endIndex];
            if (sum == k){
                System.out.println(arr[startIndex] + " + " + arr[endIndex] + " = " + k);
            }
            if (sum > k){
                endIndex --;
            } else {
                startIndex ++;
            }
        }
                
    }
}

        