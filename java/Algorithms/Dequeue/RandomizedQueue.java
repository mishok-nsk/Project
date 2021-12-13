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
        if (item == null) throw new IllegalArgumentException("Method enqueue null argument.");
        if (size == capacity) {
            capacity *= 2;
            resize();
        }
        value[size++] = item;
        
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Method dequeue call for empty queue");
        int i = StdRandom.uniform(size);
        Item rem = value[i];
        value[i] = null;
        value[i] = value[--size];
        if (size <= capacity/4 && capacity > 10) {
            capacity /= 2;
            resize();
        }
        return rem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Method sample call for empty queue");
        return value[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        int n = size;

        public boolean hasNext() {
            return n != 0;
        }

        public void remove() {
            /* not supported */ 
            throw new UnsupportedOperationException("Method remove() of iterator not supported");
        }

        public Item next() {
            if (n == 0)throw new java.util.NoSuchElementException("Method next() of iterator call when no more item");
            int i = StdRandom.uniform(n);
            Item tmp = value[i];
            value[i] = value[--n];
            value[n] = tmp;
            return tmp;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        String str;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        // rq.sample();
        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            rq.enqueue(str);
        }

        for (String s:rq) {
            StdOut.print(s + " - ");
        }
        StdOut.println();
        for (String s:rq) {
            StdOut.print(s + " - ");
        }
        StdOut.println();

        while (!rq.isEmpty()) {
            str = rq.dequeue();
            // rq.enqueue(str);
            StdOut.print(str + " - ");
        }
    }

}