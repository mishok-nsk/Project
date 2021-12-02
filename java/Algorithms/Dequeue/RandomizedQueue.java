package Dequeue;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] value;
    private int size;
    private int capacity;

    // construct an empty randomized queue
    public RandomizedQueue() {
        capacity = 10;
        value = (Item[]) new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.size;
    }

    private void resize() {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
        copy[i] = value[i];
        value = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (size == capacity) {
            capacity *= 2;
            resize();
        }
        value[size++] = item;
        
    }

    // remove and return a random item
    public Item dequeue() {
        int i = StdRandom.uniform(size);
        Item rem = value[i];
        value[i] = value[--size];
        if ( size <= capacity/4) {
            capacity /= 2;
            resize();
        }
        return rem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return value[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        int N = size;

        public boolean hasNext() {
            return N != 0;
        }

        public void remove() {
            /* not supported */ 
            throw new UnsupportedOperationException("Method remove() of iterator not supported");
        }

        public Item next() {
            if (N == 0)throw new java.util.NoSuchElementException("Method next() of iterator call when no more item");
            int i = StdRandom.uniform(N);
            Item tmp = value[i];
            value[i] = value[--N];
            value[N] = tmp;
            return tmp;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        String str;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            rq.enqueue(str);
        }

        for(String s:rq) {
            StdOut.println(s);
        }
    }

}