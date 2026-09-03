/**
 * SimpleList: a minimal List ADT for teaching purposes.
 * Represents an ordered collection of String elements, indexed from 0.
 * Implementations decide HOW the elements are stored (array, linked
 * nodes, etc.) — this interface only defines WHAT the list can do.
 */
public interface SimpleList<T> {

    // ----- Insertion -----

    /** Appends item to the end of the list. */
    void add(T item);

    /**
     * Inserts item at the given index, shifting elements at and after
     * index one position to the right.
     * @throws IndexOutOfBoundsException if index < 0 or index > size()
     */
    void add(int index, T item);

    // ----- Access -----

    /**
     * Returns the element stored at index.
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    T get(int index);

    /**
     * Replaces the element at index with item and returns the value
     * that was previously there.
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    T set(int index, T item);

    // ----- Removal -----

    /**
     * Removes and returns the element at index, shifting elements
     * after index one position to the left.
     * @throws IndexOutOfBoundsException if index < 0 or index >= size()
     */
    T remove(int index);

    /**
     * Removes the first occurrence of item from the list, if present.
     * @return true if an element was removed, false if item was not found
     */
    boolean remove(T item);

    /** Removes every element from the list. */
    void clear();

    // ----- Query -----

    /** Returns true if item appears anywhere in the list. */
    boolean contains(T item);

    /** Returns the index of the first occurrence of item, or -1 if not found. */
    int indexOf(T item);

    /** Returns the number of elements currently stored. */
    int size();

    /** Returns true if the list contains no elements. */
    boolean isEmpty();
}
