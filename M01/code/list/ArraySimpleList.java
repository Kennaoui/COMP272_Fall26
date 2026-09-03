import java.util.Arrays;

/**
 * ArraySimpleList: SimpleList<T> backed by a resizing array (Notebook 2).
 *
 * The Object[] may be larger than size; the first `size` slots hold the
 * elements in order. When it fills, append allocates a doubled array and
 * copies -- so append is O(1) amortized, not O(1) every time.
 */
public class ArraySimpleList<T> implements SimpleList<T> {

    private Object[] elements = new Object[4];
    private int size;

    // Append. O(1) amortized (grow-and-copy when full).
    @Override
    public void add(T item) {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        elements[size++] = item;
    }

    // Insert at index (valid 0..size). O(n): the tail block shifts right.
    @Override
    public void add(int index, T item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = item;
        size++;
    }

    // Indexed read. O(1).
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        return (T) elements[index];
    }

    // Remove at index (valid 0..size-1). O(n): the tail block shifts left.
    @Override
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
        T removed = (T) elements[index];
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null;
        return removed;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}
