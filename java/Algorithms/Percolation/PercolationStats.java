package Percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.Stopwatch;


public class PercolationStats {
    
    private final double[] x;
    private final int t;
    private static final double CONFIDENCE_95 = 1.96;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        
        int row, col;
        x = new double[trials];
        t = trials;
        for(int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                row = StdRandom.uniform(n)+1;
                col = StdRandom.uniform(n)+1;
                p.open(row, col);
            }
            x[i] = 1.0*p.numberOfOpenSites()/(n*n);
            
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(x);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(x);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double confLo = this.mean()-CONFIDENCE_95*this.stddev()/Math.sqrt(this.t);
        return confLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double confHi = this.mean()-CONFIDENCE_95*this.stddev()/Math.sqrt(this.t);
        return confHi;
    }

   // test client (see below)
   public static void main(String[] args) {
    int n, t;
    PercolationStats ps;
    n = Integer.parseInt(args[0]);
    t = Integer.parseInt(args[1]);
    //Stopwatch time=new Stopwatch();
    ps = new PercolationStats(n, t);
    StdOut.println("mean                    = " + ps.mean());
    StdOut.println("stddev                  = " + ps.stddev());
    StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    //StdOut.println("Time - " + time.elapsedTime());
    
   }
}
