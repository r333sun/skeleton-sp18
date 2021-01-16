package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {

    private boolean[][] open;
    private int n;
    private WeightedQuickUnionUF uf;
    private int top;
    private int bottom;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Argument should be greater than 0");
        }
        this.open = new boolean[n][n];
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        top = n * n;
        bottom = n * n + 1;
        for (int i = 0; i < n; i++) {
            uf.union(top, xyToInt(0, i));
        }
    }

    //Open the site (row, col) if it is not open
    public void open(int row, int col) {
        if (col >= n || row >= n) {
            throw new IllegalArgumentException("(" + row + ", " + col + ") is not in the universe");
        } else {
            open[row][col] = true;
            unionAround(row, col);
            if (row == n - 1 && !percolates() && uf.connected(top, xyToInt(row, col))) {
                uf.union(bottom, xyToInt(row, col));
            }
        }
    }

    // is the site(row, col) open?
    public boolean isOpen(int row, int col) {
        if (col < n && row < n)
            return open[row][col];
        else {
            throw new IllegalArgumentException("(" + row + ", " + col + ") is not in the universe");
        }
    }

    // is the site(row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && uf.connected(top, xyToInt(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isOpen(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    //does the system percolate?
    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    private int xyToInt(int row, int col) {
        return n * row + col;
    }

    private void unionAround(int row, int col) {
        int curr = xyToInt(row, col);
        if (row + 1 < n) {
            if (isOpen(row + 1, col)) {
                int down = xyToInt(row + 1, col);
                uf.union(curr, down);
            }
        }
        if (col + 1 < n) {
            if (isOpen(row, col + 1)) {
                int right = xyToInt(row, col + 1);
                uf.union(curr, right);
            }
        }
        if (row - 1 >= 0) {
            if (isOpen(row - 1, col)) {
                int up = xyToInt(row - 1, col);
                uf.union(curr, up);
            }
        }
        if (col - 1 >= 0) {
            if (isOpen(row, col - 1)) {
                int left = xyToInt(row, col - 1);
                uf.union(curr, left);
            }
        }
    }


    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        for (int i = 0; i < 5; i++) {
            System.out.println(p.isFull(0, i));
            p.open(0, i);
        }
        p.open(1, 1);
        System.out.println(p.isFull(1, 1));
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        System.out.println(p.percolates());
    }

}
