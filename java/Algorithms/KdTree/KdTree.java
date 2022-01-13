package KdTree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node root;
    int size;

    private class Node {
        Node left, right;
        Point2D key;

        public Node(Point2D p) {
            key = p;
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {                      // is the set empty? 
        return size == 0;
    }
    
    public int size() {                         // number of points in the set 
        return size;
    }
    
    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        root = put(root, p, true);
        size++;
    }

    private Node put(Node x, Point2D p, boolean odd) {
        if (x == null) return new Node(p);
        int cmp;
        if (odd)
            cmp = Point2D.X_ORDER.compare(p, x.key);
        else
            cmp = Point2D.Y_ORDER.compare(p, x.key);
        if (cmp <= 0)
            x.left = put(x.left, p, !odd);
        else 
            x.right = put(x.right, p, !odd);
        return x;
    }

    public void draw() {                         // draw all points to standard draw 
        draw(root, true, 0, 0, 1, 1);
    }

    private void draw(Node n, boolean odd, double x0, double y0, double x1, double y1) {
        if (n == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.key.draw();
        StdDraw.setPenRadius(0.002);
        if (odd) {
            double x = n.key.x();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x, y0, x, y1);
            draw(n.left, !odd, x0, y0, x, y1);
            draw(n.right, !odd, x, y0, x1, y1);
        }
        else {
            double y = n.key.y();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x0, y, x1, y);
            draw(n.left, !odd, x0, y0, x1, y);
            draw(n.right, !odd, x0, y, x1, y1);
        }
    }  

    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> range = new SET<>();
        range(range, root, rect, true);
        return range;
    }

    private void range(SET<Point2D> range, Node n, RectHV rect, boolean odd) {
        if (n == null) return;
        if (rect.contains(n.key)) range.add(n.key);
        if (odd) {
            if (rect.xmin() <= n.key.x()) range(range, n.left, rect, !odd);
            if (rect.xmax() > n.key.x()) range(range, n.right, rect, !odd);
        }
        else {
            if (rect.ymin() <= n.key.y()) range(range, n.left, rect, !odd);
            if (rect.ymax() > n.key.y()) range(range, n.right, rect, !odd);
        }
    }

    public Point2D nearest(Point2D p) {
        return nearest(root, true, 0.0, 0.0, 1.0, 1.0, p);
    }

    private Point2D nearest(Node n, boolean odd, double x0, double y0, double x1, double y1, Point2D p) {
        // double champion = n.key.distanceTo(p);
        if (n == null) return null;
        Point2D champion, opPoint;
        RectHV rect;
        Node op;
        if (odd) {
            if (p.x() <= n.key.x()) {
                champion = nearest(n.left, !odd, x0, y0, n.key.x(), y1, p);
                rect = new RectHV(n.key.x(), y0, x1, y1);
                op = n.right;
            }
            else {
                champion = nearest(n.right, !odd, n.key.x(), y0, x1, y1, p);
                rect = new RectHV(x0, y0, n.key.x(), y1);
                op = n.left;
            }
        }
        else {
            if (p.y() <= n.key.y()) {
                champion = nearest(n.left, !odd, x0, y0, x1, n.key.y(), p);
                rect = new RectHV(x0, n.key.y(), x1, y1);
                op = n.right;
            }
            else {
                champion = nearest(n.right, !odd, x0, n.key.y(), x1, y1, p);
                rect = new RectHV(x0, y0, x1, n.key.y());
                op = n.left;
            }
        }
        if (champion == null || p.distanceTo(champion) > p.distanceTo(n.key)) champion = n.key;
        // else if (p.distanceTo(champion) < p.distanceTo(n.key)) champion = n.key;
        if (champion.distanceTo(p) > rect.distanceTo(p)) {
            opPoint = nearest(op, !odd, rect.xmin(), rect.ymin(), rect.xmax(), rect.ymax(), p);
            if (opPoint != null)
                if (p.distanceTo(champion) > p.distanceTo(opPoint)) champion = opPoint;
        }
        rect = null;
        return champion;
    }

    public static void main(String[] args) {
        
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
            }
            StdDraw.pause(20);
        }
        
        
    }
}
