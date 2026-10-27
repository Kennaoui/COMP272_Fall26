public class ProbeHashMap<K, V> {
    private static final Object AVAILABLE = new Object();   // tombstone
    private Object[] keys;
    private Object[] vals;
    private int n = 0;

    public ProbeHashMap(int capacity) { keys = new Object[capacity]; vals = new Object[capacity]; }

    private int h(Object key) { return (key.hashCode() & 0x7fffffff) % keys.length; }

    // Returns index of key, or -1 if absent
    private int find(Object key) {
        int i = h(key);
        for (int p = 0; p < keys.length; p++) {
            if (keys[i] == null) return -1;                     // empty: stop
            if (keys[i] != AVAILABLE && keys[i].equals(key)) return i;
            i = (i + 1) % keys.length;                          // next cell
        }
        return -1;
    }
   
    public V get(K key) { int i = find(key); return i < 0 ? null : (V) vals[i]; }

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

    public V remove(K key) {
        int i = find(key);
        if (i < 0) return null;
        V old = (V) vals[i];
        keys[i] = AVAILABLE; vals[i] = null; n--;               // mark, don't empty
        return old;
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        for (Object k : keys) sb.append(k == null ? "_" : k == AVAILABLE ? "A" : k.toString()).append(' ');
        return sb.toString();
    }
}
