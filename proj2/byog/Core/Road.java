package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Point;
import byog.utils.GameUtils;

import java.io.Serializable;
import java.util.Random;

public class Road implements Serializable {

    private static final long serialVersionUID = 3867892398023L;

    private Point startPoint;
    private Point endPoint;
    private Point turnPoint;

    public Road(Room room, Room room2, Random random) {
        int x = GameUtils.generateMiddlePoint(room.getxLower(), room.getxUpper(),
                room2.getxLower(), room2.getxUpper(), random);

        int y = GameUtils.generateMiddlePoint(room.getyLower(), room.getyUpper(),
                room2.getyLower(), room2.getyUpper(), random);

        turnPoint = new Point(x, y);
        this.startPoint = pointToRoom(turnPoint, room);
        this.endPoint = pointToRoom(turnPoint, room2);
    }

    public Road() {

    }

    protected Point pointToRoom(Point point, Room room) {
        if (GameUtils.pointInRange(point.X, room.getxLower() + 1, room.getxUpper() - 1)) {
            int y = Math.abs(room.getyLower() - point.Y) < Math.abs(room.getyUpper() - point.Y) ?
                    room.getyLower() : room.getyUpper();
            return new Point(point.X, y);
        } else {
            int x = Math.abs(room.getxLower() - point.X) < Math.abs(room.getxUpper() - point.X) ?
                    room.getxLower() : room.getxUpper();
            return new Point(x, point.Y);
        }
    }

    public void draw(TETile[][] world) {
        int xMax = Math.max(startPoint.X, endPoint.X);
        int xMin = Math.min(startPoint.X, endPoint.X);
        int yMax = Math.max(startPoint.Y, endPoint.Y);
        int yMin = Math.min(startPoint.Y, endPoint.Y);
//        if (xMin == xMax) {
//            drawVerticalWalls(xMin, yMin, yMax, world);
//            for (int i = yMin; i <= yMax; i++) {
//                world[xMin][i] = Tileset.FLOOR;
//            }
//        }
//        if (yMin == yMax) {
//            drawHorizontalWalls(yMin, xMin, xMax, world);
//            for (int i = xMin; i <= xMax; i++) {
//                world[i][yMin] = Tileset.FLOOR;
//            }
//        }
        drawRoad(xMin, xMax, yMin, yMax, world);
        drawWalls(xMin, xMax, yMin, yMax, world);
//        world[turnPoint.X][turnPoint.Y] = Tileset.FLOWER;
        world[startPoint.X][startPoint.Y] = Tileset.FLOOR;
        world[endPoint.X][endPoint.Y] = Tileset.FLOOR;
    }

    protected void drawHorizontalWalls(int y, int xMin, int xMax, TETile[][] world) {
        for (int i = xMin; i <= xMax; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (world[i][j] == Tileset.NOTHING) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    protected void drawVerticalWalls(int x, int yMin, int yMax, TETile[][] world) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = yMin; j <= yMax; j++) {
                if (world[i][j] == Tileset.NOTHING) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    protected void drawRoad(int xMin, int xMax, int yMin, int yMax, TETile[][] world) {
        for (int x = xMin; x <= xMax; x++) {
            for (int y = yMin; y <= yMax; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }

    protected void drawWalls(int xMin, int xMax, int yMin, int yMax, TETile[][] world) {
        if (xMin == xMax) {
            drawVerticalWalls(xMin, yMin, yMax, world);
        }
        if (yMin == yMax) {
            drawHorizontalWalls(yMin, xMin, xMax, world);
        }
    }


    @Override
    public String toString() {
        return "Road{" +
                "startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                '}';
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }
}
