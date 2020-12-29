package byog.lab5;

import java.io.Serializable;

public class Point implements Serializable {

    private static final long serialVersionUID = 98479812739L;
    public int X;
    public int Y;

    public Point(int x, int y) {
        X = x;
        Y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
