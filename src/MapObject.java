import java.awt.*;

public class MapObject {
    private final Color color;
    private final int width, height;
    private final Graphics2D g2d;

    public MapObject(Color objColor, int objWidth, int objHeight, Graphics2D graphics) {
        color = objColor;
        width = objWidth;
        height = objHeight;
        g2d = graphics;
    }

    public void paintObject(int x, int y, boolean centre) {
        if (centre) {
            x += width * 2.5;
            y += height * 2.5;
        }
        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);
    }
}
