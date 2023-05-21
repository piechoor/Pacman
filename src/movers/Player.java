package movers;


import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Class represents pacman - player's character
 */
public class Player extends Mover {
    public Player() {
        dir = Direction.WEST;
        tileX = 3;
        tileY = 1;
        setIcon("imgs/pacman_icon.png");
    }

    /**
     * Paints player on the screen with proper rotation.
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

    /**
     * Sets player's direction.
     * @param d direction
     */
    public void setDirection(Direction d) {dir = d;}

    /**
     * @return Player's direction
     */
    public Direction getDirection() {return dir;}

}
