## Week 3 - Sorting

### Finishing up recursive backtracking

Template for recursive backtracking:

```
boolean findSolution(int n){
    if (found a solution){
        this.displaySolution();
        return true;
    }

    // loop over possible values for nth variable
    for (val = first to last){
        if (this.isValid(val, n)){
            this.applyValue(val, n);
            if (this.findSolution(n + 1, _other params_)){
                return true;
            }
            this.removeValue(val, n);
        }
    }
    return false;
}
```

### Recursion vs Iteration

Recursion requires a bit more overhead than iteration.

Rule of thumb: if it's easier to implement a method with recursion, do it. Otherwise, use iteration.

### Sorting and Algorithm Analysis

Ground rules:
- sort in increasing order
- sort inplace

**Goal: minimize the number of comparisons C and moves M needed to sort the array.**

Defining a swap method:
```
public static void swap(int[] arr, int pos1, int pos2){
    int swapVar = arr[pos1];
    arr[pos1] = arr[pos2];
    arr[pos2] = swapVar;
}
```

### Selection Sort
- Consider the positions in the array from left to right. For each position, find the value that belongs in that position and swap it with the element that's currently there.

Example:
```
{15, 6, 2, 12, 4} //swap 15 and 2
{2, 6, 15, 12, 4} // swap 6 and 4
{2, 4, 15, 12, 6} // swap 15 and 6
{2, 4, 6, 12, 15} // 12 swaps with itself
{2, 4, 6, 12, 15} // don't need to consider last position
```

Implementation:

```
private static int indexSmallest(int[] arr, int start){
    int idx = start;
    for (int i = start + 1; i < arr.length; i++){
        if (arr[i] < arr[idx]){
            idx = i;
        }
    }
    return idx;
}

public static void selectionSort(int[] arr){
    int smallest;
    for (int i = 0; i < arr.length - 1; i++){
        smallest = indexSmallest(arr, i);
        swap(arr, i, smallest);
    }
}
```

### Time Analysis

The _time efficiency_ of an algorithm is some measure of the numbr of operations it performs.

We want to characterize how the number of operations depends on the size of n, the input to the algorithm. How does # operations grow as n grows?

C(n): number of comparisons for array of length n
M(n): number of moves for array of length n

### Time analysis of selection sort

We know the loop performs n - 1 iterations. For each iteration (pass), the algorithm makes n - 1, then n - 2, then n - 3, etc comparisons. The formula is m(m + 1) / 2, and we can plug in n to get C(n) = n<sup>2</sup> / 2 - n/2.

### Focusing on the largest term

When n is large, mathematical expressions of n are dominated by their largest term - the term that grows fastest as a function of n. 

So we will focus on the largest term, and also ignore the coefficient.

So for selection sort, we say <code>C(n) = O(n<sup>2</sup>)</code>

|Name | Example Expressions | Big O Notation|
|-----|---------------------|---------------|
|Constant time| 1, 7, 10 |O(1)|
|Logarithmic time|3log<sub>10</sub>n, log<sub>2</sub>n + 5|O(log n)|
|Linear time|5n, 10n - 2log<sub>2</sub>n|O(n)|O(n)|
|nlogn time|4log<sub>2</sub>n, nlog<sub>2</sub>n + n| O(nlogn)|
|Quadratic time|2n<sup>2</sup> + 3n, n<sup>2</sup> - 1|O(n<sup>2</sup>)|
|Exponential time|2<sup>n</sup>, 5e<sup>n</sup> + 2n<sup>2</sup>|O(c<sup>2</sup>)|
<br>
Each consecutive efficiency class in the table above grows faster than the ones below it when n is large.
  
### Back to selection sort
Number of moves: after each of the n - 1 passes, selection sort does one swap. Each swap has 3 moves. So the number of moves is 3n - 3, which is linear with respect to n.

So for total operations for selection sort, the largest term is C(n), and the algorithm is O(n<sup>2</sup>) - quadratic time.

In this class we'll be discussing 'tight bound' for big O, not an upper bound. For example, an algorithm that is O(n) is also O(n<sup>2</sup>) in the sense that it is less than O(n<sup>2</sup>), but it's more precise to say that it's O(n).

