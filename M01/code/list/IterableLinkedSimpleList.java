import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * IterableLinkedSimpleList: the SAME List ADT and the SAME singly linked
 * representation as LinkedSimpleList, plus one design choice -- about the
 * interface, not the representation: this class also implements
 * Iterable<T>, so clients (and the class's own code) can use a for-each
 * loop over it.
 *
 * What is new: iterator() and the NodeIterator cursor class. And
 * toString() is now a for-each over "this" rather than a second
 * hand-written node walk.
 */
public class IterableLinkedSimpleList<T> implements SimpleList<T>, Iterable<T> {

    // Unchanged from LinkedSimpleList.
    // static: a Node needs nothing from the enclosing list.
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

    // Read the element at index (valid range 0..size-1). O(n).
    @Override
    public T get(int index) {
        checkIndex(index);
        return reachNode(index).data;
    }

    // EXERCISE -- same as in LinkedSimpleList.
    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("remove(int): left as an exercise");
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    // ----- the iterator design choice --------------------------------

    /**
     * The one method Iterable<T> requires. Once it is here,
     *
     *     for (T x : list) { ... }
     *
     * is legal, and the compiler expands it to
     *
     *     Iterator<T> it = list.iterator();
     *     while (it.hasNext()) { T x = it.next(); ... }
     */
    @Override
    public Iterator<T> iterator() {
        return new NodeIterator();
    }

    /**
     * A one-way cursor over the elements.
     *
     * NOT static: it reads head from the enclosing list instance.
     * (Compare Node, which is static because it needs nothing from the
     * list.) "Does it need the outer object?" is the whole rule.
     *
     * This is the only code outside the core methods that touches a
     * Node, and it stays inside the class -- so "client code never sees
     * a Node" still holds.
     */
    private class NodeIterator implements Iterator<T> {
        private Node<T> cursor = head;   // the next node to return

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public T next() {
            if (cursor == null) {
                throw new NoSuchElementException();
            }
            T value = cursor.data;
            cursor = cursor.next;        // remembers its place between calls
            return value;
        }
    }

    // toString() now consumes this class's OWN iterator: there is no
    // second hand-written node walk to keep in sync with the first.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (T item : this) {
            if (!first) sb.append(", ");
            sb.append(item);
            first = false;
        }
        return sb.append("]").toString();
    }

    // ----- internal helpers -----------------------------------------

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
    }

    // The node at position index, reached by walking from head. O(n).
    private Node<T> reachNode(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current;
    }

    // ----- demo ----------------------------------------------------

    public static void main(String[] args) {
        IterableLinkedSimpleList<String> route = new IterableLinkedSimpleList<>();
        route.add("Rogers Park");
        route.add("Loyola");
        route.add("Granville");

        // client code: a plain for-each, exactly as over an ArrayList
        for (String stop : route) {
            System.out.println("stop:  " + stop);
        }

        // the class's own toString uses the same iterator
        System.out.println("list:  " + route);

        // what the for-each expands to
        Iterator<String> it = route.iterator();
        while (it.hasNext()) {
            System.out.println("again: " + it.next());
        }
    }
}
