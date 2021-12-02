package Dequeue;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;
    
    private class Node
    {
        Item item;
        Node next;
        Node prev;
    }
    
    // construct an empty deque
    public Deque() {
        last = null;
        first = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty()
    { return size == 0;}

    // return the number of items on the deque
    public int size() 
    { return size;}

    // add the item to the front
    public void addFirst(Item item) {
        
        if (item == null) throw new IllegalArgumentException("Method addfirst null argument.");

        Node oldfirst = first;
        first = new Node();
        first.next = oldfirst;
        first.prev = null;
        first.item = item;
        if (isEmpty()) last = first;
        else oldfirst.prev = first;
        size++;   

    }

    // add the item to the back
    public void addLast(Item item) {
        
        if (item == null) throw new IllegalArgumentException("Method addlast null argument.");

        Node oldlast = last;
        last = new Node();
        last.next = null;
        last.prev = oldlast;
        last.item = item;
        if (isEmpty()) first = last;
        else oldlast.next = last;
        size++;   
    }

    // remove and return the item from the front
    public Item removeFirst() {
        
        if (isEmpty()) throw new java.util.NoSuchElementException("Method removeFirst call for empty deque");
        
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        
        if (isEmpty()) throw new java.util.NoSuchElementException("Method removeLast call for empty deque");

        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new ListIterator();}

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { 
            /* not supported */ 
            throw new UnsupportedOperationException("Method remove() of iterator not supported");
        }
        
        public Item next()
        {
            if (current == null)throw new java.util.NoSuchElementException("Method next() of iterator call when no more item");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        String str;
        boolean way = false;
        Deque<String> stringDeque = new Deque<String>();

        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            if (way) stringDeque.addFirst(str);
            else stringDeque.addLast(str);
            way = !way;
        }
        
        StdOut.println("Deque size: "+ stringDeque.size());

        for (String s : stringDeque) {
            StdOut.print(s + "->");
        }
        StdOut.println();

        while (!stringDeque.isEmpty()) {
            if (way) {
                str = stringDeque.removeFirst();
                StdOut.println("<-" + str);
            }
            else {
                str = stringDeque.removeLast();
                StdOut.println(str + "->");
            }
            way = !way;
        }
    }

}
