import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private final double d0_res = Math.abs(MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     * <p>
     * {lrlon=-122.2104604264636, ullon=-122.30410170759153, w=1085.0, h=566.0, ullat=37.870213571328854, lrlat=37.8318576119893}
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double lrlon = params.get("lrlon"), ullon = params.get("ullon"), width = params.get("w"),
                lrlat = params.get("lrlat"), ullat = params.get("ullat");
        if (!validateCo(ullon, ullat, lrlon, lrlat)) {
            results.put("query_success", false);
        } else {
            double asked_res = Math.abs(lrlon - ullon) / width;
            int depth = Math.min(7, (int) Math.ceil(log2(d0_res / asked_res)));
            double X_res = Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON) / Math.pow(2, depth);
            double Y_res = Math.abs(MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / Math.pow(2, depth);
            int startX = getX(ullon, depth), endX = getX(lrlon, depth),
                    startY = getY(ullat, depth), endY = getY(lrlat, depth);
            results.put("raster_ul_lon", MapServer.ROOT_ULLON + X_res * startX);
            results.put("raster_ul_lat", MapServer.ROOT_ULLAT - Y_res * startY);
            results.put("raster_lr_lon", MapServer.ROOT_ULLON + X_res * (endX + 1));
            results.put("raster_lr_lat", MapServer.ROOT_ULLAT - Y_res * (endY + 1));
            results.put("query_success", true);
            results.put("depth", depth);
            String[][] render_grid = getRender_grid(startX, endX, startY, endY, depth);
            results.put("render_grid", render_grid);
            System.out.println(results);
        }
        return results;
    }

    private boolean validateCo(double ullon, double ullat, double lrlon, double lrlat) {
        if(lrlon <= MapServer.ROOT_ULLON){
            return false;
        }
        if(ullon >= MapServer.ROOT_LRLON){
            return false;
        }
        if(lrlat >= MapServer.ROOT_ULLAT){
            return false;
        }
        if(ullat <= MapServer.ROOT_LRLAT){
            return false;
        }
        return true;
    }

    private String[][] getRender_grid(int startX, int endX, int startY, int endY, int depth) {
        String name = "d" + depth + "_";
        int col = endX - startX + 1;
        int row = endY - startY + 1;
        String[][] results = new String[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                results[i][j] = name + "x" + (startX + j) + "_y" + (startY + i) + ".png";
            }
        }
        return results;
    }

    private int getX(double lon, int depth) {
        int total = (int) Math.pow(2, depth);
        if (lon <= MapServer.ROOT_ULLON) {
            return 0;
        }
        if (lon >= MapServer.ROOT_LRLON) {
            return total - 1;
        }
        double gap = Math.abs(MapServer.ROOT_ULLON - MapServer.ROOT_LRLON) / total;
        double start = MapServer.ROOT_ULLON;
        for (int count = 0; count < total; count++) {
            if (lon >= start && lon <= start + gap) {
                return count;
            }
            start += gap;
        }
        return -1;
    }

    private int getY(double lat, int depth) {
        int total = (int) Math.pow(2, depth);
        if (lat >= MapServer.ROOT_ULLAT) {
            return 0;
        }
        if (lat <= MapServer.ROOT_LRLAT) {
            return total - 1;
        }
        double gap = Math.abs(MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / total;
        double start = MapServer.ROOT_ULLAT;
        for (int count = 0; count < total; count++) {
            if (lat <= start && lat >= start - gap) {
                return count;
            }
            start -= gap;
        }
        return -1;
    }

    private static double log2(double num) {
        return Math.log(num) / Math.log(2);
    }

}
