public class LinkedSimpleList<T> implements SimpleList<T> {

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private int size;

    // Append at the end. O(n): with no tail reference we must walk to the last node.
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

    // Insert so the new element ends up at position index (valid: 0..size).
    public void add(int index, T item) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException(index);
        if (index == 0) {
            Node<T> node = new Node<>(item);
            node.next = head;
            head = node;
        } else {
            Node<T> prev = node(index - 1);
            Node<T> node = new Node<>(item);
            node.next = prev.next;
            prev.next = node;
        }
        size++;
    }

    public T get(int index) {
        checkIndex(index);
        return node(index).data;
    }

    // Remove the element at index (valid: 0..size-1) and return it.
    public T remove(int index) {
        checkIndex(index);
        Node<T> removed;
        if (index == 0) {
            removed = head;
            head = head.next;
        } else {
            Node<T> prev = node(index - 1);
            removed = prev.next;
            prev.next = removed.next;
        }
        removed.next = null;
        size--;
        return removed.data;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    // --- internal helpers -------------------------------------------------

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(index);
    }

    // The node at position index, reached by walking from head. O(n).
    private Node<T> node(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current;
    }
}
