import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.time.Duration;
import java.time.Instant;
//two files, find similarities
//img processing

public class Test 
{
    public static final int row1 = 100, col1 = 300, row2 = 300, col2 = 400, min = 1, max = 1000, pgcdmin = 1000000, pgcdmax = 1000000000, result = 0, sortmin = 100, sortmax = 10000000;
    public static final long a = 1225472788, b = 16789888;

    public static void main(String[] args) throws IOException 
    {
        /* 
        int A[][] = CreateMatrix(row1, col1);
        int B[][] = CreateMatrix(row2, col2);

        long start = System.currentTimeMillis();
        multiplyMatrix(row1, col1, A, row2, col2, B);
        long end = System.currentTimeMillis();      
        System.out.println("\n\nElapsed Time in milli seconds: "+ (end-start));
        */
        

        int[] arr = new int[100000];
        for(int i = 0; i < 100000; i++)
        {
            arr[i] = ThreadLocalRandom.current().nextInt(sortmin, sortmax);
        }
        long start = System.currentTimeMillis();
        
        sortarray(arr, 0, arr.length - 1);
        long end = System.currentTimeMillis();      
        printArray(arr);
        System.out.println("\n\nElapsed Time in milli seconds: "+ (end-start));
    }

    static int[][] CreateMatrix(int rowSize, int colSize)
    {
        int[][] arr = new int[rowSize][colSize];
        for(int i = 0; i < rowSize; i++)
        {
            for(int j = 0; j < colSize; j++)
            {
                arr[i][j] = ThreadLocalRandom.current().nextInt(min, max);
            }
        }
        return arr;
    }

    // Function to print Matrix
    static void printMatrix(int M[][], int rowSize, int colSize)
    {
        for (int i = 0; i < rowSize; i++) 
        {
            for (int j = 0; j < colSize; j++)
                System.out.print(M[i][j] + " ");
            System.out.println();
        }
    }
 
    
    static void multiplyMatrix(int row1, int col1, int A[][],int row2, int col2, int B[][])
    {
        System.out.println("\nMatrix A:");
        printMatrix(A, row1, col1);
        System.out.println("\nMatrix B:");
        printMatrix(B, row2, col2);
 
        if (row2 != col1) 
        {
            System.out.println( "\nMultiplication Not Possible");
            return;
        }
 
        int C[][] = new int[row1][col2];
 
        for (int i = 0; i < row1; i++) 
        {
            for (int j = 0; j < col2; j++) 
            {
                for (int k = 0; k < row2; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        }
 
        System.out.println("\nResultant Matrix:");
        printMatrix(C, row1, col2);
    }




    static void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];
 
        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
 
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
 
    // Main function that sorts arr[l..r] using
    // merge()
    static void sortarray(int arr[], int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m =l+ (r-l)/2;
 
            // Sort first and second halves
            sortarray(arr, l, m);
            sortarray(arr, m + 1, r);
 
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }
 
    /* A utility function to print array of size n */
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
}