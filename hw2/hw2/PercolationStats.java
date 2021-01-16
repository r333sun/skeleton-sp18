package hw2;

import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        double[] threshold = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            threshold[i] = p.numberOfOpenSites() / (N * N);
        }
        this.mean = StdStats.mean(threshold);
        this.stddev = StdStats.stddev(threshold);
        this.confidenceLow = mean - (1.96 * stddev / (Math.sqrt(T)));
        this.confidenceHigh = mean + (1.96 * stddev / (Math.sqrt(T)));
    }

    public double mean() {
        return this.mean;
    }

    public double stddev() {
        return this.stddev;
    }

    public double confidenceLow() {
        return this.confidenceLow;
    }

    public double confidenceHigh() {
        return this.confidenceHigh;
    }
}
