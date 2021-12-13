package collinear;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    LineSegment[] segments;
    int numOfSegments;

    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        segments = new LineSegment[10];
        numOfSegments = 0;
        Arrays.sort(points);
        int length = points.length;
        for (int i = 0; i < length - 3; i++) {
            Arrays.sort(points, i+1, length, points[i].slopeOrder());
            segmentForPoint(i, points);
            Arrays.sort(points, i+1, length);
        }
        resize(numOfSegments);
    }

    private void segmentForPoint(int n, Point[] points) {
        int counter = 1;
        double slope = points[n].slopeTo(points[n+1]);
        for (int j = n + 2; j < points.length; j++) {
                   
            if (slope == points[n].slopeTo(points[j])) {
                counter++;

            }
            else {
                if (counter > 2) 
                    addSegment(points[n],points[j-1]);
                slope = points[n].slopeTo(points[j]);
                counter = 1;
            }
        }
        if (counter > 2)
         addSegment(points[n],points[points.length - 1]);
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
        //numOfSegments++;
        segments[numOfSegments++] = new LineSegment(a, b);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
