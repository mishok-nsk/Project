package collinear;
import java.util.Arrays;
//import java.io.*;
//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    LineSegment[] segments;
    int numOfSegments;

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        segments = new LineSegment[10];
        numOfSegments = 0;
        int length = points.length;
        Arrays.sort(points);
        /*
        for(Point p : points) {
            StdOut.println(p);
        } */
        
        for (int i = 0; i < length - 3; i++) {
            for (int j = i + 1; j < length - 2; j++) {
                double slope = points[i].slopeTo(points[j]);
                int numOfPoints = 0;
                for (int k = j + 1; k < length; k++) {
                    if (slope == points[i].slopeTo(points[k])) {
                        //numOfPoints++;
                        if (++numOfPoints == 2) {
                            if (segments.length == numOfSegments)
                                resize(numOfSegments*2); 
                            segments[numOfSegments++] = new LineSegment(points[i], points[k]);
                            //break;
                        }

                    }
                }
            }
        }
        resize(numOfSegments);
    }
    
    private void resize(int size) {
        LineSegment[] copy = new LineSegment[size];
        for (int i = 0; i < numOfSegments; i++)
        copy[i] = segments[i];
        segments = copy;
    }

    public int numberOfSegments() {       // the number of line segments
        return numOfSegments;
    }
    public LineSegment[] segments() {               // the line segments
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
