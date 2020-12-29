package byog.lab5;

import byog.TileEngine.TETile;

/**
 * @author
 * @create 2020-12-13 17:54
 */
public class Hexagon {
    private int size;
    private TETile tile;
    private TETile[][] h;

    public Hexagon(int size, TETile tile) {
        this.size = size;
        this.tile = tile;
        int height = size * 2;
        int width = size + 2*(size-1);
        boolean[][] tmp = new boolean[height][width];
        for (int i = 0; i < size; i++) {
            for(int j = (size - 1 - i); j <= (width-1)-(size-1-i);j++){
                tmp[i][j] = true;
            }
        }
        for (int i = 0; i < size; i++) {
            for(int j = i; j <= (width - 1) - i;j++){
                tmp[size+i][j] = true;
            }
        }
        h = getHexagon(tmp);
    }

    private TETile[][] getHexagon(boolean[][] tmp) {
        TETile[][] res = new TETile[tmp[0].length][tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[0].length; j++) {
                if(tmp[i][j])
                res[j][i] = tile;
            }
        }
        return res;
    }


}
