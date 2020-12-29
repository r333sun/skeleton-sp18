package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Point;

import java.util.Random;

public class LRoad extends Road {

    private Point turnPoint;
    private Point startPoint;
    private Point endPoint;


    public LRoad(Room room, Room room2, Random random, TETile[][] world) {
        super();
        int x = RandomUtils.uniform(random, room.getxLower() + 1, room.getxUpper() - 1);
        int y = RandomUtils.uniform(random, room2.getyLower() + 1, room2.getyUpper() - 1);
        int count = 0;
        while (world[x][y] != Tileset.NOTHING) {
            if(count < 3) {
                x = RandomUtils.uniform(random, room.getxLower() + 1, room.getxUpper() - 1);
                y = RandomUtils.uniform(random, room2.getyLower() + 1, room2.getyUpper() - 1);
                count++;
            }else{
                x = RandomUtils.uniform(random, room2.getxLower() + 1, room2.getxUpper() - 1);
                y = RandomUtils.uniform(random, room.getyLower() + 1, room.getyUpper() - 1);
            }
        }
        turnPoint = new Point(x, y);
        startPoint = pointToRoom(turnPoint, room);
        endPoint = pointToRoom(turnPoint, room2);
    }


    @Override
    public void draw(TETile[][] world) {
        drawLRoad(world);
        amendWalls(world);
//        world[turnPoint.X][turnPoint.Y] = Tileset.FLOWER;
//        world[startPoint.X][startPoint.Y] = Tileset.GRASS;
//        world[endPoint.X][endPoint.Y] = Tileset.GRASS;

    }

    private void drawLRoad(TETile[][] world) {
//        if(turnPoint.X == startPoint.X){
//            drawVerticalWalls(turnPoint.X,Math.min(turnPoint.Y,startPoint.Y),
//                    Math.max(turnPoint.Y,startPoint.Y),world);
//        }
        drawRoad(Math.min(startPoint.X, turnPoint.X), Math.max(startPoint.X, turnPoint.X),
                Math.min(startPoint.Y, turnPoint.Y), Math.max(startPoint.Y, turnPoint.Y), world);
        drawWalls(Math.min(startPoint.X, turnPoint.X), Math.max(startPoint.X, turnPoint.X),
                Math.min(startPoint.Y, turnPoint.Y), Math.max(startPoint.Y, turnPoint.Y), world);
        drawRoad(Math.min(endPoint.X, turnPoint.X), Math.max(endPoint.X, turnPoint.X),
                Math.min(endPoint.Y, turnPoint.Y), Math.max(endPoint.Y, turnPoint.Y), world);
        drawWalls(Math.min(endPoint.X, turnPoint.X), Math.max(endPoint.X, turnPoint.X),
                Math.min(endPoint.Y, turnPoint.Y), Math.max(endPoint.Y, turnPoint.Y), world);
    }

    private void amendWalls(TETile[][] world) {
        for (int x = turnPoint.X - 1; x <= turnPoint.X + 1; x++) {
            for (int y = turnPoint.Y - 1; y <= turnPoint.Y + 1; y++) {
                if (world[x][y] == Tileset.NOTHING) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }
}
