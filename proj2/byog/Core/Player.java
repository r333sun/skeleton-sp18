package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 9023021903483L;
    private int x;
    private int y;
    private TETile[][] world;

    public Player(int x, int y, TETile[][] world) {
        this.x = x;
        this.y = y;
        this.world = world;
    }

    public boolean isReasonable() {
        return world[x][y] == Tileset.FLOOR;
    }

    public void draw() {
        world[x][y] = Tileset.PLAYER;
    }

    public void up() {
        if (world[x][y + 1] == Tileset.FLOOR) {
            world[x][y] = Tileset.FLOOR;
            this.y += 1;
        }
        draw();
    }

    public void down() {
        if (world[x][y - 1] == Tileset.FLOOR) {
            world[x][y] = Tileset.FLOOR;
            this.y -= 1;
        }
        draw();
    }

    public void left() {
        if (world[x - 1][y] == Tileset.FLOOR) {
            world[x][y] = Tileset.FLOOR;
            this.x -= 1;
        }
        draw();
    }

    public void right() {
        if (world[x + 1][y] == Tileset.FLOOR) {
            world[x][y] = Tileset.FLOOR;
            this.x += 1;
        }
        draw();
    }
}
