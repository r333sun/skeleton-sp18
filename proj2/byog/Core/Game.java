package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

        World world;
        String path = "./proj2world.ser";
        int seed = 0;
        StdDraw.setCanvasSize(WIDTH,HEIGHT);
        String input = "0";
        StdDraw.text(10,10,"123");
        if (input.equals("l")) {
            world = loadWorld(path);
        } else if (input.endsWith(":q")) {
            seed = getSeed(input.substring(0,input.length()-2));
            world = new World(seed, WIDTH, HEIGHT);
            saveWorld(path,world);
        } else {
            seed = getSeed(input);
            world = new World(seed, WIDTH, HEIGHT);
        }

        ter.initialize(world.getWidth(), world.getHeight());
        startGame(world);
        StdDraw.enableDoubleBuffering();
        while(true){
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if(key == 'a'){
                world.getPlayer().left();
            }
            if(key == 's'){
                world.getPlayer().down();
            }
            if(key == 'd'){
                world.getPlayer().right();
            }
            if(key == 'w'){
                world.getPlayer().up();
            }
            if(key == 'n'){
                world = new World(++seed,world.getWidth(),world.getHeight());
            }
            startGame(world);
            if(key == 'q'){
                System.exit(0);
            }
        }
//        return world.getWorld();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        World world;
        String path = "./proj2world.ser";
        int seed = 0;
        if (input.equals("l")) {
            world = loadWorld(path);
        } else if (input.endsWith(":q")) {
            seed = getSeed(input.substring(0,input.length()-2));
            world = new World(seed, WIDTH, HEIGHT);
            saveWorld(path,world);
        } else {
            seed = getSeed(input);
            world = new World(seed, WIDTH, HEIGHT);
        }

        ter.initialize(world.getWidth(), world.getHeight());
        startGame(world);
        StdDraw.enableDoubleBuffering();
        while(true){
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            if(key == 'a'){
                world.getPlayer().left();
                startGame(world);
            }
            if(key == 's'){
                world.getPlayer().down();
                startGame(world);
            }
            if(key == 'd'){
                world.getPlayer().right();
                startGame(world);
            }
            if(key == 'w'){
                world.getPlayer().up();
                startGame(world);
            }
            if(key == 'n'){
                world = new World(++seed,world.getWidth(),world.getHeight());
                startGame(world);
            }
            if(key == 'q'){
//                System.exit(0);
                break;
            }
        }
        return world.getWorld();
    }


    private int getSeed(String input){
        int seed  = 0;
        char[] arr = input.toCharArray();
        for(Character c: arr){
            seed += (int) c;
        }
        return seed;
    }
    private void startGame(World world) {

        ter.renderFrame(world.getWorld());
    }

    private World loadWorld(String path){
        File f = new File(path);
        World world;
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                world = (World) os.readObject();
                os.close();
                return world;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return new World(0,WIDTH,HEIGHT);
    }

    private void saveWorld(String path, World world){
        File f = new File(path);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(world);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

}
