package collinear;

import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int numOfSegments;

    public FastCollinearPoints(Point[] inpoints) {    // finds all line segments containing 4 points
        if (inpoints == null) {
            throw new IllegalArgumentException("argument to FastCollinearPoints constructor is null");
        }

        for (Point p : inpoints) {
            if (p == null) {
                throw new IllegalArgumentException("argument to FastCollinearPoints constructor is null");
            }
        }
        Point[] points=Arrays.copyOf(inpoints, inpoints.length);
        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i-1]) == 0) {
                throw new IllegalArgumentException("argument to FastCollinearPoints contains a repeated point");
            }
        }
        
        segments = new LineSegment[1];
        numOfSegments = 0;
        int length = points.length;
        for (int i = 0; i < length - 3; i++) {
            Arrays.sort(points, points[i].slopeOrder());
            segmentForPoint(points);
            Arrays.sort(points);
        }
        resize(numOfSegments);
    }

    private void segmentForPoint(Point[] points) {
        int counter = 1;
        double slope = points[0].slopeTo(points[1]);
        for (int j = 2; j < points.length; j++) {
                   
            if (slope == points[0].slopeTo(points[j])) {
                counter++;

            }
            else {
                if (counter > 2) {
                    Arrays.sort(points, j - counter, j);
                    if (points[0].compareTo(points[j - counter]) < 0)
                        addSegment(points[0], points[j-1]);
                    
                }

                slope = points[0].slopeTo(points[j]);
                counter = 1;
            }
        }

        if (counter > 2) {
            Arrays.sort(points, points.length - counter, points.length);
            if (points[0].compareTo(points[points.length - counter]) < 0)
                addSegment(points[0], points[points.length - 1]);
        }

    }

    private void resize(int size) {
        LineSegment[] copy = new LineSegment[size];
        for (int i = 0; i < numOfSegments; i++)
        copy[i] = segments[i];
        segments = copy;
    }

    private void addSegment(Point a, Point b) {
        if (segments.length == numOfSegments)
            resize(numOfSegments*2); 
        segments[numOfSegments++] = new LineSegment(a, b);
    }

    public int numberOfSegments() {       // the number of line segments
        return numOfSegments;
    }
    public LineSegment[] segments() {               // the line segments
        return Arrays.copyOf(segments, segments.length);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
