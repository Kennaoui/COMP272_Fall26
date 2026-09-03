/**
 * SimpleList: the List ADT for Module 1, stated once for any element
 * type T.
 *
 * It says WHAT an ordered, position-indexed collection can do; an
 * implementing class decides HOW the elements are stored (a resizing
 * array, linked nodes, ...). Client code depends only on this contract.
 *
 * T is a type parameter: the caller picks the element type at the point
 * of use, e.g. SimpleList<String> or SimpleList<Integer>.
 */
public interface SimpleList<T> {

    /** Appends item to the end of the list. */
    void add(T item);

    /**
     * Inserts item so that it ends up at position index, shifting the
     * element currently at index (and everything after it) one position
     * later.
     *
     * @throws IndexOutOfBoundsException if index < 0 or index > size()
     */
    void add(int index, T item);

    /**
     * Returns the element stored at position index.
     *
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    T get(int index);

    /**
     * Removes the element at position index and returns it. Elements
     * after index move one position earlier.
     *
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    T remove(int index);

    /** Returns how many elements are currently stored. */
    int size();

    /** Returns true if the list has no elements. */
    boolean isEmpty();
}
