/**
 * LinkedSimpleList: concrete implementation of the
 * SimpleList<T> contract, backed by singly linked nodes.
 *
 * Representation: a single "head" reference and an int "size".
 *
 * Elaborated here: add(item), get, size, isEmpty, toString, and the
 * private walk helpers node(int) / checkIndex.
 *
 */
public class LinkedSimpleList<T> implements SimpleList<T> {

    /**
     * One element plus a link to the next node.
     *
     * static: a Node needs nothing from its enclosing LinkedSimpleList,
     * so it does not carry a reference to one.
     * private: outside code never sees a Node: every access goes
     * through the methods below, whihc is what lets this class keep its
     * invariant (head -> next -> ... visits exactly the stored elements,
     * in order, ending at null).
     */
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private int size;

    // Append at the end.
    // O(n): with no tail reference we must walk to the last node first.
    @Override
    public void add(T item) {
        Node<T> node = new Node<>(item);
        if (head == null) {
            head = node;
        } else {
            Node<T> current = head;
            while (current.next != null) current = current.next;
            current.next = node;
        }
        size++;
    }

    // Insert so the new element ends up at position index
    // (valid range 0..size). Relink so that walking from head still
    // visits every element in order; remember index == 0 changes head,
    // and remember to update size.
    @Override
    public void add(int index, T item) {
        throw new UnsupportedOperationException("add(int, T): left as an exercise");
    }

    // Read the element at index (valid range 0 .. size-1). O(n).
    @Override
    public T get(int index) {
        checkIndex(index);
        return reachNode(index).data;
    }

    // Remove the element at index (valid range 0 ... size-1) and
    // return it. Redirect one "next" link past the removed node; handle
    // index == 0 (head changes) and update size.
    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("remove(int): left as an exercise");
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Node<T> current = head; current != null; current = current.next) {
            sb.append(current.data);
            if (current.next != null) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    // ----- internal helpers ---------------------------------------------

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
    }

    // The node at position index, reached by walking from head. O(n).
    // Useful when you implement add(int, T) and remove(int).
    private Node<T> reachNode(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current;
    }
}
