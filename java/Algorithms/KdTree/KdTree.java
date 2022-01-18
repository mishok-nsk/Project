package KdTree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private final static boolean X_AXIS = true;
    private final static boolean Y_AXIS = false;
    private final static boolean LEFT = true;
    private final static boolean RIGHT = false;
    
    private Node root;
    private int size;
    

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
    
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) throw new IllegalArgumentException("Method contains sent null argument.");
        if (isEmpty()) return false;
        return contains(root, true, p);
    }

    private boolean contains(Node n, boolean odd, Point2D p) {
        if (n == null) return false;
        if (n.key.equals(p)) return true;
        if (odd) 
            if (p.x() <= n.key.x()) return contains(n.left, !odd, p);
            else return contains(n.right, !odd, p);
        else 
            if (p.y() <= n.key.y()) return contains(n.left, !odd, p);
            else return contains(n.right, !odd, p);
    }
    
    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("Method insert sent null argument.");
        if (contains(p)) return;
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
        StdDraw.setPenRadius();
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
        if (rect == null) throw new IllegalArgumentException("Method range sent null argument.");
        ArrayList<Point2D> range = new ArrayList<>();
        range(range, root, rect, true);
        return range;
    }

    private void range(ArrayList<Point2D> range, Node n, RectHV rect, boolean odd) {
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
        if (p == null) throw new IllegalArgumentException("Method nearest sent null argument.");
        if (root == null) return null;
        double dist = root.key.distanceSquaredTo(p);
        RectHV area = new RectHV(0.0, 0.0, 1.0, 1.0);
        return nearest(root, true, area, p, dist);
    }

    private Point2D nearest(Node n, boolean odd, RectHV area, Point2D p, double dist) {
        // double champion = n.key.distanceTo(p);
        if (n == null) return null;
        // if (n.key.distanceSquaredTo(p) < dist) dist = n.key.distanceSquaredTo(p);
        Point2D champion, opPoint;
        RectHV oppositeArea;
        Node op;
        if (odd) {
            if (p.x() <= n.key.x()) {
                oppositeArea = cut(area, n.key.x(), X_AXIS, RIGHT);
                area = cut(area, n.key.x(), X_AXIS, LEFT);
                champion = nearest(n.left, !odd, area, p, dist);
                op = n.right;
            }
            else {
                oppositeArea = cut(area, n.key.x(), X_AXIS, LEFT);
                area = cut(area, n.key.x(), X_AXIS, RIGHT);
                champion = nearest(n.right, !odd, area, p, dist);
                op = n.left;
            }
        }
        else {
            if (p.y() <= n.key.y()) {
                oppositeArea = cut(area, n.key.y(), Y_AXIS, RIGHT);
                area = cut(area, n.key.y(), Y_AXIS, LEFT);
                champion = nearest(n.left, !odd, area, p, dist);
                op = n.right;
            }
            else {
                oppositeArea = cut(area, n.key.y(), Y_AXIS, LEFT);
                area = cut(area, n.key.y(), Y_AXIS, RIGHT);
                champion = nearest(n.right, !odd, area, p, dist);
                op = n.left;
            }
        }
        
        if (champion == null || p.distanceSquaredTo(champion) > p.distanceSquaredTo(n.key))
            champion = n.key;
        if (dist > champion.distanceSquaredTo(p)) dist = champion.distanceSquaredTo(p);
        if (dist > oppositeArea.distanceSquaredTo(p)) {
            opPoint = nearest(op, !odd, oppositeArea, p, dist);
            if (opPoint != null)
                if (dist > p.distanceSquaredTo(opPoint)) champion = opPoint;
        }
        oppositeArea = null;
        return champion;
    }

    private RectHV cut(RectHV area, double coord, boolean xAxis, boolean left) {
        RectHV cutArea;
        if (xAxis) {
            if (left) cutArea = new RectHV(area.xmin(), area.ymin(), coord, area.ymax());
            else cutArea = new RectHV(coord, area.ymin(), area.xmax(), area.ymax());
        }
        else {
            if (left) cutArea = new RectHV(area.xmin(), area.ymin(), area.xmax(), coord);
            else cutArea = new RectHV(area.xmin(), coord, area.xmax(), area.ymax());
        }
        return cutArea;
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
