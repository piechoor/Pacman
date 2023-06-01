package movers;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Class represents ghosts - computer's character
 */
public class Ghost extends Mover {
    public Ghost(int width, int height, String color) {
        iconW = width;
        iconH = height;
        dir = Direction.EAST;
        if (color == "red") {
            setIcon("imgs/ghost_red.png");
            setTile(15, 13);
        }
        else if (color == "blue")
        {
            setIcon("imgs/ghost_blue.png");
            setTile(12, 13);
        }
        else if (color == "orange")
        {
            setIcon("imgs/ghost_orange.png");
            setTile(12, 14);
        }
        else if (color == "pink")
        {
            setIcon("imgs/ghost_pink.png");
            setTile(15, 14);
        }
    }

    /**
     * Paints ghost on the screen with proper rotation.
     * @param g2d graphics 2D
     * @param posX x position
     * @param posY y position
     */
    @Override
    public void paint(Graphics2D g2d, int posX, int posY) {

        double angle = switch (dir) {  //sets correct angle for the chosen direction
            case NORTH -> -Math.PI/2;
            case WEST -> Math.PI;
            case SOUTH -> Math.PI/2;
            case EAST -> 0.0;
        };

        // transforms icon on the map
        transform = new AffineTransform();
        transform.rotate(angle, posX+(double) icon.getIconWidth()/2, posY+(double) icon.getIconHeight()/2);
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }
}