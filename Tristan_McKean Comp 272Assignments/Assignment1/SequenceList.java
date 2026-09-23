/**
 * SequenceList: the List ADT used in this assignment.
 *
 * A SequenceList is an ordered collection: elements are kept in the order
 * they are added (or inserted), and each element can be reached by its
 * position, starting at index 0.
 *
 * This is an interface: it states WHAT a sequence can do, not HOW it does
 * it. A class that "implements SequenceList<T>" must provide a body for
 * every method below. Client code can then depend only on this contract
 * -- it never needs to know whether the elements live in an array, in
 * linked nodes, or in some other representation.
 *
 * T is a type parameter (a generic): it lets the SAME contract be reused
 * for a sequence of String, a sequence of Integer, a sequence of
 * anything -- the caller picks T when it declares the variable, e.g.
 * SequenceList<String>.
 */
public interface SequenceList<T> {

    /** Appends item to the end of the sequence. */
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

    /** Returns true if the sequence has no elements. */
    boolean isEmpty();
}
