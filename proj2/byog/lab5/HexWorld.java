package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.List;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void addHexagon(TETile[][] world,int size, Point startPoint,TETile tile){
        boolean[][] hexagon = Hexagon(size);
        int width  = hexagon.length;
        int height = hexagon[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(hexagon[i][j]){
                    world[startPoint.X+i][startPoint.Y+j] = tile;
                }
            }
        }
    }

    private static boolean[][] Hexagon(int size){
        int height = size * 2;
        int width = size + 2*(size-1);
        boolean [][] h = new boolean[height][width];
        for (int i = 0; i < size; i++) {
            for(int j = (size - 1 - i); j <= (width-1)-(size-1-i);j++){
                h[i][j] = true;
            }
        }
        for (int i = 0; i < size; i++) {
            for(int j = i; j <= (width - 1) - i;j++){
                h[size+i][j] = true;
            }
        }
        return rotate(h);
    }

    private static boolean[][] rotate(boolean[][] h) {
        boolean[][] res = new boolean[h[0].length][h.length];
        for (int i = 0; i < h.length; i++) {
            for (int j = 0; j < h[0].length; j++) {
                res[j][i] = h[i][j];
            }
        }
        return res;
    }

    private static List<Point> getStartPoint(){
        return null;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int size = 3;
        List<Point> starts = getStartPoint();
        addHexagon(world,size,new Point(0,0),Tileset.FLOWER);
        addHexagon(world,size,new Point(5,7),Tileset.GRASS);

        ter.renderFrame(world);
    }
}
