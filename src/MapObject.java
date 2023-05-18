import java.awt.*;

public class MapObject {
    private Color color;
    private int width;
    Graphics2D g2d;

    public MapObject(Color objColor, int objWidth, Graphics2D graphics) {
        color = objColor;
        width = objWidth;
        g2d = graphics;
    }

    public void paintObject(int x, int y) {
        g2d.setColor(color);
        g2d.fillRect(x, y, width, width);
    }
}
