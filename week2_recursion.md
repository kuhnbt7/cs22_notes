## Week 2 - Recursion

We often use _iteration_ when solving a problem that requires repetition.

An alternative method is _recursion_. A recursive method is one that calls itself.

When we use recursion, we solve a problem by reducing it to a simpler problem of the same kind. We keep doing this until we reach a problem simple enough to be solved directly (base case).


We add calls to the stack frame until the base case is reached, at which point the values from each recursive call are returned to the stack frame that created the call.

We reach _infinite recursion_ if a recursive method does not reach a base case. This is known as _stack overflow_.

### Designing a recursive method

1. What is the base case?
2. Find the recursive substructure: how could I use the solution to a smaller subproblem to solve the overall problem?
3. Solve the smaller problem using a recursive call

### Processing a string recursively

A string is either:
1. Empty ("")
2. A single character, followed by a string

So we can deal with a string by processing one-two characters, and then making a recursive call to process the rest of the string.

Example:
```
public static void printVertical(String str){
    if (str == null || str.equals("")){ // order here matters
        return;
    }
    System.out.println(str.charAt(0));
    printVertical(str.substring(1));
}
```

(I skipped a bunch of explanation on examples of recursion that I already know)

### Recursive backtracking

#### Motivating example: N-Queens problem
- Place a queen on each row of NxN chess board such that no queen 'checks' another queen

We can solve this with a class with the following structures:
```
this.board[][]  // sets up array to represent board
private void placeQueen(int row, int col);
private void removeQueen(int row, int col);
private void isSafe(int row, int col);
private boolean findSolution(int row);
```

Then use this method:
```
private boolean findSolution(){
    for (int col = 0; col < this.board.length; col++){
        if (this.isSafe(row, col)){
            this.placeQueen(row, col);
            if (this.findSolution(row + 1)){
                return true;
            }
            this.removeQueen(row, col);
        }
        
    }
    return false;
}
```
This iterates through all the possible columns, tried placing a queen in each column and then solving the rest of the board. If the algorithm gets to the end of the board and still returns through, it is solved. Otherwise, the algorithm returns false and it tries the next column.

Note that we use a wrapper to call the private recursive method.

Recursive backtracking is useful for _constraint satisfaction problems_, which are problems where you are assigning values to a set of variables according to a set of constraints.

Also, we added some optimizations to check constraints so that you don't have to iterate through the entire array every time you want to check to see if adding a queen is safe.


