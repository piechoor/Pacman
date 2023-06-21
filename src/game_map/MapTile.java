package game_map;

import java.awt.*;

/**
 * Defines map object along with necessary operating methods
 */
public class MapTile {

    private final Color color;
    private final int width, height;
    private final Graphics2D g2d;

    /**
     * Creates map tile with given parameters
     * @param objColor color of the rectangle
     * @param objWidth tile width
     * @param objHeight tile height
     * @param graphics graphics used to tile displaying
     */
    public MapTile(Color objColor, int objWidth, int objHeight, Graphics2D graphics) {

        color = objColor;
        width = objWidth;
        height = objHeight;
        g2d = graphics;
    }

    /**
     * Paints object onto the map. Coordinates start in the left-upper edge.
     * @param x x coordinate
     * @param y y coordinate
     * @param centre if object should be centered (only for food now)
     */
    public void paintTile(int x, int y, boolean centre) {
        if (centre) {  //for food
            x += width * 2.5;
            y += height * 2.5;
        }
        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);
    }
}
