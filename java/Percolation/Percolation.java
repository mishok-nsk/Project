import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private int open;
    private boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    // creates n-by-n grid, with all sites initially blocked
    
    public Percolation(int num)
    {
        if (num <= 0) {
            throw new IllegalArgumentException("Exception: N must be more 0");
        }
        n = num;
        open = 0;
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n*n+2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row or Col is outside its prescribed range");
        }
       
        if (grid[row-1][col-1]) return; // if site alredy open 

       grid[row-1][col-1] = true;
       open++;
       int p = convertToIndex(row, col);
       int q;
       boolean up, down, left, right;
       up = row > 1;
       down = row < n;
       left = col > 1;
       right = col < n;
       
       if (up) {
        if (isOpen(row-1, col)) {
            q = convertToIndex(row-1, col); 
            uf.union(p, q); 
        }
       }
       else uf.union(p, 0);

       if (down) {
        if (isOpen(row+1, col)) {
            q = convertToIndex(row+1, col); 
            uf.union(p, q);
        }
       }
       else uf.union(p, n*n+1);

       if (left) {
        if (isOpen(row, col-1)) {
            q = convertToIndex(row, col-1); 
            uf.union(p, q);
        } 
       }

       if (right) {
        if (isOpen(row, col+1)){
            q = convertToIndex(row, col+1); 
            uf.union(p,q);
        }
       }
       
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row or Col is outside its prescribed range");
        }
        return grid[row-1][col-1];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Row or Col is outside its prescribed range");
        }

        int idd = convertToIndex(row, col);
        if (isOpen(row, col)) return uf.find(0) == uf.find(idd);
        else return false;
    }
    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return open;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return uf.find(0) == uf.find(n*n+1);
    }

    private int convertToIndex(int row, int col) {
        return (row-1)*n+col;
    }

    // test client (optional)
    public static void main(String[] args)
    {
        int n, row, col;
        Percolation p;
        n = Integer.parseInt(args[0]);
        try {
            p = new Percolation(n);
        }
        catch (IllegalArgumentException e) {
            StdOut.println(e.getMessage());
            return;
        }
        while (!p.percolates())
        {
            row = StdRandom.uniform(n)+1;
            col = StdRandom.uniform(n)+1;
            p.open(row, col);
            StdOut.println("(" + row + ";" + col + ")");
        }
        StdOut.println("Open sites: " + p.numberOfOpenSites());
    }
}