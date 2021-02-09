package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import java.util.HashSet;
import java.util.Set;

public class Board implements WorldState {
    private static final int BLANK = 0;
    private int[][] tiles;
    private int N;

    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

    }

    public int tileAt(int i, int j) {
        if (i < 0 || i >= N) {
            throw new IndexOutOfBoundsException("Index of bound");
        }
        if (j < 0 || j >= N) {
            throw new IndexOutOfBoundsException("Index of bound");
        }
        return tiles[i][j];
    }

    public int size() {
        return N;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int distance = 0;
        int cnt = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    break;
                }
                if (tileAt(i, j) != cnt) {
                    distance += 1;
                }
                cnt++;
            }
        }
        return distance;
    }

    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int curr = tileAt(i, j);
                if (curr == BLANK) {
                    continue;
                }
                distance += manhattan1Point(i, j, curr);
            }
        }
        return distance;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if(this == y){
            return true;
        }
        
        if(y == null || y.getClass() != this.getClass()){
            return false;
        }

        Board board = (Board) y;
        if(tiles != null){
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if(tileAt(i,j) != board.tileAt(i,j)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int manhattan1Point(int x, int y, int curr) {
        curr -= 1;
        int currRow = curr / N;
        int currCol = curr % N;
        return Math.abs(currRow - x) + Math.abs(currCol - y);
    }

    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
