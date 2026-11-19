import java.util.Objects;
/**
 * A hash map that uses open addressing with linear probing in order to 
 * resolve collisions. Deleted entries are marked with the AVAILABLE tombstone.
 * This makes sure that probe sequences for other keys aren't broken.
 * 
 * @param <K> is the type of keys stored in the map
 * @param <V> is the type of values stored in the map
 */

public class ProbeHashMap<K, V> {
    private static final Object AVAILABLE = new Object();   // tombstone
    private Object[] keys;
    private Object[] vals;
    private int n = 0;

    /**
     * Creates an empty map with a fixed table size. Both parallel arrays 
     * start filled with null, meaning every cell is "never used." Does not resize.
     * @param capacity is the number of cells in the table
     */

    public ProbeHashMap(int capacity) { keys = new Object[capacity]; vals = new Object[capacity]; }

    /**
     * Hash Function: maps a key to its home cell. Masks with 0x7fffffff clears the sign bit
     * so that there is no negative index that can be created.
     * @param key is the key to hash
     * @return returns the home index of the key, in the range provided.
     */
    private int h(Object key) { return (key.hashCode() & 0x7fffffff) % keys.length; }

    // Returns index of key, or -1 if absent
    /**
     * Locates the cell holding key using linear probing. It moves from cell to cell
     * until it either finds the key or reaches a null cell. Tombstones are skipped over, not treated at the end of the 
     * search. A full table with no match will terminate.
     * 
     * 
     * 
     * @param key is the key being searched for
     * @return is the index of the cell holding key, or -1 if absent.
     */
    private int find(Object key) {
        int i = h(key);
        for (int p = 0; p < keys.length; p++) {
            if (keys[i] == null) return -1;                     // empty: stop
            if (keys[i] != AVAILABLE && keys[i].equals(key)) return i;
            i = (i + 1) % keys.length;                          // next cell
        }
        return -1;
    }
    /**
     * Returns the value mapped to the key, or null if absent.
     * Uses find() to locate the cell.
     * 
     * @param key is the value which should be returned
     * @return is the value mapped to the key, or null.
     */
   
    public V get(K key) { int i = find(key); return i < 0 ? null : (V) vals[i]; }

    /**
     * Puts values with keys. If the key is already present, its value is overwritten
     * and the old value is returned. Otherwise, the entry is placed in the first free cell, and 
     * null is returned. IllegalStateException occurs if every cell is occupied.
     * 
     * 
     * @param key is the key to insert/update
     * @param value the value to associate with the key
     * @return the previous value if key is already present, or null.
     */

    public V put(K key, V value) {
        int i = find(key);
        if (i >= 0) { V old = (V) vals[i]; vals[i] = value; return old; }
        if (n == keys.length) throw new IllegalStateException("table full");
        i = h(key);
        while (keys[i] != null && keys[i] != AVAILABLE)          // first free cell
            i = (i + 1) % keys.length;
        keys[i] = key; vals[i] = value; n++;
        return null;
    }
    /** 
     * A rebugging helper that returns the table layout as a string.
     * _ for a never-used cell, A for tombstone, or the key's toString() for a live entry.
     * 
     * @return a space-separated string describing every cell of the table.
     * 
     */

    public String dump() {
        StringBuilder sb = new StringBuilder();
        for (Object k : keys) sb.append(k == null ? "_" : k == AVAILABLE ? "A" : k.toString()).append(' ');
        return sb.toString();
    }
    
    /**
     * Removes the entry associated with key, if present.
     *
     * @param key the key whose entry should be removed
     * @return the removed value, or null if the key is not found
     *
     * After a successful removal:
     * - Mark the tombstone.
     * - Return the stored value.
     * - Update the number of active entries.
     * Other entries must remain reachable through probing.
     */
    public V remove(K key) {
        int i = find(key);
        if (i < 0) return null;
        V old = (V) vals[i];
        keys[i] = AVAILABLE;
        n--;
        return old;
    }