### Insertion sort

Going from left to right, 'insert' each element into its proper place with respect to all elements to its left. Slide the other elements over to make room.

Example:
```
{15, 4, 2, 12, 6}
{4, 15, 2, 12, 6}
{2, 4, 15, 12, 6}
{2, 4, 12, 15, 6}
{2, 4, 12, 15, 6}
```

So unlike selection sort, we are focusing on the elements and where they belong (versus the positions).

Implementation:
```
public static void insertionSort(int[] arr){
    for (int i = 1; i < arr.length; i ++){
        if (arr[i] < arr[i-1]) {
            int toInsert = arr[i];

            int j = i;
            do{
                arr[j] = arr[j-1];
                j = j - 1;
            } while (j > 0 && toInsert < arr[j-1]);
            
            arr[j] = toInsert;
        }
    }
}
```

### Time analysis of Insertion Sort

In the best case, when the array is sorted or almost sorted, `C(n) = n-1 = O(n)` and `M(n) = 0`. So running time is O(n).

In the worst case, array is in backwards order, and every element is compared to all the previous elements. C(n) = O(n<sup>2</sup>) and M(n) = O(n<sup>2</sup>).

In the average case, when elements are randomly arranged, each element is compared to half the elements on its left. This only changes the coefficent, so it is still O(n<sup>2</sup>).

### Shell Sort

Improves on insertion sort, takes advantage of the fact that it's fast when almost sorted. It allows larger moves so elements get closer to where they belong.

So: it creates subarrays using an increment, and then repeats using progressively smaller increments.

Example, increment of 3:
```
{36, 18, 10, 27, 3, 20, 9, 8}
Subarrays are {36, 27, 9}, {18, 3, 8}, and {10, 20}

Sort using insertion sort:
{9, 3, 10, 27, 8, 20, 36, 18}

Lower increment to 1 (this is insertion sort)
```

Shell sort doesn't actually seperate into subarrays. It does it all at once and only considers subarrays.

The sequence of increments has a number of choices. We use 2<sup>k</sup> - 1 for some k. Eg 63, 31, 15, 7, 3, 1. Avoid sequences with numbers that are multiples of each other.

Implementation:

```
public static void shellSort(int[] arr){
    int incr = 1; // set up the increment
    while (2 * incr <= arr.length){
        incr = 2 * incr;
    }
    incr = incr - 1;

    while (incr >= 1){
        for (int i = incr; i < arr.length; i++){
            if(arr[i] < arr[i-incr]){
                int toInsert = arr[i];

                int j = i;
                do{
                    arr[j] = arr[j-incr];
                    j = j - incr;
                } while (j > incr-1 && toInsert < arr[j-incr]);
                arr[j] = toInsert;
            }
        }
        incr = incr / 2; // reduce incr for next pass
    }
}
```

### Time analysis of insertion sort
- Hard to analyze precisely, we generally use experiements
- With bad sequence of increments, it's O(n<sup>2</sup>) in the worst case.
- With good interval sequence, it's O(n<sup>1.5</sup>) in the worst case and maybe more like O(n<sup>7/6</sup>)

So significantly better than insertion or selection for large n.

### Bubble Sort

Go from left to right, at each pass swap adjacent elements if they are out of order.

Example:
```
{28, 24, 37, 15, 5}
{24, 28, 15, 5, 37}
{24, 15, 5, 28, 37}
{15, 5, 24, 28, 37}
{5, 15, 24, 28, 37}
```
Note that at the end of the kth pass, the k rightmost elements are in their positions so we don't need to consider them in subsequent passes.

Implementation:

```
public static void bubbleSort(int[] arr){
    for (int i=arr.length -1; i>0; i--){
        for (int j=0; j<i; j++){
            if (arr[j] > arr[j+1]){
                swap(arr, j, j+1);
            }
        }
    }
}
```

### Time analysis of Bubble Sort

C(n) = O(n<sup>2</sup>)

M(n):
- worst case: every comparison leads to a swap, so M(n) = O(n<sup>2</sup>)
- best case: array is already sorted, so no moves are needed

Total running time: O(n<sup>2</sup>)

















