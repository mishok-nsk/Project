package KdTree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;


public class PointSET {
    private final SET<Point2D> pointset;
    
    public PointSET() {                             // construct an empty set of points 
        pointset = new SET<>();
    }
    
    public boolean isEmpty() {                      // is the set empty? 
        return pointset.isEmpty();
    }
    
    public int size() {                         // number of points in the set 
        return pointset.size();
    }
    
    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("Method insert sent null argument.");
        if (!contains(p)) pointset.add(p);
    }
    
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) throw new IllegalArgumentException("Method contains sent null argument.");
        if (pointset.isEmpty()) return false;
        return pointset.contains(p);
        /*
        for (Point2D point : pointset) {
            if (point.equals(p)) return true;
        }
        return false;
        */
    }
    
    public void draw() {                         // draw all points to standard draw 
        for (Point2D point : pointset) {
            point.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary) 
        // if (pointset.isEmpty()) return null;
        if (rect == null) throw new IllegalArgumentException("Method range sent null argument.");
        SET<Point2D> range = new SET<>();
        for (Point2D p : pointset) {
            if (rect.contains(p)) range.add(p);
        }
        return range;
    }
    
    public Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) throw new IllegalArgumentException("Method nearest sent null argument.");
        if (pointset.isEmpty()) return null;
        Point2D champion = new Point2D(0, 0);
        boolean first = true;
        for (Point2D point : pointset) {
            if (first) {
                champion = point;
                first = false;
            }
            else {
                if (champion.distanceSquaredTo(p) > point.distanceSquaredTo(p)) champion = point;
            }
        }
        return champion;
    }

    public static void main(String[] args) {                 // unit testing of the methods (optional) 
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        // KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            // kdtree.insert(p);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdDraw.show();

        // process range search queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                x0 = x1;
                y0 = y1;
                isDragging = true;
            }

            // user is dragging a rectangle
            else if (StdDraw.isMousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
            }

            // user stops dragging rectangle
            else if (!StdDraw.isMousePressed() && isDragging) {
                isDragging = false;
            }

            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw the rectangle
            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                     Math.max(x0, x1), Math.max(y0, y1));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();

            // draw the range search results for kd-tree in blue
            /*
            StdDraw.setPenRadius(0.02);
            StdDraw.setPenColor(StdDraw.BLUE);
            for (Point2D p : kdtree.range(rect))
                p.draw();
            */

            StdDraw.show();
            StdDraw.pause(20);
        }
    }
}
