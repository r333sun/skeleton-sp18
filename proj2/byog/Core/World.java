package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World implements Serializable {
    private final Random r;

    private static final long serialVersionUID = 902349732423L;

    private int height;
    private int width;

    private List<Room> rooms;
    private List<Road> roads;

    private TETile[][] world;

//    private Player player;

//    public Player getPlayer() {
//        return player;
//    }
//
//    public void setPlayer(Player player) {
//        this.player = player;
//    }

    public World(int seed, int width, int height) {
        this.r = new Random(seed);
        this.height = height;
        this.width = width;
        world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        generateRooms();
        drawRooms();
//        rooms.forEach(room -> System.out.println(room));
        generateRoads();
        drawRoads();
//        generatePlayer();
//        drawPlayer();
    }

    private void generateRooms() {
        rooms = new ArrayList<>();
        int roomNum = RandomUtils.uniform(r, 6, 8);
//        int roomNum = 7;
        int totalSize = this.width * this.height;
        int currentSize = 0;
        while ((currentSize < totalSize / 7) || (rooms.size() < roomNum)) {
            int roomWidth = this.width / 6;
            int roomHeight = this.height / 2;
            Point startPoint = new Point(RandomUtils.uniform(r, width), RandomUtils.uniform(r, height));
            int randomWidth = RandomUtils.uniform(r, 3, roomWidth);
            int randomHeight = RandomUtils.uniform(r, 3, roomHeight);
            Room tmp = new Room(startPoint, randomWidth, randomHeight);
            if (tmp.isEligible(this.width, this.height) && tmp.canAdd(rooms)) {
                rooms.add(tmp);
                currentSize += randomHeight * randomWidth;
            }
        }
    }

    private void drawRooms() {
        rooms.forEach(room -> room.draw(world));
    }

    private void generateRoads() {
        roads = new ArrayList<>();
        for (int i = 1; i < rooms.size(); i++) {
            roads.add(rooms.get(i - 1).getRoadTo(rooms.get(i), r, world));
        }
        Room room = rooms.get(0);
        for (int i = 2; i < rooms.size(); i++) {
            if (!room.isStraightTo(rooms.get(i))) {
                roads.add(new LRoad(room, rooms.get(i), this.r, world));
            }
        }
    }

    private void drawRoads() {
        roads.forEach(road -> {
            if (road != null) {
                road.draw(world);
            }
        });
//        roads.forEach(road -> System.out.println(road));
    }

//    private void generatePlayer() {
//        int x = RandomUtils.uniform(r, width);
//        System.out.println(111);
//        int y = RandomUtils.uniform(r, height);
//        while (world[x][y] != Tileset.FLOOR) {
//            x = RandomUtils.uniform(r, width);
//            y = RandomUtils.uniform(r, height);
//        }
//        this.player = new Player(x, y, world);
//    }
//
//    private void drawPlayer() {
//        player.draw();
//    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Road> getRoad() {
        return roads;
    }

    public void setRoad(List<Road> roads) {
        this.roads = roads;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public void setWorld(TETile[][] world) {
        this.world = world;
    }
}
