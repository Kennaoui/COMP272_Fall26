import java.util.TreeMap;
import java.util.TreeSet;

/**
 * COMP 272 Assignment 4 — Exercise 3: nearest free locker.
 *
 * The library has n lockers numbered 1..n, all free at the start.
 * A student asks for a preferred number and receives the FREE locker closest
 * to it. If two free lockers are equally close, the SMALLER number wins.
 *
 * Use java.util.TreeSet and/or java.util.TreeMap (your choice).
 * Do not change the method signatures. You may add private fields and helpers.
 */
public class LockerAssigner {

    // TODO: declare your field(s) here.
    private final TreeSet<Integer> free = new TreeSet<>();
    private final int n;

    /** Create lockers 1..n, all free. Throw IllegalArgumentException if n < 0. */
    public LockerAssigner(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");            
        }
        this.n = n;
        for (int i = 1; i <= n; i++) {
            free.add(i);
        }
    }
    public int assign(int preferred) {
        if (free.isEmpty()) {
            return -1;
        }

        Integer lo = free.floor(preferred);
        Integer hi = free.ceiling(preferred);

        int chosen;
        if (lo == null) {
            chosen = hi;
        } else if (hi == null) {
            chosen = lo;
        } else {
            long distLo = (long) preferred - lo;
            long distHi = (long) hi - preferred;
            chosen = (distLo <= distHi) ? lo : hi;
        }
        free.remove(chosen);
        return chosen;
    }
    public boolean release(int locker) {
        if (locker < 1 || locker > n) {
            return false;
        }
        return free.add(locker);
    }

    /*
     * ======================== Exercise 3, Part B — Analysis =======================
     * Let n be the number of lockers.
     *
     * Q1. Which structure did you use (TreeSet, TreeMap, or both), what does it
     *     store, and why does it fit this problem?
     * 
     * I used a TreeSet, which stroes the numbers of the free lockers. This fit the problem because it keeps elements 
     * sorted, so the floor and ceiling return the two closest free lockers. It will allow me to compare the distances and find
     * the closest one.
     *
     * Q2. If you had stored the free lockers in an unsorted ArrayList instead,
     *     what would be the worst-case running time of assign and of release?
     *
     * The worst-case running time of assign and release would be O(n). You would have to sort through the entire list
     * to find the element you want to assign or release. There is no order, which is why you would have to go through each element.
     * 
     * Q3. What is the worst-case running time of assign and release with your
     *     solution? Justify it using the fact that TreeSet and TreeMap are
     *     red-black trees.
     * 
     * With TreeSet, assign and release are both O(log n). A TreeSet is a red-black tree, with the height being at most 2log2(n+1). 
     * Assign and release use operations that cost O(h), which is O(log n) 
     * ============================================================================
     */
}
