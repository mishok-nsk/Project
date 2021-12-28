package puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private final SearchNode goal;

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        int manhattan;
        SearchNode prev;

        public int compareTo(SearchNode that) {
            int thispriority = this.moves + this.manhattan;
            int thatpriority = that.moves + that.manhattan;
            if (thispriority != thatpriority) return thispriority - thatpriority;
            return this.manhattan - that.manhattan;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Constructor sent null argument.");

        SearchNode first = new SearchNode();
        first.board = initial;
        first.moves = 0;
        first.manhattan = initial.manhattan();
        first.prev = null;

        Board twin = initial.twin();
        SearchNode firstTwin = new SearchNode();
        firstTwin.board = twin;
        firstTwin.moves = 0;
        firstTwin.manhattan = twin.manhattan();
        firstTwin.prev = null;

        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        pq.insert(first);
        pqTwin.insert(firstTwin);
        SearchNode cur, curTwin;
        while (true) {
            cur = pq.delMin();
            curTwin = pqTwin.delMin();
            if (cur.board.isGoal() || curTwin.board.isGoal()) break;
            for (Board n:cur.board.neighbors()) {
                if (cur.prev != null)
                    if (cur.prev.board.equals(n)) continue;
                SearchNode neighbor = new SearchNode();
                neighbor.board = n;
                neighbor.prev = cur;
                neighbor.manhattan = n.manhattan();
                neighbor.moves = cur.moves + 1;
                pq.insert(neighbor);
            }

            for (Board n:curTwin.board.neighbors()) {
                if (curTwin.prev != null)
                    if (curTwin.prev.board.equals(n)) continue;
                SearchNode neighbor = new SearchNode();
                neighbor.board = n;
                neighbor.prev = curTwin;
                neighbor.manhattan = n.manhattan();
                neighbor.moves = curTwin.moves + 1;
                pqTwin.insert(neighbor);
            }
        }
        if (cur.board.isGoal()) 
            goal = cur;
        else 
            goal = null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) 
            return goal.moves;
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (goal == null) return null;
        Stack<Board> out = new Stack<>();
        SearchNode cur = goal;
        while (cur != null) {
            out.push(cur.board);
            cur = cur.prev;
        }
        return out;
    }

    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("Unsolvable puzzle");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
