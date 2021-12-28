package puzzle;

import java.util.Arrays;
import java.util.ArrayList;
// import edu.princeton.cs.algs4.StdIn;
// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;
    private final int n;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][];
        for (int  i = 0; i < n; i++) {
            this.tiles[i] = Arrays.copyOf(tiles[i], n);    
        }
        // this.tiles = Arrays.copyOf(tiles, n);
    }
                                           
    // string representation of this board
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append(String.valueOf(n) + '\n');
        // StdOut.println(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.append(' ' + String.valueOf(tiles[i][j]));
                // StdOut.print(tiles[i][j] + ' ');
            }
            out.append('\n');
            // StdOut.println();
        }
        return out.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != i * n + j + 1) hamming++;
            }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                manhattan += Math.abs((tiles[i][j] - 1) / n - i) + Math.abs((tiles[i][j] - 1) % n - j);
            }
                
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        for (int i = 0; i < n; i++) 
            for (int j = 0; j < n; j++) 
                if (tiles[i][j] != that.tiles[i][j]) return false;
            
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int i, j = 0;
        boolean f = false;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    f = true;
                    break;
                }
            }
            if (f) break;
        }
        int[][] tilesNeighbors;
        tilesNeighbors = Arrays.copyOf(tiles, n);
        if (i < n - 1) {
            up(tilesNeighbors, i + 1, j);
            neighbors.add(new Board(tilesNeighbors));
            down(tilesNeighbors, i, j);
        }

        if (i > 0) {
            down(tilesNeighbors, i - 1, j);
            neighbors.add(new Board(tilesNeighbors));
            up(tilesNeighbors, i, j);
        }

        if (j > 0) {
            right(tilesNeighbors, i, j - 1);
            neighbors.add(new Board(tilesNeighbors));
            left(tilesNeighbors, i, j);
        }

        if (j < n - 1) {
            left(tilesNeighbors, i, j + 1);
            neighbors.add(new Board(tilesNeighbors));
            right(tilesNeighbors, i, j);
        }

        return neighbors;
    }

    private void left(int[][] tile, int i, int j) {
        int t = tile[i][j];
        tile[i][j] = tile[i][j - 1];
        tile[i][j - 1] = t; 
    }

    private void right(int[][] tile, int i, int j) {
        int t = tile[i][j];
        tile[i][j] = tile[i][j + 1];
        tile[i][j + 1] = t; 
    }

    private void up(int[][] tile, int i, int j) {
        int t = tile[i][j];
        tile[i][j] = tile[i - 1][j];
        tile[i - 1][j] = t; 
    }

    private void down(int[][] tile, int i, int j) {
        int t = tile[i][j];
        tile[i][j] = tile[i + 1][j];
        tile[i + 1][j] = t; 
    }
    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twintiles = new int[n][];
        for (int i = 0; i < n; i++) 
            twintiles[i] = Arrays.copyOf(tiles[i], n);
        
        int k = n / 2;
        if (twintiles[k][k] != 0) {
            if (twintiles[k - 1][k] != 0) 
                up(twintiles, k, k);
            else 
                left(twintiles, k, k);
        }
        else {
            k--;
            right(twintiles, k, k);
        }

        Board twin = new Board(twintiles);
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial);

        int m = initial.manhattan();
        StdOut.println(m);

        for (Board b:initial.neighbors()) {
            StdOut.println(b);
        }

        StdOut.println(initial.twin());
    }
}
