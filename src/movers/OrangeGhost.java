package movers;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * class represents orange ghost(Clyde)
 */
public class OrangeGhost extends Ghost{
    public OrangeGhost(int width, int height, Player player) {
        super(width, height, player);
        setIcon("imgs/ghost_orange.png");
        setTile(11, 13);
        dir = Direction.WEST;
    }

    /**
     * Function move for Clyde
     */
    @Override
    public void move() {
        updateTarget();
        super.move();
    }

    /**
     * Updates target for Clyde
     */
    @Override
    protected void updateTarget() {
        if (inHouse) {
            setTarget(new int[]{13, 11});
            checkHome();
        }
        else {
            if (getDistance(player.getTile(), this.getTile()) > 64) {
                setTarget(player.getTile());
            }
            else {
                setTarget(new int[]{0, MAP_HEIGHT -1});
            }
        }
    }

    /**
     * Updates Clyde's target for scared mode
     */
    @Override
    protected void updateScaredModeTarget() {
        setScaredTarget(new int[]{0, MAP_HEIGHT - 1});
    }
    /**
     * Paints ghost on the screen with proper rotation.
     * @param g2d graphics 2D
     * @param posX x position
     * @param posY y position
     */
    @Override
    public void paint(Graphics2D g2d, int posX, int posY) {

        /*double angle = switch (dir) {  //sets correct angle for the chosen direction
            case NORTH -> -Math.PI/2;
            case WEST -> Math.PI;
            case SOUTH -> Math.PI/2;
            case EAST -> 0.0;
        };  */      // transforms icon on the map
        transform = new AffineTransform();
        transform.rotate(0.0, posX+(double) icon.getIconWidth()/2, posY+(double) icon.getIconHeight()/2);
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }

}