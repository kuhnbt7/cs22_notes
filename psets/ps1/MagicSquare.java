/* 
 * MagicSquare.java
 * 
 * Computer Science E-22
 * 
 * Modified by: Ben Kuhn, kuhnbt@gmail.com
 */

import java.util.*;

public class MagicSquare {
    // the current contents of the cells of the puzzle values[r][c]
    // gives the value in the cell at row r, column c
    private int[][] values;
    
    // the order (i.e., the dimension) of the puzzle
    private int order;
    
    // the number to which each row/column should sum
    private int magicSum;
    
    // list of numbers that have already been used
    private ArrayList<Integer> usedNumbers;
    
    // track the sum of each row and column on the grid
    private int[] rowSums;
    private int[] colSums;
    
    // track the highest row and column that have been completed
    private int highestRowCompleted;
    private int highestColumnCompleted;

    /*
     * Creates a MagicSquare object for a puzzle with the specified
     * dimension/order.
     */
    public MagicSquare(int order) {
        this.values = new int[order][order];
        this.order = order;
        this.magicSum = ((order * order * order) + order) / 2;
        this.usedNumbers = new ArrayList <>();
        this.rowSums = new int[order];
        this.colSums = new int[order];
        this.highestRowCompleted = 0;
        this.highestColumnCompleted = 0;

    }
    
    // Wrapper for recursive-backtracking method 'solve'

    public boolean solve() {
        return solve(0, 0);
    }
    
    /*
     * Use recursive backtracking to find the values for each cell that solve the magic square and return true, or 
     * return false if the magic square cannot be solved
     */
    
    private boolean solve(int row, int col){
        
        int nextRow;
        int nextCol;
        

        if (row == this.values.length){ // stopping condition - if true, the script has completed every row
            return true;
        }

        for (int num = 1; num <= this.values.length * this.values.length; num++){

            if (this.usedNumbers.contains(num)){ // can't use the same number twice
                continue;
            }
            if (isSafe(row, col, num)){
                setCell(row, col, num);
            
                if (this.highestRowCompleted > this.highestColumnCompleted){ // if more rows than columns 
                                                                             // complete, complete a column
                    nextRow = row + 1;
                    nextCol = this.highestColumnCompleted;  // using highestColumnCompleted instead of col makes it
                                                            // unnecessary to periodically 'reset' the column to a lower value
                
                } else {   // otherwise continue completing the row
                    nextRow = this.highestRowCompleted;
                    nextCol = col + 1;
                }

                if (solve(nextRow, nextCol)){
                    return true;
                }
            removeCell(row, col, num);
                
            }
        }
        return false;
    }
    
    // set the value of cell at row, column equal to value. Add value to usedNumbers. Increment rowSums, colSums,
    // and highestRowCompleted and highestColumncompleted when necessary

    private void setCell(int row, int col, int value){
        this.values[row][col] = value;
        this.rowSums[row] += value;
        this.colSums[col] += value;
        this.usedNumbers.add(value);
        if (col == this.values.length - 1){
            this.highestRowCompleted ++;
        }
        if (row == this.values.length - 1){
            this.highestColumnCompleted ++;
        }
    }
    
    // remove the value of the cell at row, column, set it equal to zero, and remove the value from usedNumbers.
    // Decrement rowSums, colSums, and highestRowCompleted and highestColumnCompleted when necessary

    private void removeCell(int row, int col, int value){
        this.values[row][col] = 0;
        this.rowSums[row] -= value;
        this.colSums[col] -= value;
        this.usedNumbers.remove(Integer.valueOf(value));
        if (col == this.values.length - 1){
            this.highestRowCompleted --;
        }
        if (row == this.values.length - 1){
            this.highestColumnCompleted --;
        }
    }

    /*
     * test whether placing the value at row, column is 'safe', meaning all completed rows and columns sum to the
     * magic number. It is only necessary to test when adding the value will complete a row or column
     */
    
    private boolean isSafe(int row, int col, int value){
         
        if (col == this.values.length -  1){   // this value will complete a row
            if (this.rowSums[row] + value != this.magicSum){  // if current sum of row plus this value doesn't equal
                                                               // the magic value, return false
                return false;
            }
        }
        
        if (row == this.values.length - 1){  // this will complete a column
            if (this.colSums[col] + value != this.magicSum){  // if current sum of this column plus this value 
                                                              // doesn't equal the magic value, return false
                return false;
            }
        }
        
        return true;
    }
                

    /*
     * Displays the current state of the puzzle.
     * You should not change this method.
     */
    public void display() {
        for (int r = 0; r < order; r++) {
            printRowSeparator();
            for (int c = 0; c < order; c++) {
                System.out.print("|");
                if (values[r][c] == 0) {
                    System.out.print("   ");
                } else {
                    if (values[r][c] < 10) {
                        System.out.print(" ");
                    }
                    System.out.print(" " + values[r][c] + " ");
                }
            }
            System.out.println("|");
        }
        printRowSeparator();
    }
    
    // A private helper method used by display()
    // to print a line separating two rows of the puzzle.
    private void printRowSeparator() {
        for (int i = 0; i < order; i++) {
            System.out.print("-----");
        }
        System.out.println("-");
    }
    
    public static void main(String[] args) {
        /*
         * You should NOT change any code in this method
         */
        
        Scanner console = new Scanner(System.in);
        System.out.print("What order Magic Square? ");
        int order = console.nextInt();
        
        MagicSquare puzzle = new MagicSquare(order);
        if (puzzle.solve()) {
            System.out.println("Here's the solution:");
            puzzle.display();
        } else {
            System.out.println("No solution found.");
        }
    }
}