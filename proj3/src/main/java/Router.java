import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {

    private static PriorityQueue<Distance> fringe;
    private static HashSet<Long> marked;
    private static Best best;

    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     *
     * @param g       The graph to use.
     * @param stlon   The longitude of the start location.
     * @param stlat   The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        long source = g.closest(stlon, stlat);
        long target = g.closest(destlon, destlat);
        List<Long> result = new ArrayList<>();
        marked = new HashSet<>();
        fringe = new PriorityQueue<>();
        best = new Best();
        fringe.add(new Distance(source, 0));
        best.put(source, 0.0, null);
        while (!fringe.isEmpty()) {
            Distance d = fringe.remove();
            Long v = d.node;
            if (v.equals(target)) {
                break;
            }
            if (!marked.contains(v)) {
                marked.add(v);
                for (Long w : g.adjacent(v)) {
                    if (!best.contains(w) || best.getDistance(v) + g.distance(v, w) < best.getDistance(w)) {
                        best.put(w, best.getDistance(v) + g.distance(v, w), v);
                        fringe.add(new Distance(
                                w, best.getDistance(v) + g.distance(v, w) + g.distance(w, target)
                        ));
                    }
                }
            }
        }
        result.add(0, target);
        Long next = best.getParent(target);
        while (next != null) {
            result.add(0, next);
            next = best.getParent(next);
        }
        return result; // FIXME
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     *
     * @param g     The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> result = new ArrayList<>();
        for (int i = 0; i < route.size() - 1; i++) {
            double bearing = g.bearing(route.get(i), route.get(i + 1));

        }
        return null; // FIXME
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /**
         * Integer constants representing directions.
         */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;


        /**
         * Number of directions supported.
         */
        public static final int NUM_DIRECTIONS = 8;

        /**
         * A mapping of integer values to directions.
         */
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /**
         * Default name for an unknown way.
         */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /**
         * The direction a given NavigationDirection represents.
         */
        int direction;
        /**
         * The name of the way I represent.
         */
        String way;
        /**
         * The distance along this way I represent.
         */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         *
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                        && way.equals(((NavigationDirection) o).way)
                        && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }

    }

    private static class Distance implements Comparable<Distance> {
        private Long node;
        private double distance;

        public Distance(Long node, double distance) {
            this.node = node;
            this.distance = distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public Long getNode() {
            return node;
        }

        @Override
        public int compareTo(Distance o) {
            if (o.distance == distance) {
                return 0;
            }
            if (distance >= o.distance) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private static class Best {
        HashMap<Long, Double> distMap = new HashMap<>();
        HashMap<Long, Long> parentNode = new HashMap<>();

        public void put(Long s, double distance, Long parent) {
            distMap.put(s, distance);
            parentNode.put(s, parent);
        }

        public Long getParent(Long key) {
            return parentNode.get(key);
        }

        public Double getDistance(Long key) {
            return distMap.get(key);
        }

        public boolean contains(Long key) {
            return distMap.containsKey(key);
        }
    }
}
