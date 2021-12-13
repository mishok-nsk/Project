package Dequeue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        String str;
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            rq.enqueue(str);
        }

        for (int i = 0; i < n; i++) {
            if (rq.isEmpty()) {
                StdOut.println("No more string!");
                break;
            }
            str = rq.dequeue();
            // rq.enqueue(str);
            StdOut.println(str);
        }
    }

}
