package movers;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Class to represent red ghost (Blinky)
 */
public class RedGhost extends Ghost implements Runnable{
    public RedGhost(int width, int height, Player player) {
        super(width, height, player);
        setIcon("imgs/ghost_red.png");
        setTile(15, 13);
        dir = Direction.WEST;
    }

    /**
     * Function move for Blinky
     */
    @Override
    public void move() {
        updateTarget();
        super.move();
    }

    /**
     * Updates target for Blinky
     */
    @Override
    protected void updateTarget() {
        if (inHouse) {
            setTarget(new int[]{13, 11});
            checkHome();
        }
        else
            setTarget(player.getTile());
    }

    /**
     * Updates Blinky's target for scared mode
     */
    @Override
    protected void updateScaredModeTarget() {
        setScaredTarget(new int[]{0, MAP_WIDTH - 1});
    }
    /**
     * Paints ghost on the screen with proper rotation.
     * @param g2d graphics 2D
     * @param posX x position
     * @param posY y position
     */
    @Override
    public void paint(Graphics2D g2d, int posX, int posY) {

        transform = new AffineTransform();
        transform.rotate(0.0, posX+(double) icon.getIconWidth()/2, posY+(double) icon.getIconHeight()/2);
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }
    @Override
    public void run() {
        move();
    }
}
