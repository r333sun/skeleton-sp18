package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Point;
import byog.utils.GameUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Room implements Serializable {

    private static final long serialVersionUID = 2384759823L;

    private int xLower;
    private int xUpper;

    private int yLower;
    private int yUpper;

    public Room(Point startPoint, int width, int height) {
        this.xLower = startPoint.X;
        this.yLower = startPoint.Y;

        this.xUpper = startPoint.X + width + 2;
        this.yUpper = startPoint.Y + height + 2;
    }

    public void draw(TETile[][] world) {
        for (int i = xLower; i <= xUpper; i++) {
            for (int j = yLower; j <= yUpper; j++) {
                if (i == xLower || i == xUpper || j == yLower || j == yUpper) {
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }

    public boolean isEligible(int maxWidth, int maxHeight) {
        return this.xUpper < maxWidth && this.yUpper < maxHeight;
    }

    public boolean canAdd(List<Room> roomList) {
        for (Room room : roomList) {
            if (this.isOverlap(room)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOverlap(Room room) {
        for (int x = room.getxLower(); x <= room.getxUpper(); x++) {
            for (int y = room.getyLower(); y <= room.getyUpper(); y++) {
                if ((x <= xUpper && x >= xLower) && (y <= yLower && y >= yLower)) {
                    return true;
                }
            }
        }

        for (int x = xLower; x <= xUpper; x++) {
            for (int y = yLower; y <= yUpper; y++) {
                if (x <= room.getxUpper() && x >= room.getxLower()
                        && y <= room.getyUpper() && y >= room.getyLower()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Road getRoadTo(Room room, Random r,TETile[][] world) {
        Road road = null;
        if (isStraightTo(room)) {
            road = getStraightRoad(room, r);
        } else {
            road = getLRoad(room, r,world);
        }
        return road;
    }

    private Road getLRoad(Room room, Random r,TETile[][] world) {
        return new LRoad(this,room,r,world);
    }

    private Road getStraightRoad(Room room, Random r) {
        return new Road(this, room, r);
    }

    public boolean isContainPoint(Point point){
        if(point.X >= xLower && point.X <= xLower){
            return true;
        }
        if(point.Y >= yLower && point.Y <= yUpper){
            return true;
        }
        return false;
    }
    public boolean isStraightTo(Room room) {
        return GameUtils.isInRange(xLower + 1, xUpper - 1, room.getxLower() + 1, room.getxUpper() - 1) ||
                GameUtils.isInRange(yLower + 1, yUpper - 1, room.getyLower() + 1, room.getyUpper() - 1);
    }

    public int getxLower() {
        return xLower;
    }

    public int getxUpper() {
        return xUpper;
    }

    public int getyLower() {
        return yLower;
    }

    public int getyUpper() {
        return yUpper;
    }

    @Override
    public String toString() {
        return "Room{" +
                "xLower=" + xLower +
                ", xUpper=" + xUpper +
                ", yLower=" + yLower +
                ", yUpper=" + yUpper +
                '}';
    }
}
