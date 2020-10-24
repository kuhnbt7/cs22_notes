import java.util.Arrays;

public class Problem8{
    
    public static void main(String [] args){
        int [] arr1 = {3, 4, 1, 2, 5, 6, 7};
        int [] arr2 = {4, 6, 7, 8, 9, 2};
        int [] intersect = findIntersect(arr1, arr2);
        System.out.println(Arrays.toString(intersect));
    }
    
    public static int[] findIntersect(int[] arr1, int[] arr2){
        
        if (arr1 == null || arr2 == null){
            throw new IllegalArgumentException("Arguments cannot be null");
        }                                                   
        
        int shortArrayLength = arr1.length;
        if (arr2.length < shortArrayLength){
            shortArrayLength = arr2.length;
        }
        int [] intersect = new int[shortArrayLength];
        
        Sort.quickSort(arr1);
        Sort.quickSort(arr2);
        
        int i = 0;    // track the index of arr1
        int j = 0;    // track the index of arr2
        int k = 0;    // track the index of intersect
        
        while ((i != arr1.length) && (j != arr2.length)){  // if either index gets past the last element of array, we're finished 
            if (arr1[i] < arr2[j]){   // if arr1[i] is less than arr2[j], move to the next element in arr1 
                i++;
            } else if (arr1[i] > arr2[j]){   // if arr1[i] greater than arr2[j], move to next element in arr2
                j++;
            } else {  // else they are equal; add to intersect and increment i, j, and k
                intersect[k] = arr1[i];
                k++;
                i++;
                j++;
            }
        }
        return intersect;
    }
}

