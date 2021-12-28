package collinear;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int numOfsegments;

    public BruteCollinearPoints(Point[] inpoints) {    // finds all line segments containing 4 points
        if (inpoints == null) {
            throw new IllegalArgumentException("argument to BruteCollinearPoints constructor is null");
        }

        for (Point p : inpoints) {
            if (p == null) {
                throw new IllegalArgumentException("argument to BruteCollinearPoints constructor is null");
            }
        }
        Point[] points=Arrays.copyOf(inpoints, inpoints.length);
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0) {
                throw new IllegalArgumentException("argument to BruteCollinearPoints contains a repeated point");
            }
        }
        segments = new LineSegment[1];
        numOfsegments = 0;
        int length = points.length;
        
        for (int i = 0; i < length - 3; i++) {
            for (int j = i + 1; j < length - 2; j++) {
                double slope = points[i].slopeTo(points[j]);
                int numOfPoints = 0;
                for (int k = j + 1; k < length; k++) {
                    if (slope == points[i].slopeTo(points[k])) {
                        numOfPoints++;
                        if (numOfPoints == 2) {
                            if (segments.length == numOfsegments)
                                resize(numOfsegments*2); 
                            segments[numOfsegments++] = new LineSegment(points[i], points[k]);
                            break;
                        }

                    }
                }
            }
        }
        resize(numOfsegments);
    }
    
    private void resize(int size) {
        LineSegment[] copy = new LineSegment[size];
        for (int i = 0; i < numOfsegments; i++)
        copy[i] = segments[i];
        segments = copy;
        numOfsegments++;
        numOfsegments--;
    }

    public int numberOfSegments() {       // the number of line segments
        return numOfsegments;
    }
    public LineSegment[] segments() {               // the line segments
        return Arrays.copyOf(segments, segments.length);
        // return segments;
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