    /**
     * Removes the entry associated with key only if its current
     * value equals the specified value.
     *
     * @param key the key whose entry should be removed
     * @param value the value that must match the stored value
     * @return true if the entry was removed; false otherwise
     *
     * If the key is absent or its value does not match, leave the
     * map unchanged. A successful removal must use a tombstone
     * and update the number of active entries.
     * Compare values by equality, including possible null values.
     */
    public boolean remove(K key, V value) {
        int i = find(key);
        if (i < 0) return false;
        if (!Objects.equals(vals[i], value)) return false;
        keys[i] = AVAILABLE;
        n--;
        return true;
    }

    /**
     * Replaces the value associated with key only if the key
     * is already present in the map.
     *
     * @param key the key whose value should be replaced
     * @param value the new value to store
     * @return true if the replacement occurred; false otherwise
     *
     * If the key is absent, leave the map unchanged.
     * Do not insert a new entry or change the number of entries.
     * A key mapped to null is still considered present.
     */
    public boolean replace(K key, V value) {
        int i = find(key);
        if (i < 0) return false;
        vals[i] = value;
        return true;
    }

    /**
     * Replaces the value associated with key with newValue only
     * if the key is present and its current value equals oldValue.
     *
     * @param key the key whose value should be replaced
     * @param oldValue the value that must match the stored value
     * @param newValue the new value to store
     * @return the previous value if replacement occurred;
     *         null otherwise
     *
     * If the key is absent or its value does not match, leave the
     * map unchanged. Do not change the number of entries.
     * Compare values by equality, including possible null values.
     * A successful replacement of a null value also returns null.
     */
    public V replace(K key, V oldValue, V newValue) {
        int i = find(key);
        if (i < 0) return null;
        if (!Objects.equals(vals[i], oldValue)) return null;
        V previous = (V) vals[i];
        vals[i] = newValue;
        return previous;

    }

    /*
     * TODO: Explain the role of tombstones:
     * - How does a tombstone differ from a never-used slot?
     * A never used slot means that no key has ever been there, so you know that it is absent when searched.
     * A tombstone (AVAILABLE) means that a key used to be here but was deleted. The search should not stop, and it should continue on 
     * past the tombstone.
     * - Why can replacing a deleted key with null break searches?
     * This can break searches because it will make it look like it was never used. Any key that was placed after it will
     * become unreachable because find() stops at null.
     * - How do searches and insertions handle tombstones?
     * Searches skip over tombstones and keep probing until the key is found, a null is found, or the whole table has been searched.
     * Insertions uses find() to whether a key exists, and then stores the new entry in the first null or tombstone cell.
     * Include a short example with colliding keys and a deletion.
     * For example. If you put A into index spot 2, B into 3, and C into 4, a table would look like
     * _ _ A B C. If you remove B, it will look like _ _ A T C. When trying to find C, it would skip 3, because it is T, and move on to the 4th index.
     * However, if you removed and set 3 to Null, it would be _ _ A _ C, and the search would stop after 3, because there would be null.
     */
  
    private static void check(String name, Object expected, Object actual) {
        boolean ok = Objects.equals(expected, actual);
        System.out.println((ok ? "PASS " : "FAIL ") + name
                + " | expected: " + expected + " | actual: " + actual);

    }
    public static void main(String[] args) {
        ProbeHashMap<Integer, String> map = new ProbeHashMap<>(5);
 
        // Successful operation
        check("put new key", null, map.put(2, "a"));
        check("get existing key", "a", map.get(2));
 
        // Unsuccessful operation
        check("remove missing key", null, map.remove(99));
 
        // Collisions: 7 and 12 go to the next free cells after cell 2
        map.put(7, "b");
        map.put(12, "c");
        check("collisions", "_ _ 2 7 12 ", map.dump());
 
        // Deletion: removing 7 leaves a tombstone (A), and 12 is still found
        check("remove existing key", "b", map.remove(7));
        check("tombstone left behind", "_ _ 2 A 12 ", map.dump());
        check("get key after tombstone", "c", map.get(12));
 
        // Reuse of a tombstone: 17 also hashes to cell 2 and takes the A cell
        map.put(17, "d");
        check("tombstone reused", "_ _ 2 17 12 ", map.dump());
 
        // One check for each remaining method
        check("remove(key, value)", true, map.remove(17, "d"));
        check("replace(key, value)", true, map.replace(2, "Z"));
        check("replace(key, old, new)", "Z", map.replace(2, "Z", "Y"));
        check("replace missing key", false, map.replace(99, "q"));
    }
}

