import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class HashingProblems {
    public static double getAverage(int[] keys, HashMap<Integer, Integer> map) {
        HashSet<Integer> seen = new HashSet<>();
        double sum = 0;
        int count = 0;
        for (int key : keys) {
            if (seen.add(key) && map.containsKey(key)) {
                sum += map.get(key);
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }
    public static ArrayList<Integer> odd(HashMap<Integer, Integer> map) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int key : map.keySet()) {
            if (key % 2 != 0) result.add(map.get(key));
        }
        return result;
    }
    /**
     * My approach is that at k == 0, ap air needs the same value at two positions, so we need to count the
     * distinct values that occur at least twice.
     * At k > 0, I am putting all the values into a HashSet, and for each value, count if value + k is also in the set.
     * 
     * Time: Time expectation is O(n). One pass to build the set/map and one pass over hte distinct values.
     * Auxiliary Space: O(n) for the HashSet / Hashmap in the worst case scenario.
     * 
     */
    public static int twoSums(int[] numbers, int k) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int x : numbers) counts.put(x, counts.getOrDefault(x, 0) + 1);

        int pairs = 0;
        for (int v : counts.keySet()) {
            if (k == 0) {
                if (counts.get(v) >= 2) pairs++;
            } else if (counts.containsKey(v + k)) {
                pairs ++;
            }
        }
        return pairs;
    }
    
public static void main(String[] args) {

    // Testing getAverage()
    System.out.println("----- getAverage Tests -----");

    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(1, 10);
    map.put(2, 20);
    map.put(3, 30);

    System.out.println("Basic Test - Expected: 20.0 | Actual: "
            + getAverage(new int[]{1, 2, 3}, map));

    System.out.println("Duplicates - Expected: 20.0 | Actual: "
            + getAverage(new int[]{1, 1, 3}, map));

    System.out.println("No Matches - Expected: 0.0 | Actual: "
            + getAverage(new int[]{4, 5}, map));

    System.out.println("Empty Array - Expected: 0.0 | Actual: "
            + getAverage(new int[]{}, map));

    System.out.println("Empty Map - Expected: 0.0 | Actual: "
            + getAverage(new int[]{1, 2}, new HashMap<>()));

    // Testing odd()
    System.out.println("\n----- odd Tests -----");

    System.out.println("Basic Test - Expected: [10, 30] (any order) | Actual: "
            + odd(map));

    HashMap<Integer, Integer> negativeMap = new HashMap<>();
    negativeMap.put(-1, 5);
    negativeMap.put(-3, 7);
    negativeMap.put(2, 9);

    System.out.println("Negative Odd Keys - Expected: [5, 7] (any order) | Actual: "
            + odd(negativeMap));

    HashMap<Integer, Integer> evenMap = new HashMap<>();
    evenMap.put(2, 10);
    evenMap.put(4, 20);

    System.out.println("No Odd Keys - Expected: [] | Actual: "
            + odd(evenMap));

    System.out.println("Empty Map - Expected: [] | Actual: "
            + odd(new HashMap<>()));

    // Testing twoSums()
    System.out.println("\n----- twoSums Tests -----");

    System.out.println("Basic Test - Expected: 2 | Actual: "
            + twoSums(new int[]{1, 1, 3, 5}, 2));

    System.out.println("K = 0 - Expected: 1 | Actual: "
            + twoSums(new int[]{1, 1, 3, 5}, 0));

    System.out.println("No Duplicates K = 0 - Expected: 0 | Actual: "
            + twoSums(new int[]{1, 2, 3}, 0));

    System.out.println("Duplicates - Expected: 1 | Actual: "
            + twoSums(new int[]{1, 1, 3, 3}, 2));

    System.out.println("Negative Numbers - Expected: 2 | Actual: "
            + twoSums(new int[]{-3, -1, 1}, 2));

    System.out.println("Empty Array - Expected: 0 | Actual: "
            + twoSums(new int[]{}, 1));

    System.out.println("No Pairs - Expected: 0 | Actual: "
            + twoSums(new int[]{1, 10, 20}, 3));
}

  
}
