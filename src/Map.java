import javax.swing.*;
import java.awt.*;

public class Map extends JComponent {

    public Map() {}

    private void paintSquare(Graphics2D g2d, int posX, int posY) {
        g2d.setColor(new Color(65, 53, 233));
        g2d.fillRect(posX, posY, 20, 20);
    }
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // sets map's color
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 400, 400);

        MapObject food = new MapObject(Color.orange, 19, g2d);

        for (int i = 1; i < 17; i++) {
            for (int j = 1; j < 17; j++) {
                if (i == 1 | i == 16 | j == 1 | j == 16)
                    food.paintObject( 10 + 20 * i, 20 * j);
            }
        }

        g2d.dispose();

    }
}
