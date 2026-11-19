/******************************************************************
 *
 *   Tristan McKean  / 002
 *
 *   This java file contains the problem solutions of isSubSet, findKthLargest,
 *   and sort2Arrays methods. You should utilize the Java Collection Framework for
 *   these methods.
 *
 ********************************************************************/

import java.util.*;

class ProblemSolutions {

    /**
     * Method: isSubset()
     *
     * Given two arrays of integers, A and B, return whether
     * array B is a subset of array A. Example:
     *      Input: [1,50,55,80,90], [55,90]
     *      Output: true
     *      Input: [1,50,55,80,90], [55,90, 99]
     *      Output: false
     *
     * The solution time complexity must NOT be worse than O(n + m), where
     * n and m are the lengths of arrays A and B respectively.
     * For the solution, use a Hash Table.
     *
     * @param list1 - Input array A
     * @param list2 - input array B
     * @return      - returns boolean value B is a subset of A.
     */

    public boolean isSubset(int list1[], int list2[]) {
        
        Map<Integer, Boolean> present = new HashMap<>();
        for (int x : list1) {
            present.put(x, true);
        }
        
        for (int x : list2) {
            if (!present.containsKey(x)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Method: findKthLargest
     *
     * Given an Array A and integer K, return the k-th maximum element in the array.
     * Example:
     *      Input: [1,7,3,10,34,5,8], 4
     *      Output: 7
     *
     * You may assume that 1 <= k <= array.length. Duplicate values are
     * counted separately, e.g., for [5,5,3] and k = 2, the output is 5.
     * For the solution, use a Priority Queue.
     *
     * @param array - Array of integers
     * @param k     - the kth maximum element
     * @return      - the value in the array which is the kth maximum value
     */

    public int findKthLargest(int[] array, int k) {

       PriorityQueue<Integer> minHeap = new PriorityQueue<>();
       for (int x : array) {
        minHeap.offer(x);
        if (minHeap.size() > k) {
            minHeap.poll();
        }
       }

        return minHeap.peek();
    }


    /**
     * Method: sort2Arrays
     *
     * Given two arrays A and B with n and m integers respectively, return
     * a single array of all the elements in A and B in sorted order. Example:
     *      Input: [4,1,5], [3,2]
     *      Output: 1 2 3 4 5
     *
     * Either array may be empty. Duplicate values are kept in the output.
     * For the solution, use a Priority Queue.
     *
     * @param array1    - Input array 1
     * @param array2    - Input array 2
     * @return          - Sorted array with all elements in A and B.
     */

    public int[] sort2Arrays(int[] array1, int[] array2) {

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int x : array1) {
            minHeap.offer(x);
        }
        for (int x : array2) {
            minHeap.offer(x);

        }

        int[] result = new int[array1.length + array2.length];
        int i = 0;
        while (!minHeap.isEmpty()) {
            result[i++] = minHeap.poll();
        }
        return result;
    }

}