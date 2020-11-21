import java.util.Arrays;

/**
 * @author
 * @create 2020-11-21 12:56
 */
public class NBody {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        String canvas = "images\\starfield.jpg";

//        StdDraw.setScale(-radius, radius);
//        StdDraw.clear();
//        StdDraw.picture(0, 0, canvas);
//        for (Planet p : planets) {
//            p.draw();
//        }

        StdDraw.enableDoubleBuffering();

        double time = 0;
        while (time <= T) {

            StdDraw.setScale(-radius, radius);
            StdDraw.clear();
            StdDraw.picture(0, 0, canvas);

            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];


            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt,xForces[i],yForces[i]);
                planets[i].draw();
            }


            StdDraw.show();
            StdDraw.pause(10);

            time += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }

    public static double readRadius(String path){
        In in = new In(path);

        int count = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String path){
        In in = new In(path);

        int count = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[count];
        for (int i = 0; i < count; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass,img);
        }
        return planets;
    }
}
