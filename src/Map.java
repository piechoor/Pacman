import javax.swing.*;
import java.awt.*;

public class Map extends JComponent {

    public Map() {}

    private void paintSquare(Graphics2D g2d, int posX, int posY) {
        g2d.setColor(new Color(0,72,251));
        g2d.fillRect(posX, posY, 30, 30);
    }
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // sets map's color
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 400, 400);

        paintSquare(g2d, 70,20);
        g2d.dispose();

    }
}
