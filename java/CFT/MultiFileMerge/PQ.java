package MultiFileMerge;

public class PQ<Key extends Comparable<Key>> {
    private Key[] keys; // array of keys
    private int[] index;    // index of key
    private int N;  // number of elements on pq
    private final boolean order;

    public PQ(int capacity, boolean order) {
        keys = (Key[]) new Comparable[capacity+1];
        index = new int[capacity+1];
        this.order = order;
    }

    public boolean isEmpty() {
        return N == 0; 
    }

    public void insert(Key key, int i) {
        keys[++N] = key;
        index[N] = i;
        swim(N);
    }

    public int enqueue() { 
        int i = index[1];
        exch(1, N--);
        sink(1);
        keys[N+1] = null;
        return i;
    }

    public Key top() {
        return keys[1];
    }
    
    private void swim(int k) {
        while (k > 1 && less(k, k/2))
        {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= N)
        {
            int j = 2*k;
            if (j < N && less(j+1, j)) j++;
            if (!less(j, k)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) { 
        if (order) return keys[i].compareTo(keys[j]) < 0; 
        return keys[i].compareTo(keys[j]) > 0;
    }

    private void exch(int i, int j) { 
        Key t = keys[i]; keys[i] = keys[j]; keys[j] = t; 
        int ti = index[i]; index[i] = index[j]; index[j] = ti; 
    }
}
