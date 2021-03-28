import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture p;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        p = picture;
        width = p.width();
        height = p.height();
    }

    public Picture picture() {
        return p;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new IndexOutOfBoundsException("Illegal Arguments");
        }
        int x1 = x - 1 >= 0 ? x - 1 : width() - 1;
        int x2 = x + 1 < width() ? x + 1 : 0;
        int y1 = y - 1 >= 0 ? y - 1 : height() - 1;
        int y2 = y + 1 < height() ? y + 1 : 0;
        double delta_x = (Math.pow(p.get(x1, y).getRed() - p.get(x2, y).getRed(), 2)
                + Math.pow(p.get(x1, y).getGreen() - p.get(x2, y).getGreen(), 2)
                + Math.pow(p.get(x1, y).getBlue() - p.get(x2, y).getBlue(), 2));
        double delta_y = (Math.pow(p.get(x, y1).getRed() - p.get(x, y2).getRed(), 2)
                + Math.pow(p.get(x, y1).getGreen() - p.get(x, y2).getGreen(), 2)
                + Math.pow(p.get(x, y2).getBlue() - p.get(x, y2).getBlue(), 2));
        return delta_x + delta_y;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    private void transpose() {
        Picture temp = new Picture(height, width);
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                temp.set(row, col, p.get(col, row));
            }
        }

        p = temp;
        int t = width;
        width = height;
        height = t;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height];
        double totalEnergy = Double.MAX_VALUE;

        for (int col = 0; col < width; col++) {
            int y = 0;
            int x = col;
            int[] temp = new int[height];
            double tempEnergy = energy(x, y);
            temp[y] = x;
            y++;

            double topE = 0.0, leftE = 0.0, rightE = 0.0;


            while (y < height) {
                int top = x;
                int left = x - 1;
                int right = x + 1;

                topE = energy(top, y);
                if (left >= 0) {
                    leftE = energy(left, y);
                } else {
                    leftE = Double.MAX_VALUE;
                }

                if (right < width) {
                    rightE = energy(right, y);
                } else {
                    rightE = Double.MAX_VALUE;
                }

                if (topE <= leftE && topE <= rightE) {
                    tempEnergy += topE;
                    temp[y] = top;
                    x = top;
                } else if (leftE <= topE && leftE <= rightE) {
                    tempEnergy += leftE;
                    temp[y] = left;
                    x = left;
                } else {
                    tempEnergy += rightE;
                    temp[y] = right;
                    x = right;
                }

                y++;
            }

            if (tempEnergy <= totalEnergy) {
                totalEnergy = tempEnergy;
                seam = temp;
            }
        }

        return seam;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (checkSeam(seam)) {
            this.p = new Picture(SeamRemover.removeHorizontalSeam(this.p, seam));
            height--;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        if (checkSeam(seam)) {
            this.p = new Picture(SeamRemover.removeVerticalSeam(this.p, seam));
            width--;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private boolean checkSeam(int[] seam) {
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                return false;
            }
        }

        return true;
    }
}
